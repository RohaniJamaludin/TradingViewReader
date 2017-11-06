package com.jobpoint.model;

public class Bar {
	
	private int id;
	private int productId;
	private String value;
	private String date;
	private String time;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public int getProductId() {
		return productId;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getTime() {
		return time;
	}

}
