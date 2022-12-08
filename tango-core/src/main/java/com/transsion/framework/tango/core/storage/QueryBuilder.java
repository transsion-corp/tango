package com.transsion.framework.tango.core.storage;

import com.transsion.framework.tango.core.view.View;

/**
 * @Author mengqi.lv
 * @Date 2022/8/29
 * @Version 1.0
 **/
public interface QueryBuilder<T extends Query> {

    T build();

    QueryBuilder<Query> setView(View view);
}
