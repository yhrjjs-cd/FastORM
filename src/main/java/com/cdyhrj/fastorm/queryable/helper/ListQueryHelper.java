package com.cdyhrj.fastorm.queryable.helper;

import com.alibaba.fastjson2.JSONArray;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.parameter.ParamMap;
import com.cdyhrj.fastorm.queryable.Queryable;
import com.cdyhrj.fastorm.queryable.context.TableAvailable;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Slf4j
public class ListQueryHelper<E extends Entity> {
    private Queryable<E> queryable;

    public static <E extends Entity> ListQueryHelper<E> of(Queryable<E> queryable) {
        ListQueryHelper<E> helper = new ListQueryHelper<E>();
        helper.queryable = queryable;

        return helper;
    }

    public List<E> query() {
        ParamMap paramMap = ParamMap.of();
        queryable.getCondition().writeToParamMap(paramMap);

        String sqlText = getQueryString("*");

        log.info(sqlText);
        log.info(paramMap.toString());

        return Collections.emptyList();
    }

    public JSONArray toJSONArray() {
        ParamMap paramMap = ParamMap.from(queryable.getCondition());
        String sqlText = getQueryString("*");

        return new JSONArray();
    }

    public int count() {
        ParamMap paramMap = ParamMap.from(queryable.getCondition());
        String sqlText = getQueryString("COUNT(*)");

        return 0;
    }

    public String getQueryString(String selectFields) {
        StringJoiner sj = new StringJoiner(" ");

        TableAvailable tableEntity = queryable.getContext().getBaseTableEntity();
        sj.add("SELECT").add(selectFields).add("FROM").add(tableEntity.toSql());

        if (Objects.nonNull(queryable.getJoins())) {
            sj.add(queryable.getJoins().toSql());
        }

        if (Objects.nonNull(queryable.getCondition())) {
            sj.add("WHERE")
                    .add(queryable.getCondition().toSql());
        }

        return sj.toString();
    }
}
