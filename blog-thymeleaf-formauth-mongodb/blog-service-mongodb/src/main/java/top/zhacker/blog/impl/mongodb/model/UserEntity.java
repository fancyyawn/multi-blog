package top.zhacker.blog.impl.mongodb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * DATE: 17/1/5 上午10:01 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 用户实体类
 */
@Document(collection = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class UserEntity extends EntityBase {
    private static final long serialVersionUID = -1575177945061174211L;

    private String username;

    private String password;

    private String avatar;

    private String email;

    private String gender;

    private String bio;
}
