package com.myapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapp.dao.CommentDao;
import com.myapp.dao.UserDao;
import com.myapp.dto.Comment;
import com.myapp.service.CommentService;
import com.myapp.util.exception.ApplicationException;

@Service("commentServiceImpl")
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	CommentDao commentDaoImpl;
	
	@Autowired
	UserDao userDaoImpl;

	@Transactional(readOnly=true)
	public List<Comment> getComments(Integer postId) throws ApplicationException {
		return commentDaoImpl.getComments(postId);
	}

	@Transactional
	public Integer createComment(Comment comment) throws ApplicationException {
		return commentDaoImpl.createComment(comment, userDaoImpl.getUserEntity(comment.getCreatedBy()));
	}

	@Transactional
	public void deleteComment(Integer postId, Integer commentId) throws ApplicationException {
		commentDaoImpl.deleteComment(postId, commentId);
	}

	@Transactional
	public Comment getComment(Integer commentId) throws ApplicationException {
		return commentDaoImpl.getComment(commentId);
	}

}
