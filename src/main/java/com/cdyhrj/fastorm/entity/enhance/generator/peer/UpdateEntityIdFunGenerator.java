package com.cdyhrj.fastorm.entity.enhance.generator.peer;

import com.cdyhrj.cloud.sqlclient.Entity;
import com.cdyhrj.cloud.sqlclient.annotation.Id;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Field;

/**
 * updateEntityId 生成器
 *
 * @author huangqi
 */

public class UpdateEntityIdFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("updateEntityId")
                                                         .addModifiers(Modifier.PUBLIC)
                                                         .addParameter(Entity.class, "entity")
                                                         .addParameter(long.class, "idValue")
                                                         .returns(void.class);

        Field[] nameFields = FieldUtils.getFieldsWithAnnotation(classOfT, Id.class);

        if (nameFields.length == 1) {
            methodSpecBuilder.addStatement("$T current = ($T)entity", classOfT, classOfT);
            Field nameField = nameFields[0];
            Class<?> type = nameField.getType();

            if (type == long.class) {
                methodSpecBuilder.addStatement("current.set$L(idValue)", StringUtils.capitalize(nameField.getName()));
            } else if (type == Long.class) {
                methodSpecBuilder.addStatement("current.set$L(Long.valueOf(idValue))", StringUtils.capitalize(nameField.getName()));
            } else {
                throw new RuntimeException("field type is not supported");
            }
        }

        return methodSpecBuilder.build().toString();
    }
}
