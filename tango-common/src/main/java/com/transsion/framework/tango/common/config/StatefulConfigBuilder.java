package com.transsion.framework.tango.common.config;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2021/5/12
 * @Version 1.0
 **/
public interface StatefulConfigBuilder<T extends StatefulConfig> {

    T build(String key, String configValue);

    List<T> buildList(String key, String configValue);
}
