package com.jobpoint.model;

public class Product {
	
	private int id;
	private String name;
	private String symbol;
	private String symbol_description;
	private String chart_url;
	private boolean isMarketOpen;
	private boolean isMonday;
	private boolean isTuesday;
	private boolean isWednesday;
	private boolean isThursday;
	private boolean isFriday;
	private boolean isSaturday;
	private boolean isSunday;
	private String open_time;
	private String close_time;
	private boolean isTradingPause;
	private String pause_day;
	private String pause_time_from;
	private String pause_time_to;
	private String strategy;
	private String pta_url;
	private String order_type;
	private int lot;
	
	
	public Product() {
		
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbolDescription(String symbol_description) {
		this.symbol_description = symbol_description;
	}
	
	public String getSymbolDescription() {
		return symbol_description;
	}
	
	public void setChartUrl(String chart_url) {
		this.chart_url = chart_url;
	}
	
	public String getChartUrl() {
		return chart_url;
	}
	
	public void setIsMarketOpen(boolean isMarketOpen) {
		this.isMarketOpen = isMarketOpen;
	}
	
	public boolean getIsMarketOpen() {
		return isMarketOpen;
	}
	
	public void setIsMonday(boolean isMonday) {
		this.isMonday = isMonday;
	}
	
	public boolean getIsMonday() {
		return isMonday;
	}
	
	public void setIsTuesday(boolean isTuesday) {
		this.isTuesday = isTuesday;
	}
	
	public boolean getIsTuesday() {
		return isTuesday;
	}
	
	public void setIsWednesday(boolean isWednesday) {
		this.isWednesday = isWednesday;
	}
	
	public boolean getIsWednesday() {
		return isWednesday;
	}
	
	public void setIsThursday(boolean isThursday) {
		this.isThursday = isThursday;
	}
	
	public boolean getIsThursday() {
		return isThursday;
	}
	
	public void setIsFriday(boolean isFriday) {
		this.isFriday = isFriday;
	}
	
	public boolean getIsFriday() {
		return isFriday;
	}
	
	public void setIsSaturday(boolean isSaturday) {
		this.isSaturday = isSaturday;
	}
	
	public boolean getIsSaturday() {
		return isSaturday;
	}
	
	public void setIsSunday(boolean isSunday) {
		this.isSunday = isSunday;
	}
	
	public boolean getIsSunday() {
		return isSunday;
	}
	
	public void setOpenTime(String open_time) {
		this.open_time = open_time;
	}
	
	public String getOpenTime() {
		return open_time;
	}
	
	public void setCloseTime(String close_time) {
		this.close_time = close_time;
	}
	
	public String getCloseTime() {
		return close_time;
	}
	
	public void setIsTradingPause(boolean isTradingPause) {
		this.isTradingPause = isTradingPause;
	}
	
	public boolean getIsTradingPause() {
		return isTradingPause;
	}
	
	public void setPauseDay(String pause_day) {
		this.pause_day = pause_day;
	}
	
	public String getPauseDay() {
		return pause_day;
	}
	
	public void setPauseTimeFrom(String pause_time_from) {
		this.pause_time_from = pause_time_from;
	}
	
	public String getPauseTimeFrom() {
		return pause_time_from;
	}
	
	public void setPauseTimeTo(String pause_time_to) {
		this.pause_time_to = pause_time_to;
	}
	
	public String getPauseTimeTo() {
		return pause_time_to;
	}
	
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	
	public String getStrategy() {
		return strategy;
	}
	
	public void setPtaUrl(String pta_url) {
		this.pta_url = pta_url;
	}
	
	public String getPtaUrl() {
		return pta_url;
	}
	
	public void setOrderType(String order_type) {
		this.order_type = order_type;
	}
	
	public String getOrderType() {
		return order_type;
	}
	
	public void setLot(int lot) {
		this.lot = lot;
	}
	
	public int getLot() {
		return lot;
	}



}
