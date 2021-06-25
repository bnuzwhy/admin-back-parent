package com.why.framework.log;

import com.why.common.json.JsonUtils;
import com.why.framework.thread.ThreadExecutors;
import com.why.framework.thread.factory.TimerTasks;
import com.why.framework.utils.ResponseUtils;
import com.why.model.ExceLog;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 请求日志工具类
 *
 * @author Caratacus
 */
@Slf4j
public abstract class LogUtils {

    /**
     * 获取日志对象
     *
     * @param status
     * @param beiginTime
     * @param parameterMap
     * @param requestBody
     * @param url
     * @param actionMethod
     * @param method
     * @param ip
     * @param object
     * @return
     */
    public static void printLog(Integer status, Long beiginTime, String uid, String loginName, Map<String, String[]> parameterMap, Object requestBody, String url, String actionMethod, String method, String ip, Object object) {
        String runTime = (beiginTime != null ? System.currentTimeMillis() - beiginTime : 0) + "ms";
        Log logInfo = Log.builder()
                //查询参数
                .parameterMap(parameterMap)
                .uid(uid)
                .loginName(loginName)
                //请求体r
                .requestBody(requestBody)
                //请求路径
                .url(url)
                //控制器方法
                .actionMethod(actionMethod)
                //请求方法
                .method(method)
                .runTime(runTime)
                .result(object)
                .ip(ip)
                .build();
        String logJson = JsonUtils.toJson(logInfo);
        if (status >= HttpServletResponse.SC_BAD_REQUEST) {
            ExceLog exceLog = new ExceLog();
            exceLog.setOperName(loginName);
            exceLog.setUrl(url);
            exceLog.setActionMethod(actionMethod);
            exceLog.setRunTime(runTime);
            exceLog.setContent(logJson);
            ThreadExecutors.execute(TimerTasks.saveExceLog(ip, status, exceLog));
        }

        log.info(logJson);
    }

    public static void doAfterReturning(Object ret) {
        ResponseUtils.writeValAsJson(ApplicationUtils.getRequest(), ret);
    }

}
