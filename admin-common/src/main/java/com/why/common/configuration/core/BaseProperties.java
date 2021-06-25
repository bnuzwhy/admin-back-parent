package com.why.common.configuration.core;

import com.why.common.configuration.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 配置工具类(对外暴露)
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/24
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseProperties {
    @Autowired
    private BasePropertiesConfig config;
    /**
     * 根据文件名获取下载地址
     * @param filename 文件名
     * @return
     */
    public static String getDownloadPath(String filename) {
        BasePropertiesConfig property = getProperties();
        return property.getPath().getFilePath() + property.getPath().getPrefix().getDownload() + filename;
    }

    /**
     * 获取头像上传路径
     * @return
     */
    public static String getAvatarUploadPath() {
        BasePropertiesConfig property = getProperties();
        return property.getPath().getFilePath() + property.getPath().getPrefix().getAvatar();
    }

    /**
     * 获取头像上传路径
     * @return
     */
    public static String getUploadPath() {
        BasePropertiesConfig property = getProperties();
        return property.getPath().getFilePath() + property.getPath().getPrefix().getUpload();
    }

    /**
     * 获取头像上传路径
     * @return
     */
    public static String getUploadResourcePath(String filename) {
        BasePropertiesConfig property = getProperties();
        return property.getPath().getResourcePath() + property.getPath().getPrefix().getUpload() + filename;
    }

    /**
     * 获取头像上传路径
     * @return
     */
    public static String getUploadUrl(HttpServletRequest request, String filename) {
        return getUploadResourcePath(filename);
    }

    /**
     * 获取用户密码最大输入错误次数
     * @return
     */
    public static int getMaxRetryCount() {
        return getProperties().getPassword().getMaxRetryCount();
    }

    /**
     * 获取生成代码相关配置
     * @return
     */
    public static Generator getGenerator() {
        return getProperties().getGenerator();
    }

    /**
     * 获取Xss配置
     * @return
     */
    public static Xss getXss() {
        return getProperties().getXss();
    }

    /**
     * 获取告警Email配置
     * @return
     */
    public static Email getEmail() {
        return getProperties().getEmail();
    }

    /**
     * 获取CrownProperties
     * @return
     */
    public static BasePropertiesConfig getProperties() {
        return ApplicationUtils.getBean(BasePropertiesConfig.class);
    }
}

