package com.kao.etl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import com.kao.util.StringUtil;

public class pj_company {
	public static void main(String[] args) {
         try {
			InputStreamReader read = new  InputStreamReader(new FileInputStream(new File("D:/work/crawler/pporg/pj_company.txt")));
			BufferedReader bufferread = new BufferedReader(read);
			BufferedWriter bufferwrite = new BufferedWriter(new FileWriter(new File("D:/work/crawler/pporg/pj_companynew.txt")));
			String line = "";
			int i = 0;
			int num=0;
			while ((line = bufferread.readLine()) != null) {
				++num;
				if (line.split("\t").length ==6) {
					System.out.println("总行数：" + num + " 满足条件行数：" + (++i) + " colNum :" + line.split("\001").length);
					System.out.println(line);
					String[] strs = line.split("\t");
					String[] results = new String[] { " ", " ", " ", " ", " ", "-"};
					// notice_type,party,notice_user_name,layout,publish_time,upload_time,url,content

					results[0] =strs[0];
					results[1] = strs[1];
					results[2] = strs[2];
					results[3] = strs[3];
					results[4] = strs[4];
					
					if(!"".equals(strs[5])){
						results[5] = strs[5];
					}
					
					line = StringUtil.ArrToStr(strs, "\001");
					bufferwrite.write(line.trim() + "\n");
				}
			}

			bufferwrite.flush();
			bufferwrite.close();
			bufferread.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
		}
	}
}
