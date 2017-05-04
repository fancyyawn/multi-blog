package top.zhacker.blog.impl.mongodb.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@Document(collection = "sequence")
public class SequenceId implements Serializable {

    @Id
    private String id;

    @Field("seq_id")
    private long seqId;

    @Field("coll_name")
    private String collName;

}