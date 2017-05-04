package top.zhacker.blog.impl.jpa.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import top.zhacker.blog.impl.jpa.model.PostEntity;

/**
 * DATE: 17/3/24 下午2:05 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 博客存储
 */
@Repository
public interface PostRepo extends PagingAndSortingRepository<PostEntity, Long> {

    @Modifying
    @Query("update PostEntity p set p.title = ?2 , p.content = ?3 where p.id = ?1")
    Integer updateTitleAndContentById(Long id, String title, String content);

    Page<PostEntity> findByAuthorId(Long authorId, Pageable pageable);
}
