package com.why.service.impl;

import com.why.common.utils.StringUtils;
import com.why.common.utils.TypeUtils;
import com.why.mapper.DictDataMapper;
import com.why.model.DictData;
import com.why.service.DictDataService;
import com.why.service.base.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/25
 **/
@Service
@Slf4j
public class DictDataServiceImpl extends BaseServiceImpl<DictDataMapper, DictData> implements DictDataService {
    @Override
    public List<DictData> selectDictDataList(DictData dictData) {
        return query().eq(StringUtils.isNotEmpty(dictData.getDictType()), DictData::getDictType, dictData.getDictType())
                .like(StringUtils.isNotEmpty(dictData.getDictLabel()), DictData::getDictLabel, dictData.getDictLabel())
                .eq(StringUtils.isNotEmpty(dictData.getStatus()), DictData::getStatus, dictData.getStatus())
                .list();
    }

    @Override
    public List<DictData> selectDictDataByType(String dictType) {
        return query().eq(DictData::getDictType, dictType).eq(DictData::getStatus, "0").orderByAsc(DictData::getDictSort).list();
    }

    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return query().select(DictData::getDictLabel).eq(DictData::getDictType, dictType).eq(DictData::getDictValue, dictValue).getObj(TypeUtils::castToString);
    }
}
