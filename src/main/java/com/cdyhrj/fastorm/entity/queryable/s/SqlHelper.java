package com.cdyhrj.fastorm.entity.queryable.s;

import com.cdyhrj.fastorm.entity.EntityProxy;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.StringJoiner;

@Slf4j
public class SqlHelper {
    public static String generateUpdateSqlTextByWhere(EntityProxy entityProxy, EntityByClassQueryable<?> queryable) {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("SELECT * FROM")
                .add(entityProxy.getTableName());

        if (Objects.nonNull(queryable.getWhere())) {
            joiner.add("WHERE").add(queryable.getWhere().toNoAliasSql());
        }

        if (Objects.nonNull(queryable.getOrderBy())) {
            joiner.add(queryable.getOrderBy().toSql());
        }

        return joiner.toString();
    }
}
