package com.why.framework.servlet.wrapper;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.why.common.configuration.Xss;
import com.why.common.configuration.core.BaseProperties;
import com.why.common.utils.StringUtils;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Request包装类
 * <p>
 * 1.预防xss攻击
 * 2.拓展requestbody无限获取(HttpServletRequestWrapper只能获取一次)
 * </p>
 *
 * @author Caratacus
 */
public class GlobalRequestWrapper extends HttpServletRequestWrapper {

    public GlobalRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = htmlEscape(name, values[i]);
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (value == null) {
            return null;
        }
        return htmlEscape(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        Object value = super.getAttribute(name);
        if (StringUtils.isCharSequence(value)) {
            value = htmlEscape(name, (String) value);
        }
        return value;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null) {
            return null;
        }
        return htmlEscape(name, value);
    }

    @Override
    public String getQueryString() {
        String value = super.getQueryString();
        if (value == null) {
            return null;
        }
        return htmlEscape(value);
    }

    /**
     * 使用spring HtmlUtils 转义html标签达到预防xss攻击效果
     *
     * @param str
     * @see HtmlUtils#htmlEscape
     */
    protected String htmlEscape(String str) {
        Xss xss = BaseProperties.getXss();
        if (!xss.getEnabled()) {
            return str;
        }
        List<String> excludeUrls = xss.getExcludeUrls();
        if (CollectionUtils.isNotEmpty(excludeUrls)) {
            String url = getServletPath();
            for (String pattern : excludeUrls) {
                Pattern p = Pattern.compile("^" + pattern);
                Matcher m = p.matcher(url);
                if (m.find()) {
                    return str;
                }
            }
        }
        return HtmlUtils.htmlEscape(str);
    }

    /**
     * 使用spring HtmlUtils 转义html标签达到预防xss攻击效果
     *
     * @param field
     * @param str
     * @see HtmlUtils#htmlEscape
     */
    protected String htmlEscape(String field, String str) {
        return BaseProperties.getXss().getExcludeFields().contains(field) ? str : htmlEscape(str);
    }

}