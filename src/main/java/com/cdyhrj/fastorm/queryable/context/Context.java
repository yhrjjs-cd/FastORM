package com.cdyhrj.fastorm.queryable.context;

import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.exception.TableAliasNameException;
import com.cdyhrj.fastorm.queryable.Queryable;
import com.cdyhrj.fastorm.queryable.from.ClassFrom;
import com.cdyhrj.fastorm.queryable.from.From;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 上下文
 */
@Slf4j
public class Context<E extends Entity> {
    /**
     * 所属查询
     */
    @Getter
    private Queryable<E> belongTo;

    @Getter
    private From baseFrom;

    @Getter
    private Class<E> baseEntityClass;

    private final Map<String, TableAvailable> entityMap = new HashMap<>();

    public Context(Queryable<E> belongTo, Class<E> baseEntityClass) {
        this.belongTo = belongTo;
        this.baseEntityClass = baseEntityClass;
        this.baseFrom = ClassFrom.of(baseEntityClass);

        TableAvailable newEntity = newEntityFrom(this.baseFrom);
        entityMap.put(baseEntityClass.getName(), newEntity);
    }

    public void addEntity(Class<? extends Entity> entityClass) {
        String alias = entityClass.getName();
        if (entityMap.containsKey(alias)) {
            throw new TableAliasNameException();
        }
        TableAvailable newEntity = newEntityFrom(ClassFrom.of(entityClass));

        entityMap.put(alias, newEntity);
    }

    public void addEntity(Class<? extends Entity> entityClass, String entityAlias) {
        if (entityMap.containsKey(entityAlias)) {
            throw new TableAliasNameException();
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
