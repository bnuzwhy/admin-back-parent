package com.why.common.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.why.common.exception.admin.AdminException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Jackson 工具类
 *
 * @author Caratacus
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class JsonUtils {

    /**
     * 转换Json
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        if (isCharSequence(object)) {
            return (String) object;
        }
        try {
            return JSON.toJSONString(object);
        } catch (Exception e) {
            throw new AdminException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "JSON转换失败", e);
        }
    }

    /**
     * <p>
     * 是否为CharSequence类型
     * </p>
     *
     * @param object
     * @return
     */
    public static Boolean isCharSequence(Object object) {
        return !Objects.isNull(object) && StringUtils.isCharSequence(object.getClass());
    }

    /**
     * Json转换为对象 转换失败返回null
     *
     * @param json
     * @return
     */
    public static Object parse(String json) {
        Object object = null;
        try {
            object = JSON.parse(json);
        } catch (Exception ignored) {
        }
        return object;
    }

    public static <T> T parseObject(byte[] input, Class<T> clazz) {
        T t = null;
        try {
            t = JSON.parseObject(input, clazz);
        } catch (Exception ignored) {
        }
        return t;
    }

    public static JSONObject parseObject(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = JSON.parseObject(json);
        } catch (Exception ignored) {
        }
        return jsonObject;
    }

}
