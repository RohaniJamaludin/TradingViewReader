package com.jobpoint.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.jobpoint.controller.BarController;
import com.jobpoint.controller.StateController;
import com.jobpoint.controller.TradingController;
import com.jobpoint.gui.TradingTableModel;
import com.jobpoint.model.Bar;
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
		Chart barTwo = chartList.get(1);
		Chart barThree = chartList.get(2);
		
		state = stateController.getLastRowState(product);
		trading = tradingController.getTrading(state.getTradingId());
		
		if(isTradingAllow(product) && !isTradingPause(product)) {
			
			if(isBarCurrent(product)) {
				
				if(state.getIsEnter() && state.getType().equals("buy")) {
					if(barOne.getArrowRed() == 1.000) {
						boolean flag = false;
						List<Trading> tradingListBar = new ArrayList<Trading>();
						tradingListBar = tradingController.getTradingBarList("product", product.getId(), true);
						for(Trading tradingBar : tradingListBar) {
							if(barOne.getBar().equals(tradingBar.getFirstBarExit()) || barTwo.getBar().equals(tradingBar.getSecondBarExit())
									|| barThree.getBar().equals(tradingBar.getThirdBarExit())) {
								flag = true;
								break;
							}
						}
						
						if(!flag) {
							//exit market and sell
							state.setIsEnter(false);
							state.setType("");
							state.setEnterPrice("");
							
							trading.setExitPrice(price);
							trading.setFirstBarExit(barOne.getBar());
							trading.setSecondBarExit(barTwo.getBar());
							trading.setThirdBarExit(barThree.getBar());
							tradingController.updateTrading(trading, state, model);
							try {
								pta.sendGet(product.getPtaUrl(), product.getSymbol(), price,  product.getLot(), product.getOrderType(), "1");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
				}
				
				if(state.getIsEnter() && state.getType().equals("sell")) {
					if(barOne.getArrowGreen() == 1.000) {
						boolean flag = false;
						List<Trading> tradingListBar = new ArrayList<Trading>();
						tradingListBar = tradingController.getTradingBarList("product", product.getId(), false);
						for(Trading tradingBar : tradingListBar) {
							if(barOne.getBar().equals(tradingBar.getFirstBarExit()) || barTwo.getBar().equals(tradingBar.getSecondBarExit())
									|| barThree.getBar().equals(tradingBar.getThirdBarExit())) {
								flag = true;
								break;
							}
						}
						
						if(!flag) {
							//exit market and buy
							state.setIsEnter(false);
							state.setType("");
							state.setEnterPrice("");
							
							trading.setExitPrice(price);
							trading.setFirstBarExit(barOne.getBar());
							trading.setSecondBarExit(barTwo.getBar());
							trading.setThirdBarExit(barThree.getBar());
							tradingController.updateTrading(trading, state, model);
							try {
								pta.sendGet(product.getPtaUrl(), product.getSymbol(), price, product.getLot(), product.getOrderType(),  "0");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
				}
				
				if(!state.getIsEnter() && state.getType().equals("")) {
					if(barOne.getArrowRed() == 1.000) {
						boolean flag = false;
						List<Trading> tradingListBar = new ArrayList<Trading>();
						tradingListBar = tradingController.getTradingBarList("product", product.getId(), false);
						for(Trading tradingBar : tradingListBar) {
							if(barOne.getBar().equals(tradingBar.getFirstBarEnter()) || barTwo.getBar().equals(tradingBar.getSecondBarEnter()) 
									|| barThree.getBar().equals(tradingBar.getThirdBarEnter())) {
								flag = true;
								break;
							}
						}
						
						if(!flag) {
							//enter market and sell
							state.setIsEnter(true);
							state.setType("sell");
							state.setEnterPrice(price);
							
							trading = new Trading();
							trading.setEnterPrice(price);
							trading.setFirstBarEnter(barOne.getBar());
							trading.setSecondBarEnter(barTwo.getBar());
							trading.setThirdBarEnter(barThree.getBar());
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
				}
				
				if(!state.getIsEnter() && state.getType().equals("")) {
					if(barOne.getArrowGreen() == 1.000) {
						boolean flag = false;
						List<Trading> tradingListBar = new ArrayList<Trading>();
						tradingListBar = tradingController.getTradingBarList("product", product.getId(), true);
						for(Trading tradingBar : tradingListBar) {
							if(barOne.getBar().equals(tradingBar.getFirstBarEnter())  || barTwo.getBar().equals(tradingBar.getSecondBarEnter())
									|| barThree.getBar().equals(tradingBar.getThirdBarEnter())) {
								flag = true;
								break;
							}
						}
						
						if(!flag) {
							//enter market and buy
							state.setIsEnter(true);
							state.setType("buy");
							state.setEnterPrice(price);
							
							trading = new Trading();
							trading.setEnterPrice(price);
							trading.setFirstBarEnter(barOne.getBar());
							trading.setSecondBarEnter(barTwo.getBar());
							trading.setThirdBarEnter(barThree.getBar());
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
				
			}
			
		}else {
			if(state.getIsEnter()) {
				if(state.getType().equals("buy")) {
					//exit market and sell 
					state.setIsEnter(false);
					state.setType("");
					state.setEnterPrice("");
					
					trading.setExitPrice(price);
					trading.setFirstBarExit(barOne.getBar());
					trading.setSecondBarExit(barTwo.getBar());
					trading.setThirdBarExit(barThree.getBar());
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
					trading.setFirstBarExit(barOne.getBar());
					trading.setSecondBarExit(barTwo.getBar());
					trading.setThirdBarExit(barThree.getBar());
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
			
			if(isBarCurrent(product)) {
				if(state.getIsEnter() && state.getType().equals("buy")) {
					if(barOne.getArrowRed() == 1.000 || Float.parseFloat(barOne.getBar()) < Float.parseFloat(barTwo.getBar())) {
						boolean flag = false;
						List<Trading> tradingListBar = new ArrayList<Trading>();
						tradingListBar = tradingController.getTradingBarList("product", product.getId(), true);
						for(Trading tradingBar : tradingListBar) {
							if(barOne.getBar().equals(tradingBar.getFirstBarExit()) || barTwo.getBar().equals(tradingBar.getSecondBarExit())
									|| barThree.getBar().equals(tradingBar.getThirdBarExit())) {
								flag = true;
								break;
							}
						}
						
						if(!flag) {
							//exit market and sell
							state.setIsEnter(false);
							state.setType("");
							state.setEnterPrice("");
							
							trading.setExitPrice(price);
							trading.setFirstBarExit(barOne.getBar());
							trading.setSecondBarExit(barTwo.getBar());
							trading.setThirdBarExit(barThree.getBar());
							tradingController.updateTrading(trading, state, model);
							
							try {
								pta.sendGet(product.getPtaUrl(), product.getSymbol(), price, product.getLot(), product.getOrderType(), "1");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
				}
				
				if(state.getIsEnter() && state.getType().equals("sell")) {
					if(barOne.getArrowGreen() == 1.000 || Float.parseFloat(barOne.getBar()) > Float.parseFloat(barTwo.getBar())) {

						boolean flag = false;
						List<Trading> tradingListBar = new ArrayList<Trading>();
						tradingListBar = tradingController.getTradingBarList("product", product.getId(), false);
						for(Trading tradingBar : tradingListBar) {
							if(barOne.getBar().equals(tradingBar.getFirstBarExit()) || barTwo.getBar().equals(tradingBar.getSecondBarExit())
									|| barThree.getBar().equals(tradingBar.getThirdBarExit())) {
								flag = true;
								break;
							}
						}
						
						if(!flag) {
							//exit market and buy
							state.setIsEnter(false);
							state.setType("");
							state.setEnterPrice("");
							
							trading.setExitPrice(price);
							trading.setFirstBarExit(barOne.getBar());
							trading.setSecondBarExit(barTwo.getBar());
							trading.setThirdBarExit(barThree.getBar());
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
				
				if(!state.getIsEnter() && state.getType().equals("")) {
					if(barOne.getArrowGreen() == 1.000) {
						if(Float.parseFloat(barOne.getBar()) > Float.parseFloat(barTwo.getBar())) {
							if(Float.parseFloat(barTwo.getBar()) > Float.parseFloat(barThree.getBar())) {
								boolean flag = false;
								List<Trading> tradingListBar = new ArrayList<Trading>();
								tradingListBar = tradingController.getTradingBarList("product", product.getId(), true);
								for(Trading tradingBar : tradingListBar) {
									if(barOne.getBar().equals( tradingBar.getFirstBarEnter()) || barTwo.getBar().equals(tradingBar.getSecondBarEnter())
											|| barThree.getBar().equals(tradingBar.getThirdBarEnter())) {
										flag = true;
										break;
									}
								}
								
								if(!flag) {
									//enter market and buy
									state.setIsEnter(true);
									state.setType("buy");
									state.setEnterPrice(price);
									
									trading = new Trading();
									trading.setEnterPrice(price);
									trading.setSituation(true);
									trading.setFirstBarEnter(barOne.getBar());
									trading.setSecondBarEnter(barTwo.getBar());
									trading.setThirdBarEnter(barThree.getBar());
									
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
						
					}
					
					if(barOne.getArrowRed() == 1.000) {
						if(Float.parseFloat(barOne.getBar()) < Float.parseFloat(barTwo.getBar())) {
							if(Float.parseFloat(barTwo.getBar()) < Float.parseFloat(barThree.getBar())) {
								boolean flag = false;
								List<Trading> tradingListBar = new ArrayList<Trading>();
								tradingListBar = tradingController.getTradingBarList("product", product.getId(), false);
								for(Trading tradingBar : tradingListBar) {
									if(barOne.getBar().equals(tradingBar.getFirstBarEnter())  || barTwo.getBar().equals(tradingBar.getSecondBarEnter())
											|| barThree.getBar().equals(tradingBar.getThirdBarEnter())) {
										flag = true;
										break;
									}
								}
								
								if(!flag) {
									//enter market and sell
									state.setIsEnter(true);
									state.setType("sell");
									state.setEnterPrice(price);
									
									trading = new Trading();
									trading.setEnterPrice(price);
									trading.setSituation(false);
									trading.setFirstBarEnter(barOne.getBar());
									trading.setSecondBarEnter(barTwo.getBar());
									trading.setThirdBarEnter(barThree.getBar());
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
						
				}
			}
			
		}else {
			if(state.getType().equals("buy")) {
				state.setIsEnter(false);
				state.setType("");
				state.setEnterPrice("");
				
				trading.setExitPrice(price);
				trading.setFirstBarExit(barOne.getBar());
				trading.setSecondBarExit(barTwo.getBar());
				trading.setThirdBarExit(barThree.getBar());
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
				trading.setFirstBarExit(barOne.getBar());
				trading.setSecondBarExit(barTwo.getBar());
				trading.setThirdBarExit(barThree.getBar());
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
				if(today.equals("Monday")){
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
				if(today.equals("Tuesday")){
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
				if(today.equals("Wednesday")){
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
				if(today.equals("Thursday")){
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
				if(today.equals("Friday")){
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
				if(today.equals("Saturday")){
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
				if(today.equals("Sunday")){
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
		}
		return flag;
		
	}
	
	private boolean isBarCurrent(Product product) {
		
		boolean flag = false;
		
		Bar bar = new Bar();
		BarController barController = new BarController();
		
		SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
		bar = barController.getCurrentBar(product.getId());
		
		if(!bar.getValue().equals("")) {
			String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
			String currentTime = new SimpleDateFormat("HH:mm").format(new Date());
			
			Date date1;
		    Date date2;
		    
			try {
				date1 = myFormat.parse(bar.getDate());
				date2 = myFormat.parse(currentDate);
				long  dayDifference = date2.getTime() - date1.getTime();
				
				if(dayDifference == 0) {
					long timeDifference = Time.getTimeDifference("HH:mm", bar.getTime(), currentTime); //in minutes
					if(timeDifference < 2) {
						flag = true;
					}
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return flag;
	}

}
