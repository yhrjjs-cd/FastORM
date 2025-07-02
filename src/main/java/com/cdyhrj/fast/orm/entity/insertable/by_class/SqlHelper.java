package com.cdyhrj.fast.orm.entity.insertable.by_class;

import com.cdyhrj.fast.orm.api.entity.FieldNameSpec;
import com.cdyhrj.fast.orm.api.entity.FieldNameType;
import com.cdyhrj.fast.orm.entity.EntityProxy;

import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class SqlHelper {
    public static String generateInsertSqlText(EntityProxy entityProxy, Map<String, Object> paramMap) {
        FieldNameSpec[] insertFields = getClassInsertFields(entityProxy, paramMap);
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("INSERT INTO")
                .add(entityProxy.getTableName())
                .add("(")
                .add(getInsertFieldSelect(insertFields))
                .add(") VALUES (")
                .add(getInsertValueParamSelect(insertFields))
                .add(")");

        return joiner.toString();
    }

    private static FieldNameSpec[] getClassInsertFields(EntityProxy entityProxy, Map<String, Object> paramMap) {
        Set<String> fieldNames = paramMap.keySet();

        return entityProxy.getAllFieldInfo()
                .stream()
                .filter(fieldNameSpec -> isFieldToInsertClass(fieldNameSpec, fieldNames))
                .toArray(FieldNameSpec[]::new);
    }

    private static boolean isFieldToInsertClass(FieldNameSpec fieldNameSpec, Set<String> fieldNames) {
        if (fieldNameSpec.type() == FieldNameType.Id) {
            return false;
        }

        return fieldNames.contains(fieldNameSpec.fieldName());
    }

    private static CharSequence getInsertFieldSelect(FieldNameSpec[] insertFields) {
        StringJoiner fields = new StringJoiner(",");

        for (FieldNameSpec fieldNameSpec : insertFields) {
            fields.add(fieldNameSpec.fieldName());
        }

        return fields.toString();
    }

    private static CharSequence getInsertValueParamSelect(FieldNameSpec[] insertFields) {
        StringJoiner fields = new StringJoiner(",");

        for (FieldNameSpec fieldNameSpec : insertFields) {
            fields.add(String.format(":%s", fieldNameSpec.fieldName()));
        }

        return fields.toString();
    }
}
