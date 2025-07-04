package com.cdyhrj.fast.orm.entity.queryable;

import com.cdyhrj.fast.orm.api.parameter.ParamMap;
import com.cdyhrj.fast.orm.condition.ConditionHost;
import com.cdyhrj.fast.orm.entity.Entity;
import com.cdyhrj.fast.orm.entity.EntityProxy;
import com.cdyhrj.fast.orm.entity.context.ToSqlContext;
import com.cdyhrj.fast.orm.entity.queryable.order_by.OrderBy;
import com.cdyhrj.fast.orm.entity.queryable.where.Where;
import com.cdyhrj.fast.orm.pager.IPagerProvider;
import com.cdyhrj.fast.orm.pager.Pager;
import com.cdyhrj.fast.orm.util.QueryRowMapper;
import lombok.Getter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.List;
import java.util.Objects;

/**
 * 实体查询
 *
 * @author <a href="mailto:huangqi@cdyhrj.com">黄奇</a>
 */
public class EntityByClassQueryable<E extends Entity> implements ConditionHost<E> {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    @Getter
    private final IPagerProvider pagerProvider;
    private final Class<E> entityClass;
    private final ToSqlContext<E, EntityByClassQueryable<E>> context;

    public EntityByClassQueryable(NamedParameterJdbcOperations namedParameterJdbcOperations,
                                  IPagerProvider pagerProvider,
                                  Class<E> entityClass) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.pagerProvider = pagerProvider;
        this.entityClass = entityClass;
        this.context = new ToSqlContext<>(this, entityClass);
    }

    /**
     * 条件
     */
    private Where<E, EntityByClassQueryable<E>> where;

    Where<E, EntityByClassQueryable<E>> getWhere() {
        return where;
    }

    /**
     * Order By
     */
    private OrderBy<E, EntityByClassQueryable<E>> orderBy;

    OrderBy<E, EntityByClassQueryable<E>> getOrderBy() {
        return orderBy;
    }

    /**
     * 分页
     */
    private Pager pager;

    Pager getPager() {
        return pager;
    }

    public Where<E, EntityByClassQueryable<E>> where() {
        if (Objects.isNull(this.where)) {
            this.where = new Where<>(context, this);
        }

        return this.where;
    }

    public OrderBy<E, EntityByClassQueryable<E>> orderBy() {
        if (Objects.isNull(this.orderBy)) {
            this.orderBy = new OrderBy<>(this);
        }

        return this.orderBy;
    }

    public EntityByClassQueryable<E> pager(Pager pager) {
        this.pager = pager;

        return this;
    }

    public List<E> query() {
        EntityProxy entityProxy = Entity.getEntityProxy(entityClass);
        String sqlText = SqlHelper.generateUpdateSqlTextByWhere(entityProxy, this);

        ParamMap conditionParamMap = ParamMap.of();
        this.where.writeToParamMap(conditionParamMap);
        if (Objects.nonNull(pager)) {
            pagerProvider.writeToParamMap(conditionParamMap, pager);
        }

        return this.namedParameterJdbcOperations.query(sqlText, conditionParamMap.getParams(), new QueryRowMapper<>(entityClass));
    }
}
