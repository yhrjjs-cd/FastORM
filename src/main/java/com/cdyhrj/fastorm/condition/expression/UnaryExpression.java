package com.cdyhrj.fastorm.condition.expression;

import com.cdyhrj.fastorm.api.lambda.LambdaQuery;
import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.api.meta.FieldInfo;
import com.cdyhrj.fastorm.condition.enums.Operator;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class UnaryExpression extends AbstractUnaryExpression {
    protected Operator operator;

    public static <E extends Entity> UnaryExpression of(@NonNull ToSqlContext<?, ?> context, String entityAlias, PropFn<E, ?> field, Operator operator, Object value) {
        FieldInfo fieldInfo = LambdaQuery.resolve(field);

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
    public String toQuerySql() {
        return "%s.%s %s :%s".formatted(alias, field, operator.getValue(), paramName);
    }
}
