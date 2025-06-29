package com.cdyhrj.fastorm.entity.queryablex.with;

import com.cdyhrj.fastorm.api.meta.SqlSegment;
import lombok.Getter;

public class With implements SqlSegment {
    @Getter
    private String name;

    @Getter
    private String content;

    public With(String name, String content) {
        this.name = name;
        this.content = content;
    }

    @Override
    public String toSql() {
        return "With";
    }
}
