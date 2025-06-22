package com.cdyhrj.fastorm.entity.deletable.by_class;

import com.cdyhrj.fastorm.annotation.enums.OperationType;
import com.cdyhrj.fastorm.api.chain.Chain;
import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.api.parameter.ParamMap;
import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.EntityProxy;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.lang.NonNull;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class EntityClassDeletable<E extends Entity> implements ConditionHost<E> {
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

    private Where<E> where;

    public Where<E> where() {
        if (Objects.isNull(this.where)) {
            this.where = new Where<>(context, this);
        }

        return this.where;
    }

    public int exec() {
        EntityProxy entityProxy = Entity.getEntityProxy(entityClass);

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
