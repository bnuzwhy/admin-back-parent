package com.why.common.enums;

/**
 * @description: 数据权限过滤注解
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/25
 **/
public enum OnlineStatus {
    /**
     * 用户状态
     */
    on_line("在线"), off_line("离线");
    private final String info;

    OnlineStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

}