package com.why.common.configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Password {

    /**
     * 密码最大输入错误次数
     */
    private int maxRetryCount = 10;
}