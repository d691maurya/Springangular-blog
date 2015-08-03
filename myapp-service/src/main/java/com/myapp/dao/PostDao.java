package com.myapp.dao;

import java.util.List;

import com.myapp.dto.Post;
import com.myapp.entity.EntUser;
import com.myapp.util.exception.ApplicationException;

public interface PostDao {
	
	public List<Post> getPosts() throws ApplicationException;
	
	public Post getPost(Integer postId) throws ApplicationException;
	
	public void updatePost(Post post, boolean isView, EntUser entUser) throws ApplicationException;
	
	public void createPost(Post post, EntUser user) throws ApplicationException;
	
	public void deletePost(Integer postId) throws ApplicationException;

}
