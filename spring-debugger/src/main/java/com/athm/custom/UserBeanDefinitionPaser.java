package com.athm.custom;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.core.type.filter.AbstractClassTestingTypeFilter;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class UserBeanDefinitionPaser extends AbstractSingleBeanDefinitionParser {

	// element 对应的类;
	@Override
	protected Class<?> getBeanClass(Element element) {
		return User.class;
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {

		// 从element中解析并提取对应的元素;
		String userName = element.getAttribute("userName");
		String email = element.getAttribute("email");

		// 将提取的数据放入到BeanDefinitonBuilder中,待到完成所有的bean解析后统一注册到beanFactory中;
		if (StringUtils.hasText(userName)) {
			builder.addPropertyValue("userName",userName);

		}
		if(StringUtils.hasText(email)) {
			builder.addPropertyValue("email",email);
		}
	}
}
