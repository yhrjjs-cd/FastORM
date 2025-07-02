package com.cdyhrj.fast.orm.enhance.generator.proxy;

import com.cdyhrj.fast.orm.annotation.Id;
import com.cdyhrj.fast.orm.entity.Entity;
import com.cdyhrj.fast.orm.exception.OnlyOneIdAnnotationRequiredException;
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

public class GetIdValueFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        Field[] idFields = FieldUtils.getFieldsWithAnnotation(classOfT, Id.class);
        if (idFields.length == 0) {
            // 构造一个空函数
            return MethodSpec.methodBuilder("getIdValue")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(Long.class)
                    .addParameter(Entity.class, "entity")
                    .addStatement("throw new $T($T.class)", OnlyOneIdAnnotationRequiredException.class, classOfT)
                    .build()
                    .toString();
        }

        Field idField = idFields[0];
        String name = idField.getName();

        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("getIdValue")
                .addModifiers(Modifier.PUBLIC)
                .returns(Long.class)
                .addParameter(Entity.class, "entity");

        if (idField.getType() == long.class) {
            methodSpecBuilder.addStatement("return Long.valueOf(((%s)entity).get%s())".formatted(classOfT.getName(),
                    StringUtils.capitalize(name)));
        } else if (idField.getType() == Long.class) {
            methodSpecBuilder.addStatement(
                    "return ((%s)entity).get%s()".formatted(classOfT.getName(), StringUtils.capitalize(name)));
        } else if (idField.getType() == int.class) {
            methodSpecBuilder.addStatement("return Long.valueOf((long)((%s)entity).get%s())".formatted(classOfT.getName(), StringUtils.capitalize(name)));
        } else if (idField.getType() == Integer.class) {
            methodSpecBuilder.addStatement("return Long.valueOf(((%s)entity).get%s().longValue())".formatted(classOfT.getName(),
                    StringUtils.capitalize(name)));
        } else {
            // 其它类型返回0L
            methodSpecBuilder.addStatement("return new RuntimeException('不支持的@Id 字段类型(long|Long|int|Integer)')");
        }

        return methodSpecBuilder.build().toString();
    }
}
