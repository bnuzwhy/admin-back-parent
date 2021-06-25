package com.why.common.configuration;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Xss {

    /**
     * Xss开关
     */
    private Boolean enabled;

    /**
     * 排除字段
     */
    private List<String> excludeFields;
    /**
     * 排除路径
     */
    private List<String> excludeUrls;

}