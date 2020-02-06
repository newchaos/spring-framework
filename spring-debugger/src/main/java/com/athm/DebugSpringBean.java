package com.athm;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class DebugSpringBean {

	public static void main(String[] args) {

		System.out.println("this is springdubugger starting.........");

		debugSpringBeans();

		System.out.println("this is springdubugger end.........");

	}

	public static void debugSpringBeans(){
		// 调试spring-beans源码
		BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("beans.xml"));
		beanFactory.getBean("debugBean");
	}
}
