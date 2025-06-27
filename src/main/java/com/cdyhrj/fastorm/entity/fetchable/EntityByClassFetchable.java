package com.cdyhrj.fastorm.entity.fetchable;

import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.EntityProxy;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import com.cdyhrj.fastorm.util.ResultSetUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.util.Assert;

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
    private Where<E> where;

    public EntityByClassFetchable<E> id(Long id) {
        this.id = id;

        return this;
    }

    public EntityByClassFetchable<E> name(String name) {
        this.name = name;

        return this;
    }

    public Where<E> where() {
        if (Objects.isNull(this.where)) {
            this.where = new Where<>(context, this);
        }

        return this.where;
    }

    /**
     * Fetch entity by id
     * <blockquote><pre>
     *     Student fetch(long id {
     *         return fastORM.fetchable(Student.class)
     *                       .id(100L)
     *                       .fetchById()
     *     }
     * </pre></blockquote>
     *
     * @return entity object
     */
    public Optional<E> fetchById() {
        Assert.notNull(this.id, "id must not be null");

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

    public Optional<E> fetchByName() {
        Assert.notNull(name, "name must not be null");

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
}
