package com.kaola.edata.quartz;

import javax.annotation.Resource;

import org.apache.log4j.Logger;



public class LeyuyinQuartz {
	
	Logger	logger	= Logger.getLogger(this.getClass());
	/**
	@Resource
	private StatisticsService statisticsService;
	
	@Resource
	private WriteHtmlService writeHtmlService;
	
	@Resource
	private HtmlActionIdRankService htmlActionIdRankService;
	
	@Resource
	private HtmlImeiDetailService htmlImeiDetailService;
	
	public void leyuyinQuartzJob(){
		logger.info("******************* leyuyin quartz start *******************");
		statisticsService.statistics();
		writeHtmlService.writeMainHtml();
		htmlActionIdRankService.writeActionIdRankHtml();
		htmlImeiDetailService.writeImeiDetailHtml();
		logger.info("******************* leyuyin quartz end *******************");
	}
*/
}
