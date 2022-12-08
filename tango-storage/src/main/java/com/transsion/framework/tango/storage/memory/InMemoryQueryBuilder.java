package com.transsion.framework.tango.storage.memory;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.common.Utility;
import com.transsion.framework.tango.core.storage.Query;
import com.transsion.framework.tango.core.storage.QueryBuilder;
import com.transsion.framework.tango.core.view.View;

/**
 * @Author mengqi.lv
 * @Date 2022/8/29
 * @Version 1.0
 **/
public class InMemoryQueryBuilder implements QueryBuilder<SimpleQuery> {

    private View view;

    @Override
    public SimpleQuery build() {
        SimpleQuery query = new SimpleQuery(view.getViewId(), view.getDataId());
        if (view.getExpression() != null && !Utility.isEmpty(view.getExpression().getProperties())) {
            query.setProperties(view.getExpression().getProperties());
        }
        return query;
    }

    @Override
    public QueryBuilder<Query> setView(View view) {
        this.view = view;
        return (QueryBuilder) this;
    }
}
