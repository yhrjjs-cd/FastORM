package com.cdyhrj.fastorm.sql;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sql字符串辅助类
 */
public class SqlStringUtils {
    static final Pattern PATTER_VAR = Pattern.compile("\\$(\\w+)");

    /**
     * 抽取所有变量，$变量名
     *
     * @param template 模版
     * @return 变量列表
     */
    public static Set<String> extractVars(String template) {
        Set<String> vars = new HashSet<>();
        Matcher matcher = PATTER_VAR.matcher(template);

        while (matcher.find()) {
            vars.add(matcher.group(1));
        }

        return vars;
    }
}
