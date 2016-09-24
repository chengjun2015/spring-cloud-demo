package com.gavin.enums;

public enum AuthorityEnums {

    AUTH_USER_READ(0),
    AUTH_USER_WRITE(1);

    private Integer authority;

    AuthorityEnums(Integer authority) {
        this.authority = authority;
    }

    public Integer getValue() {
        return authority;
    }
}
