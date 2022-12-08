package com.transsion.framework.tango.core.dag;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.common.Utility;
import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2022/8/29
 * @Version 1.0
 **/
public class DefaultDAGParser implements DAGParser {

    private final NodeGenerator nodeGenerator;

    public DefaultDAGParser(NodeGenerator nodeGenerator) {
        this.nodeGenerator = nodeGenerator;
    }

    @Override
    public QueryDAG parseDAG(View view) {
        Map<Identifier, QueryNode> map = new HashMap<>();
        createNodes(view, map);
        return new QueryDAG() {
            @Override
            public QueryNode findViewNode(Identifier viewId) {
                return map.get(viewId);
            }

            @Override
            public Data getData() {
                return findViewNode(view.getViewId()).getData();
            }
        };
    }

    @Override
    public NodeGenerator getNodeGenerator() {
        return nodeGenerator;
    }

    private void createNodes(View view, Map<Identifier, QueryNode> result) {
        if (!Utility.isEmpty(view.getParents())) {
            for (View pv : view.getParents()) {
                if (!result.containsKey(pv.getViewId())) {
                    createNodes(pv, result);
                }
            }
        }

        QueryNode node = nodeGenerator.genNode(view);
        if (!Utility.isEmpty(view.getParents())) {
            for (View pv : view.getParents()) {
                QueryNode pn = result.get(pv.getViewId());
                pn.getChildren().add(node);
                node.setParent(pn);
            }
        }
        result.put(view.getViewId(), node);
        return;
    }
}
