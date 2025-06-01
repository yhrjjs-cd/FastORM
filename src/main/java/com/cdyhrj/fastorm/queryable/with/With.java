package com.cdyhrj.fastorm.queryable.with;

import com.cdyhrj.fastorm.meta.SqlNode;
import lombok.Getter;

public class With implements SqlNode {
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
