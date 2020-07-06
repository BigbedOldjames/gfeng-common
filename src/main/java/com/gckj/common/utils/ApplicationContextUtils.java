package com.gckj.common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/*** 
 * @Description：以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候取出ApplicaitonContext.
 * @author：ldc
 * date：2020-06-23
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware, DisposableBean {

	private static ApplicationContext applicationContext = null;

	private static Logger logger = LoggerFactory.getLogger(ApplicationContextUtils.class);

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 清除SpringContextHolder中的ApplicationContext为Null.
	 */
	public static void clearApplicationContext() {
		if (logger.isDebugEnabled()){
			logger.debug("清除ApplicationContext:" + applicationContext);
		}
		applicationContext = null;
	}
	
	/**
	 * 实现ApplicationContextAware接口, 注入ApplicationContext到静态变量中.
	 * 原理：spring的上下文初始化配置文件或通过注解定义的bean，当spring发现实例化的bean实现了ApplicationContextAware接口时，
	 * 就会在初始化完成后调用setApplicationContext方法，将spring 的上下文实例传入
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		System.out.println("spring上下文注入成功！");
		ApplicationContextUtils.applicationContext = applicationContext;
	}

	/**
	 * 实现DisposableBean接口, 在Context关闭时清理静态变量.
	 */
	public void destroy() throws Exception {
		ApplicationContextUtils.clearApplicationContext();
	}
	
}