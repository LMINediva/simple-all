package com.lc.mybatis.simple.type;

/**
 * @author DELL
 * @date 2022/5/8 22:50
 */
public enum Enabled {
    enabled(1), //启用
    disabled(0);//禁用

    private final int value;

    private Enabled(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
