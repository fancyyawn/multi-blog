package top.zhacker.blog.impl.mongodb.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import top.zhacker.blog.impl.mongodb.model.UserEntity;

/**
 * DATE: 17/3/24 下午2:05 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 用户存储
 */
@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
}
