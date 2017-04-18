package com.kaola.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestCAL{

	public static void main(String[] args) {
		String data_ = "";
		SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -10);
		data_ = myFmt1.format(cal.getTime());
		System.out.println(data_);
	}

}
