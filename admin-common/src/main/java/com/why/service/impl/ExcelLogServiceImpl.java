package com.why.service.impl;

import com.why.mapper.ExcelLogMapper;
import com.why.model.ExceLog;
import com.why.service.ExcelLogService;
import com.why.service.base.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/25
 **/
@Service
@Slf4j
public class ExcelLogServiceImpl extends BaseServiceImpl<ExcelLogMapper, ExceLog> implements ExcelLogService {
}
