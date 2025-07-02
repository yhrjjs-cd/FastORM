package com.cdyhrj.fast.orm.entity.fetchable;

import com.cdyhrj.fast.orm.entity.EntityProxy;
import com.cdyhrj.fast.orm.pager.IPagerProvider;
import com.cdyhrj.fast.orm.pager.Pager;

import java.util.Objects;
import java.util.StringJoiner;


public class SqlHelper {
    public static final Pager PAGER_ONE = Pager.of(1, 1);

    public static String generateUpdateSqlTextByPlaceholder(EntityProxy entityProxy,
                                                            String placeholderField,
                                                            IPagerProvider pagerProvider) {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("SELECT * FROM")
                .add(entityProxy.getTableName())
                .add("WHERE")
                .add(placeholderField)
                .add("= :%s".formatted(EntityByClassFetchable.PARAM_HOLDER_NAME));

        return pagerProvider.withSql(joiner.toString(), PAGER_ONE);
    }

    public static String generateUpdateSqlTextByWhere(EntityProxy entityProxy,
                                                      EntityByClassFetchable<?> fetchable,
                                                      IPagerProvider pagerProvider) {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("SELECT * FROM")
                .add(entityProxy.getTableName());

        if (Objects.nonNull(fetchable.getWhere())) {
            joiner.add("WHERE").add(fetchable.getWhere().toNoAliasSql());
        }

        if (Objects.nonNull(fetchable.getOrderBy())) {
            joiner.add(fetchable.getOrderBy().toSql());
        }

        return pagerProvider.withSql(joiner.toString(), PAGER_ONE);
    }
}
