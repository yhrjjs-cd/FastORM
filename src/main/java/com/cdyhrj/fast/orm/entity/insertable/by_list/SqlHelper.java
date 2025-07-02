package com.cdyhrj.fast.orm.entity.insertable.by_list;

import com.cdyhrj.fast.orm.api.entity.FieldNameSpec;
import com.cdyhrj.fast.orm.api.entity.FieldNameType;
import com.cdyhrj.fast.orm.entity.Entity;
import com.cdyhrj.fast.orm.entity.EntityProxy;

import java.util.StringJoiner;

public class SqlHelper {
    public static String generateInsertSqlText(EntityProxy entityProxy, Entity entity) {
        FieldNameSpec[] insertFields = getEntityInsertFields(entityProxy, entity);

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

    private static FieldNameSpec[] getEntityInsertFields(EntityProxy entityProxy, Entity entity) {
        return entityProxy.getAllFieldInfo()
                .stream()
                .filter(fieldNameSpec -> isFieldToInsertEntity(fieldNameSpec, entityProxy, entity))
                .toArray(FieldNameSpec[]::new);
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

    private static boolean isFieldToInsertEntity(
            FieldNameSpec fieldNameSpec,
            EntityProxy entityProxy,
            Entity entity) {
        if (fieldNameSpec.readonly()) {
            return false;
        }

        String fieldName = fieldNameSpec.fieldName();

        if (fieldNameSpec.type() == FieldNameType.Id) {
            return false;
        }

        if (isSystemUpdateFieldNames(entity, fieldName)) {
            return false;
        }


//        if (isFieldWithDefaultValue(peerEntity, fieldName)) {
//            return true;
//        }

//        if (Objects.nonNull(inserter.getWithColumns())) {
//            return inserter.getWithColumns().contains(fieldName);
//        }
//
//        if (Objects.nonNull(inserter.getIgnoreColumns())) {
//            return !inserter.getIgnoreColumns().contains(fieldName);
//        }
        return true;
    }

    /**
     * 是否系统更新字段
     *
     * @param fieldName 字段名
     * @return 是否系统更新字段则返回<code>true</code>
     */
    private static boolean isSystemUpdateFieldNames(Entity entity, String fieldName) {
        return entity.fieldIsOnlyForUpdate(fieldName);
    }
}
