package com.why.framework.shiro.session;

import com.why.common.enums.OnlineStatus;
import com.why.framework.thread.ThreadExecutors;
import com.why.framework.thread.factory.TimerTasks;
import com.why.model.OnlineSession;
import com.why.model.UserOnline;
import com.why.service.UserOnlineService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;

/**
 * 针对自定义的ShiroSession的db操作
 *
 * @author Crown
 */
public class OnlineSessionDAO extends EnterpriseCacheSessionDAO {

    /**
     * 同步session到数据库的周期 单位为毫秒（默认1分钟）
     */
    private int dbSyncPeriod;

    /**
     * 上次同步数据库的时间戳
     */
    private static final String LAST_SYNC_DB_TIMESTAMP = OnlineSessionDAO.class.getName() + "LAST_SYNC_DB_TIMESTAMP";

    @Autowired
    private UserOnlineService onlineService;

    @Autowired
    private OnlineSessionFactory onlineSessionFactory;

    public OnlineSessionDAO() {
        super();
    }

    public OnlineSessionDAO(int dbSyncPeriod) {
        super();
        this.dbSyncPeriod = dbSyncPeriod;
    }

    /**
     * 根据会话ID获取会话
     *
     * @param sessionId 会话ID
     * @return ShiroSession
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        UserOnline userOnline = onlineService.selectOnlineById(String.valueOf(sessionId));
        if (userOnline == null) {
            return null;
        }
        return onlineSessionFactory.createSession(userOnline);
    }

    /**
     * 更新会话；如更新会话最后访问时间/停止会话/设置超时时间/设置移除属性等会调用
     */
    public void syncToDb(OnlineSession onlineSession) {
        Date lastSyncTimestamp = (Date) onlineSession.getAttribute(LAST_SYNC_DB_TIMESTAMP);
        if (lastSyncTimestamp != null) {
            boolean needSync = true;
            long deltaTime = onlineSession.getLastAccessTime().getTime() - lastSyncTimestamp.getTime();
            if (deltaTime < dbSyncPeriod * 60 * 1000) {
                // 时间差不足 无需同步
                needSync = false;
            }
            // isGuest = true 访客
            boolean isGuest = onlineSession.getUserId() == null || onlineSession.getUserId() == 0L;

            // session 数据变更了 同步
            if (!isGuest && onlineSession.isAttributeChanged()) {
                needSync = true;
            }

            if (!needSync) {
                return;
            }
        }
        // 更新上次同步数据库时间
        onlineSession.setAttribute(LAST_SYNC_DB_TIMESTAMP, onlineSession.getLastAccessTime());
        // 更新完后 重置标识
        if (onlineSession.isAttributeChanged()) {
            onlineSession.resetAttributeChanged();
        }
        ThreadExecutors.execute(TimerTasks.syncSession(onlineSession));
    }

    /**
     * 当会话过期/停止（如用户退出时）属性等会调用
     */
    @Override
    protected void doDelete(Session session) {
        OnlineSession onlineSession = (OnlineSession) session;
        if (null == onlineSession) {
            return;
        }
        onlineSession.setStatus(OnlineStatus.off_line);
        onlineService.removeById(onlineSession.getId());
    }
}
