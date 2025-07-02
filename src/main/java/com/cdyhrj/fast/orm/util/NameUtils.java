package com.cdyhrj.fast.orm.util;

import java.util.Objects;

/**
 * FieldUtils
 *
 * @author huangqi
 */

public final class NameUtils {
    /**
     * 转化为下划线命名法
     *
     * @param name 待转换字符串
     * @return 转换后字符串
     */
    public static String toUnderScoreCase(String name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (Character.isUpperCase(ch)) {
                sb.append("_");
            }
            sb.append(Character.toLowerCase(ch));
        }

        return sb.toString();
    }

    /**
     * 转换为驼峰命名法
     *
     * @param name 待转换字符串
     * @return 转换后字符串
     */
    public static String toCamel(String name) {
        if (Objects.isNull(name)) {
            return "";
        }
        name = name.toLowerCase();
        boolean upperCase = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (ch == '_') {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(ch));
                upperCase = false;
            } else {
                sb.append(ch);
            }
        }

        return sb.toString();
    }
}
