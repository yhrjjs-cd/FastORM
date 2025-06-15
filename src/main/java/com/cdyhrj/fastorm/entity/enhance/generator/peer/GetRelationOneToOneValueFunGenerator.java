package com.cdyhrj.fastorm.entity.enhance.generator.peer;

import com.cdyhrj.cloud.sqlclient.Entity;
import com.cdyhrj.cloud.sqlclient.annotation.OneToOne;
import org.apache.commons.lang3.StringUtils;
import org.springframework.javapoet.MethodSpec;
import org.springframework.util.ReflectionUtils;

import javax.lang.model.element.Modifier;

/**
 * getRelationOneToOneValue 生成器
 *
 * @author huangqi
 */

public class GetRelationOneToOneValueFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("getRelationOneToOneValue")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Entity.class, "entity")
                .addParameter(String.class, "relation")
                .returns(Entity.class);

        ReflectionUtils.doWithFields(classOfT, field -> {
            methodSpecBuilder.beginControlFlow("if (relation.equals($S))", field.getName());
            methodSpecBuilder.addStatement("return (($T)entity).get%s()".formatted(StringUtils.capitalize(field.getName())), classOfT);
            methodSpecBuilder.endControlFlow();
        }, field -> field.isAnnotationPresent(OneToOne.class));

        methodSpecBuilder.addStatement("return null");

        return methodSpecBuilder.build().toString();
    }
}
