package com.cdyhrj.fast.orm.sql;

import com.cdyhrj.fast.orm.api.parameter.ParamMap;
import com.cdyhrj.fast.orm.pager.IPagerProvider;
import com.cdyhrj.fast.orm.pager.Pager;
import com.cdyhrj.fast.orm.sql.where.AndGroup;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Sql查询
 *
 * @author huangqi
 * @noinspection unused
 */
@Slf4j
public class SqlQuery {
    @Getter
    private final IPagerProvider pagerProvider;

    /**
     * 条件插入位置
     */
    private static final String CONDITION_INSERT_POSITION = "{condition}";

    /**
     * 条件插入位置
     */
    private static final String CONDITION_APPEND_POSITION = "{append_condition}";

    /**
     * 单页Pager
     */
    private static final Pager PAGER_ONE_ROW = Pager.of(1, 1);

    /**
     * Jdbc Operations
     */
    protected NamedParameterJdbcOperations namedParameterJdbcOperations;

    public static SqlQuery of(NamedParameterJdbcOperations namedParameterJdbcOperations, IPagerProvider pagerProvider) {
        return new SqlQuery(namedParameterJdbcOperations, pagerProvider);
    }

    protected SqlQuery(NamedParameterJdbcOperations namedParameterJdbcOperations, IPagerProvider pagerProvider) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.pagerProvider = pagerProvider;
    }

    private String sql;

    /**
     * 所有变量
     */
    private final VarSet vars = new VarSet();

    /**
     * 所有参数
     */
    private final ParamMap paramMap = ParamMap.of();


    /**
     * 条件
     */
    private AndGroup where;

    /**
     * 分页信息
     */
    private Pager pager;

    /**
     * 设置S分页信息
     *
     * @param pager 分页信息
     * @return 当前实例
     */
    public SqlQuery pager(Pager pager) {
        this.pager = pager;

        return this;
    }


    /**
     * 设置SQL
     *
     * @param sql sql字符串传
     * @return 当前实例
     */
    public SqlQuery sql(String sql) {
        this.sql = sql;

        return this;
    }

    /**
     * 设置变量
     *
     * @param key   参数
     * @param value 变量值
     * @return 当前实例
     */
    public SqlQuery var(String key, String value) {
        this.vars.add(key, value);

        return this;
    }

    /**
     * 设置参数
     *
     * @param key   参数
     * @param value 参数值
     * @return 当前实例
     */
    public SqlQuery param(String key, Object value) {
        this.paramMap.add(key, value);

        return this;
    }


    public SqlQuery paramMap(Map<String, Object> params) {
        if (Objects.nonNull(params)) {
            params.forEach(this.paramMap::add);
        }

        return this;
    }

    /**
     * 设置条件
     *
     * @param where 条件
     * @return 当前实例
     */
    public SqlQuery where(AndGroup where) {
        this.where = where;

        return this;
    }

    /**
     * 查询列表
     *
     * @return 数据列表
     */
    public List<Map<String, Object>> query() {
        String sqlText = getQuerySqlText();
        ParamMap paramMap = getQueryParamMap();
        log.info("paramMap: {}", paramMap);

        return this.namedParameterJdbcOperations.queryForList(sqlText, paramMap.getParams());
    }

//    public <T> List<T> queryFieldForList(Class<T> classOfT) {
//        String sqlText = getQuerySqlText();
//        ParamMap paramMap = getQueryParamMap();
//
//        return this.namedParameterJdbcOperations.queryForList(sqlText, paramMap.getParams(), classOfT);
//    }

//    /**
//     * 查询 String 字段
//     *
//     * @return String
//     */
//    public String queryString() {
//        try {
//            String sqlText = getQueryLongSqlText();
//            ParamMap paramMap = getQueryParamMap();
//
//            return this.namedParameterJdbcOperations.queryForObject(sqlText, paramMap.getParams(), String.class);
//        } catch (Exception ex) {
//            return "";
//        }
//    }

