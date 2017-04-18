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

public class Big_String_scorelog {
	private static BufferedReader fileReader = null;
	private static BufferedWriter fileWriter = null;
	public static void main(String[] args) throws Exception {

//		BigDecimal bd =  new BigDecimal("2.01509E+23");
//		System.out.println(bd.toBigInteger());
		
		String line;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd",Locale.US);
		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
			
		try {
			fileReader = new BufferedReader(new FileReader("E:/data/征信报表/cis/scorelog.csv"));
			fileWriter = new BufferedWriter(new FileWriter("E:/data/征信报表/cis/scorelog2.csv"));
			int i =1;
			while((line = fileReader.readLine()) !=null){
				String[] strs = line.split(",");
				if(strs.length==5){
					Date d =sdf.parse(strs[4]);
					strs[4] = sdfd.format(d);
					line = StringUtil.ArrToStr(strs, ",");
					System.out.println(i+"------"+line);
					fileWriter.write(line.trim() + "\n");
					fileWriter.flush();
				}
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
