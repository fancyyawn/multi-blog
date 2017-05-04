package top.zhacker.blog.impl.mongodb.service;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zhacker.blog.impl.mongodb.model.CommentEntity;
import top.zhacker.blog.impl.mongodb.repo.CommentRepo;
import top.zhacker.blog.model.Response;
import top.zhacker.blog.service.CommentService;

/**
 * DATE: 17/3/24 下午3:43 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 评论服务实现
 */
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;

    @Autowired
    public CommentServiceImpl(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    @Override
    public Response<Long> commentPost(Long postId, Long authorId, String commentContent) {
        try {
            CommentEntity entity = new CommentEntity();
            entity.setPostId(postId).setAuthorId(authorId).setContent(commentContent);
            commentRepo.save(entity);
            return Response.ok(entity.getId());
        }catch (Exception e){
            log.error("commentPost fail, postId={}, authorId={}, content={}, cause={}",
                    postId, authorId, commentContent, Throwables.getStackTraceAsString(e));
            return Response.fail("comment.post.fail");
        }
    }

    @Override
    public Response<Boolean> deleteCommentById(Long commentId, Long operatorId) {
        try{
            CommentEntity entity = commentRepo.findOne(commentId);
            if(entity==null){
                return Response.fail("comment.not.exist");
            }
            if(!entity.getAuthorId().equals(operatorId)){
                return Response.fail("permission.deny");
            }
            commentRepo.delete(commentId);

            return Response.ok(Boolean.TRUE);
        }catch (Exception e){
            log.error("deleteCommentById fail, commentId={}, operatorId={}, cause={}",
                    commentId, operatorId, Throwables.getStackTraceAsString(e));
            return Response.fail("delete.comment.fail");
        }
    }
}
