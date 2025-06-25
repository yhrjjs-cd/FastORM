package com.cdyhrj.fastorm.entity.updatable.by_class;

import com.cdyhrj.fastorm.api.entity.FieldNameSpec;
import com.cdyhrj.fastorm.entity.EntityProxy;

import java.util.Map;
import java.util.StringJoiner;


public class SqlHelper {
    public static String generateUpdateSqlTextByPlaceholder(EntityProxy entityProxy, Map<String, Object> paramMap, String placeholderField) {
        FieldNameSpec[] fieldNameSpecs = getUpdateFields(entityProxy, paramMap);

        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("UPDATE")
                .add(entityProxy.getTableName())
                .add("SET")
                .add(getUpdateFieldsSet(fieldNameSpecs))
                .add("WHERE")
                .add(placeholderField)
                .add("= :%s".formatted(EntityClassUpdatable.PARAM_HOLDER_NAME));

        return joiner.toString();
    }

    public static String generateUpdateSqlTextByWhere(EntityProxy entityProxy, Map<String, Object> paramMap, Where<?> where) {
        FieldNameSpec[] fieldNameSpecs = getUpdateFields(entityProxy, paramMap);

        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("UPDATE")
                .add(entityProxy.getTableName())
                .add("SET")
                .add(getUpdateFieldsSet(fieldNameSpecs))
                .add("WHERE")
                .add(where.toNoAliasSql());

        return joiner.toString();
    }

    private static FieldNameSpec[] getUpdateFields(EntityProxy entityProxy, Map<String, Object> paramMap) {
        return entityProxy.getAllFieldInfo().stream().filter(fieldNameSpec -> {
            return paramMap.containsKey(fieldNameSpec.fieldName());
        }).toArray(FieldNameSpec[]::new);
    }

    private static CharSequence getUpdateFieldsSet(FieldNameSpec[] fieldNameSpecs) {
        StringJoiner updateFields = new StringJoiner(",");

        for (FieldNameSpec fieldNameSpec : fieldNameSpecs) {
            updateFields.add("%s=:%s".formatted(fieldNameSpec.fieldName(), fieldNameSpec.fieldName()));
        }

        return updateFields.toString();
    }
}
