package com.why.mapper.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 基于MP
 * @author: Stan | stan.wang@paytm.com
 * @create: 2021/1/25 7:40 下午
 * @see com.baomidou.mybatisplus.core.mapper.BaseMapper
 **/
public interface BaseMapper<T> {

    int insert(T entity);

    int deleteById(Serializable id);

    /**
     * 根据实体条件删除
     * @param queryWrapper 实体对象封装操作类（可以为null）
     * @return
     */
    int delete(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    int updateById(@Param(Constants.ENTITY) T entity);

    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) T entity);

    /**
     * 根据where 条件更新
     * @param entity        实体对象（set条件值，不能为null）
     * @param updateWrapper 实体对象（where条件值，可以为null）
     * @return
     */
    int update(@Param(Constants.ENTITY) T entity, @Param(Constants.WRAPPER) Wrapper<T> updateWrapper);

    T selectById(Serializable id);

    Integer selectCount(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    List<T> selectList(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * 根据 Wrapper 条件，查询全部记录
     * 注意： 只返回第一个字段的值
     * @param queryWrapper
     * @return
     */
    List<Object> selectObjs(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
}

