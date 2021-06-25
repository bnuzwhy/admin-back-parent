package com.why.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.why.model.Logininfor;
import com.why.service.LoginInforMapper;
import com.why.service.LoginInforService;
import com.why.service.base.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @description:
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/25
 **/
@Service
@Slf4j
public class LoginInforServiceImpl extends BaseServiceImpl<LoginInforMapper, Logininfor> implements LoginInforService {
    @Override
    public List<Logininfor> selectLogininforList(Logininfor logininfor) {
        Date beginTime = logininfor.getBeginTime();
        Date endTime = logininfor.getEndTime();
        return query().select()
                .like(StringUtils.isNotBlank(logininfor.getIpaddr()), Logininfor::getIpaddr, logininfor.getIpaddr())
                .eq(Objects.nonNull(logininfor.getStatus()), Logininfor::getStatus, logininfor.getStatus())
                .like(StringUtils.isNotBlank(logininfor.getLoginName()), Logininfor::getLoginName, logininfor.getLoginName())
                .ge(Objects.nonNull(beginTime), Logininfor::getLoginTime, beginTime)
                .le(Objects.nonNull(endTime), Logininfor::getLoginTime, endTime)
                .list();
    }
}
