package com.why.common.mybatisplus.autoconfig;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * @description: 通用填充类元数据，用于MP
 * @author: Stan | stan.wang@paytm.com
 * @create: 2021/1/25 8:28 下午
 **/
public class CommonMetaObjectHandler implements MetaObjectHandler {

    //以下五个常量字段必须与com.why.model.base.BaseEnity中保持完全相同

    /**
     * 创建时间
     */
    private final String createTime = "createTime";
    /**
     * 修改时间
     */
    private final String updateTime = "updateTime";
    /**
     * 创建者
     */
    private final String createBy = "createBy";
    /**
     * 修改者
     */
    private final String updateBy = "updateBy";
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private final String deleted = "deleted";

    @Override
    public void insertFill(MetaObject metaObject) {
        strictInsertFill(metaObject, deleted, Boolean.class,false);
        strictInsertFill(metaObject, createTime, Date.class, new Date());
        strictInsertFill(metaObject, createBy, String.class, currentLoginName());
        strictInsertFill(metaObject, updateTime, Date.class, new Date());
        strictInsertFill(metaObject, updateBy, String.class, currentLoginName());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        strictUpdateFill(metaObject, updateTime, Date.class, new Date());
        strictUpdateFill(metaObject, updateBy, String.class, currentLoginName());
    }

    /**
     * 配合Security获取当前用户名
     */
    private String currentLoginName(){
        return "CommonMetaObjectHandle.currentLoginName";
    }
}
