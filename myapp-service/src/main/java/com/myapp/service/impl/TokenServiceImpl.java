package com.myapp.service.impl;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.myapp.dto.User;
import com.myapp.service.TokenService;

@Service("tokenServiceImpl")
public class TokenServiceImpl implements TokenService {
	
	private ConcurrentHashMap<String, User> session;
	
	public TokenServiceImpl(){
		session =  new ConcurrentHashMap<String, User>();
	}

	public String getToken(User user) {
		UUID token = UUID.randomUUID();
		session.putIfAbsent(token.toString(), user);
        return token.toString();
	}

	public boolean validate(String token) {
		return session.containsKey(token);
	}

	public User getUserFromToken(String token) {
		return session.get(token);
	}

}
