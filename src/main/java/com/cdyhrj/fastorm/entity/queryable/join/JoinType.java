package com.cdyhrj.fastorm.entity.queryable.join;

/**
 * 连接类型
 */
public enum JoinType {
    /**
     * LEFT JOIN
     */
    LEFT {
        @Override
        String getSqlString() {
            return "LEFT JOIN";
        }
    },
    /**
     * RIGHT JOIN
     */
    RIGHT {
        @Override
        String getSqlString() {
            return "RIGHT JOIN";
        }
    },
    /**
     * INNER JOIN
     */
    INNER {
        @Override
        String getSqlString() {
            return "INNER JOIN";
        }
    };

    abstract String getSqlString();
}
