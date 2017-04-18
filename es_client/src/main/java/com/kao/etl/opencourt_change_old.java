package com.kao.etl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import com.kao.util.StringUtil;

public class opencourt_change_old {
	public static void main(String[] args) {
         try {
			InputStreamReader read = new  InputStreamReader(new FileInputStream(new File("D:/work/crawler/opencourt/clean_openCourt_1.txt.COMPLETED")));
			BufferedReader bufferread = new BufferedReader(read);
			BufferedWriter bufferwrite = new BufferedWriter(new FileWriter(new File("D:/work/crawler/opencourt/done/clean_openCourt_1.txt")));
			String line = "";
			int i = 0;
			int num=0;
			while ((line = bufferread.readLine()) != null) {
				++num;
				if (line.split("\001").length ==4) {
					System.out.println("总行数：" + num + " 满足条件行数：" + (++i) + " colNum :" + line.split("\001").length);
					System.out.println(line);
					String[] strs = line.split("\001");
					String[] results = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ","-" };
					// name,publish_time,url,content,open_time,court,fating,jnum,judge,yuangao,beigao,dangshiren,jcase
					String str0= strs[0];
					if("".equals(str0.trim())){
						str0="-";
					}
					results[0] =str0;
					results[1] = strs[1];
					results[2] = strs[2];
					
					String str30= strs[3].trim().replaceAll("　","").replaceAll("	","");
					results[3] = str30;							
					String[] str3=str30.split("特此公告。");
					if(str3.length>1){
						results[4] = str3[1].trim();
					}
					line = StringUtil.ArrToStr(results, "\001");
					bufferwrite.write(line.trim() + "\n");
				}
			}

			bufferwrite.flush();
			bufferread.close();
			bufferread.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
		}
	}
}
