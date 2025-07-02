package com.cdyhrj.fastorm.enhance.generator.proxy;

import com.cdyhrj.fastorm.annotation.ManyToMany;
import com.cdyhrj.fastorm.annotation.OneToMany;
import com.cdyhrj.fastorm.annotation.OneToOne;
import com.cdyhrj.fastorm.api.enums.RelationType;
import org.springframework.javapoet.MethodSpec;
import org.springframework.util.ReflectionUtils;

import javax.lang.model.element.Modifier;

/**
 * getRelationType 生成器
 *
 * @author huangqi
 */

public class GetRelationTypeFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("getRelationType")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "relation")
                .returns(RelationType.class);

        ReflectionUtils.doWithFields(classOfT, field -> {
            methodSpecBuilder.beginControlFlow("if (relation.equals($S))", field.getName());
            if (field.isAnnotationPresent(OneToOne.class)) {
                methodSpecBuilder.addStatement("return RelationType.OneToOne");
            } else if (field.isAnnotationPresent(OneToMany.class)) {
                methodSpecBuilder.addStatement("return RelationType.OneToMany");
            } else if (field.isAnnotationPresent(ManyToMany.class)) {
                methodSpecBuilder.addStatement("return RelationType.ManyToMany");
            }
            methodSpecBuilder.endControlFlow();
        }, field -> field.isAnnotationPresent(OneToOne.class) ||
                field.isAnnotationPresent(OneToMany.class) ||
                field.isAnnotationPresent(ManyToMany.class));

        methodSpecBuilder.addStatement("throw new RuntimeException(\"Relation '\" + relation + \"' does not exists.\")");

        return methodSpecBuilder.build().toString();
    }
}
