package top.zhacker.blog.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * DATE: 17/1/5 上午10:03 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 博客领域类，包含用户、评论等导航信息
 *
 */
@Data
@Accessors(chain = true)
public class Post implements Serializable {
    private static final long serialVersionUID = -2826939241838421885L;

    private Long id;

    private Long authorId;

    private String title;

    private String content;

    private Integer pv; //访问数量

    private Date createdAt;

    private Date updatedAt;

    /**
     * 作者导航
     */
    private User author;

    /**
     * 评论导航
     */
    private Paging<Comment> comments;
}
