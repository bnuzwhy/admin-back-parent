package com.why.framework.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Spring Application 工具类
 *
 * @author Caratacus
 */
@Component
public final class ApplicationUtils implements BeanFactoryPostProcessor, ApplicationContextAware {

    /**
     * Spring应用上下文环境
     */
    private static ConfigurableListableBeanFactory beanFactory;
    private static ApplicationContext applicationContext;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ApplicationUtils.beanFactory = beanFactory;
    }

    /**
     * 获取ApplicationContext
     */
    public static ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    /**
     * 获取ApplicationContext
     */
    public static ApplicationContext getContext() {
        return applicationContext;
    }

    /**
     * 获取springbean
     *
     * @param beanName
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanName, Class<T> requiredType) {
        if (containsBean(beanName)) {
            return getBeanFactory().getBean(beanName, requiredType);
        }
        return null;
    }

    /**
     * 获取springbean
     *
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> requiredType) {
        return getBeanFactory().getBean(requiredType);
    }

    /**
     * 获取springbean
     *
     * @param beanName
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanName) {
        if (containsBean(beanName)) {
            Class<T> type = getType(beanName);
            return getBeanFactory().getBean(beanName, type);
        }
        return null;
    }

    /**
     * 依赖spring框架获取HttpServletRequest
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = null;
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (Objects.nonNull(requestAttributes)) {
                request = requestAttributes.getRequest();
            }
        } catch (Exception ignored) {
        }
        return request;
    }

    /**
     * ApplicationContext是否包含该Bean
     *
     * @param name
     * @return
     */
    public static boolean containsBean(String name) {
        return getBeanFactory().containsBean(name);
    }

    /**
     * ApplicationContext该Bean是否为单例
     *
     * @param name
     * @return
     */
    public static boolean isSingleton(String name) {
        return getBeanFactory().isSingleton(name);
    }

    /**
     * 获取该Bean的Class
     *
     * @param name
     * @return
     */
    public static <T> Class<T> getType(String name) {
        return (Class<T>) getBeanFactory().getType(name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationUtils.applicationContext = applicationContext;

    }
}
