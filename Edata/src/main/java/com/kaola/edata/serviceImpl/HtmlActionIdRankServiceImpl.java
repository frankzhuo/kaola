package com.kaola.edata.serviceImpl;

import java.io.File;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kaola.edata.common.Common;
import com.kaola.edata.common.LeyuyinConstant;

import com.kaola.edata.service.HtmlActionIdRankService;
import com.kaola.edata.vo.ActionIdRank;


@Service
public class HtmlActionIdRankServiceImpl implements HtmlActionIdRankService{
	
	private Logger logger = LoggerFactory.getLogger(HtmlActionIdRankServiceImpl.class);
	


	@Override
	public void writeActionIdRankHtml() {
		logger.info("-------------- start write ActionIdRank html --------------");
		writeHtml(LeyuyinConstant.EVERY_DAY);
		writeHtml(LeyuyinConstant.WEEK);
		writeHtml(LeyuyinConstant.MONTH);
		logger.info("-------------- end write html --------------");
	}
	
	private void writeHtml(int type) {
		
		List<ActionIdRank> list =  null;//behaviorStatisticsDao.getActionIdRankList(type);
		
		String fileName = LeyuyinConstant.ACTION_ID_RANK_HTML_NAME;//后缀名
		if(type==LeyuyinConstant.EVERY_DAY){
			fileName = LeyuyinConstant.ACTION_ID_RANK_HTML_NAME;
		}
		if(type==LeyuyinConstant.WEEK){
			fileName = LeyuyinConstant.ACTION_ID_RANK_WEEK_HTML_NAME;
		}
		if(type==LeyuyinConstant.MONTH){
			fileName = LeyuyinConstant.ACTION_ID_RANK_MONTH_HTML_NAME;
		}
				
		//创建文件夹
		createDir();
		
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = Common.getStatisticsDate(-1);

		try {
			c.setTime(sf.parse(dateStr));
		 
			String path = LeyuyinConstant.SYSTEM_PATH + File.separator 
				+ LeyuyinConstant.HTML_FILE_PATH + File.separator
				+ (c.get(Calendar.YEAR)) + File.separator + (c.get(Calendar.MONTH) + 1)
				+ File.separator + c.get(Calendar.DATE)+ fileName+".html";
			logger.info("actionIdRank path:"+path);
			
			logger.info("--------- use velocity ---------");
			//写静态HTML文件
			Template template = Velocity.getTemplate("actionIdRank.vm", "utf-8");//\\WEB-INF\\velocity\\hello.vm
			VelocityContext context = new VelocityContext();
			context.put("title", "用户命令排行");
			context.put("listActionIdTop", list);
			PrintWriter pw = new PrintWriter(path, "utf-8");
			template.merge(context, pw);
			pw.close();
			
			
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	private void createDir() {
		logger.info("=========start createDir html=========");
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = Common.getStatisticsDate(-1);
		try {
			c.setTime(sf.parse(dateStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String mainPath = LeyuyinConstant.SYSTEM_PATH;
		String dirPath = mainPath + File.separator + LeyuyinConstant.HTML_FILE_PATH
			+ File.separator + (c.get(Calendar.YEAR));

		String detailDirPath = dirPath + File.separator + (c.get(Calendar.MONTH) + 1);
		logger.info("" + dirPath);
		File detailFileDir = new File(detailDirPath);
		if (!detailFileDir.isDirectory()) {
			detailFileDir.mkdirs();
		}

		logger.info("=========end createDir html=========");

	}


}
