package com.kaola.edata.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class LvFilter implements Filter{
	
	private final static Logger logger = Logger.getLogger(LvFilter.class);
	
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String url = ((HttpServletRequest) request).getRequestURI();
		String contextPath = ((HttpServletRequest) request).getContextPath();
		
		logger.info("url:"+url);
		String loginUser = String.valueOf(request.getSession().getAttribute("loginuser"));
		if(url.contains("login.do")||url.contains("login.jsp")){
			chain.doFilter(request, response);
			return;
		}
		else{
			if (null==loginUser||"null".equals(loginUser)) {
				response.sendRedirect(contextPath + "/login.jsp");
			} else {
				chain.doFilter(request, response);
				return;
			}
			
		}

		

		
	}
	
	
	

	public void destroy() {

		
	}

	public void init(FilterConfig arg0) throws ServletException {

		
	}

}
