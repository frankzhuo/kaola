package com.kaola.edata.etl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import com.kaola.edata.util.StringUtil;

public class Procure_illegal_act {
	public static void main(String[] args) {
         try {
			InputStreamReader read = new  InputStreamReader(new FileInputStream(new File("C:/Users/kaola/Desktop/mail/procure_illegal_act_20161025.txt")));
			BufferedReader bufferread = new BufferedReader(read);
			BufferedWriter bufferwrite = new BufferedWriter(new FileWriter(new File("C:/Users/kaola/Desktop/mail/procure_illegal_act_20161025.done.txt")));
			String line = "";
			int i = 0;
			int num=0;
			int xuhao=248;
			while ((line = bufferread.readLine()) != null) {
				
				if (line.split("\t").length ==9) {
					System.out.println("总行数：" + num + " 满足条件行数：" + (++i) + " colNum :" + line.split("\001").length);
					System.out.println(line);
					String[] strs = line.split("\t");
					strs[0] =xuhao+"";
					
					line = StringUtil.ArrToStr(strs, "\001");
					bufferwrite.write(line.trim() + "\n");
				}
				
				num ++;
				xuhao ++;
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
