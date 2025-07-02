package com.cdyhrj.fast.orm.enhance.generator.proxy;

import com.cdyhrj.fast.orm.entity.Entity;
import org.springframework.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;

/**
 * wewEntity 生成器
 *
 * @author huangqi
 */

public class NewEntityFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        MethodSpec methodSpec = MethodSpec.methodBuilder("newEntity")
                .addModifiers(Modifier.PUBLIC)
                .returns(Entity.class)
                .addStatement("return new $L()", classOfT.getName())
                .build();

        return methodSpec.toString();
    }
}
