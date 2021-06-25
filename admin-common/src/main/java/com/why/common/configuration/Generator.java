package com.why.common.configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Generator {

    /**
     * 作者
     */
    private String author;
    /**
     * 默认生成包路径
     */
    private String packagePath;
}
