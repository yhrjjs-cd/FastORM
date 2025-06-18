package com.cdyhrj.fastorm.entity.enhance.generator.proxy;

import com.cdyhrj.fastorm.annotation.Default;
import com.cdyhrj.fastorm.annotation.enums.DefaultValueType;
import com.cdyhrj.fastorm.annotation.enums.OperationType;
import com.cdyhrj.fastorm.entity.Entity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Field;

/**
 * updateEntityWithDefaultValue 生成器
 *
 * @author huangqi
 */

public class UpdateEntityWithDefaultValueFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("updateEntityWithDefaultValue")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Entity.class, "entity")
                .addParameter(OperationType.class, "operationType")
                .returns(void.class);

        methodSpecBuilder.addStatement("$T current = ($T)entity", classOfT, classOfT);

        // 设置Default
        Field[] defaultFields = FieldUtils.getFieldsWithAnnotation(classOfT, Default.class);
        for (Field field : defaultFields) {
            Default defaultAnnotation = field.getAnnotation(Default.class);

            methodSpecBuilder.beginControlFlow("if (operationType == OperationType.$L)",
                    defaultAnnotation.operationType());

            if (defaultAnnotation.valueType() == DefaultValueType.NOW) {
                methodSpecBuilder.addStatement("current.set$L(new Date())", StringUtils.capitalize(field.getName()));
            } else if (defaultAnnotation.valueType() == DefaultValueType.UUID) {
                methodSpecBuilder.addStatement("current.set$L(IdUtils.newGuid())",
                        StringUtils.capitalize(field.getName()));
            } else if (defaultAnnotation.valueType() == DefaultValueType.STRING) {
                methodSpecBuilder.addStatement("current.set$L($S)", StringUtils.capitalize(field.getName()),
                        defaultAnnotation.extra());
            }

            methodSpecBuilder.endControlFlow();
        }

        return methodSpecBuilder.build().toString();
    }
}
