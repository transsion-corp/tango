package com.transsion.framework.tango.core.condition;

/**
 * @Author mengqi.lv
 * @Date 2022/8/31
 * @Version 1.0
 **/
public abstract class EqualsCondition<T> extends Condition<T> {

    public static class StringEqualsCondition extends EqualsCondition<String> {

        private String param;

        public void setParam(String param) {
            this.param = param;
        }

        @Override
        public String getParam() {
            return param;
        }
    }
}
