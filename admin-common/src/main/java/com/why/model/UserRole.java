package com.why.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @description: sys_user
 * @author: Stan | stan.wang@paytm.com
 * @create: 2021/1/26 9:01 下午
 **/
@Setter
@Getter
public class UserRole {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

}
