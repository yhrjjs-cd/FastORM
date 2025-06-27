package com.cdyhrj.fastorm.entity.updatable.by_class;

import com.cdyhrj.fastorm.annotation.enums.OperationType;
import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.api.parameter.ParamMap;
import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.EntityProxy;
import com.cdyhrj.fastorm.entity.base.AbstractEntityByClassParamSetter;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.lang.NonNull;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class EntityClassUpdatable<E extends Entity>
        extends AbstractEntityByClassParamSetter<E, EntityClassUpdatable<E>>
        implements ConditionHost<E> {
    public static final String PARAM_HOLDER_NAME = "_P_P_P_";

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final TransactionTemplate transactionTemplate;
    private final Class<E> entityClass;
    private final ToSqlContext<E, EntityClassUpdatable<E>> context;

    public EntityClassUpdatable(NamedParameterJdbcOperations namedParameterJdbcOperations, TransactionTemplate transactionTemplate, Class<E> entityClass) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.transactionTemplate = transactionTemplate;
        this.entityClass = entityClass;
        this.context = new ToSqlContext<>(this, entityClass);
    }

    private Long id;
    private String name;
    private Where<E> where;

    public EntityClassUpdatable<E> id(Long id) {
        this.id = id;

        return this;
    }

    public EntityClassUpdatable<E> name(String name) {
        this.name = name;

        return this;
    }

    public Where<E> where() {
        if (Objects.isNull(this.where)) {
            this.where = new Where<>(context, this);
        }

        return this.where;
    }

    public int exec() {
        if (Objects.nonNull(this.id)) {
            return this.execById();
        } else if (Objects.nonNull(this.name)) {
            return this.execByName();
        } else {
            return this.execByWhere();
        }
    }

    /**
     * execute update by id
     *
     * @return affected rows
     */
    public int execById() {
        Assert.notNull(this.id, "id must not be null");

        Map<String, Object> params = this.paramChainToMap();
        Assert.isTrue(!params.isEmpty(), "Please set update field!");

        EntityProxy entityProxy = Entity.getEntityProxy(entityClass);

        Map<String, Object> paramMap = entityProxy.getDefaultValueMap(OperationType.UPDATE);
        paramMap.putAll(params);

        String sqlText = SqlHelper.generateUpdateSqlTextByPlaceholder(entityProxy, paramMap, entityProxy.getIdFieldName());
        paramMap.put(PARAM_HOLDER_NAME, this.id);

        return this.namedParameterJdbcOperations.update(sqlText, paramMap);
    }

    /**
     * execute update by name
     *
     * @return affected rows
     */
    public int execByName() {
        Assert.notNull(this.name, "name must not be null");

        Map<String, Object> params = this.paramChainToMap();
        Assert.isTrue(!params.isEmpty(), "Please set update field!");

        EntityProxy entityProxy = Entity.getEntityProxy(entityClass);

        Map<String, Object> paramMap = entityProxy.getDefaultValueMap(OperationType.UPDATE);
        paramMap.putAll(params);

        String sqlText = SqlHelper.generateUpdateSqlTextByPlaceholder(entityProxy, paramMap, entityProxy.getNameFieldName());
        paramMap.put(PARAM_HOLDER_NAME, this.name);


        return this.namedParameterJdbcOperations.update(sqlText, paramMap);
    }

    /**
     * execute update by where
     *
     * @return affected rows
     */
    public int execByWhere() {
        Assert.notNull(this.where, "where must not be null");
        Assert.isTrue(!this.where.isEmpty(), "where must not be empty");

        Map<String, Object> params = this.paramChainToMap();
        Assert.isTrue(!params.isEmpty(), "Please set update field!");

        EntityProxy entityProxy = Entity.getEntityProxy(entityClass);

        Map<String, Object> paramMap = entityProxy.getDefaultValueMap(OperationType.UPDATE);
        paramMap.putAll(params);

        String sqlText = SqlHelper.generateUpdateSqlTextByWhere(entityProxy, paramMap, this.where);

        ParamMap conditionParamMap = ParamMap.of();
        this.where.writeToParamMap(conditionParamMap);
        paramMap.putAll(conditionParamMap.getParams());

        return this.namedParameterJdbcOperations.update(sqlText, paramMap);
    }

    public int updateField(@NonNull PropFn<E, ?> PFun1, Object value1) {
        return this.set(PFun1, value1)
                .exec();
    }

    public int updateField(@NonNull PropFn<E, ?> PFun1, Object value1,
                           @NonNull PropFn<E, ?> PFun2, Object value2) {
        return this.set(PFun1, value1)
                .set(PFun2, value2)
                .exec();
    }

    public int updateField(@NonNull PropFn<E, ?> PFun1, Object value1,
                           @NonNull PropFn<E, ?> PFun2, Object value2,
                           @NonNull PropFn<E, ?> PFun3, Object value3) {
        return this.set(PFun1, value1)
                .set(PFun2, value2)
                .set(PFun3, value3)
                .exec();
    }


    public int updateField(@NonNull PropFn<E, ?> PFun1, Object value1,
                           @NonNull PropFn<E, ?> PFun2, Object value2,
                           @NonNull PropFn<E, ?> PFun3, Object value3,
                           @NonNull PropFn<E, ?> PFun4, Object value4) {
        return this.set(PFun1, value1)
                .set(PFun2, value2)
                .set(PFun3, value3)
                .set(PFun4, value4)
                .exec();
    }

    public int updateField(@NonNull PropFn<E, ?> PFun1, Object value1,
                           @NonNull PropFn<E, ?> PFun2, Object value2,
                           @NonNull PropFn<E, ?> PFun3, Object value3,
                           @NonNull PropFn<E, ?> PFun4, Object value4,
                           @NonNull PropFn<E, ?> PFun5, Object value5) {
        return this.set(PFun1, value1)
                .set(PFun2, value2)
                .set(PFun3, value3)
                .set(PFun4, value4)
                .set(PFun5, value5)
                .exec();
    }

    public int updateField(@NonNull PropFn<E, ?> PFun1, Object value1,
                           @NonNull PropFn<E, ?> PFun2, Object value2,
                           @NonNull PropFn<E, ?> PFun3, Object value3,
                           @NonNull PropFn<E, ?> PFun4, Object value4,
                           @NonNull PropFn<E, ?> PFun5, Object value5,
                           @NonNull PropFn<E, ?> PFun6, Object value6) {
        return this.set(PFun1, value1)
                .set(PFun2, value2)
                .set(PFun3, value3)
                .set(PFun4, value4)
                .set(PFun5, value5)
                .set(PFun6, value6)
                .exec();
    }

    public int updateField(@NonNull PropFn<E, ?> PFun1, Object value1,
                           @NonNull PropFn<E, ?> PFun2, Object value2,
                           @NonNull PropFn<E, ?> PFun3, Object value3,
                           @NonNull PropFn<E, ?> PFun4, Object value4,
                           @NonNull PropFn<E, ?> PFun5, Object value5,
                           @NonNull PropFn<E, ?> PFun6, Object value6,
                           @NonNull PropFn<E, ?> PFun7, Object value7) {
        return this.set(PFun1, value1)
                .set(PFun2, value2)
                .set(PFun3, value3)
                .set(PFun4, value4)
                .set(PFun5, value5)
                .set(PFun6, value6)
                .set(PFun7, value7)
                .exec();
    }

    public int updateField(@NonNull PropFn<E, ?> PFun1, Object value1,
                           @NonNull PropFn<E, ?> PFun2, Object value2,
                           @NonNull PropFn<E, ?> PFun3, Object value3,
                           @NonNull PropFn<E, ?> PFun4, Object value4,
                           @NonNull PropFn<E, ?> PFun5, Object value5,
                           @NonNull PropFn<E, ?> PFun6, Object value6,
                           @NonNull PropFn<E, ?> PFun7, Object value7,
                           @NonNull PropFn<E, ?> PFun8, Object value8) {
        return this.set(PFun1, value1)
                .set(PFun2, value2)
                .set(PFun3, value3)
                .set(PFun4, value4)
                .set(PFun5, value5)
                .set(PFun6, value6)
                .set(PFun7, value7)
                .set(PFun8, value8)
                .exec();
    }

    public int updateField(@NonNull PropFn<E, ?> PFun1, Object value1,
                           @NonNull PropFn<E, ?> PFun2, Object value2,
                           @NonNull PropFn<E, ?> PFun3, Object value3,
                           @NonNull PropFn<E, ?> PFun4, Object value4,
                           @NonNull PropFn<E, ?> PFun5, Object value5,
                           @NonNull PropFn<E, ?> PFun6, Object value6,
                           @NonNull PropFn<E, ?> PFun7, Object value7,
                           @NonNull PropFn<E, ?> PFun8, Object value8,
                           @NonNull PropFn<E, ?> PFun9, Object value9) {
        return this.set(PFun1, value1)
                .set(PFun2, value2)
                .set(PFun3, value3)
                .set(PFun4, value4)
                .set(PFun5, value5)
                .set(PFun6, value6)
                .set(PFun7, value7)
                .set(PFun8, value8)
                .set(PFun9, value9)
                .exec();
    }
}
