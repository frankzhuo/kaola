package com.kao.etl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import com.kao.util.StringUtil;

public class opencourt_change {
	public static void main(String[] args) {
		try {
			InputStreamReader read = null;

            File[] files = new File("D:/work/crawler/opencourt/").listFiles();
            new File("D:/work/crawler/opencourt/done/opencourtbeijing_done.txt").delete();
            
            int num = 0;
			for (File file : files) {
				
				if(file.isDirectory()){
					continue;
				}
				read = new InputStreamReader(new FileInputStream(file));

				BufferedReader bufferread = new BufferedReader(read);
				BufferedWriter bufferwrite = new BufferedWriter(new FileWriter(new File("D:/work/crawler/opencourt/done/opencourtbeijing_done.txt"), true));
				String line = "";
				int i = 0;
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
						//results[1] = "1900-01-01";
						results[5] = strs[1];
						results[3] = strs[2];
						results[4] = strs[3];
						line = StringUtil.ArrToStr(results, "\001");
						bufferwrite.write(line.trim() + "\n");
					}
				}

				bufferwrite.flush();
				bufferread.close();
				bufferread.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	}
}
