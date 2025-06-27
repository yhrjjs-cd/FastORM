package com.cdyhrj.fastorm.entity.queryable.s;

import com.cdyhrj.fastorm.entity.EntityProxy;
import lombok.extern.slf4j.Slf4j;

import java.util.StringJoiner;

@Slf4j
public class SqlHelper {
    public static String generateUpdateSqlTextByWhere(EntityProxy entityProxy, Where<?> where) {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("SELECT * FROM")
                .add(entityProxy.getTableName())
                .add("WHERE")
                .add(where.toNoAliasSql());

        return joiner.toString();
    }
}
