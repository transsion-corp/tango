package com.transsion.framework.tango.core.aggregation;

import com.transsion.framework.tango.common.property.NamedType;

/**
 * @Author mengqi.lv
 * @Date 2022/8/10
 * @Version 1.0
 **/
public enum AggregateType {
    LONG_SUM(NamedType.LONG),
    LONG_AVG(NamedType.DOUBLE),
    LONG_MAX(NamedType.LONG),
    LONG_MIN(NamedType.LONG),
    LONG_LAST(NamedType.LONG),
    LONG_HISTOGRAM(NamedType.LONG),
    DOUBLE_SUM(NamedType.DOUBLE),
    DOUBLE_AVG(NamedType.DOUBLE),
    DOUBLE_MAX(NamedType.DOUBLE),
    DOUBLE_MIN(NamedType.DOUBLE),
    DOUBLE_LAST(NamedType.DOUBLE),
    DOUBLE_HISTOGRAM(NamedType.DOUBLE);

    NamedType valueType;

    AggregateType(NamedType valueType) {
        this.valueType = valueType;
    }
}
