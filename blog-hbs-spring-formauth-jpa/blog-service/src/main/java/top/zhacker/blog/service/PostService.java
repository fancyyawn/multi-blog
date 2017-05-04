package top.zhacker.blog.service;

import top.zhacker.blog.dto.PostPagingCriteria;
import top.zhacker.blog.model.Paging;
import top.zhacker.blog.model.Post;
import top.zhacker.blog.model.Response;

/**
 * DATE: 17/3/24 下午1:37 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 博客服务
 */
public interface PostService {

    Response<Long> createPost(Post post);

    Response<Paging<Post>> pagingPosts(PostPagingCriteria criteria);

    Response<Post> findPostById(Long id);

    Response<Post> findPostWithCommentsById(Long id);

    Response<Boolean> updatePostTitleAndContent(Long id, String title, String content);

    Response<Boolean> deletePostById(Long id, Long operatorId);
}
