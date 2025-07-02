package com.cdyhrj.fast.orm.entity.deletable.by_object;

import com.cdyhrj.fast.orm.condition.ConditionHost;
import com.cdyhrj.fast.orm.entity.Entity;
import com.cdyhrj.fast.orm.entity.EntityProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;
import java.util.Objects;

@Slf4j
public class EntityDeletable<E extends Entity> implements ConditionHost<E> {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final TransactionTemplate transactionTemplate;
    private final E entity;

    public EntityDeletable(NamedParameterJdbcOperations namedParameterJdbcOperations, TransactionTemplate transactionTemplate, E entity) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.transactionTemplate = transactionTemplate;
        this.entity = entity;
    }

    public int execById() {
        EntityProxy entityProxy = Entity.getEntityProxy(entity.getClass());

        String sqlText = SqlHelper.generateDeleteSqlTextById(entityProxy);

        return this.namedParameterJdbcOperations.update(sqlText,
                Map.of("id", Objects.requireNonNull(entityProxy.getIdValue(entity), "id is null")));
    }

    public int execByName() {
        EntityProxy entityProxy = Entity.getEntityProxy(entity.getClass());

        String sqlText = SqlHelper.generateDeleteSqlTextByName(entityProxy);

        return this.namedParameterJdbcOperations.update(sqlText,
                Map.of("name", Objects.requireNonNull(entityProxy.getNameValue(entity), "name is null")));
    }
}
