package com.cdyhrj.fastorm.lambda;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 属性名函数
 *
 * @author huangqi
 */
@FunctionalInterface
public interface PropFun<T, R> extends Function<T, R>, Serializable {
}
