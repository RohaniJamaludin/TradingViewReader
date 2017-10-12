package com.jobpoint.tools;

public class Parser {
	
	public static String parseString(String s) {
		return s;
	}
	
	public static String parseProfit(String enterPrice, String exitPrice, boolean situation) {
		int enterPriceInt = 0;
		int timesTenEnter = 0;
		
		int exitPriceInt = 0;
		int timesTenExit = 0;
		
		boolean isDecimal = false;
		boolean hasQuote = false;
		
		int profitInt = 0;
		
		if(enterPrice.contains(".")) {
			int index = enterPrice.indexOf(".");
			int length = enterPrice.length();
			timesTenEnter = length - index;
			enterPrice = enterPrice.replace(".", "");
		}
		
		if(enterPrice.contains("'")) {
			int index = enterPrice.indexOf(".");
			int length = enterPrice.length();
			timesTenEnter = length - index;
			enterPrice = enterPrice.replace("'", "");
		}
		
		enterPriceInt = Integer.parseInt(enterPrice);
		
		if(exitPrice.contains(".")) {
			int index = exitPrice.indexOf(".");
			int length = exitPrice.length();
			timesTenExit = length - index;
			exitPrice = exitPrice.replace(".", "");
		}
		
		if(exitPrice.contains("'")) {
			int index = exitPrice.indexOf(".");
			int length = exitPrice.length();
			timesTenExit = length - index;
			exitPrice = exitPrice.replace("'", "");
		}
		
		exitPriceInt = Integer.parseInt(exitPrice);
		
		if(situation) {
			profitInt = exitPriceInt - enterPriceInt;
		}else {
			profitInt = enterPriceInt - exitPriceInt;
		}
		
		
		String profit = String.valueOf(profitInt);
		
		if(isDecimal)
			profit = new StringBuilder(profit).insert(profit.length()-timesTenEnter, ".").toString();
		
		if(hasQuote)
			profit = new StringBuilder(profit).insert(profit.length()-timesTenEnter, "'").toString();
			
		return profit;
	}
	
	public static String parseTotalProfit(String enterPrice, String exitPrice, boolean situation) {
		String totalProfit = "";
		return totalProfit;
	}
	

}
