package top.zhacker.blog.impl.mongodb;

import com.google.common.eventbus.EventBus;
import de.bripkens.gravatar.Gravatar;
import org.dozer.DozerBeanMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.zhacker.blog.impl.mongodb.listener.SaveMongoEventListener;

/**
 * DATE: 2017/3/30 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 */
@Configuration
@ComponentScan
@EnableMongoRepositories("top.zhacker.blog.impl.mongodb.repo")
@EntityScan("top.zhacker.blog.impl.mongodb.model")
public class BlogMongodbAutoConfig {

    @Bean
    public SaveMongoEventListener saveMongoEventListener(){
        return new SaveMongoEventListener();
    }

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
