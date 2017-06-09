package com.sb.springmvc.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages={"com.sb.springmvc.config"})
@EnableTransactionManagement
@PropertySource(value={"classpath:application.properties"})
public class HibernateConfiguration {

	@Autowired
	public Environment environment;
	
	@Bean
	public LocalSessionFactoryBean sessionFactroy(){
		LocalSessionFactoryBean factoryBean=new LocalSessionFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan(new String[] {"com.sb.springmvc.model"});
		factoryBean.setHibernateProperties(hibernateProperties());
		return factoryBean;
	}
	
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource driverManagerDataSource=new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		driverManagerDataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		driverManagerDataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		driverManagerDataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		return driverManagerDataSource;
	}
	
	
	private Properties hibernateProperties(){
		 Properties properties= new Properties();
		 properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
	     properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
	     properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
	     return properties;      		
	}
	
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory factory){
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(factory);
		return txManager;
	}
	
}
