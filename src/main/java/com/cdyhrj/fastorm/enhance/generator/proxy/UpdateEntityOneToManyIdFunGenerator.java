package com.cdyhrj.fastorm.enhance.generator.proxy;

import com.cdyhrj.fastorm.annotation.IdOneToMany;
import com.cdyhrj.fastorm.entity.Entity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.javapoet.MethodSpec;
import org.springframework.util.ReflectionUtils;

import javax.lang.model.element.Modifier;

/**
 * updateEntityOneToManyName 生成器
 *
 * @author huangqi
 */

public class UpdateEntityOneToManyIdFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("updateEntityOneToManyName")
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
            } else {
                throw new RuntimeException("Only support Long type");
            }

        }, field -> field.isAnnotationPresent(IdOneToMany.class));

        return methodSpecBuilder.build().toString();
    }
}
