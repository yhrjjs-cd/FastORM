package com.cdyhrj.fastorm.queryable;

import com.cdyhrj.fastorm.condition.AndCondition;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.lambda.PropFn;
import com.cdyhrj.fastorm.queryable.context.Context;
import com.cdyhrj.fastorm.queryable.context.TableEntity;
import com.cdyhrj.fastorm.queryable.join.Join;
import com.cdyhrj.fastorm.queryable.join.JoinType;
import com.cdyhrj.fastorm.queryable.join.Joins;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Queryable
 *
 * @author <a href="mailto:huangqi@cdyhrj.com">黄奇</a>
 */
public class Queryable<T extends Entity> {
    /**
     * 查询上下文
     */
    private final Context<T> context;
    /**
     * 连接
     */
    private final Joins<T> joins;
    /**
     * 主键Id值
     */
    private Long id;

    /**
     * Name 值
     */
    private String name;

    /**
     * 条件
     */
    private AndCondition<T> condition;

    public Queryable(Class<T> entityClass) {
        this.context = new Context<T>(this, entityClass);
        this.joins = new Joins<>();
    }

    public <E extends Entity> Join<T, T, E> join(Class<E> entityClass) {
        Join<T, T, E> join = Join.join(context, JoinType.INNER, entityClass);
        joins.addJoin(join);

        return join;
    }

    public <E extends Entity> Join<T, T, E> join(Class<E> entityClass, String entityAlias) {
        Join<T, T, E> join = Join.join(context, JoinType.INNER, entityClass, entityAlias);
        joins.addJoin(join);

        return join;
    }

    public <E1 extends Entity, E2 extends Entity> Join<T, E1, E2> join(Class<E1> sourceEntityClass, Class<E2> targetEntityClass) {
        Join<T, E1, E2> join = Join.join(context, JoinType.INNER, sourceEntityClass, targetEntityClass);
        joins.addJoin(join);

        return join;
    }

    public <E extends Entity> Join<T, T, E> leftJoin(Class<E> entityClass) {
        Join<T, T, E> join = Join.join(context, JoinType.LEFT, entityClass);
        joins.addJoin(join);

        return join;
    }

    public <E extends Entity> Join<T, T, E> leftJoin(Class<E> entityClass, String entityAlias) {
        Join<T, T, E> join = Join.join(context, JoinType.LEFT, entityClass, entityAlias);
        joins.addJoin(join);

        return join;
    }

    public <E1 extends Entity, E2 extends Entity> Join<T, E1, E2> leftJoin(Class<E1> sourceEntityClass, Class<E2> targetEntityClass) {
        Join<T, E1, E2> join = Join.join(context, JoinType.LEFT, sourceEntityClass, targetEntityClass);
        joins.addJoin(join);

        return join;
    }

    public <E extends Entity> Join<T, T, E> rightJoin(Class<E> entityClass) {
        Join<T, T, E> join = Join.join(context, JoinType.RIGHT, entityClass);
        joins.addJoin(join);

        return join;
    }

    public <E extends Entity> Join<T, T, E> rightJoin(Class<E> entityClass, String entityAlias) {
        Join<T, T, E> join = Join.join(context, JoinType.RIGHT, entityClass, entityAlias);
        joins.addJoin(join);

        return join;
    }

    public <E1 extends Entity, E2 extends Entity> Join<T, E1, E2> rightJoin(Class<E1> sourceEntityClass, Class<E2> targetEntityClass) {
        Join<T, E1, E2> join = Join.join(context, JoinType.RIGHT, sourceEntityClass, targetEntityClass);
        joins.addJoin(join);

        return join;
    }

    public Queryable<T> id(Long id) {
        this.id = id;

        return this;
    }

    public Queryable<T> name(String name) {
        this.name = name;

        return this;
    }

    public AndCondition<T> where() {
        this.condition = new AndCondition<>(context, this);

        return this.condition;
    }

    /**
     * 设置参数
     *
     * @param keyFun 字段函数
     * @param value  值
     * @return 当前对象
     */
    public Queryable<T> param(PropFn<T, ?> keyFun, Object value) {

        return this;
    }

    /**
     * 查询实体列表
     *
     * @return 实体列表
     */
    public List<T> toList() {
        return Collections.emptyList();
    }

    /**
     * 查询总数
     *
     * @return 总数
     */
    public int count() {
        return 0;
    }

    public String toSqlString() {
        StringJoiner sj = new StringJoiner(" ");

        TableEntity tableEntity = context.getBaseTableEntity();
        sj.add("SELECT").add("*").add("FROM").add(tableEntity.toSql());

        if (Objects.nonNull(joins)) {
            sj.add(joins.toSql());
        }

        if (Objects.nonNull(this.condition)) {
            sj.add("WHERE")
                    .add(this.condition.toSql());
        }

        return sj.toString();
    }
}
