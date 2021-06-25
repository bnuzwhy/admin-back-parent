package com.why.common.responses;

import com.why.common.exception.core.ErrorStatus;
import com.why.framework.utils.ResponseUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * @description: 统一回调
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/24
 **/
@Getter
@Setter
@Accessors(chain = true)
public class ApiResponses<T> implements Serializable {

    private static final long serialVersionUID = -3843636557918849168L;

    /**
     * http状态码
     */
    private Integer status = 200;
    /**
     * msg
     */
    private String msg = "Operation Success";

    /**
     * 不需要返回结果
     *
     * @param status
     */
    public static ApiResponses<Void> success(HttpServletResponse response, HttpStatus status) {
        response.setStatus(status.value());
        SuccessResponses<Void> responses = new SuccessResponses<>();
        responses.setStatus(status.value());
        return responses;

    }

    /**
     * 成功返回
     *
     * @param object
     */
    public static <T> ApiResponses<T> success(HttpServletResponse response, T object) {
        return success(response, HttpStatus.OK, object);

    }

    /**
     * 成功返回
     *
     * @param status
     * @param object
     */
    public static <T> ApiResponses<T> success(HttpServletResponse response, HttpStatus status, T object) {
        response.setStatus(status.value());
        SuccessResponses<T> responses = new SuccessResponses<>();
        responses.setStatus(status.value());
        responses.setResult(object);
        return responses;
    }

    /**
     * 成功返回
     *
     * @param status
     * @param object
     */
    public static <T> ApiResponses<T> success(ServerHttpResponse response, HttpStatus status, T object) {
        response.setStatusCode(status);
        SuccessResponses<T> responses = new SuccessResponses<>();
        responses.setStatus(status.value());
        responses.setResult(object);
        return responses;
    }

    /**
     * 失败返回
     *
     * @param errorStatus
     * @param exception
     */
    public static <T> ApiResponses<T> failure(ErrorStatus errorStatus, Exception exception) {
        FailedResponse failedResponse = new FailedResponse();
        failedResponse.setError(errorStatus.code()+"")
                .setStatus(errorStatus.code())
                .setMsg(errorStatus.message());
        ResponseUtils.exceptionMsg(failedResponse, exception);
        return failedResponse;
    }

    /**
     * 失败返回
     *
     * @param errorStatus
     */
    public static <T> ApiResponses<T> failure(ErrorStatus errorStatus) {
        FailedResponse failedResponse = new FailedResponse();
        return failedResponse.setError(errorStatus.code()+"")
                .setStatus(errorStatus.code())
                .setMsg(errorStatus.message());
    }
}
