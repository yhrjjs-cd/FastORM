package com.cdyhrj.fastorm.entity.queryable;

import com.alibaba.fastjson2.JSONArray;
import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import com.cdyhrj.fastorm.entity.queryable.helper.ListQueryHelper;
import com.cdyhrj.fastorm.entity.queryable.join.Join;
import com.cdyhrj.fastorm.entity.queryable.join.JoinType;
import com.cdyhrj.fastorm.entity.queryable.join.Joins;
import com.cdyhrj.fastorm.entity.queryable.orderby.OrderBy;
import com.cdyhrj.fastorm.entity.queryable.where.Where;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

/**
 * 实体查询
 *
 * @author <a href="mailto:huangqi@cdyhrj.com">黄奇</a>
 */
public class EntityQueryable<T extends Entity> implements ConditionHost<T> {
    /**
     * 查询上下文
     */
    @Getter
    private final ToSqlContext<T> context;
    /**
     * 连接
     */
    @Getter
    private final Joins<T> joins;

    /**
     * 主键Id值
     */
    @Getter
    private Long id;

    /**
     * Name 值
     */
    @Getter
    private String name;

    /**
     * 条件
     */
    @Getter
    private Where<T> where;


    /**
     * Order By
     */
    @Getter
    private OrderBy<T> orderBy;

    public EntityQueryable(Class<T> entityClass) {
        this.context = new ToSqlContext<>(this, entityClass);
        this.joins = new Joins<>(this.context);
    }

    public EntityQueryable<T> joins(Joins<T> joins) {
        return this;
    }

    public <E extends Entity> Join<T, T, E> join(Class<E> entityClass) {
        return joins.addJoin(JoinType.INNER, entityClass);
    }

    public <E1 extends Entity, E2 extends Entity> Join<T, E1, E2> join(Class<E1> sourceEntityClass, Class<E2> targetEntityClass) {
        return joins.addJoin(JoinType.INNER, sourceEntityClass, targetEntityClass);
    }

    public <E extends Entity> Join<T, T, E> leftJoin(Class<E> entityClass) {
        return joins.addJoin(JoinType.INNER, entityClass);
    }

    public <E1 extends Entity, E2 extends Entity> Join<T, E1, E2> leftJoin(Class<E1> sourceEntityClass, Class<E2> targetEntityClass) {
        return joins.addJoin(JoinType.INNER, sourceEntityClass, targetEntityClass);
    }

    public <E extends Entity> Join<T, T, E> rightJoin(Class<E> entityClass) {
        return joins.addJoin(JoinType.INNER, entityClass);
    }


    public <E1 extends Entity, E2 extends Entity> Join<T, E1, E2> rightJoin(Class<E1> sourceEntityClass, Class<E2> targetEntityClass) {
        return joins.addJoin(JoinType.INNER, sourceEntityClass, targetEntityClass);
    }

    public EntityQueryable<T> id(Long id) {
        this.id = id;

        return this;
    }

    public EntityQueryable<T> name(String name) {
        this.name = name;

        return this;
    }

    public Where<T> where() {
        if (Objects.isNull(this.where)) {
            this.where = new Where<>(context, this);
        }

        return this.where;
    }

    public OrderBy<T> orderBy() {
        this.orderBy = new OrderBy<>(context, this);

        return this.orderBy;
    }

    /**
     * 查询实体列表
     *
     * @return 实体列表
     */
    public List<T> toList() {
        return ListQueryHelper.of(this).query();
    }

    /**
     * 查询总数
     *
     * @return 总数
     */
    public int count() {
        return ListQueryHelper.of(this).count();
    }

    /**
     * JSONArray
     *
     * @return JSON Array
     */
    public JSONArray toJSONArray() {
        return ListQueryHelper.of(this).toJSONArray();
    }
}
