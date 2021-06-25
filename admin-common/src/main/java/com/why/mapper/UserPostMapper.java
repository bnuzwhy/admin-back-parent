package com.why.mapper;

import com.why.mapper.base.BaseMapper;
import com.why.model.UserPost;

import java.util.List;

/**
 * @description:
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/25
 **/
public interface UserPostMapper extends BaseMapper<UserPost> {
    /**
     * 批量新增用户岗位信息
     *
     * @param userPostList 用户角色列表
     * @return 结果
     */
    int batchUserPost(List<UserPost> userPostList);
}
