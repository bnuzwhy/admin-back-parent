package com.why.framework.modelmapper.json;

import org.modelmapper.ModelMapper;
import org.modelmapper.Module;

/**
 * @description:
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/24
 **/
public class FastJsonModule implements Module {
    @Override
    public void setupModule(ModelMapper modelMapper) {
        modelMapper.getConfiguration().getConverters().add(0, new JSONObjectToJSONObjectConverter());
        modelMapper.getConfiguration().getConverters().add(0, new JSONArrayToJSONArrayConverter());
    }
}
