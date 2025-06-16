package com.cdyhrj.fastorm.util;

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
