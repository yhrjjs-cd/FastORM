package com.cdyhrj.fast.orm.api.lambda;


import com.cdyhrj.fast.orm.annotation.Column;
import com.cdyhrj.fast.orm.annotation.ManyToMany;
import com.cdyhrj.fast.orm.annotation.OneToMany;
import com.cdyhrj.fast.orm.annotation.OneToOne;
import com.cdyhrj.fast.orm.api.lambda.exception.ColumnAnnotationRequiredException;
import com.cdyhrj.fast.orm.api.lambda.exception.ImpossibleException;
import com.cdyhrj.fast.orm.api.lambda.exception.NotLambdaSyntheticClassException;
import com.cdyhrj.fast.orm.api.meta.FieldInfo;
import com.cdyhrj.fast.orm.entity.Entity;
import com.cdyhrj.fast.orm.util.NameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Lambda 表达式
 *
 * @author huangqi
 */
public class LambdaQuery {
    private LambdaQuery() {

    }

    /**
     * 字段映射
     */
    private static final Map<String, FieldInfo> COLUMN_CACHE_MAP = new ConcurrentHashMap<>();


    /**
     * @param lambda 需要解析的 lambda 对象
     * @return 返回解析后的字段名称
     */
    public static <E extends Entity> FieldInfo resolve(PropFn<E, ?> lambda) {
        Class<?> clazz = lambda.getClass();
        String className = clazz.getName();

        return Optional.ofNullable(COLUMN_CACHE_MAP.get(className)).orElseGet(() -> getFieldInfo(className, lambda));
    }

    /**
     * @param lambda 需要解析的 lambda 对象
     * @return 返回解析后的字段名称
     */
    @SafeVarargs
    public static FieldInfo[] resolves(PropFn<? extends Entity, ?>... lambda) {
        return Arrays.stream(lambda).map(LambdaQuery::resolve).toArray(FieldInfo[]::new);
    }

    /**
     * 获取字段信息
     *
     * @param className 类名
     * @param lambda    lambda表达式
     * @return 属性名
     */
    private static <E extends Entity> FieldInfo getFieldInfo(String className, PropFn<E, ?> lambda) {
        if (!lambda.getClass().isSynthetic()) {
            throw new NotLambdaSyntheticClassException();
        }
        try {
            Method writeReplace = lambda.getClass().getDeclaredMethod("writeReplace");
            ReflectionUtils.makeAccessible(writeReplace);
            SerializedLambda serializedLambda = (SerializedLambda) writeReplace.invoke(lambda);

            String propertyName = methodNameToPropertyName(serializedLambda.getImplMethodName());
            // 如果使用serializedLambda.getImplClass(),如果有继承关系，获取的是方法所在的实际实现类
            String entityClassRaw = serializedLambda.getInstantiatedMethodType();
            String entityClassName = entityClassRaw.substring(2, entityClassRaw.indexOf(")") - 1).replace("/", ".");

            @SuppressWarnings("unchecked")
            Class<E> entityClass = (Class<E>) Class.forName(entityClassName);

            return COLUMN_CACHE_MAP.computeIfAbsent(className, s -> propertyNameToFieldName(propertyName, entityClass));
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException |
                 ClassNotFoundException e) {
            e.printStackTrace();
            throw new ImpossibleException();
        }
    }

    public static <E extends Entity> FieldInfo propertyNameToFieldName(String propertyName, Class<E> entityClass) {
        Field field = FieldUtils.getField(entityClass, propertyName, true);
        String name = getFieldName(field, propertyName);

        return FieldInfo.of(name, field.getType(), entityClass);
    }

    private static String getFieldName(Field field, String propertyName) {
        if (field.isAnnotationPresent(OneToMany.class)
                || field.isAnnotationPresent(OneToOne.class)
                || field.isAnnotationPresent(ManyToMany.class)) {
            return field.getName();
        }

        Column column = field.getAnnotation(Column.class);
        if (Objects.isNull(column)) {
            throw new ColumnAnnotationRequiredException(propertyName);
        }

        String columnName = propertyName;
        if (column.hump()) {
            columnName = NameUtils.toUnderScoreCase(propertyName);
        } else {
            if (StringUtils.isNotEmpty(column.value())) {
                columnName = column.value();
            }
        }

        return columnName;
    }


    /**
     * 方法名转属性名
     *
     * @return
     */
    @SuppressWarnings("all")
    public static String methodNameToPropertyName(String methodName) {
        if (methodName.startsWith("is")) {
            methodName = methodName.substring(2);
        } else if (methodName.startsWith("get") || methodName.startsWith("set")) {
            methodName = methodName.substring(3);
        } else {
            throw new RuntimeException("方法名'" + methodName + "'不是以 'is','get','set' 开始的！");
        }

        methodName = methodName.substring(0, 1).toLowerCase(Locale.ENGLISH) + methodName.substring(1);

        return methodName;
    }
}
