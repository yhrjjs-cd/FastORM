package com.cdyhrj.fastorm.entity.queryable.join;

import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.AbstractSqlSegmentContextAware;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Joins<E extends Entity> extends AbstractSqlSegmentContextAware<E> {
    @Getter
    private List<Join<E, ?, ?>> joins = new ArrayList<>();

    public static <E1 extends Entity> Joins<E1> of() {
        return new Joins<>(null);
    }

    public Joins(ToSqlContext<E> context) {
        super(context);
    }

    public <E1 extends Entity> Join<E, E, E1> addJoin(JoinType joinType, Class<E1> entityClass) {
        Join<E, E, E1> join = Join.of(this.getContext(), joinType, entityClass);
        this.joins.add(join);

        return join;
    }

    public <E1 extends Entity, E2 extends Entity> Join<E, E1, E2> addJoin(JoinType joinType, Class<E1> sourceEntityClass, Class<E2> targetEntityClass) {
        Join<E, E1, E2> join = Join.of(this.getContext(), joinType, sourceEntityClass, targetEntityClass);

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
