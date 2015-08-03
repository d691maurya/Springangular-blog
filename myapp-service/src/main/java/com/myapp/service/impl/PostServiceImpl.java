package com.myapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapp.dao.PostDao;
import com.myapp.dao.UserDao;
import com.myapp.dto.Post;
import com.myapp.dto.User;
import com.myapp.service.PostService;
import com.myapp.util.exception.ApplicationException;

@Service("postServiceImpl")
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostDao postDaoImpl;
	
	@Autowired
	private UserDao userDaoImpl;

	@Transactional(readOnly=true)
	public List<Post> getPosts() throws ApplicationException {
		return postDaoImpl.getPosts();
	}

	@Transactional(readOnly=true)
	public Post getPost(Integer postId) throws ApplicationException {
		return postDaoImpl.getPost(postId);
	}

	@Transactional
	public void updatePost(Post post, boolean isView) throws ApplicationException {
		postDaoImpl.updatePost(post, isView, userDaoImpl.getUserEntity(post.getCreatedBy()));
	}
	
	@Transactional
	public void createPost(Post post) throws ApplicationException {
		postDaoImpl.createPost(post, userDaoImpl.getUserEntity(post.getCreatedBy()));
	}

	@Transactional
	public void deletePost(Integer postId) throws ApplicationException {
		postDaoImpl.deletePost(postId);
	}
	
}
