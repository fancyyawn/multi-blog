package top.zhacker.blog.impl.mongodb.listener;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ReflectionUtils;
import top.zhacker.blog.impl.mongodb.model.GeneratedValue;
import top.zhacker.blog.impl.mongodb.model.SequenceId;

import javax.annotation.Resource;
import java.lang.reflect.Field;

public class SaveMongoEventListener extends AbstractMongoEventListener<Object> {
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        if(source != null) {
            ReflectionUtils.doWithFields(source.getClass(), (Field field) ->{
                ReflectionUtils.makeAccessible(field);
                if (field.isAnnotationPresent(GeneratedValue.class)) {
                    Object fieldValue = field.get(source);
                    if(fieldValue == null) {
                        field.set(source, getNextId(source.getClass().getSimpleName()));
                    }
                }
            });
        }
   }

 /**
  * 获取下一个自增ID
  * @author yinjihuan
  * @param collName  集合名
  * @return
  */
 private Long getNextId(String collName) {
     Query query = new Query(Criteria.where("collName").is(collName));
     Update update = new Update();
     update.inc("seqId", 1);
     FindAndModifyOptions options = new FindAndModifyOptions();
     options.upsert(true);
     options.returnNew(true);
     SequenceId seqId = mongoTemplate.findAndModify(query, update, options, SequenceId.class);
     return seqId.getSeqId();
 }

}