package com.cdyhrj.fastorm.entity.queryablex.join;

import com.cdyhrj.fastorm.api.meta.SqlSegment;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.context.ToSqlContext;
import com.cdyhrj.fastorm.entity.queryablex.EntityByClassQueryableX;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Join
 *
 * @param <E> 实体类
 */
public class Joins<E extends Entity> implements SqlSegment {
    @Getter
    private final ToSqlContext<E, EntityByClassQueryableX<E>> context;

    @Getter
    private List<Join<E, ?, ?>> joins = new ArrayList<>();

    public Joins(ToSqlContext<E, EntityByClassQueryableX<E>> context) {
        this.context = context;
    }

    public EntityByClassQueryableX<E> ret() {
        return context.getBelongTo();
    }

    public <R extends Entity> Join<E, E, R> addJoin(JoinType joinType, Class<R> entityClass) {
        Join<E, E, R> join = Join.of(this, joinType, entityClass);
        this.joins.add(join);

        return join;
    }

    public <R extends Entity> Join<E, E, R> addJoin(JoinType joinType, Class<R> entityClass, String alias) {
        Join<E, E, R> join = Join.of(this, joinType, entityClass, alias);
        this.joins.add(join);

        return join;
    }

    public <R extends Entity, L extends Entity> Join<E, L, R> addJoin(JoinType joinType, Class<R> entityClass, Class<L> linkEntityClass) {
        Join<E, L, R> join = Join.of(this, joinType, entityClass, linkEntityClass);
        this.joins.add(join);

        return join;
    }

    public <R extends Entity, L extends Entity> Join<E, L, R> addJoin(JoinType joinType, Class<R> entityClass, Class<L> linkEntityClass, String linkEntityAlias) {
        Join<E, L, R> join = Join.of(this, joinType, entityClass, linkEntityClass, linkEntityAlias);
        this.joins.add(join);

        return join;
    }

    @Override
    public String toSql() {
        return joins.stream()
                .map(Join::toSql)
                .collect(Collectors.joining(" "));
    }
}
