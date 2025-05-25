//package com.cdyhrj.fastorm.lambda;
//
//
//import org.springframework.util.ReflectionUtils;
//
//import java.lang.invoke.SerializedLambda;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * Lambda 表达式
// *
// * @author huangqi
// */
//public class LambdaColumn {
//    private LambdaColumn() {
//
//    }
//
//    /**
//     * 字段映射
//     */
//    private static final Map<String, FieldInfo> COLUMN_CACHE_MAP = new ConcurrentHashMap<>();
//
//
//    /**
//     * @param lambda 需要解析的 lambda 对象
//     * @return 返回解析后的字段名称
//     */
//    public static FieldInfo resolve(PropFun<?, ?> lambda) {
//        Class<?> clazz = lambda.getClass();
//        String className = clazz.getName();
//
//        return Optional.ofNullable(COLUMN_CACHE_MAP.get(className)).orElseGet(() -> getFieldInfo(className, lambda));
//    }
//
//    /**
//     * @param lambda 需要解析的 lambda 对象
//     * @return 返回解析后的字段名称
//     */
//    public static FieldInfo[] resolves(PropFun... lambda) {
//        return Arrays.stream(lambda).map(LambdaColumn::resolve).toArray(FieldInfo[]::new);
//    }
//
//    /**
//     * 获取字段信息
//     *
//     * @param className 类名
//     * @param lambda    lambda表达式
//     * @return 属性名
//     */
//    private static FieldInfo getFieldInfo(String className, PropFun<?, ?> lambda) {
//        if (!lambda.getClass().isSynthetic()) {
//            throw new NotLambdaSyntheticClassException();
//        }
//        try {
//            Method writeReplace = lambda.getClass().getDeclaredMethod("writeReplace");
//            ReflectionUtils.makeAccessible(writeReplace);
//            SerializedLambda serializedLambda = (SerializedLambda) writeReplace.invoke(lambda);
//
//            String propertyName = methodNameToPropertyName(serializedLambda.getImplMethodName());
//            String tableClassName = serializedLambda.getImplClass().replace("/", ".");
//            Class<?> tableClass = Class.forName(tableClassName);
//
//            return COLUMN_CACHE_MAP.computeIfAbsent(className, s -> propertyNameToFieldName(propertyName, tableClass));
//        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException |
//                 ClassNotFoundException e) {
//            throw new ImpossibleException();
//        }
//    }
//
//    public static FieldInfo propertyNameToFieldName(String propertyName, Class<?> tableClass) {
//        Field field = FieldUtils.getField(tableClass, propertyName, true);
//        String name = getFieldName(field, propertyName);
//
//        return FieldInfo.of(name, field.getType(), tableClass);
//    }
//
//    private static String getFieldName(Field field, String propertyName) {
//        if (field.isAnnotationPresent(OneToMany.class) || field.isAnnotationPresent(OneToOne.class) || field.isAnnotationPresent(ManyToMany.class)) {
//            return field.getName();
//        }
//
//        Column column = field.getAnnotation(Column.class);
//        if (Objects.isNull(column)) {
//            throw new ColumnAnnotationRequiredException(propertyName);
//        }
//
//        String columnName = propertyName;
//        if (column.hump()) {
//            columnName = NameUtils.toUnderScoreCase(propertyName);
//        } else {
//            if (StringUtils.isNotEmpty(column.value())) {
//                columnName = column.value();
//            }
//        }
//
//        return columnName;
//    }
//
//
//    /**
//     * 方法名转属性名
//     *
//     * @return
//     */
//    @SuppressWarnings("all")
//    public static String methodNameToPropertyName(String methodName) {
//        if (methodName.startsWith("is")) {
//            methodName = methodName.substring(2);
//        } else if (methodName.startsWith("get") || methodName.startsWith("set")) {
//            methodName = methodName.substring(3);
//        } else {
//            throw new RuntimeException("方法名'" + methodName + "'不是以 'is','get','set' 开始的！");
//        }
//
//        methodName = methodName.substring(0, 1).toLowerCase(Locale.ENGLISH) + methodName.substring(1);
//
//        return methodName;
//    }
//}
