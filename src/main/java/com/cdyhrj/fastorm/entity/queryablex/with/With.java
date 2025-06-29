package com.cdyhrj.fastorm.entity.queryablex.with;

import com.cdyhrj.fastorm.api.meta.SqlSegment;
import com.cdyhrj.fastorm.entity.queryablex.EntityByClassQueryableX;
import lombok.Getter;

public class With implements SqlSegment {
    public static With of(String name, EntityByClassQueryableX<?> queryableX) {
        return new With(name, queryableX);
    }

    @Getter
    private String name;

    private final EntityByClassQueryableX<?> queryableX;

    public With(String name, EntityByClassQueryableX<?> queryableX) {
        this.name = name;
        this.queryableX = queryableX;
    }

    @Override
    public String toSql() {
        return "%s AS (%s)".formatted(name, queryableX.getSqlText());
    }
}
