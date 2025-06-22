package com.cdyhrj.fastorm.entity.updatable.by_class;

import com.cdyhrj.fastorm.api.entity.FieldNameSpec;
import com.cdyhrj.fastorm.api.entity.FieldNameType;
import com.cdyhrj.fastorm.entity.EntityProxy;

import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

public class SqlHelper {
    public static String generateUpdateSqlText(EntityProxy entityProxy, Map<String, Object> paramMap, EntityClassUpdatable<?> entityClassUpdatable) {
        FieldNameSpec[] fieldNameSpecs = getUpdateFields(entityProxy, paramMap);

        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("UPDATE")
                .add(entityProxy.getTableName())
                .add("SET")
                .add(getUpdateFieldsSet(fieldNameSpecs))
                .add("WHERE");

        if (Objects.nonNull(entityClassUpdatable.where())) {
            joiner.add(entityClassUpdatable.where().toNoAliasSql());
        }

        return joiner.toString();
    }

    private static FieldNameSpec[] getUpdateFields(EntityProxy entityProxy, Map<String, Object> paramMap) {
        return entityProxy.getAllFieldInfo().stream().filter(fieldNameSpec -> {
//            if (isSystemInsertFieldNames(fieldNameSpec.fieldName())) {
//                return false;
//            }

            if (fieldNameSpec.type() == FieldNameType.Id || fieldNameSpec.type() == FieldNameType.Name) {
                return false;
            }

//            if (Objects.nonNull(withColumns)) {
//                return withColumns.contains(fieldNameSpec.fieldName());
//            }
//
//            if (Objects.nonNull(ignoreColumns)) {
//                return !ignoreColumns.contains(fieldNameSpec.fieldName());
//            }

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
