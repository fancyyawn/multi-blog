package top.zhacker.blog.impl.mongodb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * DATE: 17/1/5 上午10:05 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 评论实体类
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Document(collection = "comments")
public class CommentEntity extends EntityBase {
    private static final long serialVersionUID = 8343096061678282178L;

    private Long authorId;

    private String content;

    private Long postId;
}
