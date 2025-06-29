package com.cdyhrj.fastorm.entity.queryablex;

import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.EntityProxy;
import com.cdyhrj.fastorm.entity.context.ToSqlContext;
import com.cdyhrj.fastorm.entity.queryablex.join.Joins;
import com.cdyhrj.fastorm.entity.queryablex.where.Where;
import com.cdyhrj.fastorm.pager.IPagerProvider;
import lombok.Getter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.List;
import java.util.Objects;

/**
 * 实体查询
 *
 * @author <a href="mailto:huangqi@cdyhrj.com">黄奇</a>
 */
public class EntityByClassQueryableX<E extends Entity> implements ConditionHost<E> {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    @Getter
    private final IPagerProvider pagerProvider;
    private final Class<E> entityClass;
    @Getter
    private final ToSqlContext<E, EntityByClassQueryableX<E>> context;

    /**
     * 连接
     */
    private final Joins<E> joins;

    public EntityByClassQueryableX(NamedParameterJdbcOperations namedParameterJdbcOperations,
                                   IPagerProvider pagerProvider,
                                   Class<E> entityClass) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.pagerProvider = pagerProvider;
        this.entityClass = entityClass;
        this.context = new ToSqlContext<>(this, entityClass);
        this.joins = new Joins<>(this.context);
    }


    /**
     * 条件
     */
    private Where<E, EntityByClassQueryableX<E>> where;


    //    /**
//     * Order By
//     */
//    @Getter
//    private OrderBy<T, EntityByClassQueryableX<T>> orderBy;
//

    public Joins<E> joins() {
        return this.joins;
    }

    //
//    public <E extends Entity> Join<T, EntityByClassQueryableX<T>, T, E> join(Class<E> entityClass) {
//        return joins.addJoin(JoinType.INNER, entityClass);
//    }
//
//    public <E1 extends Entity, E2 extends Entity> Join<T, EntityByClassQueryableX<T>, E1, E2> join(Class<E1> sourceEntityClass, Class<E2> targetEntityClass) {
//        return joins.addJoin(JoinType.INNER, sourceEntityClass, targetEntityClass);
//    }
//
//    public <E extends Entity> Join<T, EntityByClassQueryableX<T>, T, E> leftJoin(Class<E> entityClass) {
//        return joins.addJoin(JoinType.INNER, entityClass);
//    }
//
//    public <E1 extends Entity, E2 extends Entity> Join<T, EntityByClassQueryableX<T>, E1, E2> leftJoin(Class<E1> sourceEntityClass, Class<E2> targetEntityClass) {
//        return joins.addJoin(JoinType.INNER, sourceEntityClass, targetEntityClass);
//    }
//
//    public <E extends Entity> Join<T, EntityByClassQueryableX<T>, T, E> rightJoin(Class<E> entityClass) {
//        return joins.addJoin(JoinType.INNER, entityClass);
//    }
//
//
//    public <E1 extends Entity, E2 extends Entity> Join<T, EntityByClassQueryableX<T>, E1, E2> rightJoin(Class<E1> sourceEntityClass, Class<E2> targetEntityClass) {
//        return joins.addJoin(JoinType.INNER, sourceEntityClass, targetEntityClass);
//    }
//
//
    Where<E, EntityByClassQueryableX<E>> getWhere() {
        return this.where;
    }

    public Where<E, EntityByClassQueryableX<E>> where() {
        if (Objects.isNull(this.where)) {
            this.where = new Where<>(context, this);
        }

        return this.where;
    }

    //
//    public OrderBy<T, EntityByClassQueryableX<T>> orderBy() {
//        this.orderBy = new OrderBy<>(context, this);
//
//        return this.orderBy;
//    }
//
//    /**
//     * 查询实体列表
//     *
//     * @return 实体列表
//     */
//    public List<T> toList() {
//        return ListQueryHelper.of(this).query();
//    }
//
//    /**
//     * 查询总数
//     *
//     * @return 总数
//     */
//    public int count() {
//        return ListQueryHelper.of(this).count();
//    }
//
//    /**
//     * JSONArray
//     *
//     * @return JSON Array
//     */
//    public JSONArray toJSONArray() {
//        return ListQueryHelper.of(this).toJSONArray();
//    }
    public List<E> query() {
        EntityProxy entityProxy = Entity.getEntityProxy(entityClass);
        String sqlText = SqlHelper.generateUpdateSqlTextByWhere(entityProxy, this);

        System.out.println(sqlText);
//        ParamMap conditionParamMap = ParamMap.of();
//        this.where.writeToParamMap(conditionParamMap);
//        if (Objects.nonNull(getPager())) {
//            pagerProvider.writeToParamMap(conditionParamMap, getPager());
//        }

//        return this.namedParameterJdbcOperations.query(sqlText, conditionParamMap.getParams(), new QueryRowMapper<>(entityClass));
        return null;
    }
}
