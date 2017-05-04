package top.zhacker.blog.impl.jpa;

import com.google.common.eventbus.EventBus;
import de.bripkens.gravatar.Gravatar;
import org.dozer.DozerBeanMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * DATE: 17/3/24 下午1:48 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 基于JPA实现的服务层配置类
 */
@Configuration
@ComponentScan
@EnableJpaRepositories("top.zhacker.blog.impl.jpa.repo")
@EntityScan("top.zhacker.blog.impl.jpa.model")
public class BlogJpaAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Gravatar gravatar(){
        return new Gravatar();
    }

    @Bean
    public DozerBeanMapper beanMapper(){
        return new DozerBeanMapper();
    }

    @Bean
    public EventBus eventBus(){
        return new EventBus();
    }
}
