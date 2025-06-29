package com.cdyhrj.fastorm.entity.queryablex;

import com.cdyhrj.fastorm.entity.EntityProxy;
import com.cdyhrj.fastorm.entity.context.ToSqlContext;
import com.cdyhrj.fastorm.entity.queryablex.with.With;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Slf4j
public class SqlHelper {
    public static String generateUpdateSqlTextByWhere(EntityProxy entityProxy, EntityByClassQueryableX<?> queryable) {
        StringJoiner joiner = new StringJoiner(" ");
        ToSqlContext<?, ?> context = queryable.getContext();

        if (Objects.nonNull(queryable.getWiths())) {
            joiner.add("WITH");

            joiner.add(queryable.getWiths().stream()
                    .map(With::toSql)
                    .collect(Collectors.joining(",")));
        }

        // SELECT
        joiner.add("SELECT * FROM")
                .add(context.getPrimaryEntity().toSql());

        // JOIN
        joiner.add(queryable.joins().toSql());

        // WHERE
        if (Objects.nonNull(queryable.getWhere())) {
            joiner.add("WHERE").add(queryable.getWhere().toSql());
        }

        if (Objects.nonNull(queryable.getOrderBy())) {
            joiner.add(queryable.getOrderBy().toSql());
        }

        String sqlText = joiner.toString();
        if (Objects.nonNull(queryable.getPager())) {
            return queryable.getPagerProvider().withSql(sqlText, queryable.getPager());
        }

        return sqlText;
    }
}
