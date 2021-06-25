package com.why.common.mybatisplus.wrapper;

import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.AbstractChainWrapper;
import com.why.service.base.BaseService;

/**
 * @description:
 * @author: Stan | stan.wang@paytm.com
 * @create: 2021/1/26 2:56 下午
 **/
public class LambdaUpdateWrapperChain<T>
        extends AbstractChainWrapper<T, SFunction<T, ?>, LambdaUpdateWrapperChain<T>, LambdaUpdateWrapper<T>>
        implements Update<LambdaUpdateWrapperChain<T>, SFunction<T, ?>> {

    private final BaseService<T> baseService;

    public LambdaUpdateWrapperChain(BaseService<T> baseService){
        super();
        this.baseService = baseService;
        super.wrapperChildren = new LambdaUpdateWrapper<>();
    }
    @Override
    public LambdaUpdateWrapperChain<T> set(boolean condition, SFunction<T, ?> column, Object val) {
        wrapperChildren.set(condition, column, val);
        return typedThis;
    }

    @Override
    public LambdaUpdateWrapperChain<T> setSql(boolean condition, String sql) {
        wrapperChildren.setSql(condition,sql);
        return typedThis;
    }
    @Override
    public String getSqlSet() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getSqlSet");
    }

    public boolean execute(T entity) {
        return baseService.update(entity, getWrapper());
    }

    public boolean execute() {
        return baseService.update(getWrapper());
    }
}
