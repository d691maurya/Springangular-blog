package com.myapp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myapp.dao.PostDao;
import com.myapp.dto.Post;
import com.myapp.entity.EntPost;
import com.myapp.entity.EntUser;
import com.myapp.util.exception.ApplicationException;

@Repository("postDaoImpl")
public class PostDaoImpl implements PostDao {
	
	@Autowired
	public SessionFactory sessionFactory;
	
	public List<Post> getPosts() throws ApplicationException {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EntPost.class, "entPostAlias");
		criteria.createAlias("entPostAlias.createdByUser", "postCreatedBy", Criteria.LEFT_JOIN);
		
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("entPostAlias.id").as("id"))
				.add(Projections.property("entPostAlias.title").as("title"))
				.add(Projections.property("entPostAlias.body").as("body"))
				.add(Projections.property("entPostAlias.views").as("views"))
				.add(Projections.property("postCreatedBy.id").as("createdBy"))
				.add(Projections.property("postCreatedBy.username").as("username"))
			).setResultTransformer(new AliasToBeanResultTransformer(Post.class));
		
		@SuppressWarnings("unchecked")
		List<Post> posts = criteria.list();
		
		return posts;
	}

	public Post getPost(Integer postId) throws ApplicationException {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EntPost.class, "entPostAlias");
		criteria.createAlias("entPostAlias.createdByUser", "postCreatedBy", Criteria.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("entPostAlias.id", postId));
		
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("entPostAlias.id").as("id"))
				.add(Projections.property("entPostAlias.title").as("title"))
				.add(Projections.property("entPostAlias.body").as("body"))
				.add(Projections.property("entPostAlias.views").as("views"))
				.add(Projections.property("postCreatedBy.id").as("createdBy"))
				.add(Projections.property("postCreatedBy.username").as("username"))
			).setResultTransformer(new AliasToBeanResultTransformer(Post.class));
		
		@SuppressWarnings("unchecked")
		List<Post> posts = criteria.list();

		if(null != posts && posts.size() > 0){
			return posts.get(0);
		}
		
		return null;
	}

	public void updatePost(Post post, boolean isView, EntUser user) throws ApplicationException {
		
		try {
			int newViewCount = post.getViews() == null ? 0 : post.getViews();
			
			if(isView)
				newViewCount += 1;
			
			EntPost entPost = new EntPost(post.getId(), post.getTitle(), post.getBody(), newViewCount);
			entPost.setCreatedByUser(user);
			sessionFactory.getCurrentSession().update(entPost);
			
		} catch (Exception e) {
			throw new ApplicationException("DB Error", e);
		}
	}

	public void createPost(Post post, EntUser user) throws ApplicationException {
		
		try {
			EntPost entPost = new EntPost(null, post.getTitle(), post.getBody(), 0);
			entPost.setCreatedByUser(user);
			sessionFactory.getCurrentSession().save(entPost);
		} catch (Exception e) {
			throw new ApplicationException("DB Error", e);
		}
	}

	public void deletePost(Integer postId) throws ApplicationException {

		try {
			EntPost entPost = new EntPost();
			entPost.setId(postId);
			sessionFactory.getCurrentSession().delete(entPost);
		} catch (Exception e) {
			throw new ApplicationException("DB Error", e);
		}
	}

}
