package com.transsion.framework.tango.storage.clickhouse.view.expression;

import com.transsion.framework.tango.common.Utility;
import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.core.expression.RawExpression;

/**
 * @Author mengqi.lv
 * @Date 2022/8/31
 * @Version 1.0
 **/
public class RawExpressionGenerator extends ExpressionGenerator<RawExpression> {

    @Override
    public String genSelect() {
        RawExpression exp = getExpression();
        StringBuilder sb = new StringBuilder();
        sb.append("select (");
        if (Utility.isEmpty(exp.getProperties())) {
            sb.append("*");
        } else {
            boolean first = true;
            for (Property p : exp.getProperties()) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append("`").append(p.getName()).append("`");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String genEndClause() {
        return " limit " + getExpression().getLimit();
    }
}
