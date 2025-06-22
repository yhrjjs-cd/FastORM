package com.cdyhrj.fastorm.entity.deletable.by_class;

import com.cdyhrj.fastorm.entity.EntityProxy;

import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

public class SqlHelper {
    public static String generateDeleteSqlText(EntityProxy entityProxy, Map<String, Object> paramMap, EntityClassDeletable<?> entityClassDeletable) {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("DELETE FROM")
                .add(entityProxy.getTableName())
                .add("WHERE");

        if (Objects.nonNull(entityClassDeletable.where())) {
            joiner.add(entityClassDeletable.where().toNoAliasSql());
        }

        return joiner.toString();
    }
}
