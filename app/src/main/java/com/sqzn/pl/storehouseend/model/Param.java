package com.sqzn.pl.storehouseend.model;

/**
 * Created by Administrator on 2018/9/28.
 */

public class Param {
    String key;
    String value;

    public Param() {
    }

    public Param(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
