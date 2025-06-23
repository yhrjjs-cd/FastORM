package com.cdyhrj.fastorm.entity.enhance.generator.proxy;

import com.cdyhrj.fastorm.annotation.Id;
import com.cdyhrj.fastorm.exception.OnlyOneIdAnnotationRequiredException;
import com.cdyhrj.fastorm.util.EntityUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Field;

/**
 * getIdFieldName 生成器
 *
 * @author huangqi
 */

public class GetIdFieldNameFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        Field[] idFields = FieldUtils.getFieldsWithAnnotation(classOfT, Id.class);
        if (idFields.length == 0) {
            // 构造一个空函数
            return MethodSpec.methodBuilder("getIdFieldName")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(String.class)
                    .addStatement("throw new $T($T.class)", OnlyOneIdAnnotationRequiredException.class, classOfT)
                    .build()
                    .toString();
        }

        Field idField = idFields[0];
        String name = EntityUtils.getFieldName(idField);

        MethodSpec methodSpec = MethodSpec.methodBuilder("getIdFieldName")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addStatement("return $S", name)
                .build();

        return methodSpec.toString();
    }
}
