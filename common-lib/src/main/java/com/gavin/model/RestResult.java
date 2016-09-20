package com.gavin.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RestResult<T> implements Serializable {

    private boolean fallback = false;

    private T data;

    public RestResult(boolean fallback) {
        this.fallback = fallback;
    }

    public RestResult(boolean fallback, T data) {
        this.fallback = fallback;
        this.data = data;
    }
}
