package com.why.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.why.model.base.BaseQueryParams;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * 异常日志表 sys_exce_log
 *
 * @author Caratacus
 */
@Setter
@Getter
public class ExceLog extends BaseQueryParams {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     * 当前操作人
     */
    private String operName;
    /**
     * 请求路径
     */
    private String url;
    /**
     * 控制器方法
     */
    private String actionMethod;
    /**
     * 接口运行时间 单位:ms
     */
    private String runTime;
    /**
     * IP地址
     */
    private String ipAddr;
    /**
     * 日志详情
     */
    private String content;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

}
