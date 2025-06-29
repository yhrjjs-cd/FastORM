package com.cdyhrj.fastorm.entity.queryable;

import com.cdyhrj.fastorm.api.parameter.ParamMap;
import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.EntityProxy;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import com.cdyhrj.fastorm.entity.support.order_by.OrderBy;
import com.cdyhrj.fastorm.entity.support.where.Where;
import com.cdyhrj.fastorm.util.QueryRowMapper;
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
    private final Class<E> entityClass;
    private final ToSqlContext<E, EntityByClassQueryable<E>> context;

    public EntityByClassQueryable(NamedParameterJdbcOperations namedParameterJdbcOperations, Class<E> entityClass) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.entityClass = entityClass;
        this.context = new ToSqlContext<>(this, entityClass);
    }

    /**
     * 条件
     */
    @Getter
    private Where<E, EntityByClassQueryable<E>> where;

    /**
     * Order By
     */
    @Getter
    private OrderBy<E, EntityByClassQueryable<E>> orderBy;

    public Where<E, EntityByClassQueryable<E>> where() {
        if (Objects.isNull(this.where)) {
            this.where = new Where<>(context, this);
        }

        return this.where;
    }

    public OrderBy<E, EntityByClassQueryable<E>> orderBy() {
        this.orderBy = new OrderBy<>(context, this);

        return this.orderBy;
    }

    public List<E> query() {
        EntityProxy entityProxy = Entity.getEntityProxy(entityClass);
        String sqlText = SqlHelper.generateUpdateSqlTextByWhere(entityProxy, this);
        ParamMap conditionParamMap = ParamMap.of();
        this.where.writeToParamMap(conditionParamMap);

        return this.namedParameterJdbcOperations.query(sqlText, conditionParamMap.getParams(), new QueryRowMapper<>(entityClass));
    }
}
