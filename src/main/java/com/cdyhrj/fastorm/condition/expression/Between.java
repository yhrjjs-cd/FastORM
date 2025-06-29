package com.cdyhrj.fastorm.condition.expression;

import com.cdyhrj.fastorm.api.lambda.LambdaQuery;
import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.api.meta.FieldInfo;
import com.cdyhrj.fastorm.condition.enums.Operator;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.context.ToSqlContext;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class Between<T> extends AbstractBinaryExpression<T> {
    public static <T> Between<T> of(
            @NonNull ToSqlContext<?, ?> context,
            String entityAlias,
            PropFn<? extends Entity, T> field,
            T value1,
            T value2) {
        FieldInfo fieldInfo = LambdaQuery.resolve(field);

        String alias;
        if (Objects.isNull(entityAlias)) {
            alias = context.getTableEntity(fieldInfo.getEntityClass().getName()).getAlias();
        } else {
            alias = context.getTableEntity(entityAlias).getAlias();
        }
        return new Between<T>(alias, fieldInfo.getName(), value1, value2);
    }

    protected Between(String alias, String field, T value1, T value2) {
        super(alias, field, value1, value2);
    }

    @Override
    public String toSql() {
        return "%s.%s %s :%s AND :%s".formatted(alias, field, Operator.BETWEEN, paramName1, paramName2);
    }

    @Override
    public String toNoAliasSql() {
        return "%s %s :%s AND :%s".formatted(field, Operator.BETWEEN, paramName1, paramName2);
    }
}
