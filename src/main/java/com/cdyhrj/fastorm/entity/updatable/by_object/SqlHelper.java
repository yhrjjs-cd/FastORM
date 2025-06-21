package com.cdyhrj.fastorm.entity.updatable.by_object;

import com.cdyhrj.fastorm.api.entity.FieldNameSpec;
import com.cdyhrj.fastorm.api.entity.FieldNameType;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.EntityProxy;

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

//        if (Objects.nonNull(this.id)) {
//            String idField = peerEntity.getIdFieldName();
//            joiner.add("%s=:%s".formatted(idField, idField));
//        } else if (Objects.nonNull(this.name)) {
//            String nameField = peerEntity.getNameFieldName();
//            joiner.add("%s=:%s".formatted(nameField, nameField));
//        } else {
//            Objects.requireNonNull(this.condition, "请设置更新条件");
//            joiner.add(this.condition.toParametricExpression(peerEntity.propertyToFieldName()));
//        }

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
