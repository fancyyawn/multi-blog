package top.zhacker.blog.impl.mongodb.service;

import com.google.common.base.Throwables;
import de.bripkens.gravatar.Gravatar;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.zhacker.blog.impl.mongodb.model.UserEntity;
import top.zhacker.blog.impl.mongodb.repo.UserRepo;
import top.zhacker.blog.model.Response;
import top.zhacker.blog.model.User;
import top.zhacker.blog.service.UserService;

/**
 * DATE: 17/3/24 下午1:55 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 用户服务实现
 */
@Service
@Slf4j
public class UserServiceJpaImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepo userRepo;

    private final DozerBeanMapper beanMapper;

    private final Gravatar gravatar;

    @Autowired
    public UserServiceJpaImpl(PasswordEncoder passwordEncoder, UserRepo userRepo, DozerBeanMapper beanMapper, Gravatar gravatar) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.beanMapper = beanMapper;
        this.gravatar = gravatar;
    }

    @Override
    public Response<Long> createUser(User user) {
        try {
            UserEntity entity = beanMapper.map(user, UserEntity.class);
            entity.setPassword(passwordEncoder.encode(user.getPassword()));
            entity.setAvatar(gravatar.getUrl(user.getEmail()));
            userRepo.save(entity);
            return Response.ok(entity.getId());
        }catch (Exception e){
            log.error("createUser fail, user={}, cause={}", user, Throwables.getStackTraceAsString(e));
            return Response.fail("user.create.fail");
        }
    }

    @Override
    public Response<User> findUserByName(String name) {
        try {
            UserEntity entity = userRepo.findByUsername(name);
            if (entity == null) {
                return Response.fail("user.not.exist");
            } else {
                return Response.ok(beanMapper.map(entity, User.class));
            }
        }catch (Exception e){
            log.error("findUserByName fail, name={}, cause={}", name, Throwables.getStackTraceAsString(e));
            return Response.fail("user.find.by.name.fail");
        }
    }

    @Override
    public Response<User> findUserById(Long id) {
        try {
            UserEntity entity = userRepo.findOne(id);
            if (entity == null) {
                return Response.fail("user.not.exist");
            } else {
                return Response.ok(beanMapper.map(entity, User.class));
            }
        }catch (Exception e){
            log.error("findUserById fail, id={}, cause={}", id, Throwables.getStackTraceAsString(e));
            return Response.fail("user.find.by.id.fail");
        }
    }
}
