package com.why.common.exception;

import com.why.common.exception.admin.AdminException;
import com.why.common.exception.core.BaseException;
import com.why.common.exception.core.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 异常处理中心
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/24
 **/
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAppException(Exception e, HttpServletRequest request){
        /*ErrorResponse response = new ErrorResponse(e, request.getRequestURI());
        addLog(response);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);*/
        return null;
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> handleAppException(BaseException ex, HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(ex, request.getRequestURI());
        addLog(response);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AdminException.class)
    public ResponseEntity<ErrorResponse> AdminException(AdminException ex, HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(ex, request.getRequestURI());
        addLog(response);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * 统一日志记录
     * @param response
     */
    private void addLog(ErrorResponse response){
        log.error(response.getCode()+"");
        log.error(response.getMessage());
        log.error(response.getPath());
        log.error(response.getTimestamp().toString());
        log.error(response.getData().toString());
    }
}
