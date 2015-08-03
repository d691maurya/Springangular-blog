package com.myapp.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.myapp.dto.User;
import com.myapp.service.TokenService;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
 
	@Autowired
	TokenService tokenServiceImpl;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	String authToken = request.getHeader("x-auth-token");

        if ( (null != authToken) && !authToken.trim().equals("") && !authToken.trim().equalsIgnoreCase("null") ) {
            String strToken = authToken;
            System.out.println("Token: " + strToken);

            if ( tokenServiceImpl.validate(strToken) ) {
                System.out.println("valid token found");
                User user = tokenServiceImpl.getUserFromToken(strToken);
                request.setAttribute("loggedInUser", user);
            }else{
            	response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Authentication token was either missing or invalid." );
            }
        } else {
        	response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Authentication token was either missing or invalid." );
        }
    	
        return true;
    }
 
    @Override
    public void postHandle(HttpServletRequest request,  HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    	
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    	
    }
 
}