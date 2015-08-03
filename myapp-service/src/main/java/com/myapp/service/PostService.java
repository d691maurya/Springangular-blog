package com.myapp.service;

import java.util.List;

import com.myapp.dto.Post;
import com.myapp.util.exception.ApplicationException;

public interface PostService {
	
	public List<Post> getPosts() throws ApplicationException;
	
	public Post getPost(Integer postId) throws ApplicationException;
	
	public void updatePost(Post post, boolean isView) throws ApplicationException;
	
	public void createPost(Post post) throws ApplicationException;
	
	public void deletePost(Integer postId) throws ApplicationException;

}
