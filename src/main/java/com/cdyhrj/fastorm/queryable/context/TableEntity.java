package com.cdyhrj.fastorm.queryable.context;

import com.cdyhrj.fastorm.meta.SqlNode;
import com.cdyhrj.fastorm.queryable.from.From;
import lombok.Data;

/**
 * 表实体，对应<code>表名</code>
 */
@Data
public class TableEntity implements SqlNode {
    public static TableEntity of(String alias, From from) {
        TableEntity newInstance = new TableEntity();
        newInstance.setAlias(alias);
        newInstance.setFrom(from);

        return newInstance;
    }

    /**
     * 别名
     */
    private String alias;

    /**
     * 对应的From
     */
    private From from;

    @Override
    public String toSql() {
        return "%s %s".formatted(from.getName(), alias);
    }
}
