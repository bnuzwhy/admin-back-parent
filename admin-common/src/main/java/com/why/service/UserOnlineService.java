package com.why.service;

import com.why.model.UserOnline;
import com.why.service.base.BaseService;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/25
 **/
public interface UserOnlineService extends BaseService<UserOnline> {
    /**
     * 通过会话序号查询信息
     *
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    UserOnline selectOnlineById(String sessionId);

    /**
     * 查询会话集合
     *
     * @param userOnline 分页参数
     * @return 会话集合
     */
    List<UserOnline> selectUserOnlineList(UserOnline userOnline);

    /**
     * 强退用户
     *
     * @param sessionId 会话ID
     */
    void forceLogout(String sessionId);

    /**
     * 查询会话集合
     *
     * @param expiredDate 有效期
     * @return 会话集合
     */
    List<UserOnline> selectOnlineByExpired(Date expiredDate);
}
