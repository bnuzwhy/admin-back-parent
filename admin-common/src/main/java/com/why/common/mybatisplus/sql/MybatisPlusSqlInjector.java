package com.why.common.mybatisplus.sql;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.baomidou.mybatisplus.core.injector.methods.*;
import com.baomidou.mybatisplus.extension.injector.methods.AlwaysUpdateSomeColumnById;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description: MybatisPlusSql注入器
 * @author: Stan | stan.wang@paytm.com
 * @create: 2021/1/27 8:43 下午
 **/
public class MybatisPlusSqlInjector extends AbstractSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        return Stream.of(
                new Insert(),
                new Delete(),
                new DeleteById(),
                new Update(),
                new UpdateById(),
                new AlwaysUpdateSomeColumnById(),
                new SelectById(),
                new SelectCount(),
                new SelectObjs(),
                new SelectList()
        ).collect(Collectors.toList());
    }
}
