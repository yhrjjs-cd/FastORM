package com.cdyhrj.fast.orm.condition.expression;

import com.cdyhrj.fast.orm.api.lambda.LambdaQuery;
import com.cdyhrj.fast.orm.api.lambda.PropFn;
import com.cdyhrj.fast.orm.api.meta.FieldInfo;
import com.cdyhrj.fast.orm.api.parameter.ParamMap;
import com.cdyhrj.fast.orm.condition.enums.Operator;
import com.cdyhrj.fast.orm.entity.Entity;
import com.cdyhrj.fast.orm.entity.context.ToSqlContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class Like extends AbstractUnaryExpression<String> {
    public static Like of(
            @NonNull ToSqlContext<?, ?> context,
            String entityAlias,
            PropFn<? extends Entity, String> field,
            String value,
            String prefix,
            String suffix) {
        FieldInfo fieldInfo = LambdaQuery.resolve(field);

        String alias;
        if (Objects.isNull(entityAlias)) {
            alias = context.getTableEntity(fieldInfo.getEntityClass().getName()).getAlias();
        } else {
            alias = context.getTableEntity(entityAlias).getAlias();
        }
        Like like = new Like(alias, fieldInfo.getName(), value);
        like.prefix = prefix;
        like.suffix = suffix;

        return like;
    }

    private String prefix;
    private String suffix;

    public Like(String alias, String field, String value) {
        super(alias, field, value);
    }

    @Override
    public void writeToParamMap(@NonNull ParamMap paramMap) {
        paramMap.add(paramName, String.format("%s%s%s", prefix, StringUtils.trim(value), suffix));
    }

    @Override
    public String toSql() {
        return "%s.%s %s :%s".formatted(alias, field, Operator.LIKE, paramName);
    }

    @Override
    public String toNoAliasSql() {
        return "%s %s :%s".formatted(field, Operator.LIKE, paramName);
    }
}
