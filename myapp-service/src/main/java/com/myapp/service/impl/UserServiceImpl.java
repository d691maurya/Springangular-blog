package com.myapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapp.dao.UserDao;
import com.myapp.dto.User;
import com.myapp.service.UserService;
import com.myapp.util.exception.ApplicationException;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDaoImpl;
	
	@Transactional(readOnly=true)
	public User getUser(String username, String password) throws ApplicationException {
		return userDaoImpl.getUser(username, password);
	}

	@Transactional(readOnly=true)
	public User getUser(Integer id) throws ApplicationException {
		return userDaoImpl.getUser(id);
	}

}
