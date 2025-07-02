package com.cdyhrj.fast.orm.util;

import java.util.UUID;

/**
 * IdUtils
 *
 * @author huangqi
 */

public class IdUtils {
    public static String newGuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
