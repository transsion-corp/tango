package com.transsion.framework.tango.core.dag;

import com.transsion.framework.tango.core.view.View;

/**
 * @Author mengqi.lv
 * @Date 2022/8/31
 * @Version 1.0
 **/
public interface NodeGenerator {

    QueryNode genNode(View view);
}
