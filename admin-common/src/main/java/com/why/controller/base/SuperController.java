package com.why.controller.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.why.common.page.PageCons;
import com.why.common.sql.AntiSqlFilter;
import com.why.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * @description:
 * @author: Stan | stan.wang@paytm.com
 * @create: 2021/1/26 6:30 下午
 **/
@Slf4j
public class SuperController<Entity> implements PageCons {

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        //Date类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport(){
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(DateUtils.parseDate(text));
            }
        });
        binder.registerCustomEditor(JSONObject.class, new PropertyEditorSupport(){
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(JSON.parseObject(text));
            }
        });
        binder.registerCustomEditor(JSONArray.class, new PropertyEditorSupport(){
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(JSON.parseArray(text));
            }
        });
    }

    /**
     * 获取排序的别名
     * @return
     */
    protected String getAlias(){
        try{
            Class<Entity> superClassGenericType = (Class<Entity>) ReflectionKit.getSuperClassGenericType(getClass(), 0);
            return TableInfoHelper.getTableInfo(superClassGenericType).getTableName();
        }catch (Exception e){
            log.warn("Error to get alias:{}",e.getMessage());
            return null;
        }
    }

    /**
     * sql清洗，防止注入（sql order by 过滤）
     * @param parameter
     * @return
     */
    protected String getParameterSafeValue(String parameter){
        return AntiSqlFilter.getSafeValues(request.getParameter(parameter));
    }
}
