package com.myapp.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myapp.common.Mail;
import com.myapp.dto.Comment;
import com.myapp.dto.User;
import com.myapp.service.CommentService;
import com.myapp.util.exception.ApplicationException;

@Controller
@RequestMapping("/api")
public class CommentsController {

	@Autowired
	CommentService commentServiceImpl;
	
	@Autowired
	Mail mail;
	
	@RequestMapping(value="/posts/{postId}/comments", method= RequestMethod.GET)
    public ModelMap listPosts(@PathVariable(value="postId") Integer postId) {
		
		ModelMap map = new ModelMap();
		
		try{
			List<Comment> comments = commentServiceImpl.getComments(postId);
			map.addAttribute("comments", comments);
		} catch(ApplicationException e){
			map.addAttribute("error", true);
		}
       
        return map;
    }
	
	@RequestMapping(value="/posts/{postId}/comments", method= RequestMethod.POST)
    public ModelMap createPost(@PathVariable(value="postId") Integer postId, @RequestBody Comment comment, HttpServletRequest request) {
		
		ModelMap map = new ModelMap();
		
		try{
			User loggedInUser = (User) request.getAttribute("loggedInUser");
			
			if(null != loggedInUser) {
				comment.setCreatedBy(loggedInUser.getUserId());
				Integer newCommentId = commentServiceImpl.createComment(comment);
				Comment newComment = commentServiceImpl.getComment(newCommentId);
				map.addAttribute("comment", newComment);
				//mail.sendMail("info@xyz.com", "d691maurya@gmail.com", "test", "test");
			} else {
				map.addAttribute("error", true);
			}
		} catch(ApplicationException e){
			map.addAttribute("error", true);
		}
       
        return map;
    }
	
	@RequestMapping(value="/posts/{postId}/comments/{commentId}", method= RequestMethod.DELETE)
    public ModelMap deletePost(@PathVariable(value="postId") Integer postId, @PathVariable(value="commentId") Integer commentId, HttpServletRequest request) {
		
		ModelMap map = new ModelMap();
		
		try{
			User loggedInUser = (User) request.getAttribute("loggedInUser");
			
			if(null != loggedInUser) {
				commentServiceImpl.deleteComment(postId, commentId);
				map.addAttribute("success", true);
			} else {
				map.addAttribute("error", true);
			}
		} catch(ApplicationException e){
			map.addAttribute("error", true);
		}
       
        return map;
    }
	
}
