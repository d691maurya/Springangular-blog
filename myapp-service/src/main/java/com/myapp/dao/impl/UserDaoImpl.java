package com.myapp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myapp.dao.UserDao;
import com.myapp.dto.User;
import com.myapp.entity.EntUser;
import com.myapp.util.exception.ApplicationException;

@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {
	
	@Autowired
	public SessionFactory sessionFactory;

	public User getUser(String username, String password) throws ApplicationException {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EntUser.class, "entUserAlias");
		
		criteria.add(Restrictions.eq("entUserAlias.username", username))
				.add(Restrictions.eq("entUserAlias.password", password));
		
		criteria.setProjection(Projections.projectionList()
								.add(Projections.property("entUserAlias.id").as("userId"))
								.add(Projections.property("entUserAlias.username").as("username"))
								.add(Projections.property("entUserAlias.password").as("password"))
							).setResultTransformer(new AliasToBeanResultTransformer(User.class));
		
		@SuppressWarnings("unchecked")
		List<User> user = criteria.list();
		
		if(null != user && user.size() > 0)
			return user.get(0);
		
		return null;
	}

	public User getUser(Integer id) throws ApplicationException {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EntUser.class, "entUserAlias");
		
		criteria.add(Restrictions.eq("entUserAlias.id", id));
		
		criteria.setProjection(Projections.projectionList()
								.add(Projections.property("entUserAlias.id").as("userId"))
								.add(Projections.property("entUserAlias.username").as("username"))
								.add(Projections.property("entUserAlias.password").as("password"))
							).setResultTransformer(new AliasToBeanResultTransformer(User.class));
		
		@SuppressWarnings("unchecked")
		List<User> user = criteria.list();
		
		if(null != user && user.size() > 0)
			return user.get(0);
		
		return null;
	}

	public EntUser getUserEntity(Integer id) throws ApplicationException {
		return  (EntUser) sessionFactory.getCurrentSession().get(EntUser.class, id);
	}

}
