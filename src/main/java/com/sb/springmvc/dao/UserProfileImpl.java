package com.sb.springmvc.dao;

import java.util.List;




import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sb.springmvc.model.User;
import com.sb.springmvc.model.UserProfile;

@Repository("userProfileDao")
public class UserProfileImpl extends AbstractDao<Integer,UserProfile> implements UserProfileDao  {

	public UserProfile findById(int id) {
		UserProfile userProfile=(UserProfile) getByKey(id);
		return userProfile;
	}

	public UserProfile findByType(String type) {
		Criteria crit =createCriteria();
		crit.add(Restrictions.eq("type",type));
		return (UserProfile) crit.uniqueResult();
	}

	public List<UserProfile> findAll() {
		Criteria crit=createCriteria();
		crit.addOrder(Order.asc("type"));
		return (List<UserProfile>)crit.list();
	}

}
