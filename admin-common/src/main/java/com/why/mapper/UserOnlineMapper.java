package com.why.mapper;

import com.why.mapper.base.BaseMapper;
import com.why.model.UserOnline;

import java.util.List;

/**
 * @description: 在线用户 数据层
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/25
 **/
public interface UserOnlineMapper extends BaseMapper<UserOnline> {
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
     * @param userOnline 会话参数
     * @return 会话集合
     */
    List<UserOnline> selectUserOnlineList(UserOnline userOnline);

    /**
     * 查询过期会话集合
     *
     * @param lastAccessTime 过期时间
     * @return 会话集合
     */
    List<UserOnline> selectOnlineByExpired(String lastAccessTime);
}
