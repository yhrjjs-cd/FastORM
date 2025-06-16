package com.cdyhrj.fastorm.entity.enhance.generator.peer;

import com.cdyhrj.fastorm.annotation.Column;
import com.cdyhrj.fastorm.annotation.IdOneToOne;
import com.cdyhrj.fastorm.entity.OneToOneMeta;
import lombok.Data;
import org.springframework.javapoet.MethodSpec;
import org.springframework.util.ReflectionUtils;

import javax.lang.model.element.Modifier;


/**
 * getRelationOneToOneMeta 生成器
 *
 * @author huangqi
 */

public class GetRelationOneToOneMetaFunGenerator implements FunGenerator {

    @Override
    public String generate(Class<?> classOfT) {
        AddStatus addStatus = new AddStatus();
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("getRelationOneToOneMeta")
                .addModifiers(Modifier.PUBLIC)
                .returns(OneToOneMeta.class);
        ReflectionUtils.doWithFields(classOfT, field -> {
            if (addStatus.hasAdded) {
                throw new RuntimeException("@IdOneToOne 注解重复.");
            }
            Column column = field.getAnnotation(Column.class);
            String propertyName = field.getName();
            String fieldName = Column.Utils.getEntityFieldName(column, propertyName);

            methodSpecBuilder.addStatement("return new $T().propertyName($S).fieldName($S)", OneToOneMeta.class,
                    propertyName, fieldName);

            addStatus.hasAdded = true;
        }, field -> field.isAnnotationPresent(IdOneToOne.class));

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
