package com.cdyhrj.fastorm.entity.deletable.by_object;

import com.cdyhrj.fastorm.entity.EntityProxy;
import com.cdyhrj.fastorm.entity.deletable.by_class.EntityClassDeletable;

import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

public class SqlHelper {
    public static String generateDeleteSqlText(EntityProxy entityProxy, Map<String, Object> paramMap, EntityDeletable<?> entityDeletable) {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("DELETE FROM")
                .add(entityProxy.getTableName())
                .add("WHERE");

        if (Objects.nonNull(entityDeletable.where())) {
            joiner.add(entityDeletable.where().toNoAliasSql());
        }

        return joiner.toString();
    }
}
