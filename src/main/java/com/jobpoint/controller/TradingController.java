package com.jobpoint.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.jobpoint.database.CRUDBar;
import com.jobpoint.database.CRUDTrading;
import com.jobpoint.gui.TradingTableModel;
import com.jobpoint.model.Bar;
import com.jobpoint.model.Chart;
import com.jobpoint.model.Product;
import com.jobpoint.model.State;
import com.jobpoint.model.Trading;
import com.jobpoint.tools.Parser;
import com.jobpoint.tools.Strategy;

public class TradingController {
	public List<Trading> getAllTrading(){
		CRUDTrading crudTrading = new CRUDTrading();
		List<Trading> tradingList = new ArrayList<Trading>();
		try {
			tradingList = crudTrading.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return tradingList; 
	}
	
	public List<Trading> getAllTradingByForeignId(String object, int foreign_id){
		CRUDTrading crudTrading = new CRUDTrading();
		List<Trading> tradingList = new ArrayList<Trading>();
		tradingList = crudTrading.findByForeignId(object, foreign_id);
		return tradingList; 
	}
	
	public List<Trading> getTradingBarList(String object, int foreign_id, boolean situation){
		CRUDTrading crudTrading = new CRUDTrading();
		List<Trading> tradingList = new ArrayList<Trading>();
		tradingList = crudTrading.findTopFive(object, foreign_id, situation);
		return tradingList; 
	}
	
	public int getSizeTradingByForeignId(String object, int product_id){
		
		CRUDTrading crudTrading = new CRUDTrading();
		List<Trading> tradingList = new ArrayList<Trading>();
		tradingList = crudTrading.findByForeignId(object, product_id);
		return tradingList.size()-1; 
	}
	
	public Trading getTrading(int id) {
		Trading trading = new Trading();
		
		CRUDTrading crudTrading = new CRUDTrading();
		trading = crudTrading.findById(id);
		
		return trading;
	}
	
	public void saveTrading(Trading trading, State state, TradingTableModel model) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime now = LocalDateTime.now();
		
		trading.setEnterDate(dateFormat.format(now));
		trading.setEnterTime(timeFormat.format(now));
		trading.setProductId(state.getProductId());
		
		CRUDTrading crudTrading = new CRUDTrading();
		int id = crudTrading.insert(trading);
		trading.setId(id);
		
		state.setTradingId(id);
		StateController stateController = new StateController();
		stateController.updateState(state);
		model.addTrading(trading);
	}
	
	public void updateTrading(Trading trading, State state, TradingTableModel model) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime now = LocalDateTime.now();
		
		trading.setExitDate(dateFormat.format(now));
		trading.setExitTime(timeFormat.format(now));
		String profit = Parser.parseProfit(trading.getEnterPrice(), trading.getExitPrice(), trading.getSituation());
		trading.setProfit(profit);
		
		CRUDTrading crudTrading = new CRUDTrading();
		crudTrading.update(trading.getId(), trading);
		
		state.setTradingId(0);
		StateController stateController = new StateController();
		stateController.updateState(state);
		int rowIndex = getSizeTradingByForeignId("product", state.getProductId());
		model.updateTrading(rowIndex, trading);
	}
	
	public void analyzeTrading(Product product, String price, List<Chart> chartList, TradingTableModel model) {
		Strategy strategy = new Strategy();
		
		switch(product.getStrategy()) {
		
			case "Arrow Only" : strategy.strategyArrow(product, price, chartList, model);
			break;
			
			case "Arrow + Momentum" : strategy.strategyArrowMomentum(product, price, chartList, model);
			break;
			
			default : strategy.strategyArrow(product, price, chartList, model);
			break;
		}
		
	}
	
}
