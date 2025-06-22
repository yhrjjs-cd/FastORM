package com.cdyhrj.fastorm.entity.queryable.helper;

import com.alibaba.fastjson2.JSONArray;
import com.cdyhrj.fastorm.api.parameter.ParamMap;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.EntityQueryable;
import com.cdyhrj.fastorm.entity.queryable.context.TableAvailable;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Slf4j
public class ListQueryHelper<E extends Entity> {
    private EntityQueryable<E> queryable;

    public static <E extends Entity> ListQueryHelper<E> of(EntityQueryable<E> queryable) {
        ListQueryHelper<E> helper = new ListQueryHelper<E>();
        helper.queryable = queryable;

        return helper;
    }

    public List<E> query() {
        ParamMap paramMap = ParamMap.of();

        if (Objects.nonNull(queryable.getWhere())) {
            queryable.getWhere().writeToParamMap(paramMap);
        }

        String sqlText = getQueryString("*");

        log.info(sqlText);
        log.info(paramMap.toString());

        return Collections.emptyList();
    }

    public JSONArray toJSONArray() {
        ParamMap paramMap = ParamMap.of();

        if (Objects.nonNull(queryable.getWhere())) {
            queryable.getWhere().writeToParamMap(paramMap);
        }

        String sqlText = getQueryString("*");

        return new JSONArray();
    }

    public int count() {
        ParamMap paramMap = ParamMap.of();
        if (Objects.nonNull(queryable.getWhere())) {
            queryable.getWhere().writeToParamMap(paramMap);
        }
        String sqlText = getQueryString("COUNT(*)");

        return 0;
    }

    public String getQueryString(String selectFields) {
        StringJoiner sj = new StringJoiner(" ");

        TableAvailable tableEntity = queryable.getContext().getBaseTableEntity();
        sj.add("SELECT").add(selectFields).add("FROM").add(tableEntity.toQuerySql());

        if (Objects.nonNull(queryable.getJoins())) {
            sj.add(queryable.getJoins().toQuerySql());
        }

        if (Objects.nonNull(queryable.getWhere())) {
            sj.add(queryable.getWhere().toQuerySql());
        }

        if (Objects.nonNull(queryable.getOrderBy())) {
            sj.add(queryable.getOrderBy().toQuerySql());
        }

        return sj.toString();
    }
}
