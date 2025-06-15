package com.cdyhrj.fastorm.entity.enhance.generator.peer;

import com.cdyhrj.cloud.sqlclient.annotation.Id;
import com.cdyhrj.cloud.sqlclient.util.EntityUtils;
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
        Field[] nameFields = FieldUtils.getFieldsWithAnnotation(classOfT, Id.class);
        if (nameFields.length == 0) {
            // 构造一个空函数
            return MethodSpec.methodBuilder("getIdFieldName")
                             .addModifiers(Modifier.PUBLIC)
                             .returns(String.class)
                             .addStatement("return $S", "")
                             .build()
                             .toString();
        }

        Field nameField = nameFields[0];
        String name = EntityUtils.getFieldName(nameField);

        MethodSpec methodSpec = MethodSpec.methodBuilder("getIdFieldName")
                                          .addModifiers(Modifier.PUBLIC)
                                          .returns(String.class)
                                          .addStatement("return $S", name)
                                          .build();

        return methodSpec.toString();
    }
}
