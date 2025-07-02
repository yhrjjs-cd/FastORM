package com.cdyhrj.fastorm.enhance.generator.proxy;

import com.cdyhrj.fastorm.annotation.Name;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.exception.NameAnnotationRequiredException;
import com.cdyhrj.fastorm.exception.StringFieldTypeRequiredException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Field;

/**
 * getNameValue 生成器
 *
 * @author huangqi
 */

public class GetNameValueFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        Field[] nameFields = FieldUtils.getFieldsWithAnnotation(classOfT, Name.class);
        if (nameFields.length == 0) {
            // 构造一个空函数
            return MethodSpec.methodBuilder("getNameValue")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(String.class)
                    .addParameter(Entity.class, "entity")
                    .addStatement("throw new $T($T.class)", NameAnnotationRequiredException.class, classOfT)
                    .build()
                    .toString();
        }

        Field nameField = nameFields[0];
        String name = nameField.getName();
        if (nameField.getType() != String.class) {
            throw new StringFieldTypeRequiredException(name, classOfT);
        }

        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("getNameValue")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addParameter(Entity.class, "entity");

        methodSpecBuilder.addStatement("return ((%s)entity).get%s()".formatted(classOfT.getName(),
                StringUtils.capitalize(name)));

        return methodSpecBuilder.build().toString();
    }
}
