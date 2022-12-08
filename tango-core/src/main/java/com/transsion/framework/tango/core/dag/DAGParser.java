package com.transsion.framework.tango.core.dag;

import com.transsion.framework.tango.core.view.View;

/**
 * @Author mengqi.lv
 * @Date 2022/8/24
 * @Version 1.0
 **/
public interface DAGParser {
    QueryDAG parseDAG(View view);

    NodeGenerator getNodeGenerator();

    default QueryNode parseQuery(View view) {
        return getNodeGenerator().genNode(view);
    }
}
