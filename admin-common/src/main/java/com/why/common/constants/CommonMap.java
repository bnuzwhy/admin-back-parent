package com.why.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 通用数据库映射Map数据
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/25
 **/
public class CommonMap {

    /**
     * 状态编码转换
     */
    public static final Map<String, String> javaTypeMap = new HashMap<>();

    static {
        initJavaTypeMap();
    }

    /**
     * 返回状态映射
     */
    public static void initJavaTypeMap() {
        javaTypeMap.put("tinyint", "Integer");
        javaTypeMap.put("smallint", "Integer");
        javaTypeMap.put("mediumint", "Integer");
        javaTypeMap.put("int", "Integer");
        javaTypeMap.put("number", "Integer");
        javaTypeMap.put("integer", "integer");
        javaTypeMap.put("bigint", "Long");
        javaTypeMap.put("float", "Float");
        javaTypeMap.put("double", "Double");
        javaTypeMap.put("decimal", "BigDecimal");
        javaTypeMap.put("bit", "Boolean");
        javaTypeMap.put("char", "String");
        javaTypeMap.put("varchar", "String");
        javaTypeMap.put("varchar2", "String");
        javaTypeMap.put("tinytext", "String");
        javaTypeMap.put("text", "String");
        javaTypeMap.put("mediumtext", "String");
        javaTypeMap.put("longtext", "String");
        javaTypeMap.put("time", "Date");
        javaTypeMap.put("date", "Date");
        javaTypeMap.put("datetime", "Date");
        javaTypeMap.put("timestamp", "Date");
    }
}
