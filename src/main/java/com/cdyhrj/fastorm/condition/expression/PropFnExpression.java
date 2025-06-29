package com.cdyhrj.fastorm.condition.expression;

import com.cdyhrj.fastorm.api.lambda.LambdaQuery;
import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.api.meta.FieldInfo;
import com.cdyhrj.fastorm.api.parameter.ParamMap;
import com.cdyhrj.fastorm.condition.enums.Operator;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.context.ToSqlContext;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class PropFnExpression implements Expression {
    private String leftAlias;
    private String leftFieldName;
    private String rightAlias;
    private String rightFieldName;

    protected Operator operator;

    public static PropFnExpression of(@NonNull ToSqlContext<?, ?> context,
                                      String leftEntityAlias,
                                      PropFn<? extends Entity, ?> leftField,
                                      Operator operator,
                                      String rightEntityAlias,
                                      PropFn<? extends Entity, ?> rightField) {
        FieldInfo leftFieldInfo = LambdaQuery.resolve(leftField);
        String leftAlias = leftEntityAlias;
        if (Objects.isNull(leftAlias)) {
            leftAlias = context.getTableEntity(leftFieldInfo.getEntityClass().getName()).getAlias();
        }

        FieldInfo rightFieldInfo = LambdaQuery.resolve(rightField);
        String rightAlias = rightEntityAlias;
        if (Objects.isNull(rightAlias)) {
            rightAlias = context.getTableEntity(rightFieldInfo.getEntityClass().getName()).getAlias();
        }

        return new PropFnExpression(leftAlias, leftFieldInfo.getName(), operator, rightAlias, rightFieldInfo.getName());
    }

    public PropFnExpression(String leftAlias, String leftFieldName, Operator operator, String rightAlias, String rightFieldName) {
        this.leftAlias = leftAlias;
        this.leftFieldName = leftFieldName;
        this.rightAlias = rightAlias;
        this.rightFieldName = rightFieldName;

        this.operator = operator;
    }

    @Override
    public String toSql() {
        return "%s.%s %s %s.%s".formatted(leftAlias, leftFieldName, operator, rightAlias, rightFieldName);
    }

    @Override
    public String toNoAliasSql() {
        return "%s %s %s".formatted(leftFieldName, operator, rightFieldName);
    }

    @Override
    public void writeToParamMap(@NonNull ParamMap paramMap) {
        //no need do anything
    }
}
