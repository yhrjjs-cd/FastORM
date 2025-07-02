package com.cdyhrj.fast.orm.pager.impl;


import com.cdyhrj.fast.orm.api.parameter.ParamMap;
import com.cdyhrj.fast.orm.pager.IPagerProvider;
import com.cdyhrj.fast.orm.pager.Pager;

/**
 * Oracle分页实现
 *
 * @author huangqi
 */

public class MySql8PagerProvider implements IPagerProvider {
    @Override
    public String withSql(String sqlText, Pager pager) {
        return sqlText + " LIMIT :__L_P_O__, :__L_P_S__";
    }

    @Override
    public void writeToParamMap(ParamMap paramMap, Pager pager) {
        paramMap.add("__L_P_O__", pager.getOffset());
        paramMap.add("__L_P_S__", pager.getPageSize());
    }
}
