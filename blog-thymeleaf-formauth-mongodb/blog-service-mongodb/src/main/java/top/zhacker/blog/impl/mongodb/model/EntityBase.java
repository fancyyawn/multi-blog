package top.zhacker.blog.impl.mongodb.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.dozer.DozerBeanMapper;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * DATE: 17/3/24 下午2:01 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 实体类基类
 */
@Data
@Accessors(chain = true)
public class EntityBase implements Serializable {

    public static final DozerBeanMapper beanMapper = new DozerBeanMapper();

    @Id
    @GeneratedValue
    private Long id;

    private Date createdAt;

    private Date updatedAt;

    /**
     * 转换成对应的领域类
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T toModel(Class<T> clazz){
        return beanMapper.map(this, clazz);
    }
}
