package com.cdyhrj.fastorm.enhance.generator.proxy;

import com.cdyhrj.fastorm.annotation.Table;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;
import java.util.Objects;

/**
 * GgtTableName 生成器
 *
 * @author huangqi
 */

public class GetTableNameFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        Table table = AnnotationUtils.findAnnotation(classOfT, Table.class);
        Objects.requireNonNull(table, "table name is null.");

        MethodSpec methodSpec = MethodSpec.methodBuilder("getTableName")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addStatement("return $S", table.name())
                .build();

        return methodSpec.toString();
    }
}
