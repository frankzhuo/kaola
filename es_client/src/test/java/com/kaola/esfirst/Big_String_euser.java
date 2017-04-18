package com.kaola.esfirst;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.kao.util.StringUtil;

public class Big_String_euser {
	private static BufferedReader fileReader = null;
	private static BufferedWriter fileWriter = null;
	public static void main(String[] args) throws Exception {

//		BigDecimal bd =  new BigDecimal("2.01509E+23");
//		System.out.println(bd.toBigInteger());
		
		String line;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy",Locale.US);
		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
			
		try {
			fileReader = new BufferedReader(new FileReader("E:/data/征信报表/cis/euser.csv"));
			fileWriter = new BufferedWriter(new FileWriter("E:/data/征信报表/cis/euser2.csv"));
			int i =1;
			while((line = fileReader.readLine()) !=null && line.trim().length()>0){
				String[] strs = line.split(",");
				Date d =sdf.parse(strs[3]);
				strs[3] = sdfd.format(d);
				line = StringUtil.ArrToStr(strs, ",");
				System.out.println(i+"------"+line);
				fileWriter.write(line.trim() + "\n");
				fileWriter.flush();
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
