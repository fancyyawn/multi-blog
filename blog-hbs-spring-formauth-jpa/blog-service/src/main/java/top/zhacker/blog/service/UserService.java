package top.zhacker.blog.service;

import top.zhacker.blog.model.Response;
import top.zhacker.blog.model.User;

/**
 * DATE: 17/1/5 上午10:12 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 用户服务
 */
public interface UserService {

    Response<Long> createUser(User user);

    Response<User> findUserByName(String name);

    Response<User> findUserById(Long id);
}
