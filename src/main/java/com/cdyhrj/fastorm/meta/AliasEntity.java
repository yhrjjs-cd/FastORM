package com.cdyhrj.fastorm.meta;

import com.cdyhrj.fastorm.entity.Entity;
import lombok.Data;

/**
 * 别名实体，对应<code>表名</code>
 */
@Data
public class AliasEntity<E extends Entity> implements Node {
    public static <S extends Entity> AliasEntity<S> of(String alias, Class<S> entityClass) {
        AliasEntity<S> newInstance = new AliasEntity<>();
        newInstance.setAlias(alias);
        newInstance.setEntityClass(entityClass);
        newInstance.setTableName(entityClass.getSimpleName());

        return newInstance;
    }

    /**
     * 别名
     */
    private String alias;

    /**
     * 实体类名
     */
    private Class<E> entityClass;

    /**
     * 对应的表名
     */
    private String tableName;

    @Override
    public String toSqlString() {
        return "%s %s".formatted(tableName, alias);
    }
}
