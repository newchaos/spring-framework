package com.athm;

import com.athm.custom.User;
import com.athm.factorybean.Car;
import com.athm.factorybean.CarFactoryBean;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;

/**
 * 越来越觉得源码的阅读就是要多跟踪源码这样才行;
 * 不然只是看别人说啥的,或者看书啥的,很容易就会还给人家;
 * 所以只能逼迫自己输出才是最好的学习方法;
 * 而且只有多翻翻，才能领悟到其中的精髓；
 *
 * 一切框架的设计都必须要考虑扩展性扩展性；
 *
 * 关于environment的设计;
 * https://www.cnblogs.com/cxyAtuo/p/11618979.html
 *
 *
 * 有一种阅读源码的方法叫做制造错误跟踪法;
 *
 *
 */
public class DebugSpringBean{

	public static void main(String[] args) throws Exception {
		//args[0]="spring.profiles.active=dev";

		System.out.println("");
		System.out.println("this is springdubugger starting.........");
		System.out.println("");

		//debugSpringBeans();
		//debugCustomElement();
		//debugFactoryBean();
		debugApplicationContext();
		System.out.println("");
		System.out.println("this is springdubugger end.........");
		System.out.println("");

	}

	/**
	 * 有一种profile为dev的时候怎么实现的，这块没有搞明白;
	 */
	public static void debugSpringBeans(){
		// 调试spring-beans源码
		BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("beans.xml"),null);
		// 只要容器已经有了的bean，就可以通过这种方式把它给拿到;
		//XmlBeanDefinitionReader reader = beanFactory.getBean(XmlBeanDefinitionReader.class);
		//((StandardEnvironment)reader.getEnvironment()).setActiveProfiles("dev");
		//System.out.println(beanFactory.getBean("debugBean"));
		beanFactory.getBean("debugBeanAlias2");
		System.out.println(beanFactory.getBean("debugBeanAlias2"));
	}

	public static void debugCustomElement() {
		BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("beans.xml"),null);
		beanFactory.getBean("testbean");
		System.out.println(((User)beanFactory.getBean("testbean")).getUserName());
	}

	public static void debugFactoryBean() throws Exception {
		BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("beans.xml"),null);
		beanFactory.getBean("&car");
		beanFactory.getBean("&car");
		beanFactory.getBean("&car");
		beanFactory.getBean("car2");
		beanFactory.getBean("car2");
		beanFactory.getBean("car2");
		System.out.println((((CarFactoryBean)beanFactory.getBean("&car"))).getObject().toString());
		System.out.println(((Car)beanFactory.getBean("car2")).getBrand());
	}

	public static void debugApplicationContext() throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		System.out.println((Car)ctx.getBean("car3"));
	}
}
