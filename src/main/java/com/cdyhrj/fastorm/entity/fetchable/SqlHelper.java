package com.cdyhrj.fastorm.entity.fetchable;

import com.cdyhrj.fastorm.entity.EntityProxy;

import java.util.Objects;
import java.util.StringJoiner;


public class SqlHelper {
    public static String generateUpdateSqlTextByPlaceholder(EntityProxy entityProxy, String placeholderField) {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("SELECT * FROM")
                .add(entityProxy.getTableName())
                .add("WHERE")
                .add(placeholderField)
                .add("= :%s".formatted(EntityByClassFetchable.PARAM_HOLDER_NAME));

        return joiner.toString();
    }

    public static String generateUpdateSqlTextByWhere(EntityProxy entityProxy, EntityByClassFetchable<?> fetchable) {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("SELECT * FROM")
                .add(entityProxy.getTableName());

        if (Objects.nonNull(fetchable.getWhere())) {
            joiner.add("WHERE").add(fetchable.getWhere().toNoAliasSql());
        }

        if (Objects.nonNull(fetchable.getOrderBy())) {
            joiner.add(fetchable.getOrderBy().toSql());
        }

        return joiner.toString();
    }
}
