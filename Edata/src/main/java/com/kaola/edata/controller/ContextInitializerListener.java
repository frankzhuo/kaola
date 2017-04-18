package com.kaola.edata.controller;

import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.velocity.app.Velocity;

import com.kaola.edata.common.LeyuyinConstant;
import com.kaola.edata.common.PropertyReader;

/**
 * 初始化配置
 * @author chengqj
 *
 */
public class ContextInitializerListener implements ServletContextListener {
	
	protected Logger logger = Logger.getLogger(ContextInitializerListener.class);

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {

	}

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		final ServletContext config = sce.getServletContext();
		System.out.println("*************** init conf ***************");
		PropertyReader.setResourceBundle("conf");
		String realPath = config.getRealPath("/");
		LeyuyinConstant.SYSTEM_PATH = realPath;
		logger.info("realPath:"+realPath);
		
		String contextPath = config.getContextPath();
		LeyuyinConstant.CONTEXT_PATH = contextPath;
		logger.info("contextPath:"+contextPath);

		System.out.println("============== init contextInitialized ==============");

		// 初始化Velocity
		Properties properties = new Properties();
		// 指定日志文件存放位置
		ServletContext context = sce.getServletContext();
		if (null == context) {
			System.out.println("context is null");
		}
		properties.put("runtime.log",
			sce.getServletContext().getRealPath("/WEB-INF/log/velocity.log"));
		// 指定模板文件加载位置
		properties.put("file.resource.loader.path",
			sce.getServletContext().getRealPath("/WEB-INF/velocity"));
		// 指定输入编码格式
		properties.put("input.encoding", "UTF-8");
		// 指定velocity的servlet向浏览器输出内容的编码
		properties.put("default.contentType", "text/html;charset\\=UTF-8");
		// 指定输出编码格式
		properties.put("output.encoding", "UTF-8");
		// 迭代索引名
		properties.put("directive.foreach.counter.name", "index");
		// 迭代开始的下标
		properties.put("directive.foreach.counter.initial.value", "0");
		try {
			Velocity.init(properties);
		} catch (Exception e) {
			System.out.println("velocity init failed");
			e.printStackTrace();
		}
	}

}
