package com.cdyhrj.fast.orm.entity.fetchable;

import com.cdyhrj.fast.orm.api.parameter.ParamMap;
import com.cdyhrj.fast.orm.condition.ConditionHost;
import com.cdyhrj.fast.orm.entity.Entity;
import com.cdyhrj.fast.orm.entity.EntityProxy;
import com.cdyhrj.fast.orm.entity.context.ToSqlContext;
import com.cdyhrj.fast.orm.entity.queryable.order_by.OrderBy;
import com.cdyhrj.fast.orm.entity.queryable.where.Where;
import com.cdyhrj.fast.orm.exception.ConditionRequiredException;
import com.cdyhrj.fast.orm.pager.IPagerProvider;
import com.cdyhrj.fast.orm.util.ResultSetUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.Objects;
import java.util.Optional;

import static com.cdyhrj.fast.orm.entity.fetchable.SqlHelper.PAGER_ONE;

/**
 * 通过<code>Class</code>获取<code>Entity</code>
 */
@Slf4j
public class EntityByClassFetchable<E extends Entity> implements ConditionHost<E> {
    public static final String PARAM_HOLDER_NAME = "_P_P_P_";
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final IPagerProvider pagerProvider;
    private final Class<E> entityClass;
    private final ToSqlContext<E, EntityByClassFetchable<E>> context;

    public EntityByClassFetchable(NamedParameterJdbcOperations namedParameterJdbcOperations,
                                  IPagerProvider pagerProvider,
                                  Class<E> entityClass) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.pagerProvider = pagerProvider;
        this.entityClass = entityClass;
        this.context = new ToSqlContext<>(this, entityClass);
    }

    private Long id;
    private String name;
    /**
     * 条件
     */
    @Getter
    private Where<E, EntityByClassFetchable<E>> where;
    /**
     * Order By
     */
    @Getter
    private OrderBy<E, EntityByClassFetchable<E>> orderBy;

    public EntityByClassFetchable<E> id(Long id) {
        this.id = id;

        return this;
    }

    public EntityByClassFetchable<E> name(String name) {
        this.name = name;

        return this;
    }

    public Where<E, EntityByClassFetchable<E>> where() {
        if (Objects.isNull(this.where)) {
            this.where = new Where<>(context, this);
        }

        return this.where;
    }

    public OrderBy<E, EntityByClassFetchable<E>> orderBy() {
        this.orderBy = new OrderBy<>(this);

        return this.orderBy;
    }

    /**
     * Fetch entity
     * <blockquote><pre>
     *     Student fetch(long id {
     *         return fastORM.fetchable(Student.class)
     *                       .id(100L)
     *                       .fetch()
     *     }
     * </pre></blockquote>
     *
     * @return entity object
     */

    public Optional<E> fetch() {
        if (Objects.nonNull(id)) {
            return fetchById();
        } else if (Objects.nonNull(name)) {
            return fetchByName();
        } else if (Objects.nonNull(where)) {
            return fetchByWhere();
        }

        throw new ConditionRequiredException();
    }

    private Optional<E> fetchById() {
        EntityProxy entityProxy = Entity.getEntityProxy(entityClass);
        String sqlText = SqlHelper.generateUpdateSqlTextByPlaceholder(entityProxy, entityProxy.getIdFieldName(), pagerProvider);

        ParamMap conditionParamMap = ParamMap.of(PARAM_HOLDER_NAME, id);
        pagerProvider.writeToParamMap(conditionParamMap, PAGER_ONE);

        return this.namedParameterJdbcOperations.query(sqlText, conditionParamMap.getParams(), rs -> {
            if (rs.next()) {
                return Optional.of(ResultSetUtils.toEntity(rs, entityClass));
            } else {
                return Optional.empty();
            }
        });
    }

    private Optional<E> fetchByName() {
        EntityProxy entityProxy = Entity.getEntityProxy(entityClass);
        String sqlText = SqlHelper.generateUpdateSqlTextByPlaceholder(entityProxy, entityProxy.getNameFieldName(), pagerProvider);

        ParamMap conditionParamMap = ParamMap.of(PARAM_HOLDER_NAME, name);
        pagerProvider.writeToParamMap(conditionParamMap, PAGER_ONE);

        return this.namedParameterJdbcOperations.query(sqlText, conditionParamMap.getParams(), rs -> {
            if (rs.next()) {
                return Optional.of(ResultSetUtils.toEntity(rs, entityClass));
            } else {
                return Optional.empty();
            }
        });
    }

    private Optional<E> fetchByWhere() {
        EntityProxy entityProxy = Entity.getEntityProxy(entityClass);
        String sqlText = SqlHelper.generateUpdateSqlTextByWhere(entityProxy, this, pagerProvider);

        ParamMap conditionParamMap = ParamMap.of();
        this.where.writeToParamMap(conditionParamMap);
        pagerProvider.writeToParamMap(conditionParamMap, PAGER_ONE);

        return this.namedParameterJdbcOperations.query(sqlText, conditionParamMap.getParams(), rs -> {
            if (rs.next()) {
                return Optional.of(ResultSetUtils.toEntity(rs, entityClass));
            } else {
                return Optional.empty();
            }
        });
    }
}
