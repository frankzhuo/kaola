package com.lenovo.lv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;



@ContextConfiguration
(locations={"classpath:spring/root-context.xml","classpath:spring/appServlet/servlet-context.xml"})


public class ModelTest extends AbstractTestNGSpringContextTests {

//	@Autowired
//	LeyuyinStatisticsDao	dao;
//
//
//	@Test
//	public void getPcCommandCount() {
//		dao.getPcCommandCount("2012-09-01", "2012-12-01", "1700");
//	
//	}
//

}