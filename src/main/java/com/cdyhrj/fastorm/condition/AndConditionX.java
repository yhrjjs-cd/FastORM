package com.cdyhrj.fastorm.condition;

import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.api.meta.NoAliasSqlSegment;
import com.cdyhrj.fastorm.api.meta.SqlSegment;
import com.cdyhrj.fastorm.api.parameter.ParamMap;
import com.cdyhrj.fastorm.condition.expression.Expression;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AndConditionX<T extends Entity, H extends ConditionHost<T>> implements SqlSegment, NoAliasSqlSegment, Expression {
    private final ToSqlContext<T, H> context;
    private final List<Expression> expressionList = new ArrayList<>();
    private OrConditionX<T, H> belongTo;

    public AndConditionX(@NonNull ToSqlContext<T, H> context) {
        this.context = context;
    }

    public AndConditionX(@NonNull ToSqlContext<T, H> context, OrConditionX<T, H> belongTo) {
        this.context = context;
        this.belongTo = belongTo;
    }

    public OrConditionX<T, H> andOrGroup() {
        OrConditionX<T, H> group = new OrConditionX<>(context, this);
        addExpression(group);

        return group;
    }

    private void addExpression(final Expression expression) {
        this.expressionList.add(expression);
    }

    @Override
    public void writeToParamMap(@NonNull ParamMap paramMap) {
        this.expressionList.forEach(expression -> expression.writeToParamMap(paramMap));
    }

    @Override
    public String toSql() {
        Assert.isTrue(!this.expressionList.isEmpty(), "expression list is empty");

        return "(" + this.expressionList.stream().map(SqlSegment::toSql)
                .collect(Collectors.joining(" AND ")) + ")";
    }


    @Override
    public String toNoAliasSql() {
        Assert.isTrue(!this.expressionList.isEmpty(), "expression list is empty");

        return "(" + this.expressionList.stream().map(NoAliasSqlSegment::toNoAliasSql)
                .collect(Collectors.joining(" AND ")) + ")";
    }

    /**
     * 是否为空，没有包含任何条件
     *
     * @return 如果为空, 返回<code>true</code>,否则<code>false</code>
     */
    public boolean isEmpty() {
        return this.expressionList.isEmpty();
    }

    public H ret() {
        // let subclass implement
        return null;
    }

    public OrConditionX<T, H> end() {
        return this.belongTo;
    }

    public AndConditionX<T, H> andEq(PropFn<T, ?> propFn, Object value) {
        return andEq(null, propFn, value);
    }

    public AndConditionX<T, H> andEq(String alias, PropFn<? extends Entity, ?> propFn, Object value) {
        addExpression(Exps.eq(context, alias, propFn, value));

        return this;
    }

    public <N extends Number> AndConditionX<T, H> andLt(PropFn<T, N> propFn, N value) {
        return andLt(null, propFn, value);
    }

    public <N extends Number> AndConditionX<T, H> andLt(String alias, PropFn<? extends Entity, N> propFn, N value) {
        addExpression(Exps.lt(context, alias, propFn, value));

        return this;
    }

    public <N extends Number> AndConditionX<T, H> andLte(PropFn<T, N> propFn, N value) {
        return andLte(null, propFn, value);
    }

    public <N extends Number> AndConditionX<T, H> andLte(String alias, PropFn<? extends Entity, N> propFn, N value) {
        addExpression(Exps.lte(context, alias, propFn, value));

        return this;
    }

    public <N extends Number> AndConditionX<T, H> andGt(PropFn<T, N> propFn, N value) {
        return andGt(null, propFn, value);
    }

    public <N extends Number> AndConditionX<T, H> andGt(String alias, PropFn<? extends Entity, N> propFn, N value) {
        addExpression(Exps.gt(context, alias, propFn, value));

        return this;
    }

    public <N extends Number> AndConditionX<T, H> andGte(PropFn<T, N> propFn, N value) {
        return andGte(null, propFn, value);
    }

    public <N extends Number> AndConditionX<T, H> andGte(String alias, PropFn<? extends Entity, N> propFn, N value) {
        addExpression(Exps.gte(context, alias, propFn, value));

        return this;
    }

    public AndConditionX<T, H> andIsNull(PropFn<T, ?> propFn) {
        return andIsNull(null, propFn);
    }

    public AndConditionX<T, H> andIsNull(String alias, PropFn<? extends Entity, ?> propFn) {
        addExpression(Exps.isNull(context, alias, propFn));

        return this;
    }

    public AndConditionX<T, H> andLike(PropFn<T, String> propFn, String value) {
        return andLike(null, propFn, value
        );
    }

    public AndConditionX<T, H> andLike(String alias, PropFn<? extends Entity, String> propFn, String value) {
        addExpression(Exps.like(context, alias, propFn, value));

        return this;
    }

    public AndConditionX<T, H> andBetween(PropFn<T, LocalDateTime> propFn, LocalDateTime value1, LocalDateTime value2) {
        return andBetween(null, propFn, value1, value2);
    }

    public AndConditionX<T, H> andBetween(String alias, PropFn<? extends Entity, LocalDateTime> propFn, LocalDateTime value1, LocalDateTime value2) {
        addExpression(Exps.between(context, alias, propFn, value1, value2));

        return this;
    }

    public AndConditionX<T, H> andBetween(PropFn<T, LocalDate> propFn, LocalDate value1, LocalDate value2) {
        return andBetween(null, propFn, value1, value2);
    }

    public AndConditionX<T, H> andBetween(String alias, PropFn<? extends Entity, LocalDate> propFn, LocalDate value1, LocalDate value2) {
        addExpression(Exps.between(context, alias, propFn, value1, value2));

        return this;
    }

    public AndConditionX<T, H> andBetween(PropFn<T, YearMonth> propFn, YearMonth value1, YearMonth value2) {
        return andBetween(null, propFn, value1, value2);
    }

    public AndConditionX<T, H> andBetween(String alias, PropFn<? extends Entity, YearMonth> propFn, YearMonth value1, YearMonth value2) {
        addExpression(Exps.between(context, alias, propFn, value1, value2));

        return this;
    }

    public AndConditionX<T, H> andBetween(PropFn<T, Year> propFn, Year value1, Year value2) {
        return andBetween(null, propFn, value1, value2);
    }

    public AndConditionX<T, H> andBetween(String alias, PropFn<? extends Entity, Year> propFn, Year value1, Year value2) {
        addExpression(Exps.between(context, alias, propFn, value1, value2));

        return this;
    }

    public AndConditionX<T, H> andBetween(PropFn<T, Number> propFn, Number value1, Number value2) {
        return andBetween(null, propFn, value1, value2);
    }

    public AndConditionX<T, H> andBetween(String alias, PropFn<? extends Entity, Number> propFn, Number value1, Number value2) {
        addExpression(Exps.between(context, alias, propFn, value1, value2));

        return this;
    }
}
