package com.why.common.exception.core;

import com.why.common.exception.admin.AdminResultCode;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/24
 **/
public abstract class BaseException extends RuntimeException{
    @Getter
    private ErrorStatus errorStatus;
    @Getter
    private final HashMap<String,Object> data = new HashMap<>(16);

    public BaseException(ErrorStatus errorStatus){
        super(errorStatus.message());
        this.errorStatus = errorStatus;
    }

    public BaseException(ErrorStatus errorStatus, Map<String,Object> data){
        super(errorStatus.message());
        this.errorStatus = errorStatus;
        if(!ObjectUtils.isEmpty(data)){
            this.data.putAll(data);
        }
    }

    public BaseException(ErrorStatus errorStatus, Map<String,Object> data, Throwable cause){
        super(errorStatus.message(),cause);
        this.errorStatus =errorStatus;
        if(!ObjectUtils.isEmpty(data)){
            this.data.putAll(data);
        }
    }

    public BaseException(int code, String message, Map<String,Object> data){
        super(message);
        this.errorStatus = getErrorStatus(code,message);
        if(!ObjectUtils.isEmpty(data)){
            this.data.putAll(data);
        }
    }
    public BaseException(int code, String message, Map<String,Object> data, Throwable cause){
        super(message,cause);
        this.errorStatus = getErrorStatus(code,message);
        if(!ObjectUtils.isEmpty(data)){
            this.data.putAll(data);
        }
    }

    public BaseException(AdminResultCode resultCode){
        super(resultCode.message());
        this.errorStatus = getErrorStatus(resultCode.code(), resultCode.message());
    }

    public static ErrorStatus getErrorStatus(int code, String message){
        return new ErrorStatus() {
            @Override
            public int code() {
                return code;
            }

            @Override
            public String message() {
                return message;
            }
        };
    }
}
