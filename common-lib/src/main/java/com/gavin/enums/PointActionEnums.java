package com.gavin.enums;

public enum PointActionEnums {

    POINT_ACTION_REWARD(1),
    POINT_ACTION_CONSUME(2),
    POINT_ACTION_EXPIRE(3);
    
    private Integer action;

    PointActionEnums(Integer action) {
        this.action = action;
    }

    public Integer getValue() {
        return action;
    }
}
