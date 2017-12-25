package com.mmall.common;

/**
 * Created by yuanli on 2017/9/9.
 */
//枚举类：私有的构造器，私有的finall成员变量，公共的get方法，成员不能被改变
public enum ResponseCode {

    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),
    NEED_LOGIN(10, "NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;

    private ResponseCode(int conde, String desc) {
        this.code = conde;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
