package com.kao.etl;

public class Test {
	public static void main(String[] args) {
		String str = "国作登字-2015-A-00229411^A2015-09-30^A美容针（Botox）^A文字^A黄美娜(HWANG MINA)^A2007-04-16^A-";
		System.out.println(str.split("\001")[0]);
	}

}
