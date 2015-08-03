package com.myapp.dao;

import com.myapp.dto.User;
import com.myapp.entity.EntUser;
import com.myapp.util.exception.ApplicationException;

public interface UserDao {
	
	public User getUser(String username, String password) throws ApplicationException;
	
	public User getUser(Integer id) throws ApplicationException;
	
	public EntUser getUserEntity(Integer id) throws ApplicationException;

}
