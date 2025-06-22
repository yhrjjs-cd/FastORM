package com.cdyhrj.fastorm.api.meta;

public interface SqlSegment {
    /**
     * 用于查询的Sql，包含表别名
     *
     * @return 用于查询的SQL
     */
    String toSql();
}
