package com.cdyhrj.fastorm.entity.queryablex.join;

import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.context.AbstractSqlSegmentContextAware;
import com.cdyhrj.fastorm.entity.context.ToSqlContext;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Joins<E extends Entity, H extends ConditionHost<E>> extends AbstractSqlSegmentContextAware<E, H> {
    @Getter
    private List<Join<E, H, ?, ?>> joins = new ArrayList<>();

    public static <E1 extends Entity, H extends ConditionHost<E1>> Joins<E1, H> of() {
        return new Joins<>(null);
    }

    public Joins(ToSqlContext<E, H> context) {
        super(context);
    }

    public <E1 extends Entity> Join<E, H, E, E1> addJoin(JoinType joinType, Class<E1> entityClass) {
        Join<E, H, E, E1> join = Join.of(this.getContext(), joinType, entityClass);
        this.joins.add(join);

        return join;
    }

    public <E1 extends Entity, E2 extends Entity> Join<E, H, E1, E2> addJoin(JoinType joinType, Class<E1> sourceEntityClass, Class<E2> targetEntityClass) {
        Join<E, H, E1, E2> join = Join.of(this.getContext(), joinType, sourceEntityClass, targetEntityClass);

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
