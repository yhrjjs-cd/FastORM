package com.cdyhrj.fastorm.entity.updatable.by_object;

import com.cdyhrj.fastorm.annotation.enums.OperationType;
import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.EntityProxy;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import lombok.Getter;
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
    /**
     * 上下文
     */
    @Getter
    private final ToSqlContext<E, EntityUpdatable<E>> context;

    public EntityUpdatable(NamedParameterJdbcOperations namedParameterJdbcOperations, TransactionTemplate transactionTemplate, E entity) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.transactionTemplate = transactionTemplate;
        this.entity = entity;
        //noinspection unchecked
        this.context = new ToSqlContext<>(this, (Class<E>) entity.getClass());
    }

    /**
     * 执行插入操作
     *
     * @return 插入的行数
     */
    public int exec() {
        EntityProxy entityProxy = Entity.getEntityProxy(entity.getClass());
        entityProxy.updateEntityWithDefaultValue(entity, OperationType.UPDATE);

        Map<String, Object> paramMap = entityProxy.getValueMap(entity);

        String sqlText = SqlHelper.generateUpdateSqlTextWithEntity(entityProxy, entity);
        return this.namedParameterJdbcOperations.update(sqlText, new MapSqlParameterSource(paramMap));
    }
}
