package com.myapp.dao;

import java.util.List;

import com.myapp.dto.Comment;
import com.myapp.entity.EntUser;
import com.myapp.util.exception.ApplicationException;

public interface CommentDao {

	public List<Comment> getComments(int postId) throws ApplicationException;
	
	public Comment getComment(int commentId) throws ApplicationException;
	
	public Integer createComment(Comment comment, EntUser user) throws ApplicationException;
	
	public void deleteComment(Integer postId, Integer commentId) throws ApplicationException;
	
}
