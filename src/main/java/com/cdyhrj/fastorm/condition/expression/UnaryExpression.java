package com.cdyhrj.fastorm.condition.expression;

import com.cdyhrj.fastorm.condition.enums.Operator;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.lambda.LambdaColumn;
import com.cdyhrj.fastorm.lambda.PropFn;
import com.cdyhrj.fastorm.meta.FieldInfo;
import com.cdyhrj.fastorm.queryable.context.Context;

import java.util.Objects;

public class UnaryExpression extends AbstractUnaryExpression {
    protected Operator operator;

    public static <E extends Entity> UnaryExpression of(Context<?> context, String entityAlias, PropFn<E, ?> field, Operator operator, Object value) {
        FieldInfo fieldInfo = LambdaColumn.resolve(field);

        String alias;
        if (Objects.isNull(entityAlias)) {
            alias = context.getTableEntity(fieldInfo.getEntityClass().getName()).getAlias();
        } else {
            alias = context.getTableEntity(entityAlias).getAlias();
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
}
