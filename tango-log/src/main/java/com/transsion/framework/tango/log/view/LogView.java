package com.transsion.framework.tango.log.view;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.common.Utility;
import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.core.condition.Condition;
import com.transsion.framework.tango.core.expression.Expression;
import com.transsion.framework.tango.core.expression.RawExpression;
import com.transsion.framework.tango.core.view.View;

import java.util.Collections;
import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/10/24
 * @Version 1.0
 **/
public class LogView implements View {

    private final Identifier viewId;
    private final Identifier dataId;
    private long startTimeNanos = -1;
    private long endTimeNanos = -1;
    private Condition condition;

    private int limit = 500;
    private List<Property> properties;

    public LogView(String biz, String scenario) {
        this(new Identifier("__log_view__", Utility.nextRandom(100000) + ""), biz, scenario);
    }

    public LogView(Identifier viewId, String biz, String scenario) {
        this.viewId = viewId;
        this.dataId = new Identifier(biz, scenario);
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    @Override
    public List<View> getParents() {
        return Collections.emptyList();
    }

    @Override
    public Identifier getViewId() {
        return viewId;
    }

    @Override
    public Identifier getDataId() {
        return dataId;
    }

    @Override
    public long getStartTime() {
        return startTimeNanos;
    }

    @Override
    public long getEndTime() {
        return endTimeNanos;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setStartTimeNanos(long startTimeNanos) {
        this.startTimeNanos = startTimeNanos;
    }

    public void setEndTimeNanos(long endTimeNanos) {
        this.endTimeNanos = endTimeNanos;
    }

    @Override
    public Condition getCondition() {
        return condition;
    }

    @Override
    public Expression getExpression() {
        return new RawExpression(properties);
    }
}
