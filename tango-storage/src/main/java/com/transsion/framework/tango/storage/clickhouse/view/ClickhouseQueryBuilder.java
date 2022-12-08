package com.transsion.framework.tango.storage.clickhouse.view;

import com.transsion.framework.tango.core.storage.Query;
import com.transsion.framework.tango.core.storage.QueryBuilder;
import com.transsion.framework.tango.core.view.View;
import com.transsion.framework.tango.storage.clickhouse.view.condition.ConditionGenerator;
import com.transsion.framework.tango.storage.clickhouse.view.expression.ExpressionGenerator;

/**
 * @Author mengqi.lv
 * @Date 2022/8/31
 * @Version 1.0
 **/
public class ClickhouseQueryBuilder implements QueryBuilder<ClickhouseQuery> {

    private static final long THREE_HOUR_NANOS = 3L * 3600 * 1000 * 1000 * 1000;

    private View view;

    @Override
    public ClickhouseQuery build() {
        if (view == null) {
            return null;
        }

        try {
            StringBuilder sb = new StringBuilder();

            ExpressionGenerator expressionGenerator = ExpressionGenerator.newGenerator(view.getExpression());

            sb.append(expressionGenerator.genSelect());
            sb.append(" from ").append(view.getDataId().getGroup()).append(".`").append(view.getDataId().getName()).append("_all")
                    .append("` where (timestamp between ").append(getStartTimeNanos(view)).append(" and ").append(getEndTimeNanos(view)).append(")");
            if (view.getCondition() != null) {
                sb.append(" and ").append(ConditionGenerator.genCondition(view.getCondition()));
            }
            sb.append(expressionGenerator.genEndClause());

            ClickhouseQuery query = new ClickhouseQuery();
            query.setDb(view.getDataId().getGroup());
            query.setTable(view.getDataId().getName());
            query.setSql(sb.toString());
            query.setViewId(view.getViewId());
            query.setProperties(view.getExpression().getProperties());

            return query;
        } catch (Exception e) {
            // TODO add log
            return null;
        }
    }

    public long getStartTimeNanos(View view) {
        if (view.getStartTime() > 0) {
            return view.getStartTime();
        }
        return System.currentTimeMillis() * 1000 * 1000 - THREE_HOUR_NANOS;
    }

    public long getEndTimeNanos(View view) {
        if (view.getEndTime() > 0) {
            return view.getEndTime();
        }
        return System.currentTimeMillis() * 1000 * 1000;
    }

    @Override
    public QueryBuilder<Query> setView(View view) {
        this.view = view;
        return (QueryBuilder) this;
    }
}
