package com.transsion.framework.tango.core.dag;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.core.data.Data;

/**
 * @Author mengqi.lv
 * @Date 2022/8/29
 * @Version 1.0
 **/
public interface QueryDAG {

    QueryNode findViewNode(Identifier viewId);

    Data getData();
}
