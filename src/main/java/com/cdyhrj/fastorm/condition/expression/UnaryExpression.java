package com.cdyhrj.fastorm.condition.expression;

import com.cdyhrj.fastorm.api.lambda.LambdaQuery;
import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.api.meta.FieldInfo;
import com.cdyhrj.fastorm.condition.enums.Operator;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.context.ToSqlContext;
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
        return "%s.%s %s :%s".formatted(alias, field, operator.getValue(), paramName);
    }

    @Override
    public String toNoAliasSql() {
        return "%s %s :%s".formatted(field, operator.getValue(), paramName);
    }
}
