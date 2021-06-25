package com.why.service.impl;

import com.why.common.utils.StringUtils;
import com.why.mapper.ConfigMapper;
import com.why.model.Config;
import com.why.service.ConfigService;
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
public class ConfigServiceImpl extends BaseServiceImpl<ConfigMapper, Config> implements ConfigService {
    @Override
    public Config selectConfigByKey(String configKey) {
        return query().eq(Config::getConfigKey, configKey).getOne();
    }

    @Override
    public List<Config> selectConfigList(Config config) {
        Date beginTime = config.getBeginTime();
        Date endTime = config.getEndTime();
        return query().like(StringUtils.isNotEmpty(config.getConfigName()), Config::getConfigName, config.getConfigName())
                .eq(StringUtils.isNotEmpty(config.getConfigType()), Config::getConfigType, config.getConfigType())
                .like(StringUtils.isNotEmpty(config.getConfigKey()), Config::getConfigKey, config.getConfigKey())
                .gt(Objects.nonNull(beginTime), Config::getCreateTime, beginTime)
                .lt(Objects.nonNull(endTime), Config::getCreateTime, endTime)
                .list();
    }

    @Override
    public boolean checkConfigKeyUnique(Config config) {
        Long configId = config.getConfigId();
        Config info = selectConfigByKey(config.getConfigKey());
        return Objects.isNull(info) || info.getConfigId().equals(configId);
    }

    @Override
    public String getConfigValueByKey(String configkey) {
        Config config = selectConfigByKey(configkey);
        return StringUtils.isNotNull(config) ? config.getConfigValue() : "";
    }
}
