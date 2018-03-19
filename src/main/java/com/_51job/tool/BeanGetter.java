package com._51job.tool;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BeanGetter implements ApplicationContextAware{

	private ApplicationContext applicationContext;
	private static BeanGetter beanGetter;
	private BeanGetter(){}

	public static BeanGetter getBeanGetter() {
		return beanGetter;
	}

	public void setBeanGetter(BeanGetter beanGetter) {
		BeanGetter.beanGetter = beanGetter;
	}


	public Object getBean(String beanName){
		return applicationContext.getBean(beanName);
	}

	public <T> T getBean(String beanName, Class<T> tClass){
		return applicationContext.getBean(beanName,tClass);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}

	public List<String> allBeanNames(){
		return Arrays.asList(applicationContext.getBeanDefinitionNames());
	}
	public <T> Collection<T> beansByClass(Class<T> tClass){
		return applicationContext.getBeansOfType(tClass).values();
	}

}
