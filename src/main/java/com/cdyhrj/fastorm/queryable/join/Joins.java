package com.cdyhrj.fastorm.queryable.join;

import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.meta.SqlSegment;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Joins<T extends Entity> implements SqlSegment {
    @Getter
    private List<Join<T, ?, ?>> joins = new ArrayList<>();

    public Joins() {
    }

    public void addJoin(final Join<T, ?, ?> join) {
        this.joins.add(join);
    }

    @Override
    public String toSql() {
        return joins.stream()
                .map(Join::toSql)
                .collect(Collectors.joining(" "));
    }
}
