package com.gavin.enums;

public enum PointDetailEnums {

    POINT_DETAIL_EARN(1),
    POINT_DETAIL_SPEND(2);

    private Integer detailFlag;

    PointDetailEnums(Integer detailFlag) {
        this.detailFlag = detailFlag;
    }

    public Integer getValue() {
        return detailFlag;
    }
}
