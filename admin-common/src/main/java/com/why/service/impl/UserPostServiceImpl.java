package com.why.service.impl;

import com.why.mapper.UserPostMapper;
import com.why.model.UserPost;
import com.why.service.UserPostService;
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
public class UserPostServiceImpl extends BaseServiceImpl<UserPostMapper, UserPost> implements UserPostService {
}
