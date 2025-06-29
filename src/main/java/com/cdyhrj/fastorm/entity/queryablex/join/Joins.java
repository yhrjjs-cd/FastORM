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
    private List<Join> joinItems = new ArrayList<>();

    public Joins(ToSqlContext<E, EntityByClassQueryableX<E>> context) {
        this.context = context;
    }

    public EntityByClassQueryableX<E> ret() {
        return context.getBelongTo();
    }

    public <R extends Entity> EntityJoin<E, E, R> addJoin(JoinType joinType, Class<R> entityClass) {
        EntityJoin<E, E, R> join = EntityJoin.of(this, joinType, entityClass);
        this.joinItems.add(join);

        return join;
    }

    public <R extends Entity> EntityJoin<E, E, R> addJoin(JoinType joinType, Class<R> entityClass, String alias) {
        EntityJoin<E, E, R> join = EntityJoin.of(this, joinType, entityClass, alias);
        this.joinItems.add(join);

        return join;
    }

    public <R extends Entity, L extends Entity> EntityJoin<E, L, R> addJoin(JoinType joinType, Class<R> entityClass, Class<L> linkEntityClass) {
        EntityJoin<E, L, R> join = EntityJoin.of(this, joinType, entityClass, linkEntityClass);
        this.joinItems.add(join);

        return join;
    }

    public <R extends Entity, L extends Entity> EntityJoin<E, L, R> addJoin(JoinType joinType, Class<R> entityClass, Class<L> linkEntityClass, String linkEntityAlias) {
        EntityJoin<E, L, R> join = EntityJoin.of(this, joinType, entityClass, linkEntityClass, linkEntityAlias);
        this.joinItems.add(join);

        return join;
    }

//    public WithJoin<E> addWithJoin(JoinType joinType, String nameOfWith) {
//        WithJoin<E> join = WithJoin.of(joinType, this, nameOfWith);
//        this.joinItems.add(join);
//
//        return join;
//    }

    @Override
    public String toSql() {
        if (joinItems.isEmpty()) {
            return "";
        }

        return joinItems.stream()
                .map(Join::toSql)
                .collect(Collectors.joining(" "));
    }
}
