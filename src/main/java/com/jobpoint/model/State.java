package com.jobpoint.model;

public class State {
	
	private int id;
	private int product_id;
	private int trading_id;
	private boolean isEnter;
	private String type;
	private String enter_price;
	
	public State() {
		trading_id = 0;
		isEnter = false;
		type = "";
		enter_price = "";
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
	
	public int getTradingId() {
		return trading_id;
	}
	
	public void setTradingId(int trading_id) {
		this.trading_id = trading_id;
	}
	
	public boolean getIsEnter() {
		return isEnter;
	}
	
	public void setIsEnter(boolean isEnter) {
		this.isEnter = isEnter;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	

	
	public String getEnterPrice() {
		return enter_price;
	}
	
	public void setEnterPrice(String enter_price) {
		this.enter_price = enter_price;
	}
	
}
