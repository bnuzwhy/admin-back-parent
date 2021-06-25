package com.why.model;

import com.why.common.enums.OnlineStatus;
import lombok.Data;
import org.apache.shiro.session.mgt.SimpleSession;

/**
 * 在线用户会话属性
 *
 * @author Crown
 */
@Data
public class OnlineSession extends SimpleSession {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String loginName;

    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 登录IP地址
     */
    private String host;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 在线状态
     */
    private OnlineStatus status = OnlineStatus.on_line;

    /**
     * 属性是否改变 优化session数据同步
     */
    private transient boolean attributeChanged = false;

    public void markAttributeChanged() {
        this.attributeChanged = true;
    }

    public void resetAttributeChanged() {
        this.attributeChanged = false;
    }
}
