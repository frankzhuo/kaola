package com.kaola.edata.controller;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kaola.edata.service.CuishouService;
import com.kaola.edata.service.CuishouServiceOld;
import com.kaola.edata.service.HtmlActionIdRankService;
import com.kaola.edata.service.HtmlImeiDetailService;
import com.kaola.edata.service.JudgeSearch;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger		logger	= LoggerFactory.getLogger(HomeController.class);


//	@Resource
//	private HtmlActionIdRankService	htmlActionIdRankService;
//
//	@Resource
//	private HtmlImeiDetailService	htmlImeiDetailService;

	@RequestMapping(value = "/cuishousearch.do")
	public String cuishou(HttpServletRequest request, HttpServletResponse response) {

		String mobile  =request.getParameter("mobile");
		String idno  =request.getParameter("idno");
		String cardno  =request.getParameter("cardno");
		 //JudgeSearch.Search(str);
		System.out.println(mobile);
		
		request.setAttribute("data", CuishouService.Search(mobile,idno,cardno));
		return "/cuishousearch_result.jsp";
	
	}

	@RequestMapping(value = "/search.do")
	public String mainDataList(HttpServletRequest request, HttpServletResponse response) {

		String str  =request.getParameter("str");;

		 //JudgeSearch.Search(str);
		System.out.println(str);
		
		request.setAttribute("data", JudgeSearch.Search(str));
		return "/search_result.jsp";
	
	}

}
