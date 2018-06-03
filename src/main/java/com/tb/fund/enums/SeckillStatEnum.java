package com.tb.fund.enums;

public enum SeckillStatEnum {
    SUCCESS(1, "秒杀成功"),
    END(0, "秒杀结束"),
    REPEAT_KILL(3, "重复秒杀"),
    INNER_ERROR(4, "系统异常"),
    DATA_REWRITE(5, "数据篡改");

    private int state;
    private String stateInfo;

    public static SeckillStatEnum stateOf(int index) {
        for (SeckillStatEnum state : values() ) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
    SeckillStatEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    SeckillStatEnum() {
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
