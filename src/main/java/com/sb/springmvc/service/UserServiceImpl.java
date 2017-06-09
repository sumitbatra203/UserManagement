package com.sb.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.springmvc.dao.UserDao;
import com.sb.springmvc.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userDao;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public User findById(int id) {
		return userDao.findById(id);
	}

	public User findBySSO(String sso) {
		return userDao.findBySSO(sso);
	}

	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDao.saveUser(user);
	}

	public void updateUser(User user) {
		User userFromDb=userDao.findById(user.getId());
		if(userFromDb !=null){
			userFromDb.setSsoId(user.getSsoId());
			if(!user.getPassword().equals(userFromDb.getPassword())){
				userFromDb.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			userFromDb.setEmail(user.getEmail());
			userFromDb.setFirstName(user.getFirstName());
			userFromDb.setLastName(user.getLastName());
			userFromDb.setUserProfiles(user.getUserProfiles());
			
		}							
	}

	public void deleteUserBySSO(String sso) {
		userDao.deleteUserBySSO(sso);
	}

	public List<User> findAllUser() {
		return userDao.findAllUser();
	}

	public boolean isUserSSOUnique(Integer id, String sso) {
		User entity=userDao.findBySSO(sso);		
		return (entity==null || ((id!=null) && (entity.getId()==id)));
	}

}
