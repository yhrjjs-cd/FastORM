package com.cdyhrj.fastorm.entity.queryablex.where;

import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.api.meta.NoAliasSqlSegment;
import com.cdyhrj.fastorm.api.meta.SqlSegment;
import com.cdyhrj.fastorm.api.parameter.ParamMap;
import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.condition.Exps;
import com.cdyhrj.fastorm.condition.expression.Expression;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.context.ToSqlContext;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AndConditionX<E extends Entity, H extends ConditionHost<E>> implements SqlSegment, NoAliasSqlSegment, Expression {
    private final ToSqlContext<E, H> context;
    private final List<Expression> expressionList = new ArrayList<>();
    private OrConditionX<E, H> belongTo;

    public AndConditionX(@NonNull ToSqlContext<E, H> context) {
        this.context = context;
    }

    public AndConditionX(@NonNull ToSqlContext<E, H> context, OrConditionX<E, H> belongTo) {
        this.context = context;
        this.belongTo = belongTo;
    }

    public OrConditionX<E, H> andOrGroup() {
        OrConditionX<E, H> group = new OrConditionX<>(context, this);
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

    public OrConditionX<E, H> end() {
        return this.belongTo;
    }

    public AndConditionX<E, H> andEq(PropFn<E, ?> propFn, Object value) {
        return andEq(null, propFn, value);
    }

    public <T extends Entity> AndConditionX<E, H> andEq(String alias, PropFn<T, ?> propFn, Object value) {
        addExpression(Exps.eq(context, alias, propFn, value));

        return this;
    }

    public <N extends Number> AndConditionX<E, H> andLt(PropFn<E, N> propFn, N value) {
        return andLt(null, propFn, value);
    }

    public <N extends Number, T extends Entity> AndConditionX<E, H> andLt(String alias, PropFn<T, N> propFn, N value) {
        addExpression(Exps.lt(context, alias, propFn, value));

        return this;
    }

    public <N extends Number> AndConditionX<E, H> andLte(PropFn<E, N> propFn, N value) {
        return andLte(null, propFn, value);
    }

    public <N extends Number, T extends Entity> AndConditionX<E, H> andLte(String alias, PropFn<T, N> propFn, N value) {
        addExpression(Exps.lte(context, alias, propFn, value));

        return this;
    }

    public <N extends Number> AndConditionX<E, H> andGt(PropFn<E, N> propFn, N value) {
        return andGt(null, propFn, value);
    }

    public <N extends Number, T extends Entity> AndConditionX<E, H> andGt(String alias, PropFn<T, N> propFn, N value) {
        addExpression(Exps.gt(context, alias, propFn, value));

        return this;
    }

    public <N extends Number> AndConditionX<E, H> andGte(PropFn<E, N> propFn, N value) {
        return andGte(null, propFn, value);
    }

    public <N extends Number, T extends Entity> AndConditionX<E, H> andGte(String alias, PropFn<T, N> propFn, N value) {
        addExpression(Exps.gte(context, alias, propFn, value));

        return this;
    }

    public AndConditionX<E, H> andIsNull(PropFn<E, ?> propFn) {
        return andIsNull(null, propFn);
    }

    public <T extends Entity> AndConditionX<E, H> andIsNull(String alias, PropFn<T, ?> propFn) {
        addExpression(Exps.isNull(context, alias, propFn));

        return this;
    }

    public AndConditionX<E, H> andLike(PropFn<E, String> propFn, String value) {
        return andLike(null, propFn, value
        );
    }

    public <T extends Entity> AndConditionX<E, H> andLike(String alias, PropFn<T, String> propFn, String value) {
        addExpression(Exps.like(context, alias, propFn, value));

        return this;
    }

    public AndConditionX<E, H> andBetween(PropFn<E, LocalDateTime> propFn, LocalDateTime value1, LocalDateTime value2) {
        return andBetween(null, propFn, value1, value2);
    }

    public <T extends Entity> AndConditionX<E, H> andBetween(String alias, PropFn<T, LocalDateTime> propFn, LocalDateTime value1, LocalDateTime value2) {
        addExpression(Exps.between(context, alias, propFn, value1, value2));

        return this;
    }

    public AndConditionX<E, H> andBetween(PropFn<E, LocalDate> propFn, LocalDate value1, LocalDate value2) {
        return andBetween(null, propFn, value1, value2);
    }

    public <T extends Entity> AndConditionX<E, H> andBetween(String alias, PropFn<T, LocalDate> propFn, LocalDate value1, LocalDate value2) {
        addExpression(Exps.between(context, alias, propFn, value1, value2));

        return this;
    }

    public AndConditionX<E, H> andBetween(PropFn<E, YearMonth> propFn, YearMonth value1, YearMonth value2) {
        return andBetween(null, propFn, value1, value2);
    }

    public <T extends Entity> AndConditionX<E, H> andBetween(String alias, PropFn<T, YearMonth> propFn, YearMonth value1, YearMonth value2) {
        addExpression(Exps.between(context, alias, propFn, value1, value2));

        return this;
    }

    public AndConditionX<E, H> andBetween(PropFn<E, Year> propFn, Year value1, Year value2) {
        return andBetween(null, propFn, value1, value2);
    }

    public <T extends Entity> AndConditionX<E, H> andBetween(String alias, PropFn<T, Year> propFn, Year value1, Year value2) {
        addExpression(Exps.between(context, alias, propFn, value1, value2));

        return this;
    }

    public AndConditionX<E, H> andBetween(PropFn<E, Number> propFn, Number value1, Number value2) {
        return andBetween(null, propFn, value1, value2);
    }

    public <T extends Entity> AndConditionX<E, H> andBetween(String alias, PropFn<T, Number> propFn, Number value1, Number value2) {
        addExpression(Exps.between(context, alias, propFn, value1, value2));

        return this;
    }
}
