package com.why.framework.servlet.wrapper;

import com.google.common.base.Throwables;
import com.why.common.constants.APICons;
import com.why.common.exception.core.ErrorStatus;
import com.why.common.json.JsonUtils;
import com.why.common.responses.ApiResponses;
import com.why.common.utils.TypeUtils;
import com.why.framework.spring.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * response包装类
 *
 * @author Caratacus
 */
@Slf4j
public class ResponseWrapper extends HttpServletResponseWrapper {

    private ErrorStatus errorStatus;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public ResponseWrapper(HttpServletResponse response, ErrorStatus errorStatus) {
        super(response);
        setErrorCode(errorStatus);
    }

    /**
     * 获取ErrorCode
     *
     * @return
     */
    public ErrorStatus getErrorCode() {
        return errorStatus;
    }

    /**
     * 设置ErrorCode
     *
     * @param errorStatus
     */
    public void setErrorCode(ErrorStatus errorStatus) {
        if (Objects.nonNull(errorStatus)) {
            this.errorStatus = errorStatus;
            boolean isRest = TypeUtils.castToBoolean(ApplicationUtils.getRequest().getAttribute(APICons.API_REST), false);
            if (isRest) {
                super.setStatus(this.errorStatus.code());
            }
        }
    }

    /**
     * 向外输出错误信息
     *
     * @param e
     * @throws IOException
     */
    public void writerErrorMsg(Exception e) {
        if (Objects.isNull(errorStatus)) {
            log.warn("Warn: ErrorCodeEnum cannot be null, Skip the implementation of the method.");
            return;
        }
        printWriterApiResponses(ApiResponses.failure(this.getErrorCode(), e));
    }

    /**
     * 设置ApiErrorMsg
     */
    public void writerErrorMsg() {
        writerErrorMsg(null);
    }

    /**
     * 向外输出ApiResponses
     *
     * @param apiResponses
     */
    public void printWriterApiResponses(ApiResponses apiResponses) {
        writeValueAsJson(apiResponses);
    }

    /**
     * 向外输出json对象
     *
     * @param obj
     */
    public void writeValueAsJson(Object obj) {
        if (super.isCommitted()) {
            log.warn("Warn: Response isCommitted, Skip the implementation of the method.");
            return;
        }
        super.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        super.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try (PrintWriter writer = super.getWriter()) {
            writer.print(JsonUtils.toJson(obj));
            writer.flush();
        } catch (IOException e) {
            log.warn("Error: Response printJson faild, stackTrace: {}", Throwables.getStackTraceAsString(e));
        }
    }

}
