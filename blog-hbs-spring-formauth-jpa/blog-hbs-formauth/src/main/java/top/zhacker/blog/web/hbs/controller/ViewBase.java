package top.zhacker.blog.web.hbs.controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import top.zhacker.blog.model.User;
import top.zhacker.blog.service.UserService;

/**
 * DATE: 17/1/7 下午11:39 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 *
 * 视图基类： 用于获取当前用户信息
 */
public abstract class ViewBase {

    private UserService userService;

    @Autowired
    protected void setUserService(UserService userService){
        this.userService = userService;
    }

    @ModelAttribute("currentUser")
    public User getCurrentUser(){
        Object object = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if(object instanceof UserDetails){
            UserDetails userDetails = (UserDetails) object;
            val resp = userService.findUserByName(userDetails.getUsername());
            if(resp.isSuccess()){
                return resp.getResult();
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    public Long getCurrentUserId(){
        User user = getCurrentUser();
        if(user==null){
            return null;
        }else{
            return user.getId();
        }
    }
}
