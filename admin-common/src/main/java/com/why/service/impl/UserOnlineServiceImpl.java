package com.why.service.impl;

import com.why.common.utils.DateUtils;
import com.why.framework.shiro.session.OnlineSessionDAO;
import com.why.mapper.UserOnlineMapper;
import com.why.model.UserOnline;
import com.why.service.UserOnlineService;
import com.why.service.base.impl.BaseServiceImpl;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/25
 **/
@Service
public class UserOnlineServiceImpl extends BaseServiceImpl<UserOnlineMapper, UserOnline> implements UserOnlineService {
    @Autowired
    private OnlineSessionDAO onlineSessionDAO;

    @Override
    public UserOnline selectOnlineById(String sessionId) {
        return baseMapper.selectOnlineById(sessionId);
    }

    @Override
    public List<UserOnline> selectUserOnlineList(UserOnline userOnline) {
        return baseMapper.selectUserOnlineList(userOnline);
    }

    @Override
    public void forceLogout(String sessionId) {
        Session session = onlineSessionDAO.readSession(sessionId);
        if (session == null) {
            return;
        }
        session.setTimeout(1000);
        removeById(sessionId);
    }

    @Override
    public List<UserOnline> selectOnlineByExpired(Date expiredDate) {
        String lastAccessTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, expiredDate);
        return baseMapper.selectOnlineByExpired(lastAccessTime);
    }
}
