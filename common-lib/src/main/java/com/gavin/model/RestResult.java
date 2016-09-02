package com.gavin.model;

import java.io.Serializable;

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

    public boolean isFallback() {
        return fallback;
    }

    public void setFallback(boolean fallback) {
        this.fallback = fallback;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
