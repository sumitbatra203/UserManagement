package com.sb.springmvc.security;

import java.util.ArrayList;
import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.springmvc.model.User;
import com.sb.springmvc.model.UserProfile;
import com.sb.springmvc.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	 
	@Autowired	
	public UserService userService;

	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String ssoId)
			throws UsernameNotFoundException {
		User user =userService.findBySSO(ssoId);
		logger.info("value of user"+user);
		if(user==null){
			logger.error("user not found");
			throw new UsernameNotFoundException("username not found");
		}
		return new org.springframework.security.core.userdetails.User(user.getSsoId(),user.getPassword(),
				true, true, true, true, getGrantedAuthorities(user));
	}
	
	
	   private List<GrantedAuthority> getGrantedAuthorities(User user){
	        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	         
	        for(UserProfile userProfile : user.getUserProfiles()){
	            logger.info("UserProfile : {}", userProfile);
	            authorities.add(new SimpleGrantedAuthority("ROLE_"+userProfile.getType()));
	        }
	        logger.info("authorities : {}", authorities);
	        return authorities;
	    }
	     
}
