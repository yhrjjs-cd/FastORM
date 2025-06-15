package com.cdyhrj.fastorm.entity.enhance.generator.peer;

import com.cdyhrj.cloud.sqlclient.annotation.Default;
import com.cdyhrj.cloud.sqlclient.annotation.enums.DefaultValueType;
import com.cdyhrj.cloud.sqlclient.annotation.enums.OperationType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * getDefaultValueMap 生成器
 *
 * @author huangqi
 */

public class GetDefaultValueMapFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("getDefaultValueMap")
                                                         .addModifiers(Modifier.PUBLIC)
                                                         .addParameter(OperationType.class, "operationType")
                                                         .returns(Map.class)
                                                         .addStatement("$T result = new $T()", Map.class,
                                                                 HashMap.class);

        // 设置Default
        Field[] defaultFields = FieldUtils.getFieldsWithAnnotation(classOfT, Default.class);
        for (Field field : defaultFields) {
            Default defaultAnnotation = field.getAnnotation(Default.class);

            methodSpecBuilder.beginControlFlow("if (operationType == OperationType.$L)",
                    defaultAnnotation.operationType());

            if (defaultAnnotation.valueType() == DefaultValueType.NOW) {
                methodSpecBuilder.addStatement("result.put($S, new Date())", field.getName());
            } else if (defaultAnnotation.valueType() == DefaultValueType.UUID) {
                methodSpecBuilder.addStatement("result.put($S, IdUtils.newGuid())",
                        StringUtils.capitalize(field.getName()));
            } else if (defaultAnnotation.valueType() == DefaultValueType.STRING) {
                methodSpecBuilder.addStatement("result.put($S, $S)", field.getName(), defaultAnnotation.extra());
            }

            methodSpecBuilder.endControlFlow();
        }

        methodSpecBuilder.addStatement("return result");

        return methodSpecBuilder.build().toString();
    }
}
