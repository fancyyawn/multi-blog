package top.zhacker.blog.impl.mongodb.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import top.zhacker.blog.impl.mongodb.model.PostEntity;

/**
 * DATE: 17/3/24 下午2:05 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 博客存储
 */
@Repository
public interface PostRepo extends PagingAndSortingRepository<PostEntity, Long> {

    Page<PostEntity> findByAuthorId(Long authorId, Pageable pageable);
}
