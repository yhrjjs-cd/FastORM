package com.cdyhrj.fastorm.pager.impl;


import com.cdyhrj.fastorm.api.parameter.ParamMap;
import com.cdyhrj.fastorm.pager.IPagerProvider;
import com.cdyhrj.fastorm.pager.Pager;

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
