package com.cdyhrj.fastorm.enhance.generator.proxy;

import com.cdyhrj.fastorm.annotation.ManyToMany;
import com.cdyhrj.fastorm.annotation.OneToMany;
import com.cdyhrj.fastorm.annotation.OneToOne;
import org.springframework.javapoet.MethodSpec;
import org.springframework.util.ReflectionUtils;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * getAllRelations 生成器
 *
 * @author huangqi
 */

public class GetAllRelationsFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("getAllRelations")
                                                         .addModifiers(Modifier.PUBLIC)
                                                         .returns(List.class)
                                                         .addStatement("$T result = new $T()", List.class,
                                                                 ArrayList.class);

        ReflectionUtils.doWithFields(classOfT, field -> {
            methodSpecBuilder.addStatement("result.add($S)", field.getName());
        }, field -> field.isAnnotationPresent(OneToOne.class) ||
                field.isAnnotationPresent(OneToMany.class) ||
                field.isAnnotationPresent(ManyToMany.class));

        methodSpecBuilder.addStatement("return result");

        return methodSpecBuilder.build().toString();
    }
}
