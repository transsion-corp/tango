package com.transsion.framework.tango.core.dag;

import com.transsion.framework.tango.core.storage.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/29
 * @Version 1.0
 **/
public abstract class AbstructQueryNode implements QueryNode {
    private Query query;
    private List<QueryNode> children;

    public void setQuery(Query query) {
        this.query = query;
    }

    public void setChildren(List<QueryNode> children) {
        this.children = children;
    }

    public void addChild(QueryNode node) {
        if (this.children == null) {
            this.children = new ArrayList<>(2);
            this.children.add(node);
        }
    }

    @Override
    public Query getQuery() {
        return query;
    }

    @Override
    public List<QueryNode> getChildren() {
        return children;
    }
}
