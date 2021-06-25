package com.why.common.enums;

/**
 * @description: 用户状态
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/25
 **/
public enum UserStatus {
    OK("0", "正常"),
    DISABLE("1", "停用");

    private final String code;
    private final String info;

    UserStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
