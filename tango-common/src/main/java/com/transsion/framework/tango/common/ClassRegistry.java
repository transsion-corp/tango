package com.transsion.framework.tango.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2021/11/18
 * @Version 1.0
 **/
public class ClassRegistry<KeyClass, ValueClass> {
    private final Map<Class<? extends KeyClass>, Class<? extends ValueClass>> registry = new
            HashMap<>();
    private final Class<KeyClass> keyClass;

    public ClassRegistry(Class<KeyClass> keyClass) {
        if (keyClass == null) {
            throw new RuntimeException("keyClass is null");
        }
        this.keyClass = keyClass;
    }

    public void register(Class<? extends KeyClass> kc, Class<? extends ValueClass> vc) {
        registry.put(kc, vc);
    }

    public Class<? extends ValueClass> get(Class<? extends KeyClass> kc) {
        Class<? extends ValueClass> result = null;

        Class<? extends KeyClass> currentClass = kc;
        while (currentClass != null) {
            result = registry.get(currentClass);
            if (result != null) {
                return result;
            }

            Class<?> superClass = currentClass.getSuperclass();
            if (superClass == null || !keyClass.isAssignableFrom(superClass)) {
                break;
            }
            currentClass = (Class<? extends KeyClass>)superClass;
        }

        // not found
        return null;
    }
}
