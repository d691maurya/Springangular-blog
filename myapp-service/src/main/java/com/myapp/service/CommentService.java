package com.myapp.service;

import java.util.List;

import com.myapp.dto.Comment;
import com.myapp.util.exception.ApplicationException;

public interface CommentService {
	
	public List<Comment> getComments(Integer postId) throws ApplicationException;
	
	public Comment getComment(Integer commentId) throws ApplicationException;
	
	public Integer createComment(Comment comment) throws ApplicationException;
	
	public void deleteComment(Integer postId, Integer commentId) throws ApplicationException;
	
}
