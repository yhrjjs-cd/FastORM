package com.cdyhrj.fastorm.entity.fetchable;

import com.cdyhrj.fastorm.api.entity.FieldNameSpec;
import com.cdyhrj.fastorm.entity.EntityProxy;
import com.cdyhrj.fastorm.entity.updatable.by_class.EntityClassUpdatable;
import com.cdyhrj.fastorm.entity.updatable.by_class.Where;

import java.util.Map;
import java.util.StringJoiner;


public class SqlHelper {
    public static String generateUpdateSqlTextByPlaceholder(EntityProxy entityProxy, String placeholderField) {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("SELECT * FROM")
                .add(entityProxy.getTableName())
                .add("WHERE")
                .add(placeholderField)
                .add("= :%s".formatted(EntityClassUpdatable.PARAM_HOLDER_NAME));

        return joiner.toString();
    }

    public static String generateUpdateSqlTextByWhere(EntityProxy entityProxy, Where<?> where) {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("SELECT * FROM")
                .add(entityProxy.getTableName())
                .add("WHERE")
                .add(where.toNoAliasSql());

        return joiner.toString();
    }
}
