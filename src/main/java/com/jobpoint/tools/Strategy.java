package com.jobpoint.tools;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.jobpoint.controller.StateController;
import com.jobpoint.controller.TradingController;
import com.jobpoint.gui.TradingTableModel;
import com.jobpoint.model.Chart;
import com.jobpoint.model.Product;
import com.jobpoint.model.State;
import com.jobpoint.model.Trading;

public class Strategy {
	
	public Strategy() {
		
	}
	
	public void strategyArrow(Product product, String price, List<Chart> chartList, TradingTableModel model) {
		State state = new State();
		StateController stateController = new StateController();
		
		Trading trading = new Trading();
		TradingController tradingController = new TradingController();
		
		PTA pta = new PTA();
		
		Chart barOne = chartList.get(0);
		
		state = stateController.getLastRowState(product);
		trading = tradingController.getTrading(state.getTradingId());
		
		if(isTradingAllow(product) && !isTradingPause(product)) {
			
			if(state.getIsEnter() && state.getType().equals("buy")) {
				if(barOne.getArrowRed() == 1.000) {
					//exit market and sell
					state.setIsEnter(false);
					state.setType("");
					state.setEnterPrice("");
					
					trading.setExitPrice(price);
					tradingController.updateTrading(trading, state, model);
					try {
						pta.sendGet(product.getPtaUrl(), product.getSymbol(), price,  product.getLot(), product.getOrderType(), "1");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			if(state.getIsEnter() && state.getType().equals("sell")) {
				if(barOne.getArrowGreen() == 1.000) {
					//exit market and buy
					state.setIsEnter(false);
					state.setType("");
					state.setEnterPrice("");
					
					trading.setExitPrice(price);
					tradingController.updateTrading(trading, state, model);
					try {
						pta.sendGet(product.getPtaUrl(), product.getSymbol(), price, product.getLot(), product.getOrderType(),  "0");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			if(!state.getIsEnter() && state.getType().equals("")) {
				if(barOne.getArrowRed() == 1.000) {
					//enter market and sell
					state.setIsEnter(true);
					state.setType("sell");
					state.setEnterPrice(price);
					
					trading = new Trading();
					trading.setEnterPrice(price);
					trading.setSituation(false);
					
					tradingController.saveTrading(trading, state, model);
					try {
						pta.sendGet(product.getPtaUrl(), product.getSymbol(), price, product.getLot(), product.getOrderType(),  "1");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			if(!state.getIsEnter() && state.getType().equals("")) {
				if(barOne.getArrowGreen() == 1.000) {
					
					//enter market and buy
					state.setIsEnter(true);
					state.setType("buy");
					state.setEnterPrice(price);
					
					trading = new Trading();
					trading.setEnterPrice(price);
					trading.setSituation(true);
					
					tradingController.saveTrading(trading, state, model);
					try {
						pta.sendGet(product.getPtaUrl(), product.getSymbol(), price, product.getLot(), product.getOrderType(), "0");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			
		}else {
			if(state.getIsEnter()) {
				if(state.getType().equals("buy")) {
					//exit market and sell 
					state.setIsEnter(false);
					state.setType("");
					state.setEnterPrice("");
					
					trading.setExitPrice(price);
					tradingController.updateTrading(trading, state, model);
					
					try {
						pta.sendGet(product.getPtaUrl(), product.getSymbol(), price, product.getLot(), product.getOrderType(),  "1");//1 is sell
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if(state.getType().equals("sell")) {
					//exit market and buy
					state.setIsEnter(false);
					state.setType("");
					state.setEnterPrice("");
					
					trading.setExitPrice(price);
					tradingController.updateTrading(trading, state, model);
					
					try {
						pta.sendGet(product.getPtaUrl(), product.getSymbol(), price, product.getLot(), product.getOrderType(), "0");//0 is buy
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
	public void strategyArrowMomentum(Product product, String price, List<Chart> chartList, TradingTableModel model) {
		Chart barOne = chartList.get(0);
		Chart barTwo = chartList.get(1);
		Chart barThree = chartList.get(2);
		
		State state = new State();
		StateController stateController = new StateController();
		
		PTA pta = new PTA();
		
		Trading trading = new Trading();
		TradingController tradingController = new TradingController();
		
		state = stateController.getLastRowState(product);
		trading = tradingController.getTrading(state.getTradingId());
		
		if(isTradingAllow(product) && !isTradingPause(product)) {
			
			if(state.getIsEnter() && state.getType().equals("buy")) {
				if(barOne.getArrowRed() == 1.000 || barOne.getBar() < barTwo.getBar()) {
					//exit market and sell
					state.setIsEnter(false);
					state.setType("");
					state.setEnterPrice("");
					
					trading.setExitPrice(price);
					tradingController.updateTrading(trading, state, model);
					
					try {
						pta.sendGet(product.getPtaUrl(), product.getSymbol(), price, product.getLot(), product.getOrderType(), "1");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			
			if(state.getIsEnter() && state.getType().equals("sell")) {
				if(barOne.getArrowGreen() == 1.000 || barOne.getBar() > barTwo.getBar()) {
					//exit market and buy
					state.setIsEnter(false);
					state.setType("");
					state.setEnterPrice("");
					
					trading.setExitPrice(price);
					tradingController.updateTrading(trading, state, model);
					
					try {
						pta.sendGet(product.getPtaUrl(), product.getSymbol(), price, product.getLot(), product.getOrderType(), "0");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
			
			if(!state.getIsEnter() && state.getType().equals("")) {
				if(barOne.getArrowGreen() == 1.000) {
					if(barOne.getBar() > barTwo.getBar()) {
						if(barTwo.getBar() > barThree.getBar()) {
							//enter market and buy
							state.setIsEnter(true);
							state.setType("buy");
							state.setEnterPrice(price);
							
							trading = new Trading();
							trading.setEnterPrice(price);
							trading.setSituation(true);
							
							tradingController.saveTrading(trading, state, model);
							
							try {
								pta.sendGet(product.getPtaUrl(), product.getSymbol(), price, product.getLot(), product.getOrderType(), "0");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
				}
				
				if(barOne.getArrowRed() == 1.000) {
					if(barOne.getBar() < barTwo.getBar()) {
						if(barTwo.getBar() < barThree.getBar()) {
							//enter market and sell
							state.setIsEnter(true);
							state.setType("sell");
							state.setEnterPrice(price);
							
							trading = new Trading();
							trading.setEnterPrice(price);
							trading.setSituation(false);
							tradingController.saveTrading(trading, state, model);
							
							try {
								pta.sendGet(product.getPtaUrl(), product.getSymbol(), price, product.getLot(), product.getOrderType(), "1");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
					
				}
					
			}
			
			
		}else {
			if(state.getType().equals("buy")) {
				state.setIsEnter(false);
				state.setType("");
				state.setEnterPrice("");
				
				trading.setExitPrice(price);
				tradingController.updateTrading(trading, state, model);
				
				try {
					pta.sendGet(product.getPtaUrl(), product.getSymbol(), price, product.getLot(), product.getOrderType(), "1");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(state.getType().equals("sell")) {
				state.setIsEnter(false);
				state.setType("");
				state.setEnterPrice("");
				
				trading.setExitPrice(price);
				tradingController.updateTrading(trading, state, model);
				
				try {
					pta.sendGet(product.getPtaUrl(), product.getSymbol(), price, product.getLot(), product.getOrderType(), "0");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private boolean isTradingAllow(Product product) {
		boolean flag = true;
		
		if(product.getIsMarketOpen()) {
		
			//calculateTimeDifference
			
			DayOfWeek dayOfWeek = DayOfWeek.from(LocalDate.now());
			String today = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
			
			long hoursOfOperation = Time.getTimeDifference("HH:mm", product.getOpenTime(), product.getCloseTime()); //in minutes
			
			String currentTime = new SimpleDateFormat("HH:mm").format(new Date());
			long elapseTime = Time.getTimeDifference("HH:mm", product.getOpenTime(), currentTime);
			long passMidNightTime = Time.getTimeDifference("HH:mm", currentTime, "00:00");
			long closeAfterMidNightTime =  Time.getTimeDifference("HH:mm", product.getCloseTime() , "00:00");
			boolean isDifferentDay = Time.isDifferentDay("HH:mm", product.getOpenTime(), product.getCloseTime());
			if(isDifferentDay) {
				if(passMidNightTime>closeAfterMidNightTime) {
					today = dayOfWeek.minus(1).getDisplayName(TextStyle.FULL, Locale.ENGLISH);	
				}
			}
			
			if(product.getIsMonday()) {
				if(today.equals("Monday")){
					if(elapseTime<hoursOfOperation) {
						flag = true;
					}else {
						flag = false;
					}
				}
				
			}else {
				if(today.equals("Monday")) {
					flag = false;
				}
				
			}
			
			if(product.getIsTuesday()) {
				if(today.equals("Tuesday")){
					if(elapseTime<hoursOfOperation) {
						flag = true;
					}else {
						flag = false;
					}
				}
				
			}else {
				if(today.equals("Tuesday")) {
					flag = false;
				}
				
			}
			
			if(product.getIsWednesday()) {
				if(today.equals("Wednesday")){
					if(elapseTime<hoursOfOperation) {
						flag = true;
					}else {
						flag = false;
					}
				}
				
			}else {
				if(today.equals("Wednesday")) {
					flag = false;
				}
				
			}
			
			if(product.getIsThursday()) {
				if(today.equals("Thursday")){
					if(elapseTime<hoursOfOperation) {
						flag = true;
					}else {
						flag = false;
					}
				}
				
			}else {
				if(today.equals("Thursday")) {
					flag = false;
				}
				
			}
			
			if(product.getIsFriday()) {
				if(today.equals("Friday")){
					if(elapseTime<hoursOfOperation) {
						flag = true;
					}else {
						flag = false;
					}
				}
				
			}else {
				if(today.equals("Friday")) {
					flag = false;
				}
				
			}
			
			if(product.getIsSaturday()) {
				if(today.equals("Saturday")){
					if(elapseTime<hoursOfOperation) {
						flag = true;
					}else {
						flag = false;
					}
				}
				
			}else {
				if(today.equals("Saturday")) {
					flag = false;
				}
				
			}
			
			if(product.getIsSunday()) {
				if(today.equals("Sunday")){
					if(elapseTime<hoursOfOperation) {
						flag = true;
					}else {
						flag = false;
					}
				}
				
			}else {
				if(today.equals("Sunday")) {
					flag = false;
				}
				
			}
			
		}
		
		return flag;
	}
	
	private boolean isTradingPause(Product product) {
		boolean flag = false;
		if(product.getIsTradingPause()) {
			DayOfWeek dayOfWeek = DayOfWeek.from(LocalDate.now());
			String today = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
			
			long hoursOfOperation = Time.getTimeDifference("HH:mm", product.getPauseTimeFrom(), product.getPauseTimeTo()); //in minutes
			
			String currentTime = new SimpleDateFormat("HH:mm").format(new Date());
			long elapseTime = Time.getTimeDifference("HH:mm", product.getPauseTimeFrom(), currentTime);
			long passMidNightTime = Time.getTimeDifference("HH:mm", currentTime, "00:00");
			long closeAfterMidNightTime =  Time.getTimeDifference("HH:mm", product.getPauseTimeTo() , "00:00");
			boolean isDifferentDay = Time.isDifferentDay("HH:mm", product.getPauseTimeFrom(), product.getPauseTimeTo());
			if(isDifferentDay) {
				if(passMidNightTime>closeAfterMidNightTime) {
					today = dayOfWeek.minus(1).getDisplayName(TextStyle.FULL, Locale.ENGLISH);	
				}
			}
			
			if(today.equals(product.getPauseDay())) {
				if(elapseTime<hoursOfOperation) {
					flag = true;
				}else {
					flag = false;
				}
				
			}
			return flag;
			
		}else {
			return flag;
		}
		
	}

}
