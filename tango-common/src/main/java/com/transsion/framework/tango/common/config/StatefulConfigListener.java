package com.transsion.framework.tango.common.config;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2021/5/11
 * @Version 1.0
 **/
public interface StatefulConfigListener<T extends StatefulConfig> {

    void onInitial(List<T> configs);

    void onChange(T oldConfig, T newConfig);

    void onRemove(T config);

    void onInsert(T config);
}
