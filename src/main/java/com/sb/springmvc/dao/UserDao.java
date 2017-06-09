package com.sb.springmvc.dao;

import java.util.List;

import com.sb.springmvc.model.User;

public interface UserDao {

	User findById(int id);
	
	User findBySSO(String sso);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserBySSO(String sso);
	
	List<User> findAllUser();
	
	
}
