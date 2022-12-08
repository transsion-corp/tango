package com.transsion.framework.tango.metrics.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2022/9/15
 * @Version 1.0
 **/
public class Utils {

    public static Map<String, Object> newMap(String... keyvalues) {
        if (keyvalues == null) {
            return Collections.emptyMap();
        }
        Map<String, Object> map = new HashMap<>(keyvalues.length / 2);
        for (int i = 0; i < keyvalues.length - 1; i += 2) {
            map.put(keyvalues[i], keyvalues[i+1]);
        }
        return map;
    }
}
