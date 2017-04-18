package com.kaola.edata.vo;

/**
 * 用户命令排行bean模型
 * @author sunjq
 *
 */
public class ActionIdRank {
	
	private String rank;
	
	private String actionId;
	
	private int imeiCount;
	
	private int actionIdCount;
	
	private String actionName;

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public int getImeiCount() {
		return imeiCount;
	}

	public void setImeiCount(int imeiCount) {
		this.imeiCount = imeiCount;
	}

	public int getActionIdCount() {
		return actionIdCount;
	}

	public void setActionIdCount(int actionIdCount) {
		this.actionIdCount = actionIdCount;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	

}
