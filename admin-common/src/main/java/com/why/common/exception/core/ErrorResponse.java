package com.why.common.exception.core;

import lombok.Getter;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/24
 **/
@Getter
public class ErrorResponse {
    private int code;
    private String message;
    private String path;
    private Instant timestamp;
    private HashMap<String,Object> data = new HashMap<>();

    public ErrorResponse(){ }

    public ErrorResponse(BaseException ex, String path){
        this(ex.getErrorStatus().code(), ex.getErrorStatus().message(),path,ex.getData());
    }

    public ErrorResponse(int code, String message, String path, Map<String, Object> data){
        this.code = code;
        this.message = message;
        this.path = path;
        this.timestamp = Instant.now();
        if(!ObjectUtils.isEmpty(data)){
            this.data.putAll(data);
        }
    }
}
