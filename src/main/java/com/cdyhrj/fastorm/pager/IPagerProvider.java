package com.cdyhrj.fastorm.pager;

import com.cdyhrj.fastorm.api.parameter.ParamMap;

/**
 * 分页提供
 *
 * @author huangqi
 */
public interface IPagerProvider {
    /**
     * Sql添加分页信息
     *
     * @param sqlText 原始SQL文本
     * @param pager   分页信息
     * @return 添加了分页信息的SQL文本
     */
    String withSql(String sqlText, Pager pager);


    /**
     * 添加参数
     *
     * @param paramMap 参数map
     * @param pager    分页信息
     */
    void writeToParamMap(ParamMap paramMap, Pager pager);
}
