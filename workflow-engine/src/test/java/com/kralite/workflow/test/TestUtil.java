package com.kralite.workflow.test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChenDaLin on 2019/2/1.
 */
public class TestUtil {
    static public Map asMap(Object key, Object value) {
        Map map = new HashMap();
        map.put(key, value);
        return map;
    }
}
