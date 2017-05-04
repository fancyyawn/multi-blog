package top.zhacker.blog.web.thymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.zhacker.blog.service.CommentService;

/**
 * DATE: 17/1/11 上午10:28 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 评论视图
 */
@Controller
@RequestMapping("/posts/{postId}/comments")
public class Comments extends ViewBase {

    private final CommentService commentService;

    @Autowired
    public Comments(CommentService commentService) {
        this.commentService = commentService;
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public String comment(@PathVariable Long postId, String content, RedirectAttributes attributes){

        commentService.commentPost(postId, getCurrentUserId(), content);

        attributes.addFlashAttribute("success", "comment.create.success");
        return "redirect:/posts/"+postId;
    }

    @RequestMapping(value = "/{commentId}/remove", method = RequestMethod.POST)
    public String removeComment(@PathVariable Long commentId,
                                @PathVariable Long postId,
                                RedirectAttributes attributes){

        commentService.deleteCommentById(commentId, getCurrentUserId());
        attributes.addFlashAttribute("success", "comment.remove.success");
        return "redirect:/posts/"+postId;
    }
}
