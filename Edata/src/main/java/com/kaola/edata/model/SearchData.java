package com.kaola.edata.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5824317698702903729L;
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	private int total=0;
	
	private int pageNum=0;
	
	private List    list= new ArrayList();


	
	

}
