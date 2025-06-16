package com.cdyhrj.fastorm.entity.enhance.generator.peer;

import com.cdyhrj.fastorm.annotation.IdOneToOne;
import com.cdyhrj.fastorm.entity.Entity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.javapoet.MethodSpec;
import org.springframework.util.ReflectionUtils;

import javax.lang.model.element.Modifier;

/**
 * updateEntityOneToOneId 生成器
 *
 * @author huangqi
 */

public class UpdateEntityOneToOneIdFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("updateEntityOneToOneId")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Entity.class, "entity")
                .addParameter(long.class, "value")
                .returns(void.class);

        ReflectionUtils.doWithFields(classOfT, field -> {
            if (field.getType() == long.class) {
                methodSpecBuilder.addStatement("(($T)entity).set$L(value)", classOfT,
                        StringUtils.capitalize(field.getName()));
            } else if (field.getType() == Long.class) {
                methodSpecBuilder.addStatement("(($T)entity).set$L(Long.valueOf(value))", classOfT,
                        StringUtils.capitalize(field.getName()));
            }

        }, field -> field.isAnnotationPresent(IdOneToOne.class));

        return methodSpecBuilder.build().toString();
    }
}
