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

import com.myapp.dto.Post;
import com.myapp.dto.User;
import com.myapp.service.PostService;
import com.myapp.util.exception.ApplicationException;

@Controller
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	public PostService postServiceImpl;
	
	@RequestMapping(value="/posts", method= RequestMethod.GET)
    public ModelMap listPosts() {
		
		ModelMap map = new ModelMap();
		
		try{
			List<Post> posts = postServiceImpl.getPosts();
			map.addAttribute("posts", posts);
		} catch(ApplicationException e){
			map.addAttribute("error", true);
		}
       
        return map;
    }
	
	@RequestMapping(value="/posts/{postId}", method= RequestMethod.GET)
    public ModelMap getPost(@PathVariable(value="postId") Integer postId) {
		
		ModelMap map = new ModelMap();
		
		try{
			Post post = postServiceImpl.getPost(postId);
			
			if(null != post){
				postServiceImpl.updatePost(post, true);
			}
			
			map.addAttribute("post", post);
		} catch(ApplicationException e){
			map.addAttribute("error", true);
		}
       
        return map;
    }
	
	@RequestMapping(value="/posts", method= RequestMethod.POST)
    public ModelMap createPost(@RequestBody Post post, HttpServletRequest request) {
		
		ModelMap map = new ModelMap();
		
		try{
			User loggedInUser = (User) request.getAttribute("loggedInUser");
			
			if(null != loggedInUser) {
				post.setCreatedBy(loggedInUser.getUserId());
				postServiceImpl.createPost(post);
				map.addAttribute("success", true);
			} else {
				map.addAttribute("error", true);
			}
		} catch(ApplicationException e){
			map.addAttribute("error", true);
		}
       
        return map;
    }
	
	@RequestMapping(value="/posts", method= RequestMethod.PUT)
    public ModelMap updatePost(@RequestBody Post post, HttpServletRequest request) {
		
		ModelMap map = new ModelMap();
		
		try{
			User loggedInUser = (User) request.getAttribute("loggedInUser");
			
			if(null != loggedInUser) {
				post.setCreatedBy(loggedInUser.getUserId());
				postServiceImpl.updatePost(post, false);
				map.addAttribute("success", true);
			} else {
				map.addAttribute("error", true);
			}
		} catch(ApplicationException e){
			map.addAttribute("error", true);
		}
       
        return map;
    }
	
	@RequestMapping(value="/posts/{postId}", method= RequestMethod.DELETE)
    public ModelMap deletePost(@PathVariable(value="postId") Integer postId, HttpServletRequest request) {
		
		ModelMap map = new ModelMap();
		
		try{
			User loggedInUser = (User) request.getAttribute("loggedInUser");
			
			if(null != loggedInUser) {
				postServiceImpl.deletePost(postId);
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
