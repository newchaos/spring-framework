/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.aop.framework;

import java.io.Serializable;
import java.lang.reflect.Proxy;

import org.springframework.aop.SpringProxy;

/**
 * Default {@link AopProxyFactory} implementation, creating either a CGLIB proxy
 * or a JDK dynamic proxy.
 *
 * <p>Creates a CGLIB proxy if one the following is true for a given
 * {@link AdvisedSupport} instance:
 * <ul>
 * <li>the {@code optimize} flag is set
 * <li>the {@code proxyTargetClass} flag is set
 * <li>no proxy interfaces have been specified
 * </ul>
 *
 * <p>In general, specify {@code proxyTargetClass} to enforce a CGLIB proxy,
 * or specify one or more interfaces to use a JDK dynamic proxy.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 12.03.2004
 * @see AdvisedSupport#setOptimize
 * @see AdvisedSupport#setProxyTargetClass
 * @see AdvisedSupport#setInterfaces
 */
@SuppressWarnings("serial")
public class DefaultAopProxyFactory implements AopProxyFactory, Serializable {

	/**
	 * 如果目标对象实现了接口，默认情况下会采用jdk的动态代理实现aop；
	 * 如果目标对象实现了接口，可以强制使用cglib的aop；
	 * 如果目标对象没有实现接口，必须要使用cglib库，spring会自动在jdk动态代理和cglib直接转换；
	 * 如何强制使用cglib实现aop；
	 * 1、添加cglib库，spring-home/cglib/*.jar;
	 * 2、在spring配置文件中加入 proxy-target-class的参数;
	 * jdk动态代理和cglib字节码生成的区别;
	 * jdk针对接口实现代理；
	 * cglib针对实现生成代理，生成一个子类覆盖原来方法，因为继承，所以该方法最好不要声明final；另外内部调用可能有问题;
	 * @param config the AOP configuration in the form of an
	 * AdvisedSupport object
	 * @return
	 * @throws AopConfigException
	 */
	@Override
	public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {

		// 代理配置的三个属性:optimize; optimize:设置为true时,强制作用CGLib代理
		// proxyTargetClass == true 为cglib代理;
		// hasNoUserSuppliedProxyInterfaces 是否存在代理接口;

		if (config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config)) {
			Class<?> targetClass = config.getTargetClass();
			if (targetClass == null) {
				throw new AopConfigException("TargetSource cannot determine target class: " +
						"Either an interface or a target is required for proxy creation.");
			}
			if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
				return new JdkDynamicAopProxy(config);
			}
			// cglib包的底层是通过使用一个小儿快的字节码处理框架ASM,来转换字节码并生成新的类;
			// 除了cglib包,脚本语言例如groovy和BeanShell,也是使用ASM来生成Java的字节码;
			// 当然不鼓励使用ASM,因为他要求你必须要对JVM内部结构非常熟悉;
			return new ObjenesisCglibAopProxy(config);
		}
		else {
			// jdk实现了InvocationHandler里面有实现调用方法的invoke方法;核心逻辑会在这个方法里面;
			return new JdkDynamicAopProxy(config);
		}
	}

	/**
	 * Determine whether the supplied {@link AdvisedSupport} has only the
	 * {@link org.springframework.aop.SpringProxy} interface specified
	 * (or no proxy interfaces specified at all).
	 */
	private boolean hasNoUserSuppliedProxyInterfaces(AdvisedSupport config) {
		Class<?>[] ifcs = config.getProxiedInterfaces();
		return (ifcs.length == 0 || (ifcs.length == 1 && SpringProxy.class.isAssignableFrom(ifcs[0])));
	}

}
