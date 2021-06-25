package com.why.common.annotation;

import org.springframework.http.HttpStatus;

import java.lang.annotation.*;

/**
 * @description: 包装API返回注解
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/25
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UnifiedReturn {

    /**
     * 是否包装返回
     */
    boolean wrapper() default false;

    /**
     * 正常返回httpcode码
     */
    HttpStatus status() default HttpStatus.OK;
}