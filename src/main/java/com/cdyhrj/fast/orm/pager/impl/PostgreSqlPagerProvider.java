package com.cdyhrj.fast.orm.pager.impl;

import com.cdyhrj.fast.orm.api.parameter.ParamMap;
import com.cdyhrj.fast.orm.pager.IPagerProvider;
import com.cdyhrj.fast.orm.pager.Pager;

/**
 * Oracle分页实现
 *
 * @author huangqi
 */

public class PostgreSqlPagerProvider implements IPagerProvider {

    @Override
    public String withSql(String sqlText, Pager pager) {
        return sqlText + " LIMIT :__L__ OFFSET :__L_O__";
    }

    @Override
    public void writeToParamMap(ParamMap paramMap, Pager pager) {
        paramMap.add("__L__", pager.getPageSize());
        paramMap.add("__L_O__", pager.getOffset());
    }
}
