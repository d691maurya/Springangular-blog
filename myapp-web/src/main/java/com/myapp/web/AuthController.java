package com.myapp.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myapp.dto.User;
import com.myapp.service.TokenService;
import com.myapp.service.UserService;
import com.myapp.util.exception.ApplicationException;

@Controller
@RequestMapping("/api")
public class AuthController {
	
	@Autowired
	TokenService tokenServiceImpl;
	
	@Autowired
	UserService userServiceImpl;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelMap login(@RequestBody User user, HttpServletResponse response) {
		
		ModelMap map = new ModelMap();
		
		try{
			User dbUser = userServiceImpl.getUser(user.getUsername(), user.getPassword());
			
			if( dbUser != null ) {
				String token = tokenServiceImpl.getToken(dbUser);
				
				if( null != token && !token.equals("") ){
					map.addAttribute("success", true);
					response.setHeader("x-auth-token", token);
				} else {
					map.addAttribute("error", true);
				}
			}
		} catch (ApplicationException e){
			map.addAttribute("error", true);
		}
		
		return map;
    }
	
}
