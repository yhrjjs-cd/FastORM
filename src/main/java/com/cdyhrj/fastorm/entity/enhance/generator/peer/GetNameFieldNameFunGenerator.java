package com.cdyhrj.fastorm.entity.enhance.generator.peer;

import com.cdyhrj.fastorm.annotation.Name;
import com.cdyhrj.fastorm.util.EntityUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Field;

/**
 * getNameFieldName 生成器
 *
 * @author huangqi
 */

public class GetNameFieldNameFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        Field[] nameFields = FieldUtils.getFieldsWithAnnotation(classOfT, Name.class);
        if (nameFields.length == 0) {
            // 构造一个空函数
            return MethodSpec.methodBuilder("getNameFieldName")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(String.class)
                    .addStatement("return $S", "")
                    .build()
                    .toString();
        }

        Field nameField = nameFields[0];
        String name = EntityUtils.getFieldName(nameField);

        MethodSpec methodSpec = MethodSpec.methodBuilder("getNameFieldName")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addStatement("return $S", name)
                .build();

        return methodSpec.toString();
    }
}
