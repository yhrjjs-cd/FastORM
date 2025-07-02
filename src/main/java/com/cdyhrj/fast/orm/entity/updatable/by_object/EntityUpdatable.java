package com.cdyhrj.fast.orm.entity.updatable.by_object;

import com.cdyhrj.fast.orm.annotation.enums.OperationType;
import com.cdyhrj.fast.orm.condition.ConditionHost;
import com.cdyhrj.fast.orm.entity.Entity;
import com.cdyhrj.fast.orm.entity.EntityProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;

@Slf4j
public class EntityUpdatable<E extends Entity> implements ConditionHost<E> {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final TransactionTemplate transactionTemplate;
    private final E entity;

    public EntityUpdatable(NamedParameterJdbcOperations namedParameterJdbcOperations, TransactionTemplate transactionTemplate, E entity) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.transactionTemplate = transactionTemplate;
        this.entity = entity;
    }

    /**
     * 执行插入操作
     *
     * @return 插入的行数
     */
    public int update() {
        EntityProxy entityProxy = Entity.getEntityProxy(entity.getClass());
        entityProxy.updateEntityWithDefaultValue(entity, OperationType.UPDATE);

        Map<String, Object> paramMap = entityProxy.getValueMap(entity);

        String sqlText = SqlHelper.generateUpdateSqlTextWithEntity(entityProxy, entity);
        return this.namedParameterJdbcOperations.update(sqlText, new MapSqlParameterSource(paramMap));
    }
}
