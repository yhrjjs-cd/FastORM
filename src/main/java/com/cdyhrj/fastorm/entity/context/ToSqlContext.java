package com.cdyhrj.fastorm.entity.context;

import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryablex.from.ClassFrom;
import com.cdyhrj.fastorm.entity.queryablex.from.From;
import com.cdyhrj.fastorm.exception.TableAliasNameDuplicateException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 上下文
 */
@Slf4j
public class ToSqlContext<E extends Entity, H extends ConditionHost<E>> {
    /**
     * 所属查询
     */
    @Getter
    private H belongTo;

    @Getter
    private From baseFrom;

    @Getter
    private Class<E> baseEntityClass;

    private final Map<String, TableAvailable> entityMap = new HashMap<>();

    public ToSqlContext(H belongTo, Class<E> baseEntityClass) {
        this.belongTo = belongTo;
        this.baseEntityClass = baseEntityClass;
        this.baseFrom = ClassFrom.of(baseEntityClass);

        TableAvailable newEntity = newEntityFrom(this.baseFrom);
        entityMap.put(baseEntityClass.getName(), newEntity);
    }

    public void addEntity(Class<? extends Entity> entityClass) {
        String alias = entityClass.getName();
        if (entityMap.containsKey(alias)) {
            throw new TableAliasNameDuplicateException(entityClass);
        }
        TableAvailable newEntity = newEntityFrom(ClassFrom.of(entityClass));

        entityMap.put(alias, newEntity);
    }

    public void addEntity(Class<? extends Entity> entityClass, String entityAlias) {
        if (entityMap.containsKey(entityAlias)) {
            throw new TableAliasNameDuplicateException(entityClass);
        }

        TableAvailable newEntity = newEntityFrom(ClassFrom.of(entityClass), entityAlias);

        entityMap.put(entityAlias, newEntity);
    }

    public TableAvailable getTableEntity(String key) {
        return Objects.requireNonNull(entityMap.get(key), "table '%s' not exists".formatted(key));
    }

    public TableAvailable getBaseTableEntity() {
        return getTableEntity(this.baseEntityClass.getName());
    }

    private TableAvailable newEntityFrom(From from) {
        String alias = "T%s".formatted(entityMap.size() + 1);

        return TableAvailable.of(alias, from);
    }

    private TableAvailable newEntityFrom(From from, String entityAlias) {
        return TableAvailable.of(entityAlias, from);
    }

}
