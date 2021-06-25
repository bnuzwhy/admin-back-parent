package com.why.framework.shiro.service;

import com.why.common.constants.Constants;
import com.why.common.constants.ShiroConstants;
import com.why.common.constants.UserConstants;
import com.why.common.enums.UserStatus;
import com.why.common.exception.admin.AdminException;
import com.why.common.utils.DateUtils;
import com.why.framework.shiro.ShiroUtils;
import com.why.framework.thread.ThreadExecutors;
import com.why.framework.thread.factory.TimerTasks;
import com.why.model.User;
import com.why.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * 登录校验方法
 *
 * @author Crown
 */
@Component
public class LoginService {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserService userService;

    /**
     * 登录
     */
    public User login(String username, String password) {
        // 验证码校验
        if (!StringUtils.isEmpty(ApplicationUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA))) {
            ThreadExecutors.execute(TimerTasks.recordLogininfor(username, Constants.LOGIN_FAIL, "验证码错误"));
            throw new AdminException(HttpServletResponse.SC_BAD_REQUEST, "验证码错误");
        }
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            ThreadExecutors.execute(TimerTasks.recordLogininfor(username, Constants.LOGIN_FAIL, "用户名或密码为空"));
            throw new AdminException(HttpServletResponse.SC_BAD_REQUEST, "用户名或密码为空");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            ThreadExecutors.execute(TimerTasks.recordLogininfor(username, Constants.LOGIN_FAIL, "用户名或密码错误"));
            throw new AdminException(HttpServletResponse.SC_BAD_REQUEST, "用户名或密码错误");
        }

        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            ThreadExecutors.execute(TimerTasks.recordLogininfor(username, Constants.LOGIN_FAIL, "用户名或密码错误"));
            throw new AdminException(HttpServletResponse.SC_BAD_REQUEST, "用户名或密码错误");
        }

        // 查询用户信息
        User user = userService.selectUserByLoginName(username);

        if (user == null && maybeMobilePhoneNumber(username)) {
            user = userService.selectUserByPhoneNumber(username);
        }

        if (user == null && maybeEmail(username)) {
            user = userService.selectUserByEmail(username);
        }

        if (user == null) {
            ThreadExecutors.execute(TimerTasks.recordLogininfor(username, Constants.LOGIN_FAIL, "用户名或密码错误"));
            throw new AdminException(HttpServletResponse.SC_BAD_REQUEST, "用户名或密码错误");
        }

        if (user.getDeleted()) {
            ThreadExecutors.execute(TimerTasks.recordLogininfor(username, Constants.LOGIN_FAIL, "对不起，您的账号已被删除"));
            throw new AdminException(HttpServletResponse.SC_BAD_REQUEST, "对不起，您的账号已被删除");
        }

        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            ThreadExecutors.execute(TimerTasks.recordLogininfor(username, Constants.LOGIN_FAIL, "用户已被禁用，请联系管理员", user.getRemark()));
            throw new AdminException(HttpServletResponse.SC_BAD_REQUEST, "用户已被禁用，请联系管理员");
        }

        passwordService.validate(user, password);

        ThreadExecutors.execute(TimerTasks.recordLogininfor(username, Constants.LOGIN_SUCCESS, "登录成功"));
        recordLoginInfo(user);
        return user;
    }

    private boolean maybeEmail(String username) {
        return username.matches(UserConstants.EMAIL_PATTERN);
    }

    private boolean maybeMobilePhoneNumber(String username) {
        return username.matches(UserConstants.MOBILE_PHONE_NUMBER_PATTERN);
    }

    /**
     * 记录登录信息
     */
    public void recordLoginInfo(User user) {
        user.setLoginIp(ShiroUtils.getIp());
        user.setLoginDate(DateUtils.getNowDate());
        userService.updateById(user);
    }
}
