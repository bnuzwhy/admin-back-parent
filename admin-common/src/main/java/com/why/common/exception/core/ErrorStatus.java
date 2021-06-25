package com.why.common.exception.core;

/**
 * @description:统一异常enum接口，自定义异常enum要实现此方法
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/24
 **/
public interface ErrorStatus {

    int code();

    String message();
}
