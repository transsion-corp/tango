package com.transsion.framework.tango.core.expression;

import com.transsion.framework.tango.common.property.Property;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/10
 * @Version 1.0
 **/
public interface Expression {

    ExpressionType getType();

    List<Property> getProperties();
}
