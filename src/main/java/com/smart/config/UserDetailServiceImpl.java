package com.smart.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.dao.UserRepo;
import com.smart.entities.User;

public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepo userrepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//fetiching user from db
		User user=userrepo.getUserNameByUserName(username);
		
		if(user==null)
		{
			throw new UsernameNotFoundException("Could not found user !!");
			
		}
		
		CustomUserDetials customuserdetials=new CustomUserDetials(user);
		
		return customuserdetials;
	}

}
