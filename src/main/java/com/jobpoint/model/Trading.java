package com.jobpoint.model;

public class Trading {
	
	private int id;
	private int product_id;
	private String enterPrice;
	private String exitPrice;
	private String enterDate;
	private String enterTime;
	private String exitDate;
	private String exitTime;
	private String profit;  
	private boolean situation;  //when enter buy then  exit sell value is true and when enter sell then exit buy value is false 
	private String firstBarEnter;
	private String secondBarEnter;
	private String thirdBarEnter;
	private String firstBarExit;
	private String secondBarExit;
	private String thirdBarExit;
	
	public Trading() {
		exitPrice = "n/a";
		exitDate = "n/a";
		exitTime = "n/a";
		profit = "n/a";
		firstBarExit = "n/a";
		secondBarExit = "n/a";
		thirdBarExit = "n/a";
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getProductId() {
		return product_id;
	}
	
	public void setProductId(int product_id) {
		this.product_id = product_id;
	}
	
	public String getEnterPrice() {
		return enterPrice;
	}
	
	public void setEnterPrice(String enterPrice) {
		this.enterPrice = enterPrice;
	}
	
	public String getExitPrice() {
		return exitPrice;
	}
	
	public void setExitPrice(String exitPrice) {
		this.exitPrice = exitPrice;
	}
	
	public String getEnterDate() {
		return enterDate;
	}
	
	public void setEnterDate(String enterDate) {
		this.enterDate = enterDate;
	}
	
	public String getEnterTime() {
		return enterTime;
	}
	
	public void setEnterTime(String enterTime) {
		this.enterTime = enterTime;
	}
	
	public String getExitDate() {
		return exitDate;
	}
	
	public void setExitDate(String exitDate) {
		this.exitDate = exitDate;
	}
	
	public String getExitTime() {
		return exitTime;
	}
	
	public void setExitTime(String exitTime) {
		this.exitTime = exitTime;
	}
	
	public String getProfit() {
		return profit;
	}
	
	public void setProfit(String profit) {
		this.profit = profit;
	}
	
	public boolean getSituation() {
		return situation;
	}
	
	public void setSituation(boolean situation) {
		this.situation = situation;
	}
	
	public String getFirstBarEnter() {
		return firstBarEnter;
	}
	
	public void setFirstBarEnter(String firstBarEnter) {
		this.firstBarEnter = firstBarEnter;
	}
	
	public String getSecondBarEnter() {
		return secondBarEnter;
	}
	
	public void setSecondBarEnter(String secondBarEnter) {
		this.secondBarEnter = secondBarEnter;
	}
	
	public String getThirdBarEnter() {
		return thirdBarEnter;
	}
	
	public void setThirdBarEnter(String thirdBarEnter) {
		this.thirdBarEnter = thirdBarEnter;
	}
	
	public String getFirstBarExit() {
		return firstBarExit;
	}
	
	public void setFirstBarExit(String firstBarExit) {
		this.firstBarExit = firstBarExit;
	}
	
	public String getSecondBarExit() {
		return secondBarExit;
	}
	
	public void setSecondBarExit(String secondBarExit) {
		this.secondBarExit = secondBarExit;
	}
	
	public String getThirdBarExit() {
		return thirdBarExit;
	}
	
	public void setThirdBarExit(String thirdBarExit) {
		this.thirdBarExit = thirdBarExit;
	}
	

}
