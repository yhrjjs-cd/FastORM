package com.cdyhrj.fastorm.condition.expression;

import com.cdyhrj.fastorm.api.lambda.LambdaQuery;
import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.api.meta.FieldInfo;
import com.cdyhrj.fastorm.api.parameter.ParamMap;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class IsNull implements Expression {
    private final String alias;
    private final String field;

    public static IsNull of(@NonNull ToSqlContext<?, ?> context,
                            String entityAlias,
                            PropFn<? extends Entity, ?> field) {
        FieldInfo fieldInfo = LambdaQuery.resolve(field);

        String alias;
        if (Objects.isNull(entityAlias)) {
            alias = context.getTableEntity(fieldInfo.getEntityClass().getName()).getAlias();
        } else {
            alias = context.getTableEntity(entityAlias).getAlias();
        }
        return new IsNull(alias, fieldInfo.getName());
    }

    public IsNull(String alias, String field) {
        this.alias = alias;
        this.field = field;
    }

    @Override
    public void writeToParamMap(@NonNull ParamMap paramMap) {
        // 不需要做啥
    }

    @Override
    public String toSql() {
        return "%s.%s IS NULL".formatted(alias, field);
    }

    @Override
    public String toNoAliasSql() {
        return "%s IS NULL".formatted(field);
    }
}
