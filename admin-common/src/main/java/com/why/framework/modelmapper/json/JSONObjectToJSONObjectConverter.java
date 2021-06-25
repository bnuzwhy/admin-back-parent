package com.why.framework.modelmapper.json;

import com.alibaba.fastjson.JSONObject;
import org.modelmapper.internal.Errors;
import org.modelmapper.spi.ConditionalConverter;
import org.modelmapper.spi.MappingContext;

/**
 * @description:
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/24
 **/
public class JSONObjectToJSONObjectConverter implements ConditionalConverter<JSONObject, JSONObject> {

    @Override
    public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
        return JSONObject.class.isAssignableFrom(sourceType)
                && JSONObject.class.isAssignableFrom(destinationType)
                ? MatchResult.FULL : MatchResult.NONE;
    }

    @Override
    public JSONObject convert(MappingContext<JSONObject, JSONObject> mappingContext) {
        if (mappingContext.getSource() == null) {
            return null;

        } else if (mappingContext.getSourceType().equals(mappingContext.getDestinationType())) {
            JSONObject source = mappingContext.getSource();
            return (JSONObject) source.clone();
        } else {
            throw new Errors().addMessage("Unsupported mapping types[%s->%s]",
                    mappingContext.getSourceType().getName(), mappingContext.getDestinationType())
                    .toMappingException();
        }

    }
}
