package com.cdyhrj.fastorm.entity.enhance.generator.proxy;

import com.cdyhrj.fastorm.api.entity.FieldNameSpec;
import com.cdyhrj.fastorm.util.EntityUtils;
import org.springframework.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * getAllFieldInfo 生成器
 *
 * @author huangqi
 */
public class GetAllFieldInfoFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("getAllFieldInfo")
                .addModifiers(Modifier.PUBLIC)
                .returns(List.class)
                .addStatement("$T result = new $T()", List.class, ArrayList.class);

        List<FieldNameSpec> fields = EntityUtils.getObjectFieldNames(classOfT);
        for (FieldNameSpec field : fields) {
            methodSpecBuilder.addStatement("result.add(FieldNameSpec.of().propertyName($S).fieldName($S).type(FieldNameType.$L).readonly($L))",
                    field.propertyName(), field.fieldName(), field.type(), field.readonly());
        }

        methodSpecBuilder.addStatement("return result");

        return methodSpecBuilder.build().toString();
    }
}
