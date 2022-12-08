package com.transsion.framework.tango.core.storage;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.common.property.Property;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/26
 * @Version 1.0
 **/
public interface Query {

    Identifier getViewId();

    List<Property> getProperties();
}
