package com.why.common.sql;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.why.common.utils.MapsUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * AntiSQLFilter is a J2EE Web Application Filter that protects web components from SQL Injection hacker attacks.<br>
 * Must to be configured with web.xml descriptors.
 * <br><br>
 * Below, the filter initialization parameters to configure:
 * <br><br>
 * <b>logging</b> - a <i>true</i> value enables output to Servlet Context logging in case of a SQL Injection detection.
 * Defaults to <i>false</i>.
 * <br><br>
 * <b>behavior</b> - there are three possible behaviors in case of a SQL Injection detection:
 * <li> protect : (default) dangerous SQL keywords are 2nd character supressed /
 * dangerous SQL delimitters are blank space replaced.
 * Afterwards the request flows as expected.
 * <li> throw: a ServletException is thrown - breaking the request flow.
 * <li> forward: thre request is forwarded to a specific resource.
 * <br><br>
 * <b>forwardTo</b> - the resource to forward when forward behavior is set.<br>
 * <P>
 * http://antisqlfilter.sourceforge.net/
 * </p>
 * @author: Stan | stan.wang@paytm.com
 * @create: 2021/1/26 8:13 下午
 */
public class AntiSqlFilter {

    /**
     * 仅支持字母、数字、下划线、空格、逗号（支持多个字段排序）
     */
    private static final String SQL_PATTERN = "[a-zA-Z0-9_ ,.]+";

    private static final String[] keyWords = {";", "\"", "\'", "/*", "*/", "--", "exec",
            "select", "update", "delete", "insert",
            "alter", "drop", "create", "shutdown"};

    public static Map<String, String[]> getSafeParameterMap(Map<String, String[]> parameterMap){
        MapsUtils.MapBuilder<String, String[]> builder = MapsUtils.builder(HashMap::new);
        Iterator<String> iterator = parameterMap.keySet().iterator();
        while(iterator.hasNext()){
            String key = iterator.next();
            String[] oldVal = parameterMap.get(key);
            builder.put(key,getSafeValues(oldVal));
        }
        return builder.unmodifiable().build();
    }

    public static String[] getSafeValues(String[] oldValues){
        if(ArrayUtils.isNotEmpty(oldValues)){
            String[] newVal = new String[oldValues.length];
            for(int i=0;i<oldValues.length;i++){
                newVal[i] = getSafeValues(oldValues[i]);
            }
            return newVal;
        }
        return null;
    }

    public static String getSafeValues(String oldValue){
        String value = escapeOrderBySql(oldValue);
        if(StringUtils.EMPTY.equals(value)){
            return value;
        }
        StringBuilder sb = new StringBuilder(value);
        String lowerCase = value.toLowerCase();
        for (String keyWord : keyWords) {
            int x;
            while((x = lowerCase.indexOf(keyWord))>=0){
                if(keyWord.length() == 1){
                    sb.replace(x,x+1, " ");
                    lowerCase = sb.toString().toLowerCase();
                    continue;
                }
                sb.deleteCharAt(x+1);
                lowerCase = sb.toString().toLowerCase();
            }
        }
        return sb.toString();
    }

    /**
     * 检查字符，防止sql注入
     * @param value
     * @return
     */
    private static String escapeOrderBySql(String value){
        if(StringUtils.isNotBlank(value) && !isValidOrderBySql(value)){
            return StringUtils.EMPTY;
        }
        return value;
    }

    /**
     * 验证orderBy语法是否符合规范
     * @param val
     * @return
     */
    private static boolean isValidOrderBySql(String val){
        return val.matches(SQL_PATTERN);
    }
}
