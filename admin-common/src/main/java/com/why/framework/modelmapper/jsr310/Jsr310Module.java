package com.why.framework.modelmapper.jsr310;

import org.modelmapper.ModelMapper;
import org.modelmapper.Module;

/**
 * @description:
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/24
 **/
public class Jsr310Module implements Module {

    private final Jsr310ModuleConfig config;

    public Jsr310Module() {
        this(new Jsr310ModuleConfig());
    }

    public Jsr310Module(Jsr310ModuleConfig config) {
        this.config = config;
    }

    @Override
    public void setupModule(ModelMapper modelMapper) {
        modelMapper.getConfiguration().getConverters().add(0, new FromTemporalConverter(config));
        modelMapper.getConfiguration().getConverters().add(0, new ToTemporalConverter(config));
        modelMapper.getConfiguration().getConverters().add(0, new TemporalToTemporalConverter());
    }
}
