package com.why.common.annotation;

import java.lang.annotation.*;

/**
 * @description: 数据权限过滤注解
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/25
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 部门表的别名
     */
    String deptAlias() default "sys_dept";

    /**
     * 用户表的别名
     */
    String userAlias() default "sys_user";
}
