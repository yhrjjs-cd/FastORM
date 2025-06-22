package com.cdyhrj.fastorm.entity.deletable.by_object;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class EntityDeletable<E extends Entity> implements ConditionHost<E> {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final TransactionTemplate transactionTemplate;
    private final E entity;
    /**
     * 上下文
     */
    @Getter
    private final ToSqlContext<E, EntityDeletable<E>> context;

    public EntityDeletable(NamedParameterJdbcOperations namedParameterJdbcOperations, TransactionTemplate transactionTemplate, E entity) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.transactionTemplate = transactionTemplate;
        this.entity = entity;
        //noinspection unchecked
        this.context = new ToSqlContext<>(this, (Class<E>) entity.getClass());
    }

    private Where<E> where;

    public Where<E> where() {
        if (Objects.isNull(this.where)) {
            this.where = new Where<>(context, this);
        }

        return this.where;
    }

    public int exec() {
        EntityProxy entityProxy = Entity.getEntityProxy(entity.getClass());

        Map<String, Object> paramMap = new HashMap<>();

        if (Objects.nonNull(this.where)) {
            ParamMap conditionParamMap = ParamMap.of();
            this.where.writeToParamMap(conditionParamMap);

            paramMap.putAll(conditionParamMap.getParams());
        }

        String sqlText = SqlHelper.generateDeleteSqlText(entityProxy, paramMap, this);

        return this.namedParameterJdbcOperations.update(sqlText, paramMap);
    }
}
