package com.cdyhrj.fast.orm.enhance.generator.proxy;

import com.cdyhrj.fast.orm.adapter.ValueAdapter;
import com.cdyhrj.fast.orm.annotation.ColDefine;
import com.cdyhrj.fast.orm.annotation.Column;
import com.cdyhrj.fast.orm.entity.Entity;
import com.cdyhrj.fast.orm.util.EntityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.javapoet.CodeBlock;
import org.springframework.javapoet.MethodSpec;
import org.springframework.util.ReflectionUtils;

import javax.lang.model.element.Modifier;
import java.sql.ResultSet;
import java.util.Objects;

/**
 * updateEntity 生成器
 *
 * @author huangqi
 */

public class UpdateEntityFunGenerator implements FunGenerator {
    @Override
    public String generate(Class<?> classOfT) {
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("updateEntity")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Entity.class, "entity")
                .addParameter(int.class, "index")
                .addParameter(String.class, "fieldName")
                .addParameter(ResultSet.class, "rs")
                .returns(void.class)
                .addStatement("$T current = ($T)entity", classOfT, classOfT);

        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder().beginControlFlow("switch (fieldName)");

        ReflectionUtils.doWithFields(classOfT, field -> {
            String fieldName = EntityUtils.getFieldName(field);
            String propertyName = StringUtils.capitalize(field.getName());
            codeBlockBuilder.add("case $S:", fieldName);

            Class<?> dataType = field.getType();

            Class<? extends ValueAdapter> columnAdapterClass = null;
            if (field.isAnnotationPresent(ColDefine.class)) {
                ColDefine colDefine = field.getAnnotation(ColDefine.class);
                Class<? extends ValueAdapter> adaptor = colDefine.adapter();

                if (Objects.nonNull(adaptor)) {
                    columnAdapterClass = adaptor;
                }
            }

            if (Objects.isNull(columnAdapterClass)) {
                if (dataType.isPrimitive()) {
                    codeBlockBuilder.addStatement(
                            "Object v = ValueAdapterFactory.getValueAdapterOfType($T.class).readFromRs(rs,index,$T.class)",
                            dataType, dataType);
                    codeBlockBuilder.add("if (v != null) {");
                    if (dataType == Integer.TYPE) {
                        codeBlockBuilder.addStatement("current.set$L(((java.lang.Integer)v).intValue())", propertyName);
                    } else if (dataType == Long.TYPE) {
                        codeBlockBuilder.addStatement("current.set$L(((java.lang.Long)v).longValue())", propertyName);
                    } else if (dataType == Double.TYPE) {
                        codeBlockBuilder.addStatement("current.set$L(((java.lang.Double)v).doubleValue())",
                                propertyName);
                    } else if (dataType == Boolean.TYPE) {
                        codeBlockBuilder.addStatement("current.set$L(((java.lang.Boolean)v).booleanValue())",
                                propertyName);
                    } else if (dataType == Float.TYPE) {
                        codeBlockBuilder.addStatement("current.set$L(((java.lang.Float)v).floatValue())", propertyName);
                    } else if (dataType == Short.TYPE) {
                        codeBlockBuilder.addStatement("current.set$L(((java.lang.Short)v).shortValue())", propertyName);
                    } else if (dataType == Byte.TYPE) {
                        codeBlockBuilder.addStatement("current.set$L(((java.lang.Byte)v).byteValue())", propertyName);
                    } else if (dataType == Character.TYPE) {
                        codeBlockBuilder.addStatement("current.set$L(((java.lang.Character)v).charValue())",
                                propertyName);
                    }
                    codeBlockBuilder.add("}");
                } else {
                    codeBlockBuilder.addStatement(
                            "current.set$L(($T)ValueAdapterFactory.getValueAdapterOfType($T.class).readFromRs(rs,index,$T.class))",
                            propertyName, dataType, dataType, dataType);
                }
            } else {
                codeBlockBuilder.addStatement(
                        "current.set$L(($T)ValueAdapterFactory.getValueAdapter($T.class).readFromRs(rs,index,$T.class))",
                        propertyName, dataType, columnAdapterClass, dataType);
            }
            codeBlockBuilder.addStatement("break");
        }, field -> field.isAnnotationPresent(Column.class));

        codeBlockBuilder.endControlFlow();
        methodSpecBuilder.addCode(codeBlockBuilder.build());

        return methodSpecBuilder.build().toString();
    }
}