//    /**
//     * 查询 Long 数字
//     *
//     * @return 数量
//     */
//    public Long queryLong() {
//        String sqlText = getQueryLongSqlText();
//        ParamMap paramMap = getQueryParamMap();
//
//        return this.namedParameterJdbcOperations.queryForObject(sqlText, paramMap.getParams(), Long.class);
//    }

    /**
     * 查询Map对象,如果不存在，返回:<b style=color:red>空对象</b>
     *
     * @return 查询结果
     */
    public Optional<?> fetch() {
        String sqlText = getFetchSqlText();
        ParamMap paramMap = getFetchParamMap();

        log.info("paramMap: {}", paramMap);

        try {
            return Optional.of(this.namedParameterJdbcOperations.queryForMap(sqlText, paramMap.getParams()));
        } catch (Exception e) {
            return Optional.empty();
        }

    }

    /**
     * 查找SQL对应的所有字段名,
     *
     * @return 所有的字段名代表
     */
    public List<String> queryMetaFieldNames() {
        List<String> fields = Lists.newArrayList();
        this.namedParameterJdbcOperations.getJdbcOperations().query(this.sql, rs -> {
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            for (int i = 1, size = resultSetMetaData.getColumnCount(); i <= size; i++) {
                fields.add(resultSetMetaData.getColumnName(i));
            }
        });

        return fields;
    }

    private String getQuerySqlText() {
        String sqlText = getSourceSqlText();

        if (Objects.nonNull(this.pager)) {
            sqlText = pagerProvider.withSql(sqlText, this.pager);
        }

        return sqlText;
    }

    private String getQueryLongSqlText() {
        return getSourceSqlText();
    }


    private String getSourceSqlText() {
        String sqlText = this.sql;

        if (Objects.nonNull(this.where)) {
            if (this.sql.contains(CONDITION_APPEND_POSITION)) {
                sqlText = sqlText.replace(CONDITION_APPEND_POSITION, " AND " + this.where.toSql());
            } else if (this.sql.contains(CONDITION_INSERT_POSITION)) {
                sqlText = sqlText.replace(CONDITION_INSERT_POSITION, " WHERE " + this.where.toSql());
            } else {
                sqlText += " WHERE " + this.where.toSql();
            }
        } else {
            if (this.sql.contains(CONDITION_APPEND_POSITION)) {
                sqlText = sqlText.replace(CONDITION_APPEND_POSITION, "");
            }

            if (this.sql.contains(CONDITION_INSERT_POSITION)) {
                sqlText = sqlText.replace(CONDITION_INSERT_POSITION, "");
            }
        }

        sqlText = this.updateVars(sqlText);

        return sqlText;
    }

    private ParamMap getQueryParamMap() {
        if (Objects.nonNull(this.pager)) {
            pagerProvider.writeToParamMap(paramMap, this.pager);
        }

        if (Objects.nonNull(this.where)) {
            this.where.writeToParamMap(paramMap);
        }

        return paramMap;
    }

    private String getFetchSqlText() {
        String sqlText = this.sql;
        sqlText = this.updateVars(sqlText);
        sqlText = pagerProvider.withSql(sqlText, PAGER_ONE_ROW);

        return sqlText;
    }

    private ParamMap getFetchParamMap() {
        pagerProvider.writeToParamMap(paramMap, PAGER_ONE_ROW);

        if (Objects.nonNull(this.where)) {
            this.where.writeToParamMap(paramMap);
        }

        return paramMap;
    }

    /**
     * 更新vars
     *
     * @param sqlText sql文本
     * @return 更新vars后的sql文本
     */
    private String updateVars(String sqlText) {
        String targetText = sqlText;
        Set<String> usedVars = SqlStringUtils.extractVars(sqlText);
        if (!usedVars.isEmpty()) {
            for (String usedVar : usedVars) {
                String targetValue = "";
                if (this.vars.hasVar(usedVar)) {
                    targetValue = this.vars.get(usedVar);
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("var '{}' is not used!", usedVar);
                    }
                }

                targetText = targetText.replace("$%s".formatted(usedVar), targetValue);
            }
        }
        return targetText;
    }
}
