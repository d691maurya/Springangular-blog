package com.myapp.service;

import com.myapp.dto.User;

public interface TokenService {
    
	String getToken(User user);
    
    boolean validate(String token);
    
    User getUserFromToken(String token);
    
}