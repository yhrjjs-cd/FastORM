package com.cdyhrj.fastorm.entity.enhance.generator.peer;

import com.cdyhrj.fastorm.annotation.OneToMany;
import com.cdyhrj.fastorm.entity.Entity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.javapoet.MethodSpec;
import org.springframework.util.ReflectionUtils;

import javax.lang.model.element.Modifier;
import java.util.List;

/**
 * getRelationOneToManyValue 生成器
 *
 * @author huangqi
 */

public class GetRelationOneToManyValueFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("getRelationOneToManyValue")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Entity.class, "entity")
                .addParameter(String.class, "relation")
                .returns(List.class);

        ReflectionUtils.doWithFields(classOfT, field -> {
            methodSpecBuilder.beginControlFlow("if (relation.equals($S))", field.getName());
            methodSpecBuilder.addStatement("return (($T)entity).get$L()", classOfT, StringUtils.capitalize(field.getName()));
            methodSpecBuilder.endControlFlow();
        }, field -> field.isAnnotationPresent(OneToMany.class));

        methodSpecBuilder.addStatement("return null");

        return methodSpecBuilder.build().toString();
    }
}
