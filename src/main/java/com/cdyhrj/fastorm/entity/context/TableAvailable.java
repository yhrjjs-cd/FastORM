package com.cdyhrj.fastorm.entity.context;

import com.cdyhrj.fastorm.api.meta.SqlSegment;
import com.cdyhrj.fastorm.entity.queryablex.from.From;
import lombok.Data;

/**
 * 表实体，对应<code>表名</code>
 */
@Data
public class TableAvailable implements SqlSegment {
    public static TableAvailable of(String alias, From from) {
        TableAvailable newInstance = new TableAvailable();
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
        return "%s %s".formatted(from.getContent(), alias);
    }
}
