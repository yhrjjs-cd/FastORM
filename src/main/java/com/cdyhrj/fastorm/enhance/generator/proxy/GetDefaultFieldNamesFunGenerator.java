package com.cdyhrj.fastorm.enhance.generator.proxy;

import com.cdyhrj.fastorm.annotation.Column;
import com.cdyhrj.fastorm.annotation.Default;
import com.cdyhrj.fastorm.annotation.enums.OperationType;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * getDefaultFieldNames 生成器
 *
 * @author huangqi
 */

public class GetDefaultFieldNamesFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("getDefaultFieldNames")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(OperationType.class, "operationType")
                .returns(List.class)
                .addStatement("$T result = new $T()", List.class,
                        ArrayList.class);
        Field[] defaultFields = FieldUtils.getFieldsWithAnnotation(classOfT, Default.class);
        for (Field field : defaultFields) {
            Column column = field.getAnnotation(Column.class);
            String fieldName = Column.Utils.getEntityFieldName(column, field.getName());
            Default defaultAnnotation = field.getAnnotation(Default.class);

            methodSpecBuilder.beginControlFlow("if (operationType == OperationType.$L)",
                    defaultAnnotation.operationType());

            methodSpecBuilder.addStatement("result.add($S)", fieldName);
            methodSpecBuilder.endControlFlow();
        }

        methodSpecBuilder.addStatement("return result");

        return methodSpecBuilder.build().toString();
    }
}
