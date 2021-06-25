package com.why.service;

import com.why.model.Logininfor;
import com.why.service.base.BaseService;

import java.util.List;

/**
 * @description:
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/25
 **/
public interface LoginInforService extends BaseService<Logininfor> {
    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    List<Logininfor> selectLogininforList(Logininfor logininfor);
}
