package com.myapp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myapp.dao.CommentDao;
import com.myapp.dto.Comment;
import com.myapp.entity.EntComment;
import com.myapp.entity.EntUser;
import com.myapp.util.exception.ApplicationException;

@Repository("commentDaoImpl")
public class CommentDaoImpl implements CommentDao {
	
	@Autowired
	public SessionFactory sessionFactory;

	public List<Comment> getComments(int postId) throws ApplicationException {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EntComment.class, "entCommentAlias");
		criteria.createAlias("entCommentAlias.createdByUser", "commentCreatedBy", Criteria.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("entCommentAlias.postId", postId));
		
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("entCommentAlias.id").as("id"))
				.add(Projections.property("entCommentAlias.body").as("body"))
				.add(Projections.property("entCommentAlias.postId").as("postId"))
				.add(Projections.property("entCommentAlias.createdAt").as("createdAt"))
				.add(Projections.property("commentCreatedBy.id").as("createdBy"))
				.add(Projections.property("commentCreatedBy.username").as("username"))
			).setResultTransformer(new AliasToBeanResultTransformer(Comment.class));
		
		@SuppressWarnings("unchecked")
		List<Comment> comments = criteria.list();
		
		return comments;
	}

	public Integer createComment(Comment comment, EntUser user) throws ApplicationException {

		try {
			EntComment entComment = new EntComment(null, comment.getBody(), comment.getPostId());
			entComment.setCreatedByUser(user);
			return (Integer) sessionFactory.getCurrentSession().save(entComment);
		} catch (Exception e) {
			throw new ApplicationException("DB Error", e);
		}
	}

	public void deleteComment(Integer postId, Integer commentId) throws ApplicationException {

		try {
			EntComment entComment = new EntComment();
			entComment.setId(commentId);
			entComment.setPostId(postId);
			sessionFactory.getCurrentSession().delete(entComment);
		} catch (Exception e) {
			throw new ApplicationException("DB Error", e);
		}
		
	}

	public Comment getComment(int commentId) throws ApplicationException {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EntComment.class, "entCommentAlias");
		criteria.createAlias("entCommentAlias.createdByUser", "commentCreatedBy", Criteria.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("entCommentAlias.id", commentId));
		
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("entCommentAlias.id").as("id"))
				.add(Projections.property("entCommentAlias.body").as("body"))
				.add(Projections.property("entCommentAlias.postId").as("postId"))
				.add(Projections.property("entCommentAlias.createdAt").as("createdAt"))
				.add(Projections.property("commentCreatedBy.id").as("createdBy"))
				.add(Projections.property("commentCreatedBy.username").as("username"))
			).setResultTransformer(new AliasToBeanResultTransformer(Comment.class));
		
		@SuppressWarnings("unchecked")
		List<Comment> comments = criteria.list();
		
		if(null != comments)
			return comments.get(0);
		
		return null;
	}

}
