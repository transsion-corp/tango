package com.transsion.framework.tango.core.view;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.core.condition.Condition;
import com.transsion.framework.tango.core.expression.Expression;
import com.transsion.framework.tango.core.window.TimeWindow;

import java.util.Collections;
import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/16
 * @Version 1.0
 **/
public class DefaultView implements View {
    private Identifier viewId;
    private Identifier dataId;
    private long startTime;
    private long endTime;
    private TimeWindow timeWindow;
    private Condition condition;
    private Expression expression;

    @Override
    public List<View> getParents() {
        return Collections.EMPTY_LIST;
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
        return startTime;
    }

    @Override
    public long getEndTime() {
        return endTime;
    }

    @Override
    public Condition getCondition() {
        return condition;
    }

    @Override
    public Expression getExpression() {
        return expression;
    }


    public static final class DefaultViewBuilder {
        private Identifier viewId;
        private Identifier dataId;
        private long startTime;
        private long endTime;
        private Condition condition;
        private Expression expression;

        private DefaultViewBuilder() {
        }

        public static DefaultViewBuilder builder() {
            return new DefaultViewBuilder();
        }

        public DefaultViewBuilder viewId(Identifier viewId) {
            this.viewId = viewId;
            return this;
        }

        public DefaultViewBuilder dataId(Identifier dataId) {
            this.dataId = dataId;
            return this;
        }

        public DefaultViewBuilder startTime(long startTime) {
            this.startTime = startTime;
            return this;
        }

        public DefaultViewBuilder endTime(long endTime) {
            this.endTime = endTime;
            return this;
        }

        public DefaultViewBuilder condition(Condition condition) {
            this.condition = condition;
            return this;
        }

        public DefaultViewBuilder expression(Expression expression) {
            this.expression = expression;
            return this;
        }

        public DefaultView build() {
            DefaultView defaultView = new DefaultView();
            defaultView.dataId = this.dataId;
            defaultView.viewId = this.viewId;
            defaultView.condition = this.condition;
            defaultView.startTime = this.startTime;
            defaultView.endTime = this.endTime;
            defaultView.expression = this.expression;
            return defaultView;
        }
    }
}
