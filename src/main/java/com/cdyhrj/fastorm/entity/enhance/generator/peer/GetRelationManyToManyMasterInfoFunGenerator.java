package com.cdyhrj.fastorm.entity.enhance.generator.peer;

import com.cdyhrj.fastorm.annotation.ManyToMany;
import com.cdyhrj.fastorm.entity.meta.ManyToManyMasterInfo;
import org.springframework.javapoet.MethodSpec;
import org.springframework.util.ReflectionUtils;

import javax.lang.model.element.Modifier;

/**
 * getRelationManyToManyMeta 生成器
 *
 * @author huangqi
 */

public class GetRelationManyToManyMasterInfoFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("getManyToManyRelationMasterInfoClass")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "relation")
                .returns(ManyToManyMasterInfo.class);
        ReflectionUtils.doWithFields(classOfT, field -> {
            ManyToMany manyToMany = field.getAnnotation(ManyToMany.class);
            methodSpecBuilder.beginControlFlow("if (relation.equals($S))", field.getName());
            methodSpecBuilder.addStatement("return new $T().relationClass($T.class)", ManyToManyMasterInfo.class,
                    manyToMany.relationClass());
            methodSpecBuilder.endControlFlow();

        }, field -> field.isAnnotationPresent(ManyToMany.class));

        methodSpecBuilder.addStatement("return null");

        return methodSpecBuilder.build().toString();
    }
}
