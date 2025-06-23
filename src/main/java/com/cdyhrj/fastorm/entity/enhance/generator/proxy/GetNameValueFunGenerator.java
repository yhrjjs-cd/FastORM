package com.cdyhrj.fastorm.entity.enhance.generator.proxy;

import com.cdyhrj.fastorm.annotation.Name;
import com.cdyhrj.fastorm.entity.Entity;
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
        Field[] idFields = FieldUtils.getFieldsWithAnnotation(classOfT, Name.class);
        if (idFields.length == 0) {
            // 构造一个空函数
            return MethodSpec.methodBuilder("getNameValue")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(String.class)
                    .addParameter(Entity.class, "entity")
                    .addStatement("return \"\"")
                    .build()
                    .toString();
        }

        Field nameField = idFields[0];
        String name = nameField.getName();

        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("getNameValue")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addParameter(Entity.class, "entity");

        methodSpecBuilder.addStatement("return ((%s)entity).get%s()".formatted(classOfT.getName(),
                StringUtils.capitalize(name)));

        return methodSpecBuilder.build().toString();
    }
}
