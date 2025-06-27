package com.cdyhrj.fastorm.entity.deletable.by_class;

import com.cdyhrj.fastorm.api.parameter.ParamMap;
import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.EntityProxy;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class EntityClassDeletable<E extends Entity> implements ConditionHost<E> {
    public static final String PARAM_HOLDER_NAME = "_P_P_P_";
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final TransactionTemplate transactionTemplate;
    private final Class<E> entityClass;
    /**
     * 上下文
     */
    @Getter
    private final ToSqlContext<E, EntityClassDeletable<E>> context;

    public EntityClassDeletable(NamedParameterJdbcOperations namedParameterJdbcOperations, TransactionTemplate transactionTemplate, Class<E> entityClass) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.transactionTemplate = transactionTemplate;
        this.entityClass = entityClass;
        this.context = new ToSqlContext<>(this, entityClass);
    }

    private Long id;
    private String name;
    private Where<E> where;

    public EntityClassDeletable<E> id(Long id) {
        this.id = id;

        return this;
    }

    public EntityClassDeletable<E> name(String name) {
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
     * execute update by id
     *
     * @return affected rows
     */
    public int execById() {
        Assert.notNull(this.id, "id must not be null");

        EntityProxy entityProxy = Entity.getEntityProxy(entityClass);

        String sqlText = SqlHelper.generateUpdateSqlTextByPlaceholder(entityProxy, entityProxy.getIdFieldName());

        return this.namedParameterJdbcOperations.update(sqlText, Map.of(PARAM_HOLDER_NAME, this.id));
    }

    /**
     * execute update by name
     *
     * @return affected rows
     */
    public int execByName() {
        Assert.notNull(this.name, "name must not be null");

        EntityProxy entityProxy = Entity.getEntityProxy(entityClass);

        String sqlText = SqlHelper.generateUpdateSqlTextByPlaceholder(entityProxy, entityProxy.getNameFieldName());

        return this.namedParameterJdbcOperations.update(sqlText, Map.of(PARAM_HOLDER_NAME, this.name));
    }

    /**
     * execute update by where
     *
     * @return affected rows
     */
    public int execByWhere() {
        Assert.notNull(where, "where must not be null");
        Assert.isTrue(!where.isEmpty(), "where must not be empty");

        EntityProxy entityProxy = Entity.getEntityProxy(entityClass);

        String sqlText = SqlHelper.generateUpdateSqlTextByWhere(entityProxy, this.where);

        ParamMap whereParamMap = ParamMap.of();
        this.where.writeToParamMap(whereParamMap);

        return this.namedParameterJdbcOperations.update(sqlText, whereParamMap.getParams());
    }
}
