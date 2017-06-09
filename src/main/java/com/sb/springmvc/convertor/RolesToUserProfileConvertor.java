package com.sb.springmvc.convertor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.sb.springmvc.model.UserProfile;
import com.sb.springmvc.service.UserProfileService;


@Component
public class RolesToUserProfileConvertor implements Converter<Object,UserProfile> {

	static final Logger logger=LoggerFactory.getLogger(RolesToUserProfileConvertor.class); 
	
	@Autowired
	UserProfileService profileService;
	
	public UserProfile convert(Object element) {
		Integer id=Integer.parseInt((String)element);
		UserProfile userProfile=profileService.findById(id);
		logger.debug("user profile is"+userProfile);		
		return userProfile;
	}
	
}
