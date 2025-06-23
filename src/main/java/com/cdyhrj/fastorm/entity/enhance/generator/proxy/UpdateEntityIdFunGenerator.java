package com.cdyhrj.fastorm.entity.enhance.generator.proxy;

import com.cdyhrj.fastorm.annotation.Id;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.exception.NameAnnotationRequiredException;
import com.cdyhrj.fastorm.exception.OnlyOneIdAnnotationRequiredException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Field;

/**
 * updateEntityId 生成器
 *
 * @author huangqi
 */

public class UpdateEntityIdFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        Field[] idFields = FieldUtils.getFieldsWithAnnotation(classOfT, Id.class);

        if (idFields.length == 0) {
            // 构造一个空函数
            return MethodSpec.methodBuilder("updateEntityId")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(Entity.class, "entity")
                    .addParameter(Long.class, "idValue")
                    .returns(void.class)
                    .addStatement("throw new $T($T.class)", OnlyOneIdAnnotationRequiredException.class, classOfT)
                    .build()
                    .toString();
        }

        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("updateEntityId")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Entity.class, "entity")
                .addParameter(Long.class, "idValue")
                .returns(void.class);

        if (idFields.length == 1) {
            methodSpecBuilder.addStatement("$T current = ($T)entity", classOfT, classOfT);
            Field nameField = idFields[0];
            Class<?> type = nameField.getType();

            if (type == long.class) {
                methodSpecBuilder.addStatement("current.set$L(idValue.longValue())", StringUtils.capitalize(nameField.getName()));
            } else if (type == Long.class) {
                methodSpecBuilder.addStatement("current.set$L(idValue)", StringUtils.capitalize(nameField.getName()));
            } else if (type == int.class) {
                methodSpecBuilder.addStatement("current.set$L(idValue.intValue())", StringUtils.capitalize(nameField.getName()));
            } else if (type == Integer.class) {
                methodSpecBuilder.addStatement("current.set$L(Integer.valueOf(idValue.intValue()))", StringUtils.capitalize(nameField.getName()));
            } else {
                throw new RuntimeException("field type is not supported");
            }
        } else {
            methodSpecBuilder.addStatement("throw new $T($T.class)", OnlyOneIdAnnotationRequiredException.class, classOfT);
        }

        return methodSpecBuilder.build().toString();
    }
}
