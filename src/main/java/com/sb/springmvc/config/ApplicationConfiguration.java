package com.sb.springmvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.sb.springmvc.convertor.RolesToUserProfileConvertor;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"com.sb.springmvc"})
public class ApplicationConfiguration extends WebMvcConfigurerAdapter{

	@Autowired
	RolesToUserProfileConvertor  rolesToUserProfileConvertor;
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
	InternalResourceViewResolver internalResourceViewResolver=new InternalResourceViewResolver();
	internalResourceViewResolver.setPrefix("/WEB-INF/views/");
	internalResourceViewResolver.setSuffix(".jsp");
	registry.viewResolver(internalResourceViewResolver);	
	}
	
	//required for adding static js,css files to application
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
	registry.addConverter(rolesToUserProfileConvertor);	
	}
	
	@Bean
	public MessageSource messageSource(){
	ResourceBundleMessageSource bundleMessageSource=new ResourceBundleMessageSource();
	bundleMessageSource.setBasename("messages");
	return bundleMessageSource;
	}
	
	//adding to have . in parameters
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
	configurer.setUseRegisteredSuffixPatternMatch(true);	
	}

}
