package com.cdyhrj.fastorm.entity.insertable.by_object;

import com.cdyhrj.fastorm.api.entity.FieldNameSpec;
import com.cdyhrj.fastorm.api.entity.FieldNameType;
import com.cdyhrj.fastorm.entity.EntityProxy;

import java.util.Set;
import java.util.StringJoiner;

public class SqlHelper {
    public static String generateInsertSqlText(EntityProxy entityProxy) {
        FieldNameSpec[] insertFields = getEntityInsertFields(entityProxy);

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

    private static FieldNameSpec[] getEntityInsertFields(EntityProxy entityProxy) {
        return entityProxy.getAllFieldInfo()
                .stream()
                .filter(fieldNameSpec -> isFieldToInsertEntity(fieldNameSpec, entityProxy))
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
            EntityProxy entityProxy) {
        if (fieldNameSpec.readonly()) {
            return false;
        }

        String fieldName = fieldNameSpec.fieldName();

        if (isSystemUpdateFieldNames(fieldName)) {
            return false;
        }

        if (fieldNameSpec.type() == FieldNameType.Id) {
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
     * 系统更新字段
     */
    public static final Set<String> SYSTEM_UPDATE_FIELDS = Set.of("updated_by", "updated_by_name", "updated_at");

    /**
     * 是否系统更新字段
     *
     * @param fieldName 字段名
     * @return 是否系统更新字段则返回<code>true</code>
     */
    private static boolean isSystemUpdateFieldNames(String fieldName) {
        return SYSTEM_UPDATE_FIELDS.contains(fieldName);
    }
}
