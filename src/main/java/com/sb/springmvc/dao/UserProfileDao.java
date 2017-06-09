package com.sb.springmvc.dao;

import java.util.List;

import com.sb.springmvc.model.UserProfile;

public interface UserProfileDao {
	
	UserProfile findById(int id);
	
	UserProfile findByType(String type);
	
	List<UserProfile> findAll();

}
