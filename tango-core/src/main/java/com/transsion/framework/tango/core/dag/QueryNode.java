package com.transsion.framework.tango.core.dag;

import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.storage.Query;
import com.transsion.framework.tango.core.view.View;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/29
 * @Version 1.0
 **/
public interface QueryNode {
    View getView();

    Query getQuery();

    List<QueryNode> getChildren();

    Data getData();

    void setParent(QueryNode pn);
}
