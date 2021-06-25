package com.why.service.base.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.why.mapper.base.BaseMapper;
import com.why.service.base.BaseService;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description: 基础服务层，用于简化重复sql，配合MP
 * @author: Stan | stan.wang@paytm.com
 * @create: 2021/1/26 3:01 下午
 **/
public class BaseServiceImpl<M extends BaseMapper<T>, T> implements BaseService<T> {

    @Autowired
    protected M baseMapper;

    /**
     * 判断sql是否执行成功
     * @param result 数据库操作返回影响条数
     * @return
     */
    protected boolean retBool(Integer result){
        return SqlHelper.retBool(result);
    }

    protected Class<T> currentModelClass(){
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(),1);
    }

    /**
     * 批量操作 sqlSession
     * @return
     */
    protected SqlSession sqlSessionBatch(){
        return SqlHelper.sqlSessionBatch(currentModelClass());
    }

    /**
     * 释放 sqlSession
     * @param sqlSession
     */
    protected void closeSqlSession(SqlSession sqlSession){
        SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(currentModelClass()));
    }

    /**
     * 获取 statement ｜ 如果多个mapper公用一个实体，可能会出现获取命名空间错误
     * @param sqlMethod
     * @return
     */
    protected String sqlStatement(SqlMethod sqlMethod){
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(T entity) {
        return retBool(baseMapper.insert(entity));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveBatch(Collection<T> entityList) {
        if(CollectionUtils.isEmpty(entityList)){
            return;
        }
        entityList.forEach(this::save);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdate(T entity) {
        if(entity != null){
            Class<?> aClass = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(aClass);
            if(tableInfo != null && !ObjectUtils.isEmpty(tableInfo.getKeyProperty())){
                Object fieldValue = ReflectionKit.getFieldValue(entity, tableInfo.getKeyProperty());
                if(StringUtils.checkValNull(fieldValue) || Objects.isNull(getById((Serializable) fieldValue))){
                    save(entity);
                }else{
                    updateById(entity);
                }
            }else{
                throw ExceptionUtils.mpe("Error:  Can not execute. Could not find @TableId.");
            }
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList) {
        if(CollectionUtils.isEmpty(entityList)){
            return true;
        }
        Class<?> tClass = currentModelClass();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(tClass);
        int i = 0;
        try(SqlSession batchSqlSession = sqlSessionBatch()){
            for(T entity: entityList){
                if(null != tableInfo && StringUtils.isNotBlank(tableInfo.getKeyProperty())){
                    Object fieldValue = ReflectionKit.getFieldValue(entity, tableInfo.getKeyProperty());
                    if(StringUtils.checkValNull(fieldValue) || Objects.isNull(getById((Serializable) fieldValue))){
                        batchSqlSession.insert(sqlStatement(SqlMethod.INSERT_ONE),entity);
                    }else{
                        MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                        param.put(Constants.ENTITY, entity);
                        batchSqlSession.update(sqlStatement(SqlMethod.UPDATE_BY_ID), param);
                    }
                    //这里有个小逻辑问题，如果更新失败是否要插入
                    if(i >= 1 && i % batchSize == 0){
                        batchSqlSession.flushStatements();
                    }
                    i++;
                }else{
                    throw ExceptionUtils.mpe("Error:  Can not execute. Could not find @TableId.");
                }
            }
            batchSqlSession.flushStatements();
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {
        return SqlHelper.retBool(baseMapper.deleteById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean remove(Wrapper<T> queryWrapper) {
        return SqlHelper.retBool(baseMapper.delete(queryWrapper));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateById(T entity) {
        return retBool(baseMapper.updateById(entity));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean alwaysUpdateSomeColumnById(T entity) {
        return retBool(baseMapper.alwaysUpdateSomeColumnById(entity));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(T entity, Wrapper<T> updateWrapper) {
        return retBool(baseMapper.update(entity,updateWrapper));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        if(CollectionUtils.isEmpty(entityList)){
            return true;
        }
        int i = 0;
        String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
        try(SqlSession batchSqlSession = sqlSessionBatch()){
            for(T anEntityList : entityList){
                MapperMethod.ParamMap<T> objectParamMap = new MapperMethod.ParamMap<>();
                objectParamMap.put(Constants.ENTITY, anEntityList);
                batchSqlSession.update(sqlStatement,objectParamMap);
                if(i>=1 && i%batchSize == 0){
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        }
        return false;
    }

    @Override
    public T getById(Serializable id) {
        return baseMapper.selectById(id);
    }

    @Override
    public int count(Wrapper<T> queryWrapper) {
        return SqlHelper.retCount(baseMapper.selectCount(queryWrapper));
    }

    @Override
    public List<T> list(Wrapper<T> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public <R> List<R> listObjs(Wrapper<T> queryWrapper, Function<? super Object, R> mapper) {
        return baseMapper.selectObjs(queryWrapper).stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
    }

    @Override
    public <R> List<R> entitys(Wrapper<T> wrapper, Function<? super T, R> mapper) {
        return list(wrapper).stream().map(mapper).collect(Collectors.toList());
    }

    @Override
    public <K> Map<K, T> list2Map(Wrapper<T> wrapper, SFunction<T, K> column) {
        return list2Map(list(wrapper), column);
    }

    private <K> Map<K, T> list2Map(List<T> list, SFunction<T, K> column){
        if(list == null){
            return Collections.emptyMap();
        }
        Map<K, T> map = new LinkedHashMap<>(list.size());
        for (T t : list) {
            Field field = ReflectionUtils.findField(t.getClass(), getColumn(LambdaUtils.resolve(column)));
            if(Objects.isNull(field)){
                continue;
            }
            ReflectionUtils.makeAccessible(field);
            Object field1 = ReflectionUtils.getField(field, t);
            map.put((K) field1, t);
        }
        return map;
    }

    private String getColumn(SerializedLambda lambda){
        return resolveFieldName(lambda.getImplMethodName());
    }

    public static String resolveFieldName(String getMethodName) {
        if (getMethodName.startsWith("get")) {
            getMethodName = getMethodName.substring(3);
        } else if (getMethodName.startsWith("is")) {
            getMethodName = getMethodName.substring(2);
        }
        // 小写第一个字母
        return StringUtils.firstToLowerCase(getMethodName);
    }
}
