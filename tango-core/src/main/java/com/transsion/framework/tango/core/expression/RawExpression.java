package com.transsion.framework.tango.core.expression;

import com.transsion.framework.tango.common.property.Property;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/31
 * @Version 1.0
 **/
public class RawExpression implements Expression {
    private final List<Property> selectProperties;
    private int limit = 100;

    public RawExpression(List<Property> selectProperties) {
        this.selectProperties = selectProperties;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public ExpressionType getType() {
        return ExpressionType.RAW;
    }

    @Override
    public List<Property> getProperties() {
        return selectProperties;
    }
}
