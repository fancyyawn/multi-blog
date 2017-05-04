package top.zhacker.blog.web.hbs.controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.zhacker.blog.dto.PostPagingCriteria;
import top.zhacker.blog.model.Post;
import top.zhacker.blog.model.Response;
import top.zhacker.blog.service.PostService;
import top.zhacker.blog.service.UserService;

/**
 * DATE: 17/1/5 上午10:55 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 博客视图
 */
@Controller
@RequestMapping("/posts")
public class Posts extends ViewBase {

    private final PostService postService;

    private final UserService userService;

    @Autowired
    public Posts(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @RequestMapping(value = "", method = {RequestMethod.GET})
    public String list(@RequestParam(required = false) Long author,
                       @RequestParam(defaultValue = "0") Integer pageNo,
                       @RequestParam(defaultValue = "20") Integer pageSize,
                       Model model){

        val respPaging = postService.pagingPosts(
                new PostPagingCriteria()
                        .setAuthorId(author)
                        .setPageNo(pageNo)
                        .setPageSize(pageSize));

        if(respPaging.isSuccess()) {
            model.addAttribute("posts", respPaging.getResult().getData());
            return "posts/list";
        }else{
            model.addAttribute("error", respPaging.getError());
            return "error";
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createPost(){
        return "posts/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createPost(Post post, RedirectAttributes redirectAttributes){

        post.setAuthorId(getCurrentUserId());

        Response<Long> resp = postService.createPost(post);
        if(resp.isSuccess()) {
            redirectAttributes.addFlashAttribute("success", "发表成功");
            return "redirect:/posts/" + resp.getResult();
        }else{
            redirectAttributes.addFlashAttribute("error", resp.getError());
            return "posts/create";
        }
    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.GET)
    public String findById(@PathVariable("postId") Long postId, Model model){

        Response<Post> postResp = postService.findPostWithCommentsById(postId);

        model.addAttribute("post", postResp.getResult());
        return "posts/detail";
    }

    @RequestMapping(value = "/{postId}/edit", method = RequestMethod.GET)
    public String editPost(@PathVariable("postId") Long postId, Model model){
        Response<Post> respPost = postService.findPostById(postId);
        model.addAttribute("post", respPost.getResult());
        return "posts/edit";
    }


    @RequestMapping(value = "/{postId}/edit", method = RequestMethod.POST)
    public String editPost(@PathVariable("postId") Long postId,
                       String title, String content, RedirectAttributes attributes){

        postService.updatePostTitleAndContent(postId, title, content);

        attributes.addFlashAttribute("success", "post.edit.success");
        return "redirect:/posts/"+ postId;
    }

    @RequestMapping(value = "/{postId}/remove", method = RequestMethod.POST)
    public String removePost(@PathVariable("postId") Long postId, RedirectAttributes attributes){

        postService.deletePostById(postId, getCurrentUserId());

        attributes.addFlashAttribute("success", "post.remove.success");
        return "redirect:/posts";
    }

}
