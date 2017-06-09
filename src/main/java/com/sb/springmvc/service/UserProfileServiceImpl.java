package com.sb.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sb.springmvc.dao.UserProfileDao;
import com.sb.springmvc.model.UserProfile;

@Component
@Transactional
public class UserProfileServiceImpl implements UserProfileService{

	@Autowired
	@Qualifier("userProfileDao")
	UserProfileDao userProfileDao;
	
	
	public UserProfile findById(int id) {
		return userProfileDao.findById(id);
	}

	public UserProfile findByType(String type) {
		return userProfileDao.findByType(type);
	}

	public List<UserProfile> findAll() { 
		return userProfileDao.findAll();
	}

}
