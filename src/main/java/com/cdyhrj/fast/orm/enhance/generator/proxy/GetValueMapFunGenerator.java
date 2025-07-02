package com.cdyhrj.fast.orm.enhance.generator.proxy;

import com.cdyhrj.fast.orm.adapter.ValueAdapter;
import com.cdyhrj.fast.orm.annotation.ColDefine;
import com.cdyhrj.fast.orm.annotation.Column;
import com.cdyhrj.fast.orm.entity.Entity;
import com.cdyhrj.fast.orm.util.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.javapoet.MethodSpec;
import org.springframework.util.ReflectionUtils;

import javax.lang.model.element.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * getValueMap 生成器
 *
 * @author huangqi
 */
@Slf4j
public class GetValueMapFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("getValueMap")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Entity.class, "entity")
                .returns(Map.class)
                .addStatement("$T result = new $T()", Map.class, HashMap.class)
                .addStatement("$T current = ($T)entity", classOfT, classOfT);

        ReflectionUtils.doWithFields(classOfT, field -> {
            Class<? extends ValueAdapter> columnAdapterClass = null;
            if (field.isAnnotationPresent(ColDefine.class)) {
                ColDefine colDefine = field.getAnnotation(ColDefine.class);
                Class<? extends ValueAdapter> adaptor = colDefine.adapter();

                if (Objects.nonNull(adaptor)) {
                    columnAdapterClass = adaptor;
                }
            }

            String propName = field.getName();
            String fieldName = EntityUtils.getFieldName(field);
            if (Objects.isNull(columnAdapterClass)) {
                if (field.getType().isPrimitive()) {
                    if (field.getType() == Boolean.TYPE) {
                        methodSpecBuilder.addStatement("result.put($S, ValueAdapterFactory.getValueAdapterOfType($T.class).transValue($L.valueOf(current.is$L())))",
                                fieldName, field.getType(), getPrimitiveBoxClass(field.getType()), StringUtils.capitalize(propName));
                    } else {
                        methodSpecBuilder.addStatement("result.put($S, ValueAdapterFactory.getValueAdapterOfType($T.class).transValue($L.valueOf(current.get$L())))",
                                fieldName, field.getType(), getPrimitiveBoxClass(field.getType()), StringUtils.capitalize(propName));
                    }
                } else {
                    methodSpecBuilder.addStatement("result.put($S, ValueAdapterFactory.getValueAdapterOfType($T.class).transValue(current.get$L()))",
                            fieldName, field.getType(), StringUtils.capitalize(propName));
                }
            } else {
                methodSpecBuilder.addStatement("result.put($S, ValueAdapterFactory.getValueAdapter($T.class).transValue(current.get$L()))",
                        fieldName, columnAdapterClass, StringUtils.capitalize(propName));
            }


        }, field -> field.isAnnotationPresent(Column.class));

        methodSpecBuilder.addStatement("return result");

        return methodSpecBuilder.build().toString();
    }

    /**
     * 获取基本类对应包装类名
     *
     * @param type 基本类
     * @return 包装类名
     */
    private String getPrimitiveBoxClass(Class<?> type) {
        if (type == Integer.TYPE) {
            return "Integer";
        } else if (type == Long.TYPE) {
            return "Long";
        } else if (type == Double.TYPE) {
            return "Double";
        } else if (type == Boolean.TYPE) {
            return "Boolean";
        } else if (type == Float.TYPE) {
            return "Float";
        } else if (type == Short.TYPE) {
            return "Short";
        } else if (type == Byte.TYPE) {
            return "Byte";
        } else if (type == Character.TYPE) {
            return "Character";
        }

        return "";
    }
}
