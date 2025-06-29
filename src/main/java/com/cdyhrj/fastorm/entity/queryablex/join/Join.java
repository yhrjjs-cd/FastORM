package com.cdyhrj.fastorm.entity.queryablex.join;

import com.cdyhrj.fastorm.api.lambda.LambdaQuery;
import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.api.meta.FieldInfo;
import com.cdyhrj.fastorm.api.meta.SqlSegment;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.context.TableAvailable;
import com.cdyhrj.fastorm.entity.context.ToSqlContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Join 项
 *
 * @param <E> Joins类，主实体类
 * @param <L> 左实体类
 * @param <R> 右实体类
 */
@Slf4j
public class Join<E extends Entity, L extends Entity, R extends Entity> implements SqlSegment {
    public static <E extends Entity, R extends Entity> Join<E, E, R> of(
            Joins<E> joins,
            JoinType joinType,
            Class<R> rightEntityClass) {
        Join<E, E, R> newInstance = new Join<>();

        newInstance.joins = joins;
        newInstance.context = joins.getContext();
        newInstance.joinType = joinType;
        newInstance.rightEntityKey = rightEntityClass.getName();

        newInstance.context.addEntity(rightEntityClass);

        return newInstance;
    }

    public static <E extends Entity, R extends Entity, L extends Entity> Join<E, L, R> of(
            Joins<E> joins,
            JoinType joinType,
            Class<R> rightEntityClass,
            Class<L> linkEntityClass) {
        Join<E, L, R> newInstance = new Join<>();

        newInstance.joins = joins;
        newInstance.context = joins.getContext();
        newInstance.joinType = joinType;
        newInstance.rightEntityKey = rightEntityClass.getName();
        newInstance.linkEntityKey = linkEntityClass.getName();

        newInstance.context.addEntity(rightEntityClass);

        return newInstance;
    }

    public static <E extends Entity, R extends Entity, L extends Entity> Join<E, L, R> of(
            Joins<E> joins,
            JoinType joinType,
            Class<R> rightEntityClass,
            Class<L> linkEntityClass,
            String linkEntityAlias) {
        Join<E, L, R> newInstance = new Join<>();

        newInstance.joins = joins;
        newInstance.context = joins.getContext();
        newInstance.joinType = joinType;
        newInstance.rightEntityKey = rightEntityClass.getName();
        newInstance.linkEntityKey = linkEntityAlias;

        newInstance.context.addEntity(rightEntityClass);

        return newInstance;
    }

    public static <E extends Entity, R extends Entity> Join<E, E, R> of(
            Joins<E> joins,
            JoinType joinType,
            Class<R> rightEntityClass,
            String alias) {
        Join<E, E, R> newInstance = new Join<>();

        newInstance.joins = joins;
        newInstance.context = joins.getContext();
        newInstance.joinType = joinType;
        newInstance.rightEntityKey = alias;

        newInstance.context.addEntity(rightEntityClass, alias);

        return newInstance;
    }

    private Joins<E> joins;
    private ToSqlContext<?, ?> context;
    private JoinType joinType;
    private String rightEntityKey;
    private String linkEntityKey;

    private final List<OnItem> items = new ArrayList<>();

    public Join<E, L, R> onEq(PropFn<L, ?> leftFn, PropFn<R, ?> rightFn) {
        FieldInfo left = LambdaQuery.resolve(leftFn);
        TableAvailable leftTable = getLeftTable();
        FieldInfo right = LambdaQuery.resolve(rightFn);
        TableAvailable rightTable = context.getTableEntity(rightEntityKey);


        this.items.add(OnItem2.of(leftTable.getAlias(), left.getName(), rightTable.getAlias(), right.getName()));

        return this;
    }

    public Join<E, L, R> onLeftEq(PropFn<L, ?> leftFn, Object value) {
        FieldInfo left = LambdaQuery.resolve(leftFn);
        TableAvailable leftTable = getLeftTable();

        this.items.add(OnItem1.of(leftTable.getAlias(), left.getName(), value));

        return this;
    }

    public Join<E, L, R> onRightEq(PropFn<R, ?> rightFn, Object value) {
        FieldInfo right = LambdaQuery.resolve(rightFn);
        TableAvailable rightTable = context.getTableEntity(rightEntityKey);


        this.items.add(OnItem1.of(rightTable.getAlias(), right.getName(), value));

        return this;
    }

    private TableAvailable getLeftTable() {
        if (Objects.isNull(linkEntityKey)) {
            return context.getBaseTableEntity();
        } else {
            return context.getTableEntity(linkEntityKey);
        }
    }

    public Joins<E> end() {
        return joins;
    }

    @Override
    public String toSql() {
        TableAvailable target = context.getTableEntity(rightEntityKey);

        return "%s %s ON %s".formatted(joinType.getSqlString(), target.toSql(), getItemsSql());
    }

    private String getItemsSql() {
        Assert.notEmpty(this.items, "At least one item is required");

        return this.items.stream().map(SqlSegment::toSql)
                .collect(Collectors.joining(" AND "));
    }
}
