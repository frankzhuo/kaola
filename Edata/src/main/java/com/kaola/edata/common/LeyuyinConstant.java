package com.kaola.edata.common;

public class LeyuyinConstant {

	//ͳ统计每天
	public final static int		EVERY_DAY				= 0;

	//ͳ周统计
	public final static int		WEEK					= 1;

	//ͳ月统计
	public final static int		MONTH					= 2;
	
	//最近自然月30/31天内，使用乐语音至少5天的用户
	public final static int		ACTIVE_USER_MONTH_5		= 4;
	
	//最近自然月30/31天内，使用乐语音至少1天的用户
	public final static int		ACTIVE_USER_MONTH_1		= 5;

	//ͳ统计上线后
	public final static int		AFTER_ON_LINE			= 3;

	//每天生成HTML文件主路径
	public final static String	HTML_FILE_PATH			= "html";

	//主统计HTML页面文件后缀名
	public final static String	MAIN_HTML_NAME			= ".html";


	//用户命令使用排行页面后缀名
	public final static String	ACTION_ID_RANK_HTML_NAME	= "rank";
	//用户命令使用排行页面后缀名(周)
	public final static String	ACTION_ID_RANK_WEEK_HTML_NAME	= "rankWeek";
	//用户命令使用排行页面后缀名(月)
	public final static String	ACTION_ID_RANK_MONTH_HTML_NAME	= "rankMonth";
	
	
	//终端用户数据页面后缀名
	public final static String	IMEI_DETAIL_HTML_NAME		= "imei";
	//终端用户数据页面后缀名(周)
	public final static String	IMEI_DETAIL_WEEK_HTML_NAME	= "imeiWeek";
	//终端用户数据页面后缀名(月)
	public final static String	IMEI_DETAIL_MONTH_HTML_NAME	= "imeiMonth";


	//新终端
	public final static int		NEW_PHONE				= 1;

	//旧终端
	public final static int		OLD_PHONE				= 2;




	//用于记录WEB服务真实路径
	public static String		SYSTEM_PATH				= "";

	//用于记录WEB服务名
	public static String		CONTEXT_PATH			= "";

	//统计类型，包含测试人员IMEI号
	public final static int		STATISTICS_INCLUDE_TEST	= 1;

	//统计类型，不包含测试人员IMEI号
	public final static int		STATISTICS_NO_TEST		= 2;

	public final static String	TEST_IMEI				= "'863363010006129','863363010006177','351602001084423','351602001084423','863363010006078','863363010005021','863363010005476','864737010007575','864737010007591','867743000008711',"
															+ "'863363010006129lv','863363010006177lv','351602001084423lv','351602001084423lv','863363010006078lv','863363010005021lv','863363010005476lv','864737010007575lv','864737010007591lv','867743000008711lv'";

	public final static String	ONLINE_DATE = "2012-03-31";
	
	public final static int LEYUYIN_MODEL_NUM = 10;
	
	public final static int DATA_DELETE = 1;
	/** 首页页面名称 */
	public final static String	MAIN_SUMMARY_MONTH_HTML_NAME	= "mainSummary";
	
	
	/** 分机型top 20的功能表和对应总使用次数 */ 
	public final static String	ACTION_ID_RANK_HTML_NAME_MODEL	= "RankModel";
	/** 分机型top 20的功能表和对应总使用次数(周) */
	public final static String	ACTION_ID_RANK_WEEK_HTML_NAME_MODEL	= "RankModelWeek";
	
	/** 分版本top 20的功能表和对应总使用次数 */
	public final static String	ACTION_ID_RANK_HTML_NAME_VERSION	= "RankVersion";
	/** 分版本top 20的功能表和对应总使用次数(周) */
	public final static String	ACTION_ID_RANK_WEEK_HTML_NAME_VERSION	= "RankVersionWeek";
	
}
