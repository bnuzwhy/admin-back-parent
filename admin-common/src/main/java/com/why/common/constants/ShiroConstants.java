package com.why.common.constants;

/**
 * @description: Shiro通用常量
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/25
 **/
public interface ShiroConstants {

    /**
     * 用户名
     */
    String CURRENT_USERNAME = "username";

    /**
     * 当前在线会话
     */
    String ONLINE_SESSION = "online_session";

    /**
     * 验证码key
     */
    String CURRENT_CAPTCHA = "captcha";

    /**
     * 登录记录缓存
     */
    String LOGINRECORDCACHE = "loginRecordCache";

    /**
     * 系统活跃用户缓存
     */
    String SYS_USERCACHE = "sys-userCache";
}