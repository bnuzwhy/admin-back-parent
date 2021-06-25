package com.why.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.why.common.constants.UserConstants;
import com.why.common.exception.admin.AdminException;
import com.why.common.utils.StringUtils;
import com.why.common.utils.TypeUtils;
import com.why.mapper.DictTypeMapper;
import com.why.model.DictType;
import com.why.model.base.Ztree;
import com.why.service.DictTypeService;
import com.why.service.base.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @description:
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/25
 **/
@Service
@Slf4j
public class DictTypeServiceImpl extends BaseServiceImpl<DictTypeMapper, DictType> implements DictTypeService {
    @Override
    public List<DictType> selectDictTypeList(DictType dictType) {
        Date beginTime = dictType.getBeginTime();
        Date endTime = dictType.getEndTime();
        return query().like(StringUtils.isNotEmpty(dictType.getDictName()), DictType::getDictName, dictType.getDictName())
                .eq(StringUtils.isNotEmpty(dictType.getStatus()), DictType::getStatus, dictType.getStatus())
                .like(StringUtils.isNotEmpty(dictType.getDictType()), DictType::getDictType, dictType.getDictType())
                .gt(Objects.nonNull(beginTime), DictType::getCreateTime, beginTime)
                .lt(Objects.nonNull(endTime), DictType::getCreateTime, endTime)
                .list();
    }

    @Override
    public boolean deleteDictTypeByIds(String ids) {
        List<Long> dictIds = StringUtils.split2List(ids, TypeUtils::castToLong);
        for (Long dictId : dictIds) {
            DictType dictType = getById(dictId);
            if (query().eq(DictType::getDictType, dictType.getDictType()).exist()) {
                throw new AdminException(HttpServletResponse.SC_BAD_REQUEST, dictType.getDictName() + "已分配，不能删除");
            }
        }
        return delete().in(DictType::getDictId, dictIds).execute();
    }

    @Override
    public boolean updateDictType(DictType dictType) {
        DictType oldDict = getById(dictType.getDictId());
        DictType updateDictType = new DictType();
        updateDictType.setDictType(dictType.getDictType());
        update(updateDictType, Wrappers.<DictType>lambdaQuery().eq(DictType::getDictType, oldDict.getDictType()));
        return updateById(dictType);
    }

    @Override
    public boolean checkDictTypeUnique(DictType dict) {
        Long dictId = dict.getDictId();
        DictType info = query().eq(DictType::getDictType, dict.getDictType()).getOne();
        return Objects.isNull(info) || info.getDictId().equals(dictId);
    }

    @Override
    public List<Ztree> selectDictTree(DictType dictType) {
        List<Ztree> ztrees = new ArrayList<>();
        List<DictType> dictList = selectDictTypeList(dictType);
        for (DictType dict : dictList) {
            if (UserConstants.DICT_NORMAL.equals(dict.getStatus())) {
                Ztree ztree = new Ztree();
                ztree.setId(dict.getDictId());
                ztree.setName(transDictName(dict));
                ztree.setTitle(dict.getDictType());
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }

    public String transDictName(DictType dictType) {
        StringBuilder builder = new StringBuilder();
        builder.append("(").append(dictType.getDictName()).append(")");
        builder.append("&nbsp;&nbsp;&nbsp;").append(dictType.getDictType());
        return builder.toString();
    }
}
