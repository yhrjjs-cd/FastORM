package com.cdyhrj.fast.orm.enhance.generator.proxy;

import com.cdyhrj.fast.orm.annotation.Column;
import com.cdyhrj.fast.orm.annotation.IdManyToManyLeft;
import com.cdyhrj.fast.orm.annotation.IdManyToManyRight;
import com.cdyhrj.fast.orm.entity.meta.ManyToManyMeta;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Field;

/**
 * getRelationManyToManyMeta 生成器
 *
 * @author huangqi
 */

public class GetRelationManyToManyMetaFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("getRelationManyToManyMeta")
                .addModifiers(Modifier.PUBLIC)
                .returns(ManyToManyMeta.class);

        Field[] left = FieldUtils.getFieldsWithAnnotation(classOfT, IdManyToManyLeft.class);
        Field[] right = FieldUtils.getFieldsWithAnnotation(classOfT, IdManyToManyRight.class);

        if (left.length == 1 && right.length == 1) {
            String manyToManyLeft = getFieldName(left[0]);
            String manyToManyRight = getFieldName(right[0]);

            methodSpecBuilder.addStatement("return new $T().leftFieldName($S).rightFieldName($S)", ManyToManyMeta.class,
                    manyToManyLeft, manyToManyRight);
        } else {
            methodSpecBuilder.addStatement("return null");
        }

        return methodSpecBuilder.build().toString();
    }

    private String getFieldName(Field field) {
        Column column = field.getAnnotation(Column.class);

        return Column.Utils.getEntityFieldName(column, field.getName());
    }
}
