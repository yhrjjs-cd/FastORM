package com.cdyhrj.fastorm.queryable.join;

import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.lambda.LambdaColumn;
import com.cdyhrj.fastorm.lambda.PropFn;
import com.cdyhrj.fastorm.meta.FieldInfo;
import com.cdyhrj.fastorm.meta.SqlNode;
import com.cdyhrj.fastorm.queryable.Queryable;
import com.cdyhrj.fastorm.queryable.context.Context;
import com.cdyhrj.fastorm.queryable.context.TableEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Join<T extends Entity, L extends Entity, R extends Entity> implements SqlNode {
    public static <T extends Entity, L extends Entity, R extends Entity> Join<T, L, R> join(
            Context<T> context,
            Class<L> leftEntityClass,
            Class<R> rightEntityClass) {
        Join<T, L, R> newInstance = new Join<>();

        newInstance.context = context;
        newInstance.joinType = JoinType.INNER;
        newInstance.entityKey = rightEntityClass.getName();

        context.addEntity(rightEntityClass);

        return newInstance;
    }

    public static <T extends Entity, L extends Entity, R extends Entity> Join<T, L, R> join(
            Context<T> context,
            Class<R> rightEntityClass) {
        Join<T, L, R> newInstance = new Join<>();

        newInstance.context = context;
        newInstance.joinType = JoinType.INNER;
        newInstance.entityKey = rightEntityClass.getName();

        context.addEntity(rightEntityClass);

        return newInstance;
    }

    public static <T extends Entity, L extends Entity, R extends Entity> Join<T, L, R> join(
            Context<T> context,
            Class<R> rightEntityClass,
            String entityAlias) {
        Join<T, L, R> newInstance = new Join<>();

        newInstance.context = context;
        newInstance.joinType = JoinType.INNER;
        newInstance.entityKey = entityAlias;

        context.addEntity(rightEntityClass, entityAlias);

        return newInstance;
    }

    private Context<T> context;
    private JoinType joinType;
    private String entityKey;

    private final List<OnItem> items = new ArrayList<>();

    public Queryable<T> ret() {
        return context.getBelongTo();
    }

    public Join<T, L, R> andEqual(PropFn<L, ?> leftKeyFun, PropFn<R, ?> rightKeyFun) {
        FieldInfo leftFiled = LambdaColumn.resolve(leftKeyFun);
        TableEntity leftEntity = context.getTableEntity(leftFiled.getEntityClass().getName());

        FieldInfo rightFiled = LambdaColumn.resolve(rightKeyFun);
        TableEntity rightEntity = context.getTableEntity(rightFiled.getEntityClass().getName());

        OnItem2 item = OnItem2.of(leftEntity.getAlias(), leftFiled.getName(), rightEntity.getAlias(), rightFiled.getName());
        items.add(item);

        return this;
    }

    public Join<T, L, R> andEqual(PropFn<L, ?> leftKeyFun, Object value) {
        FieldInfo leftFiled = LambdaColumn.resolve(leftKeyFun);
        TableEntity leftEntity = context.getTableEntity(leftFiled.getEntityClass().getName());

        OnItem1 item = OnItem1.of(leftEntity.getAlias(), leftFiled.getName(), value);
        items.add(item);

        return this;
    }

    public <RE extends Entity> Join<T, L, R> andLEqual(PropFn<L, ?> leftKeyFun, PropFn<RE, ?> rightKeyFun) {
        FieldInfo leftFiled = LambdaColumn.resolve(leftKeyFun);
        TableEntity leftEntity = context.getTableEntity(leftFiled.getEntityClass().getName());

        FieldInfo rightFiled = LambdaColumn.resolve(rightKeyFun);
        TableEntity rightEntity = context.getTableEntity(rightFiled.getEntityClass().getName());

        OnItem2 item = OnItem2.of(leftEntity.getAlias(), leftFiled.getName(), rightEntity.getAlias(), rightFiled.getName());
        items.add(item);

        return this;
    }

    public Join<T, L, R> andLEqual(PropFn<L, ?> leftKeyFun, Object value) {
        FieldInfo leftFiled = LambdaColumn.resolve(leftKeyFun);
        TableEntity leftEntity = context.getTableEntity(leftFiled.getEntityClass().getName());

        OnItem1 item = OnItem1.of(leftEntity.getAlias(), leftFiled.getName(), value);
        items.add(item);

        return this;
    }

    public <LE extends Entity> Join<T, L, R> andREqual(PropFn<R, ?> leftKeyFun, PropFn<LE, ?> rightKeyFun) {
        FieldInfo leftFiled = LambdaColumn.resolve(leftKeyFun);
        TableEntity leftEntity = context.getTableEntity(leftFiled.getEntityClass().getName());

        FieldInfo rightFiled = LambdaColumn.resolve(rightKeyFun);
        TableEntity rightEntity = context.getTableEntity(rightFiled.getEntityClass().getName());

        OnItem2 item = OnItem2.of(leftEntity.getAlias(), leftFiled.getName(), rightEntity.getAlias(), rightFiled.getName());
        items.add(item);

        return this;
    }

    public Join<T, L, R> andREqual(PropFn<R, ?> leftKeyFun, Object value) {
        FieldInfo leftFiled = LambdaColumn.resolve(leftKeyFun);
        TableEntity leftEntity = context.getTableEntity(leftFiled.getEntityClass().getName());

        OnItem1 item = OnItem1.of(leftEntity.getAlias(), leftFiled.getName(), value);
        items.add(item);

        return this;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public Join<T, L, R> andEqual(String leftAlias, PropFn<L, ?> leftKeyFun, PropFn<R, ?> rightKeyFun) {
        FieldInfo leftFiled = LambdaColumn.resolve(leftKeyFun);
        TableEntity leftEntity = context.getTableEntity(leftAlias);

        FieldInfo rightFiled = LambdaColumn.resolve(rightKeyFun);
        TableEntity rightEntity = context.getTableEntity(rightFiled.getEntityClass().getName());

        OnItem2 item = OnItem2.of(leftEntity.getAlias(), leftFiled.getName(), rightEntity.getAlias(), rightFiled.getName());
        items.add(item);

        return this;
    }

    public Join<T, L, R> andEqual(String leftAlias, PropFn<L, ?> leftKeyFun, Object value) {
        FieldInfo leftFiled = LambdaColumn.resolve(leftKeyFun);
        TableEntity leftEntity = context.getTableEntity(leftAlias);

        OnItem1 item = OnItem1.of(leftEntity.getAlias(), leftFiled.getName(), value);
        items.add(item);

        return this;
    }

    public <RE extends Entity> Join<T, L, R> andLEqual(String leftAlias, PropFn<L, ?> leftKeyFun, PropFn<RE, ?> rightKeyFun) {
        FieldInfo leftFiled = LambdaColumn.resolve(leftKeyFun);
        TableEntity leftEntity = context.getTableEntity(leftAlias);

        FieldInfo rightFiled = LambdaColumn.resolve(rightKeyFun);
        TableEntity rightEntity = context.getTableEntity(rightFiled.getEntityClass().getName());

        OnItem2 item = OnItem2.of(leftEntity.getAlias(), leftFiled.getName(), rightEntity.getAlias(), rightFiled.getName());
        items.add(item);

        return this;
    }

    public Join<T, L, R> andLEqual(String leftAlias, PropFn<L, ?> leftKeyFun, Object value) {
        FieldInfo leftFiled = LambdaColumn.resolve(leftKeyFun);
        TableEntity leftEntity = context.getTableEntity(leftAlias);

        OnItem1 item = OnItem1.of(leftEntity.getAlias(), leftFiled.getName(), value);
        items.add(item);

        return this;
    }

    public <LE extends Entity> Join<T, L, R> andREqual(String rightAlias, PropFn<R, ?> leftKeyFun, PropFn<LE, ?> rightKeyFun) {
        FieldInfo leftFiled = LambdaColumn.resolve(leftKeyFun);
        TableEntity leftEntity = context.getTableEntity(rightAlias);

        FieldInfo rightFiled = LambdaColumn.resolve(rightKeyFun);
        TableEntity rightEntity = context.getTableEntity(rightFiled.getEntityClass().getName());

        OnItem2 item = OnItem2.of(leftEntity.getAlias(), leftFiled.getName(), rightEntity.getAlias(), rightFiled.getName());
        items.add(item);

        return this;
    }

    public Join<T, L, R> andREqual(String rightAlias, PropFn<R, ?> leftKeyFun, Object value) {
        FieldInfo leftFiled = LambdaColumn.resolve(leftKeyFun);
        TableEntity leftEntity = context.getTableEntity(rightAlias);

        OnItem1 item = OnItem1.of(leftEntity.getAlias(), leftFiled.getName(), value);
        items.add(item);

        return this;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public Join<T, L, R> andEqual(String leftAlias, PropFn<L, ?> leftKeyFun, String rightAlias, PropFn<R, ?> rightKeyFun) {
        FieldInfo leftFiled = LambdaColumn.resolve(leftKeyFun);
        TableEntity leftEntity = context.getTableEntity(leftAlias);

        FieldInfo rightFiled = LambdaColumn.resolve(rightKeyFun);
        TableEntity rightEntity = context.getTableEntity(rightAlias);

        OnItem2 item = OnItem2.of(leftEntity.getAlias(), leftFiled.getName(), rightEntity.getAlias(), rightFiled.getName());
        items.add(item);

        return this;
    }

    public <RE extends Entity> Join<T, L, R> andLEqual(String leftAlias, PropFn<L, ?> leftKeyFun, String rightAlias, PropFn<RE, ?> rightKeyFun) {
        FieldInfo leftFiled = LambdaColumn.resolve(leftKeyFun);
        TableEntity leftEntity = context.getTableEntity(leftAlias);

        FieldInfo rightFiled = LambdaColumn.resolve(rightKeyFun);
        TableEntity rightEntity = context.getTableEntity(rightAlias);

        OnItem2 item = OnItem2.of(leftEntity.getAlias(), leftFiled.getName(), rightEntity.getAlias(), rightFiled.getName());
        items.add(item);

        return this;
    }


    public <LE extends Entity> Join<T, L, R> andREqual(String leftAlias, PropFn<R, ?> leftKeyFun, String rightAlias, PropFn<LE, ?> rightKeyFun) {
        FieldInfo leftFiled = LambdaColumn.resolve(leftKeyFun);
        TableEntity leftEntity = context.getTableEntity(leftAlias);

        FieldInfo rightFiled = LambdaColumn.resolve(rightKeyFun);
        TableEntity rightEntity = context.getTableEntity(rightAlias);

        OnItem2 item = OnItem2.of(leftEntity.getAlias(), leftFiled.getName(), rightEntity.getAlias(), rightFiled.getName());
        items.add(item);

        return this;
    }

    @Override
    public String toSql() {
        TableEntity right = context.getTableEntity(entityKey);

        return "%s %s ON %s".formatted(joinType.getSqlString(), right.toSql(), getItemsSql());
    }

    private String getItemsSql() {
        return this.items.stream().map(SqlNode::toSql)
                .collect(Collectors.joining(" AND "));
    }
}
