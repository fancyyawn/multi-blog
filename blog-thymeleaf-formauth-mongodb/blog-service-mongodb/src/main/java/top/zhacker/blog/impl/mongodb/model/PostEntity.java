package top.zhacker.blog.impl.mongodb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * DATE: 17/1/5 上午10:03 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 博客实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Document(collection = "posts")
public class PostEntity extends EntityBase {
    private static final long serialVersionUID = -2826939241838421885L;

    private Long authorId;

    private String title;

    private String content;

    private Integer pv;
}
