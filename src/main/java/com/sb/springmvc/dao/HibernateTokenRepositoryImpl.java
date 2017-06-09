package com.sb.springmvc.dao;

import java.util.Date;



import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sb.springmvc.model.PersistentLogin;

@Repository("tokenRepositoryDao")
@Transactional
public class HibernateTokenRepositoryImpl extends AbstractDao<String, PersistentLogin> implements PersistentTokenRepository{

	static final Logger logger=LoggerFactory.getLogger(HibernateTokenRepositoryImpl.class);
	
	public void createNewToken(PersistentRememberMeToken token) {		
		logger.info("creating new token");
		PersistentLogin persistentLogin=new PersistentLogin();
		persistentLogin.setUsername(token.getUsername());
		persistentLogin.setToken(token.getTokenValue());
		persistentLogin.setSeries(token.getSeries());
		persistentLogin.setLast_used(token.getDate());
		persist(persistentLogin);		
	}

	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		logger.info("in getTokenForSeries"+seriesId);
		try{
			Criteria crit=createCriteria();
			crit.add(Restrictions.eq("series",seriesId));
			PersistentLogin persistentLogin=(PersistentLogin) crit.uniqueResult();
			return new PersistentRememberMeToken(persistentLogin.getUsername(), persistentLogin.getSeries(),
                    persistentLogin.getToken(), persistentLogin.getLast_used());
		}catch(Exception e){
			logger.error("exception is"+e);
			return null;
		}				
	}

	
	public void removeUserTokens(String username) {
	       logger.info("Removing Token if any for user : {}", username);
	        Criteria crit = createCriteria();
	        crit.add(Restrictions.eq("username", username));
	        PersistentLogin persistentLogin = (PersistentLogin) crit.uniqueResult();
	        if (persistentLogin != null) {
	            logger.info("rememberMe was selected");
	            delete(persistentLogin);
	        }		
	}

	public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
	       	logger.info("Updating Token for seriesId : {}", seriesId);
	        PersistentLogin persistentLogin = getByKey(seriesId);
	        persistentLogin.setToken(tokenValue);
	        persistentLogin.setLast_used(lastUsed);
	        update(persistentLogin);		
	}

	
	
}
