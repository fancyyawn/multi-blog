package top.zhacker.blog.impl.jpa.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.dozer.DozerBeanMapper;
import top.zhacker.blog.model.Post;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * DATE: 17/1/5 上午10:03 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 博客实体类
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Table(name = "posts")
public class PostEntity extends EntityBase {
    private static final long serialVersionUID = -2826939241838421885L;

    private Long authorId;

    private String title;

    private String content;

    private Integer pv;
}
