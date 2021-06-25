package com.why.common.mybatisplus.wrapper;

import com.baomidou.mybatisplus.core.conditions.AbstractLambdaWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: lambda update
 * @author: Stan | stan.wang@paytm.com
 * @create: 2021/1/26 2:37 下午
 **/
public class LambdaUpdateWrapper<T>
        extends AbstractLambdaWrapper<T, LambdaUpdateWrapper<T>>
        implements Update<LambdaUpdateWrapper<T>, SFunction<T, ?>> {

    /**
     * SQL 更新字段内容（set后内容），例如name = 'test', age = 20
     */
    private final List<String> sqlSet;

    public LambdaUpdateWrapper(){
        this(null);
    }
    public LambdaUpdateWrapper(T entity){
        super.setEntity(entity);
        super.initNeed();
        this.sqlSet = new ArrayList<>();
    }

    LambdaUpdateWrapper(T entity, List<String> sqlSet, AtomicInteger paramNameSeq, Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments){
        super.setEntity(entity);
        this.sqlSet = sqlSet;
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
    }

    @Override
    protected LambdaUpdateWrapper<T> instance() {
        return new LambdaUpdateWrapper<>(getEntity(),sqlSet, paramNameSeq, paramNameValuePairs, new MergeSegments());
    }


    @Override
    public LambdaUpdateWrapper<T> set(boolean condition, SFunction<T, ?> column, Object val) {
        if(condition){
            sqlSet.add(String.format("%s=%s", columnsToString(column), val.toString()));
        }
        return typedThis;
    }

    @Override
    public LambdaUpdateWrapper<T> setSql(boolean condition, String sql) {
        if(condition && !ObjectUtils.isEmpty(sql)){
            sqlSet.add(sql);
        }
        return typedThis;
    }
    @Override
    public String getSqlSet() {
        if (CollectionUtils.isEmpty(sqlSet)) {
            return null;
        }
        return String.join(StringPool.COMMA, sqlSet);
    }
}
