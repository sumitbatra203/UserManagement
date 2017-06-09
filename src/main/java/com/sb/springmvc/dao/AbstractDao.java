package com.sb.springmvc.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao<PK extends Serializable,T> {

	private final Class<T> persistantClass;
	
	@SuppressWarnings("unchecked")
	public AbstractDao(){
		this.persistantClass= (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")	
	public T getByKey(PK key){
		return (T) getSession().get(persistantClass,key);
	}
	
	
	public void persist(T entity){
		getSession().persist(entity);
	}
	
	
	public void update(T entity){
		getSession().update(entity);
	}
	
	public void delete(T entity){
		getSession().delete(entity);
	}
	
	protected Criteria createCriteria(){
		return getSession().createCriteria(persistantClass);
	}
	
	
}
