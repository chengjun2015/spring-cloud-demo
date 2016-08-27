package com.gavin.enums;

public enum PointActionEnums {

    POINT_ACTION_EARN(1),
    POINT_ACTION_SPEND(2);

    private Integer action;

    PointActionEnums(Integer action) {
        this.action = action;
    }

    public Integer getValue() {
        return action;
    }
}
