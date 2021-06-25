package com.why.common.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@RefreshScope
public class Email {

    private static Map<String,String> map = new HashMap<>();

    /**
     * 开关
     */
    private boolean enabled = false;
    /**
     * 告警邮箱地址
     */
    private String send;

    private List<String> testList;

    private Map<String,Map<String,String>> mapList;

    private List<String> configs;

    private String start;

    public String getValue(String s){

        testList.forEach(str -> {
            map.put(str,"testList");
        });

        return map.get(s);
    }

    private String test;
}