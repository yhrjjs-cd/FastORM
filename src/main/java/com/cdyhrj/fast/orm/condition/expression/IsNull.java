package com.cdyhrj.fast.orm.condition.expression;

import com.cdyhrj.fast.orm.api.lambda.LambdaQuery;
import com.cdyhrj.fast.orm.api.lambda.PropFn;
import com.cdyhrj.fast.orm.api.meta.FieldInfo;
import com.cdyhrj.fast.orm.api.parameter.ParamMap;
import com.cdyhrj.fast.orm.entity.Entity;
import com.cdyhrj.fast.orm.entity.context.ToSqlContext;
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
