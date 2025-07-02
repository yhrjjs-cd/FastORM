package com.cdyhrj.fast.orm.entity.updatable.by_object;

import com.cdyhrj.fast.orm.api.entity.FieldNameSpec;
import com.cdyhrj.fast.orm.api.entity.FieldNameType;
import com.cdyhrj.fast.orm.entity.Entity;
import com.cdyhrj.fast.orm.entity.EntityProxy;
import com.cdyhrj.fast.orm.exception.KeyValueRequiredException;

import java.util.Objects;
import java.util.StringJoiner;

public class SqlHelper {
    public static String generateUpdateSqlTextWithEntity(EntityProxy entityProxy, Entity entity) {
        FieldNameSpec[] fieldNameSpecs = getUpdateFields(entityProxy, entity);

        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("UPDATE")
                .add(entityProxy.getTableName())
                .add("SET")
                .add(toUpdateFields(fieldNameSpecs))
                .add("WHERE");

        // 判断Id
        Long id = entityProxy.getIdValue(entity);
        if (Objects.nonNull(id) && id > 0) {
            String idField = entityProxy.getIdFieldName();
            joiner.add("%s=:%s".formatted(idField, idField));
        } else {
            // 判断Name
            String name = entityProxy.getNameValue(entity);
            if (Objects.nonNull(name)) {
                String nameField = entityProxy.getNameFieldName();
                joiner.add("%s=:%s".formatted(nameField, nameField));
            } else {
                throw new KeyValueRequiredException(entity.getClass());
            }
        }

        return joiner.toString();
    }

    private static FieldNameSpec[] getUpdateFields(EntityProxy entityProxy, Entity entity) {
        return entityProxy.getAllFieldInfo().stream().filter(fieldNameSpec -> {
            if (isSystemInsertFieldNames(entity, fieldNameSpec.fieldName())) {
                return false;
            }

            if (fieldNameSpec.type() == FieldNameType.Id || fieldNameSpec.type() == FieldNameType.Name) {
                return false;
            }

            return true;
        }).toArray(FieldNameSpec[]::new);
    }

    private static CharSequence toUpdateFields(FieldNameSpec[] fieldNameSpecs) {
        StringJoiner updateFields = new StringJoiner(",");

        for (FieldNameSpec fieldNameSpec : fieldNameSpecs) {
            updateFields.add("%s=:%s".formatted(fieldNameSpec.fieldName(), fieldNameSpec.fieldName()));
        }

        return updateFields.toString();
    }

    /**
     * 是否系统新增字段
     *
     * @param fieldName 字段名
     * @return 是否系统更新字段则返回<code>true</code>
     */
    private static boolean isSystemInsertFieldNames(Entity entity, String fieldName) {
        return entity.fieldIsOnlyForInsert(fieldName);
    }
}
