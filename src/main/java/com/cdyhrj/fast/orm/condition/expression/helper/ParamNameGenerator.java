package com.cdyhrj.fast.orm.condition.expression.helper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 参数名生成器
 *
 * @author huangqi
 */

public final class ParamNameGenerator {
    private static final AtomicInteger intPtr = new AtomicInteger(0);
    public static final Integer MAX_PARAM_INDEX = 10000;

    /**
     * 获取不冲突的参数名
     *
     * @return 参数名
     */
    public static String getNewParamName() {
        intPtr.compareAndExchange(MAX_PARAM_INDEX, 0);

        return String.format("P%s", intPtr.incrementAndGet());
    }
}
