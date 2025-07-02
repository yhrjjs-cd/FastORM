package com.cdyhrj.fast.orm.condition.expression;

import com.cdyhrj.fast.orm.api.lambda.LambdaQuery;
import com.cdyhrj.fast.orm.api.lambda.PropFn;
import com.cdyhrj.fast.orm.api.meta.FieldInfo;
import com.cdyhrj.fast.orm.condition.enums.Operator;
import com.cdyhrj.fast.orm.entity.Entity;
import com.cdyhrj.fast.orm.entity.context.ToSqlContext;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class UnaryExpression extends AbstractUnaryExpression<Object> {
    protected Operator operator;

    public static UnaryExpression of(@NonNull ToSqlContext<?, ?> context,
                                     String entityAlias,
                                     PropFn<? extends Entity, ?> field,
                                     Operator operator, Object value) {
        FieldInfo fieldInfo = LambdaQuery.resolve(field);

        String alias = entityAlias;
        if (Objects.isNull(alias)) {
            alias = context.getTableEntity(fieldInfo.getEntityClass().getName()).getAlias();
        }

        return new UnaryExpression(alias, fieldInfo.getName(), operator, value);
    }

    public UnaryExpression(String alias, String field, Operator operator, Object value) {
        super(alias, field, value);

        this.operator = operator;
    }

    @Override
    public String toSql() {
        return "%s.%s %s :%s".formatted(alias, field, operator, paramName);
    }

    @Override
    public String toNoAliasSql() {
        return "%s %s :%s".formatted(field, operator, paramName);
    }
}
