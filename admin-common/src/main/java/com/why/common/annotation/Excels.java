package com.why.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel注解集
 *
 * @author Crown
 */
/**
 * @description: 数据权限过滤注解
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/25
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Excels {

    Excel[] value();
}