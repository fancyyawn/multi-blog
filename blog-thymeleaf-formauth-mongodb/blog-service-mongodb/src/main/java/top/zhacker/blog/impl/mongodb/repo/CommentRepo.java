package top.zhacker.blog.impl.mongodb.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import top.zhacker.blog.impl.mongodb.model.CommentEntity;

import java.util.List;

/**
 * DATE: 17/3/24 下午2:06 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 评论存储
 */
@Repository
public interface CommentRepo extends PagingAndSortingRepository<CommentEntity, Long> {

    List<CommentEntity> findByPostId(Long postId);
}