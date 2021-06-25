package com.why.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.why.mapper.OperLogMapper;
import com.why.model.OperLog;
import com.why.service.OperLogService;
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
public class OperLogServiceImpl extends BaseServiceImpl<OperLogMapper, OperLog> implements OperLogService {
    @Override
    public List<OperLog> selectOperLogList(OperLog operLog) {
        Date beginTime = operLog.getBeginTime();
        Date endTime = operLog.getEndTime();
        return query().like(StringUtils.isNotBlank(operLog.getTitle()), OperLog::getTitle, operLog.getTitle())
                .eq(Objects.nonNull(operLog.getBusinessType()), OperLog::getBusinessType, operLog.getBusinessType())
                .in(CollectionUtils.isNotEmpty(operLog.getBusinessTypes()), OperLog::getBusinessType, operLog.getBusinessTypes())
                .eq(Objects.nonNull(operLog.getStatus()), OperLog::getStatus, operLog.getStatus())
                .like(StringUtils.isNotBlank(operLog.getOperName()), OperLog::getOperName, operLog.getOperName())
                .gt(Objects.nonNull(beginTime), OperLog::getOperTime, beginTime)
                .lt(Objects.nonNull(endTime), OperLog::getOperTime, endTime)
                .list();
    }
}
