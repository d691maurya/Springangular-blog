package com.myapp.service;

import com.myapp.dto.User;
import com.myapp.util.exception.ApplicationException;

public interface UserService {
	
	public User getUser(String username, String password) throws ApplicationException;
	
	public User getUser(Integer id) throws ApplicationException;

}
