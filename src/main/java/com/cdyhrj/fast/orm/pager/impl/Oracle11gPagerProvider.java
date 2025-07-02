package com.cdyhrj.fast.orm.pager.impl;

import com.cdyhrj.fast.orm.api.parameter.ParamMap;
import com.cdyhrj.fast.orm.pager.IPagerProvider;
import com.cdyhrj.fast.orm.pager.Pager;

/**
 * Oracle分页实现
 *
 * @author huangqi
 */

public class Oracle11gPagerProvider implements IPagerProvider {
    @Override
    public String withSql(String sqlText, Pager pager) {
        return "SELECT * FROM (SELECT T.*, ROWNUM RN FROM (" +
                sqlText +
                ") T WHERE ROWNUM <= :__R_N_T__) WHERE RN > :__R_N_F__";
    }

    @Override
    public void writeToParamMap(ParamMap paramMap, Pager pager) {
        paramMap.add("__R_N_T__", pager.getOffset() + pager.getPageSize());
        paramMap.add("__R_N_F__", pager.getOffset());
    }
}
