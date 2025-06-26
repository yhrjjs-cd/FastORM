package com.cdyhrj.fastorm.entity.deletable.by_class;

import com.cdyhrj.fastorm.entity.EntityProxy;

import java.util.StringJoiner;

public class SqlHelper {
    public static String generateUpdateSqlTextByPlaceholder(EntityProxy entityProxy, String placeholderField) {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("DELETE FROM")
                .add(entityProxy.getTableName())
                .add("WHERE")
                .add(placeholderField)
                .add("= :%s".formatted(EntityClassDeletable.PARAM_HOLDER_NAME));

        return joiner.toString();
    }

    public static String generateUpdateSqlTextByWhere(EntityProxy entityProxy, Where<?> where) {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("DELETE FROM")
                .add(entityProxy.getTableName())
                .add("WHERE")
                .add(where.toNoAliasSql());

        return joiner.toString();
    }
}
