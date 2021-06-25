package com.why.service.impl;

import com.why.common.utils.StringUtils;
import com.why.mapper.NoticeMapper;
import com.why.model.Notice;
import com.why.service.NoticeService;
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
public class NoticeSercviceImpl extends BaseServiceImpl<NoticeMapper, Notice> implements NoticeService {
    @Override
    public List<Notice> selectNoticeList(Notice notice) {
        return query().like(StringUtils.isNotEmpty(notice.getNoticeTitle()), Notice::getNoticeTitle, notice.getNoticeTitle())
                .eq(StringUtils.isNotEmpty(notice.getNoticeType()), Notice::getNoticeType, notice.getNoticeType())
                .like(StringUtils.isNotEmpty(notice.getCreateBy()), Notice::getCreateBy, notice.getCreateBy())
                .list();
    }
}
