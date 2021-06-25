package com.why.service.impl;

import com.why.common.exception.admin.AdminException;
import com.why.common.utils.StringUtils;
import com.why.common.utils.TypeUtils;
import com.why.mapper.PostMapper;
import com.why.model.Post;
import com.why.model.UserPost;
import com.why.service.PostService;
import com.why.service.UserPostService;
import com.why.service.base.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * @description:
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/25
 **/
@Service
@Slf4j
public class PostServiceImpl extends BaseServiceImpl<PostMapper, Post> implements PostService {
    @Autowired
    private UserPostService userPostService;

    @Override
    public List<Post> selectPostList(Post post) {
        return query().like(StringUtils.isNotEmpty(post.getPostCode()), Post::getPostCode, post.getPostCode())
                .eq(StringUtils.isNotEmpty(post.getStatus()), Post::getStatus, post.getStatus())
                .like(StringUtils.isNotEmpty(post.getPostName()), Post::getPostName, post.getPostName())
                .list();
    }

    @Override
    public List<Post> selectAllPostsByUserId(Long userId) {
        List<Post> userPosts = selectPostsByUserId(userId);
        List<Post> posts = list();
        for (Post post : posts) {
            for (Post userRole : userPosts) {
                if (post.getPostId().longValue() == userRole.getPostId().longValue()) {
                    post.setFlag(true);
                    break;
                }
            }
        }
        return posts;
    }

    @Override
    public List<Post> selectPostsByUserId(Long userId) {
        return baseMapper.selectPostsByUserId(userId);
    }

    @Override
    public boolean deletePostByIds(String ids) {
        List<Long> postIds = StringUtils.split2List(ids, TypeUtils::castToLong);
        for (Long postId : postIds) {
            Post post = getById(postId);
            if (userPostService.query().eq(UserPost::getPostId, postId).exist()) {
                throw new AdminException(HttpServletResponse.SC_BAD_REQUEST, post.getPostName() + "已分配，不能删除");
            }
        }
        return delete().in(Post::getPostId, postIds).execute();
    }

    @Override
    public boolean checkPostNameUnique(Post post) {
        Long postId = post.getPostId();
        Post info = query().eq(Post::getPostName, post.getPostName()).getOne();
        return Objects.isNull(info) || info.getPostId().equals(postId);
    }

    @Override
    public boolean checkPostCodeUnique(Post post) {
        Long postId = post.getPostId();
        Post info = query().eq(Post::getPostCode, post.getPostCode()).getOne();
        return Objects.isNull(info) || info.getPostId().equals(postId);

    }
}
