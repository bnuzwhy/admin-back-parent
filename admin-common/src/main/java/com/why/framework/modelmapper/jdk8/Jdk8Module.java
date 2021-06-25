package com.why.framework.modelmapper.jdk8;
import org.modelmapper.ModelMapper;
import org.modelmapper.Module;

/**
 * @description:
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/24
 **/
public class Jdk8Module implements Module {
    @Override
    public void setupModule(ModelMapper modelMapper) {
        modelMapper.getConfiguration().getConverters().add(0, new FromOptionalConverter());
        modelMapper.getConfiguration().getConverters().add(0, new ToOptionalConverter());
    }
}
