package top.zhacker.blog.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * DATE: 17/1/5 上午10:05 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 评论领域类
 */
@Data
@Accessors(chain = true)
public class Comment implements Serializable {
    private static final long serialVersionUID = 8343096061678282178L;

    private Long id;

    private Long authorId;

    private String content;

    private Long postId;

    private Date createdAt;

    private Date updatedAt;

    /**
     * 作者导航
     */
    private User author;

    /**
     * 博客导航
     */
    private Post post;
}
