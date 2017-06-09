package com.sb.springmvc.dao;

import java.util.List;




import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sb.springmvc.model.User;

@Repository("userDao")
public class UserDaoImpl  extends AbstractDao<Integer,User> implements UserDao{

	static Logger logger=LoggerFactory.getLogger(UserDaoImpl.class);

	public User findById(int id) {
		User user=getByKey(id);
		if(user!=null){
		Hibernate.initialize(user.getUserProfiles());
		}
		return user;
	}

	public User findBySSO(String sso) {
		logger.debug("SSO is"+sso);
		Criteria criteria=createCriteria();
		criteria.add(Restrictions.eq("ssoId",sso));
		User user=(User)criteria.uniqueResult();
		if(user!=null){
		Hibernate.initialize(user.getUserProfiles());
		}
		return user;
	}

	public void saveUser(User user) {
		persist(user);
	}

	public void updateUser(User user) {
		update(user);
	}

	public void deleteUserBySSO(String sso) {
		Criteria crit=createCriteria();
		crit.add(Restrictions.eq("ssoId", sso));
		User user=(User)crit.uniqueResult();
		delete(user);		
	}

	public List<User> findAllUser() {
		Criteria crit=createCriteria();
		crit.addOrder(Order.asc("firstName"));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<User> userList=(List<User>)crit.list();
		
		// No need to fetch userProfiles since we are not showing them on list page. Let them lazy load. 
        // Uncomment below lines for eagerly fetching of userProfiles if you want.
        /*
        for(User user : users){
            Hibernate.initialize(user.getUserProfiles());
        }*/
		
		return userList;
	}
	
	
}
