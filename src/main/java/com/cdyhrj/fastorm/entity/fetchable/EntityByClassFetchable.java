package com.cdyhrj.fastorm.entity.fetchable;

import com.cdyhrj.fastorm.api.parameter.ParamMap;
import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.EntityProxy;
import com.cdyhrj.fastorm.entity.context.ToSqlContext;
import com.cdyhrj.fastorm.entity.support.order_by.OrderBy;
import com.cdyhrj.fastorm.entity.support.where.Where;
import com.cdyhrj.fastorm.exception.ConditionRequiredException;
import com.cdyhrj.fastorm.util.ResultSetUtils;
import lombok.Getter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 通过<code>Class</code>获取<code>Entity</code>
 */
public class EntityByClassFetchable<E extends Entity> implements ConditionHost<E> {
    public static final String PARAM_HOLDER_NAME = "_P_P_P_";
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final Class<E> entityClass;
    private final ToSqlContext<E, EntityByClassFetchable<E>> context;

    public EntityByClassFetchable(NamedParameterJdbcOperations namedParameterJdbcOperations, Class<E> entityClass) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
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
        this.orderBy = new OrderBy<>(context, this);

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
        String sqlText = SqlHelper.generateUpdateSqlTextByPlaceholder(entityProxy, entityProxy.getIdFieldName());

        return this.namedParameterJdbcOperations.query(sqlText, Map.of(PARAM_HOLDER_NAME, id), rs -> {
            if (rs.next()) {
                return Optional.of(ResultSetUtils.toEntity(rs, entityClass));
            } else {
                return Optional.empty();
            }
        });
    }

    private Optional<E> fetchByName() {
        EntityProxy entityProxy = Entity.getEntityProxy(entityClass);
        String sqlText = SqlHelper.generateUpdateSqlTextByPlaceholder(entityProxy, entityProxy.getNameFieldName());

        return this.namedParameterJdbcOperations.query(sqlText, Map.of(PARAM_HOLDER_NAME, name), rs -> {
            if (rs.next()) {
                return Optional.of(ResultSetUtils.toEntity(rs, entityClass));
            } else {
                return Optional.empty();
            }
        });
    }

    private Optional<E> fetchByWhere() {
        EntityProxy entityProxy = Entity.getEntityProxy(entityClass);
        String sqlText = SqlHelper.generateUpdateSqlTextByWhere(entityProxy, this);
        ParamMap conditionParamMap = ParamMap.of();
        this.where.writeToParamMap(conditionParamMap);

        return this.namedParameterJdbcOperations.query(sqlText, conditionParamMap.getParams(), rs -> {
            if (rs.next()) {
                return Optional.of(ResultSetUtils.toEntity(rs, entityClass));
            } else {
                return Optional.empty();
            }
        });
    }
}
