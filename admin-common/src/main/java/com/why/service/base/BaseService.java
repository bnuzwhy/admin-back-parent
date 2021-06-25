package com.why.service.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.why.common.mybatisplus.wrapper.LambdaDeleteWrapperChain;
import com.why.common.mybatisplus.wrapper.LambdaQueryWrapperChain;
import com.why.common.mybatisplus.wrapper.LambdaUpdateWrapperChain;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @description:
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/24
 **/
public interface BaseService<T> {
    Log log = LogFactory.getLog(SqlHelper.class);

    int batchSize = 1024;

    /**
     * 策略插入一条记录
     * @param entity
     * @return
     */
    boolean save(T entity);

    /**
     * 批量插入
     * @param entityList
     */
    void saveBatch(Collection<T> entityList);

    /**
     * 批量修改插入
     * @param entityList
     * @return
     */
    boolean saveOrUpdateBatch(Collection<T> entityList);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    boolean removeById(Serializable id);

    /**
     * 删除所有记录
     * @return
     */
    default boolean remove() {
        return remove(Wrappers.emptyWrapper());
    }

    /**
     * 根据entity条件删除
     * @param queryWrapper
     * @return
     */
    boolean remove(Wrapper<T> queryWrapper);

    /**
     * 根据id选择修改
     * @param entity
     * @return
     */
    boolean updateById(T entity);

    /**
     * 根据id全部修改
     * @param entity
     * @return
     */
    boolean alwaysUpdateSomeColumnById(T entity);

    default boolean update(Wrapper<T> udpateWrapper){
        return update(null, udpateWrapper);
    }

    /**
     * 根据where条件更新记录
     * @param entity        要更新的实体
     * @param updateWrapper 被更新的实体
     * @return
     */
    boolean update(T entity, Wrapper<T> updateWrapper);

    /**
     * 根据id批量更新
     * @param entityList
     * @return
     */
    default boolean updateBatchById(Collection<T> entityList){
        return updateBatchById(entityList, batchSize);
    }

    /**
     * 根据id，批量更新
     * @param entityList 实体对象集合
     * @param batchSize  更新批次数量
     * @return
     */
    boolean updateBatchById(Collection<T> entityList, int batchSize);

    /**
     * TableId注解存在更新记录，否则插入一条新的
     * @param entity
     * @return
     */
    boolean saveOrUpdate(T entity);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    T getById(Serializable id);

    /**
     * 根据wrapper查询一条记录
     * @param queryWrapper
     * @return
     * @link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
     */
    default T getOne(Wrapper<T> queryWrapper){
        return SqlHelper.getObject(log,list(queryWrapper));
    }

    /**
     * 根据wrapper查询一条记录
     * @param queryWrapper
     * @param mapper
     * @param <R>
     * @return
     * @link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
     */
    default <R> R getObj(Wrapper<T> queryWrapper, Function<? super Object, R> mapper){
        return SqlHelper.getObject(log, listObjs(queryWrapper, mapper));
    }

    default int count(){
        return count(Wrappers.emptyWrapper());
    }

    default boolean exist(Wrapper<T> queryWrapper){
        return count(queryWrapper)>0;
    }

    default boolean nonExist(Wrapper<T> queryWrapper){
        return !exist(queryWrapper);
    }

    int count(Wrapper<T> queryWrapper);

    default List<T> list(){
        return list(Wrappers.emptyWrapper());
    }

    List<T> list(Wrapper<T> queryWrapper);

    default <R> List<R> listObjs(Function<? super Object, R> mapper){
        return listObjs(Wrappers.emptyWrapper(), mapper);
    }

    <R> List<R> listObjs(Wrapper<T> queryWrapper, Function<? super Object, R> mapper);

    default <R> R entity(Wrapper<T> wrapper, Function<? super T, R> mapper){
        return SqlHelper.getObject(log,entitys(wrapper, mapper));
    }

    default <R> List<R> entitys(Function<? super T, R> mapper){
        return entitys(Wrappers.emptyWrapper(),mapper);
    }

    <R> List<R> entitys(Wrapper<T> wrapper, Function<? super T,R> mapper);

    default <K> Map<K, T> list2Map(SFunction<T, K> column){
        return list2Map(Wrappers.<T>emptyWrapper(),column);
    }

    <K> Map<K,T> list2Map(Wrapper<T> wrapper, SFunction<T, K> column);

    default LambdaQueryWrapperChain<T> query(){
        return new LambdaQueryWrapperChain<>(this);
    }

    default LambdaUpdateWrapperChain<T> update(){
        return new LambdaUpdateWrapperChain<>(this);
    }

    default LambdaDeleteWrapperChain<T> delete(){
        return new LambdaDeleteWrapperChain<>(this);
    }
}
