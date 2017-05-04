package top.zhacker.blog.web.hbs.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import top.zhacker.blog.model.Response;
import top.zhacker.blog.model.User;
import top.zhacker.blog.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * DATE: 17/1/6 下午5:36 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * SpringSecurity要求的获取用户信息的组件
 *
 */
@Slf4j
public class BlogUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Response<User> resp =  userService.findUserByName(username);
        if(resp.isFail()){
            throw new RuntimeException(resp.getError());
        }
        List<GrantedAuthority> gas = new ArrayList<>();
        gas.add(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                username, resp.getResult().getPassword(), true, true, true, true, gas);
        return userDetails;
    }
}
