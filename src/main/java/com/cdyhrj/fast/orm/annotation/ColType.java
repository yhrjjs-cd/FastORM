package com.cdyhrj.fast.orm.annotation;

/**
 * 实体列类型
 *
 * @author huangqi
 */

public enum ColType {
    /**
     * 定长字符串
     */
    CHAR,

    /**
     * 字符串
     */
    VARCHAR,

    /**
     * 长文本
     */
    TEXT,

    /**
     * 布尔型，数据库以 数字 存储
     */
    BOOLEAN,

    /**
     * 二进制
     */
    BINARY,
    /**
     * 时间戳
     */
    TIMESTAMP,
    /**
     * 日期时间
     */
    DATETIME,

    /**
     * 日期
     */
    DATE,

    /**
     * 时间
     */
    TIME,

    /**
     * 整型
     */
    INT,
    /**
     * 浮点型
     */
    FLOAT,
    /**
     * JSON
     */
    JSON
}
