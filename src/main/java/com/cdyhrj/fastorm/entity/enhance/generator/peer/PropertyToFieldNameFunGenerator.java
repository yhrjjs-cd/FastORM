package com.cdyhrj.fastorm.entity.enhance.generator.peer;

import com.cdyhrj.cloud.sqlclient.api.FieldNameSpec;
import com.cdyhrj.cloud.sqlclient.api.PropertyToFieldNameMap;
import com.cdyhrj.cloud.sqlclient.util.EntityUtils;
import org.springframework.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;
import java.util.List;

/**
 * propertyToFieldName 生成器
 *
 * @author huangqi
 */

public class PropertyToFieldNameFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("propertyToFieldName")
                                                         .addModifiers(Modifier.PUBLIC)
                                                         .returns(PropertyToFieldNameMap.class)
                                                         .addStatement("$T result = new $T()",
                                                                 PropertyToFieldNameMap.class,
                                                                 PropertyToFieldNameMap.class);

        List<FieldNameSpec> fields = EntityUtils.getObjectFieldNames(classOfT);
        for (FieldNameSpec field : fields) {
            if (!field.fieldName().equals(field.propertyName())) {
                methodSpecBuilder.addStatement("result.add($S, $S)", field.propertyName(), field.fieldName());
            }
        }

        methodSpecBuilder.addStatement("return result");

        return methodSpecBuilder.build().toString();
    }
}
