package com.why.common.configuration.core;

import com.why.common.configuration.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * @description: 配置中心
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/24
 **/
@Component
@ConfigurationProperties(prefix = BasePropertiesConfig.BASE_PREFIX)
@Setter
@Getter
public class BasePropertiesConfig {
    public static final String BASE_PREFIX = "base";

    /**
     * 路径
     */
    @NestedConfigurationProperty
    private Path path;

    /**
     * 获取地址开关
     */
    @NestedConfigurationProperty
    private Address address;
    /**
     * 用户密码配置
     */
    @NestedConfigurationProperty
    private Password password;
    /**
     * 生成代码配置
     */
    @NestedConfigurationProperty
    private Generator generator;
    /**
     * Xss配置
     */
    @NestedConfigurationProperty
    private Xss xss;
    /**
     * 告警通知Email配置
     */
    @NestedConfigurationProperty
    private Email email;
}
