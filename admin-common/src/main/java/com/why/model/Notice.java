package com.why.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.why.model.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 通知公告表 sys_notice
 *
 * @author Crown
 */
@Setter
@Getter
public class Notice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 公告ID
     */
    @TableId
    private Long noticeId;
    /**
     * 公告标题
     */
    @NotBlank(message = "公告标题不能为空")
    @Size(max = 50, message = "公告标题不能超过50个字符")
    private String noticeTitle;
    /**
     * 公告类型（1通知 2公告）
     */
    private String noticeType;
    /**
     * 公告内容
     */
    private String noticeContent;
    /**
     * 公告状态（0正常 1关闭）
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

}
