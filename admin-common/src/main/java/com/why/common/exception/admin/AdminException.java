package com.why.common.exception.admin;

import com.why.common.exception.core.BaseException;
import com.why.common.exception.core.ErrorStatus;

import java.util.Map;

/**
 * @description:
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/24
 **/
public class AdminException extends BaseException {
    public AdminException(ErrorStatus errorStatus){
        super(errorStatus);
    }
    public AdminException(ErrorStatus errorStatus, Map<String, Object> data){
        super(errorStatus, data);
    }
    public AdminException(int code, String message, Map<String, Object> data, Throwable cause){
        super(code,message, data, cause);
    }
    public AdminException(int code, String message, Map<String, Object> data){
        super(code,message, data);
    }
    public AdminException(int code, String message, Throwable cause){
        super(code,message, null, cause);
    }
    public AdminException(int code, String message){
        super(code,message, null);
    }
    public AdminException(AdminResultCode resultCode){
        super(resultCode);
    }
}
