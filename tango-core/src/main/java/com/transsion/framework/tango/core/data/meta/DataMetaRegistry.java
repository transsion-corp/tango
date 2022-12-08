package com.transsion.framework.tango.core.data.meta;

import com.transsion.framework.tango.common.Identifier;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public interface DataMetaRegistry<T extends DataMeta> {

    void register(DataMeta meta);

    void deregister(DataMeta meta);

    T getDataMeta(Identifier metaId);

    boolean hasMeta(Identifier dataId);
}
