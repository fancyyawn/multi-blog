package top.zhacker.blog.service;

import top.zhacker.blog.model.Response;

/**
 * DATE: 17/3/24 下午1:41 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 评论服务
 */
public interface CommentService {

    Response<Long> commentPost(Long postId, Long authorId, String commentContent);

    Response<Boolean> deleteCommentById(Long commentId, Long operatorId);
}
