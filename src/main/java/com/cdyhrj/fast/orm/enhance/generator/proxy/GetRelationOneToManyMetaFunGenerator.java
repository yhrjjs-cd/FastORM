package com.cdyhrj.fast.orm.enhance.generator.proxy;

import com.cdyhrj.fast.orm.annotation.Column;
import com.cdyhrj.fast.orm.annotation.IdOneToMany;
import com.cdyhrj.fast.orm.entity.meta.OneToManyMeta;
import lombok.Data;
import org.springframework.javapoet.MethodSpec;
import org.springframework.util.ReflectionUtils;

import javax.lang.model.element.Modifier;

/**
 * getRelationOneToManyMeta 生成器
 *
 * @author huangqi
 */

public class GetRelationOneToManyMetaFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        AddStatus addStatus = new AddStatus();
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("getRelationOneToManyMeta")
                .addModifiers(Modifier.PUBLIC)
                .returns(OneToManyMeta.class);
        ReflectionUtils.doWithFields(classOfT, field -> {
            if (addStatus.hasAdded) {
                throw new RuntimeException("@IdOneToMany 注解重复.");
            }
            Column column = field.getAnnotation(Column.class);
            String propertyName = field.getName();
            String fieldName = Column.Utils.getEntityFieldName(column, propertyName);

            methodSpecBuilder.addStatement("return new $T().propertyName($S).fieldName($S)", OneToManyMeta.class,
                    propertyName, fieldName);

            addStatus.hasAdded = true;
        }, field -> field.isAnnotationPresent(IdOneToMany.class));

        if (!addStatus.hasAdded) {
            methodSpecBuilder.addStatement("return null");
        }

        return methodSpecBuilder.build().toString();
    }

    @Data
    private static class AddStatus {
        boolean hasAdded = false;
    }
}
