package com.cdyhrj.fastorm.entity.enhance.generator.peer;

import com.cdyhrj.cloud.sqlclient.Entity;
import com.cdyhrj.cloud.sqlclient.annotation.Id;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Field;

/**
 * getIdValue 生成器
 *
 * @author huangqi
 */

public class GetIdFieldValueFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        Field[] idFields = FieldUtils.getFieldsWithAnnotation(classOfT, Id.class);
        if (idFields.length == 0) {
            // 构造一个空函数
            return MethodSpec.methodBuilder("getIdFieldValue")
                             .addModifiers(Modifier.PUBLIC)
                             .returns(Long.class)
                             .addParameter(Entity.class, "entity")
                             .addStatement("return Long.valueOf(0L)")
                             .build()
                             .toString();
        }

        Field idField = idFields[0];
        String name = idField.getName();

        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("getIdFieldValue")
                                                         .addModifiers(Modifier.PUBLIC)
                                                         .returns(Long.class)
                                                         .addParameter(Entity.class, "entity");

        if (idField.getType() == long.class) {
            methodSpecBuilder.addStatement("return Long.valueOf(((%s)entity).get%s())".formatted(classOfT.getName(),
                    StringUtils.capitalize(name)));
        } else if (idField.getType() == Long.class) {
            methodSpecBuilder.addStatement(
                    "return ((%s)entity).get%s()".formatted(classOfT.getName(), StringUtils.capitalize(name)));
        } else {
            // 其它类型返回0L
            methodSpecBuilder.addStatement("return Long.valueOf(0L)");
        }

        return methodSpecBuilder.build().toString();
    }
}
