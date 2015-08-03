package com.myapp.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.myapp.dto.User;
import com.myapp.service.TokenService;
import com.myapp.service.impl.TokenServiceImpl;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {
	
	TokenService tokenServiceImpl;
	
	AuthenticationManager authManager;
	
	public AuthenticationTokenProcessingFilter(AuthenticationManager authManager) {
        this.authManager = authManager;
        
        /*tokenServiceImpl = WebApplicationContextUtils.
        		  getRequiredWebApplicationContext(getServletContext()).
        		  getBean(TokenServiceImpl.class);*/
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	
        String authToken = ((HttpServletRequest) request).getHeader("x-auth-token");

        if ( (null != authToken) && !authToken.trim().equals("") && !authToken.trim().equalsIgnoreCase("null") ) {
            String strToken = authToken;
            System.out.println("Token: " + strToken);

            if ( tokenServiceImpl.validate(strToken) ) {
                System.out.println("valid token found");
                
                User user = tokenServiceImpl.getUserFromToken(strToken);
                
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
                SecurityContextHolder.getContext().setAuthentication(authManager.authenticate(authentication));
            }else{
                System.out.println("invalid token");
            }
        } else {
            System.out.println("no token found");
        }
        
        // continue thru the filter chain
        chain.doFilter(request, response);
    }

}