package top.zhacker.blog.impl.mongodb.service;

import com.google.common.base.Throwables;
import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import top.zhacker.blog.dto.PostPagingCriteria;
import top.zhacker.blog.event.PostVisitedEvent;
import top.zhacker.blog.impl.mongodb.model.CommentEntity;
import top.zhacker.blog.impl.mongodb.model.PostEntity;
import top.zhacker.blog.impl.mongodb.model.UserEntity;
import top.zhacker.blog.impl.mongodb.repo.CommentRepo;
import top.zhacker.blog.impl.mongodb.repo.PostRepo;
import top.zhacker.blog.impl.mongodb.repo.UserRepo;
import top.zhacker.blog.model.*;
import top.zhacker.blog.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DATE: 17/3/24 下午3:43 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 博客服务实现
 */
@Slf4j
@Service
public class PostServiceJpaImpl implements PostService {

    private final PostRepo postRepo;

    private final CommentRepo commentRepo;

    private final UserRepo userRepo;

    private final DozerBeanMapper beanMapper;

    private final EventBus eventBus;

    @Autowired
    public PostServiceJpaImpl(PostRepo postRepo, CommentRepo commentRepo, UserRepo userRepo,
                              DozerBeanMapper beanMapper, EventBus eventBus) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
        this.userRepo = userRepo;
        this.beanMapper = beanMapper;
        this.eventBus = eventBus;
    }

    @Override
    public Response<Long> createPost(Post post) {
        try {
            post.setPv(0);
            val entity = beanMapper.map(post, PostEntity.class);
            postRepo.save(entity);
            return Response.ok(entity.getId());

        }catch (Exception e){
            log.error("createPost fail, post={}, cause={}", post, Throwables.getStackTraceAsString(e));
            return Response.fail("post.create.fail");
        }
    }

    @Override
    public Response<Paging<Post>> pagingPosts(PostPagingCriteria criteria) {

        try {
            Page<PostEntity> postEntities;

            //根据作者ID进行分页查询
            Pageable pageable = new PageRequest(criteria.getPageNo(), criteria.getPageSize(), Sort.Direction.DESC, "id");
            if(criteria.getAuthorId()!=null) {
                postEntities = postRepo.findByAuthorId(criteria.getAuthorId(), pageable);
            }else{
                postEntities = postRepo.findAll(pageable);
            }

            //从实体类转换为领域类
            val posts = postEntities.getContent().stream()
                    .map(x -> x.toModel(Post.class))
                    .collect(Collectors.toList());

            //为博客关联作者
            posts.forEach(post -> {
                UserEntity entity = userRepo.findOne(post.getAuthorId());
                post.setAuthor(beanMapper.map(entity, User.class));
            });

            return Response.ok(new Paging<>(postEntities.getTotalElements(), posts));

        }catch (Exception e){
            log.error("pagingPosts posts fail, criteria={}, cause={}", criteria, Throwables.getStackTraceAsString(e));
            return Response.fail("post.paging.fail");
        }
    }

    @Override
    public Response<Post> findPostById(Long id) {
        try {
            return Response.ok(doFindPostById(id));
        }catch (Exception e){
            log.error("findPostById fail, id={}, cause={}", id, Throwables.getStackTraceAsString(e));
            return Response.fail("post.find.by.id.fail");
        }
    }

    private Post doFindPostById(Long id){
        PostEntity entity = postRepo.findOne(id);
        Post post = beanMapper.map(entity, Post.class); //todo NPE

        UserEntity userEntity = userRepo.findOne(post.getAuthorId());
        post.setAuthor(beanMapper.map(userEntity, User.class)); //todo NPE

        eventBus.post(new PostVisitedEvent(id, post.getAuthorId())); //todo

        return post;
    }

    @Override
    public Response<Post> findPostWithCommentsById(Long id) {
        try {
            Post post = doFindPostById(id);

            List<CommentEntity> commentEntities = commentRepo.findByPostId(post.getId());
            List<Comment> comments = commentEntities.stream()
                    .map(x -> x.toModel(Comment.class))
                    .collect(Collectors.toList());

            comments.forEach(comment->{
                UserEntity userEntity = userRepo.findOne(comment.getAuthorId());
                comment.setAuthor(beanMapper.map(userEntity, User.class));
                comment.setPost(post);
            });

            post.setComments(new Paging<>((long)comments.size(), comments));

            return Response.ok(post);
        }catch (Exception e){
            log.error("findPostWithCommentsById fail, id={}, cause={}", id, Throwables.getStackTraceAsString(e));
            return Response.fail("post.find.fail");
        }
    }

    @Override
    public Response<Boolean> updatePostTitleAndContent(Long id, String title, String content) {
        try {
            PostEntity entity = postRepo.findOne(id);
            if(entity==null){
                return Response.fail("post.not.exist");
            }
            entity.setTitle(title);
            entity.setContent(content);
            postRepo.save(entity);
            return Response.ok(Boolean.TRUE);
        }catch (Exception e){
            log.error("updatePostTitleAndContent fail, id={}, title={}, content={}, cause={}",
                    id, title, content, Throwables.getStackTraceAsString(e));
            return Response.fail("post.update.fail");
        }
    }

    @Override
    public Response<Boolean> deletePostById(Long id, Long operatorId) {
        try{
            PostEntity entity = postRepo.findOne(id);
            if(entity==null){
                return Response.fail("post.not.exist");
            }
            if(!entity.getAuthorId().equals(operatorId)){
                return Response.fail("permission.deny");
            }
            postRepo.delete(id);
            return Response.ok(Boolean.TRUE);
        }catch (Exception e){
            log.error("deletePostById fail, id={}, cause={}", id, Throwables.getStackTraceAsString(e));
            return Response.fail("post.delete.fail");
        }
    }
}
