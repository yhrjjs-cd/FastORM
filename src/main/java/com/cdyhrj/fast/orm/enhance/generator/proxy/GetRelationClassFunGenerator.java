package com.cdyhrj.fast.orm.enhance.generator.proxy;

import com.cdyhrj.fast.orm.annotation.ManyToMany;
import com.cdyhrj.fast.orm.annotation.OneToMany;
import com.cdyhrj.fast.orm.annotation.OneToOne;
import org.springframework.javapoet.MethodSpec;
import org.springframework.util.ReflectionUtils;

import javax.lang.model.element.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * getRelationClass 生成器
 *
 * @author huangqi
 */

public class GetRelationClassFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("getRelationClass")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "relation")
                .returns(Class.class);

        ReflectionUtils.doWithFields(classOfT, field -> {
            methodSpecBuilder.beginControlFlow("if (relation.equals($S))", field.getName());

            Class<?> clazz = field.getType();
            if (List.class.isAssignableFrom(clazz)) {
                Type gType = field.getGenericType();

                ParameterizedType pType = (ParameterizedType) gType;
                Type[] tArgs = pType.getActualTypeArguments();
                methodSpecBuilder.addStatement("return $T.class", tArgs[0]);
            } else {
                methodSpecBuilder.addStatement("return $T.class", clazz);
            }
            methodSpecBuilder.endControlFlow();
        }, field -> field.isAnnotationPresent(OneToOne.class) ||
                field.isAnnotationPresent(OneToMany.class) ||
                field.isAnnotationPresent(ManyToMany.class));

        methodSpecBuilder.addStatement("return null");

        return methodSpecBuilder.build().toString();
    }
}
