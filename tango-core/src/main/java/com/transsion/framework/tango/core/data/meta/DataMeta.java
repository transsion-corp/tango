package com.transsion.framework.tango.core.data.meta;

import com.transsion.framework.tango.common.Identifier;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public interface DataMeta {

    Identifier getId();

    long ttl();
}
