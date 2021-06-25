package com.why.model.base;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.why.common.exception.admin.AdminException;
import com.why.common.utils.MapsUtils;
import com.why.common.utils.TypeUtils;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @description: Entity最最最基础类
 * @author: Stan | stan.wang@paytm.com
 * @create: 2021/1/22 8:23 下午
 **/
@Getter@Setter
public class BaseQueryParams implements Serializable {
    private static final long serialVersionUID = -1433541875798239362L;

    /**
     * 搜索值
     */
    @TableField(exist = false)
    private String searchValue;

    /**
     * 请求参数
     */
    @TableField(exist = false)
    private Map<String, Object> params;

    public Map<String,Object> getParams(){
        if(params == null){
            params = MapsUtils.<String,Object>builder().build();
        }
        return params;
    }

    public Date getBeginTime(){
        return TypeUtils.castToDate(getParams().get("beginTime"));
    }

    public Date getEndTime(){
        return TypeUtils.castToDate(getParams().get("endTime"));
    }

    @Override
    public String toString() {
        try {
            return this.getClass().getSimpleName() + ":" + JSON.toJSONString(this);
        }catch (Exception e){
            throw new AdminException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "JSON转换失败", e);
        }
    }
}
