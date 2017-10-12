package com.jobpoint.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import com.jobpoint.model.Trading;

public class TradingTableModel extends AbstractTableModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Trading> tradingList;
	private final String[] columnNames = new String[] {
             "ID", "Enter Price", "Enter Date",  "Enter Time", "Exit Price", "Exit Date", "Exit Time", "Profit"  };

	public TradingTableModel(List<Trading> tradingList) {
		this.tradingList = tradingList;
	}

	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return tradingList.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Trading row = tradingList.get(rowIndex);
		String enterPrice, exitPrice;
		
		
		//true when enter buy exit sell 
		//false when enter sell exit buy
		if(row.getSituation()) {
			enterPrice = "BUY:" + row.getEnterPrice(); //buying price red color
			exitPrice = "SELL:" + row.getExitPrice(); //selling price green color
		}else {
			enterPrice = "SELL:" + row.getEnterPrice(); // selling price green color
			exitPrice = "BUY:" + row.getExitPrice(); //buying price red color
		}
		
        if(0 == columnIndex) {
            return row.getId();
        }
        else if(1 == columnIndex) {
            return enterPrice;
        }
        else if(2 == columnIndex) {
            return row.getEnterDate();
        }
        else if(3 == columnIndex) {
            return row.getEnterTime();
        }
        else if(4 == columnIndex) {
            return exitPrice;
        }
        else if(5 == columnIndex) {
            return row.getExitDate();
        }
        else if(6 == columnIndex) {
            return row.getExitTime();
        }
        else if(7 == columnIndex) {
        	return row.getProfit();
        }
        return null;
	}
	
	public String getColumnName(int column){
		return columnNames[column];
	}
	 
	public void addTrading(Trading trading) {
		tradingList.add(trading);
		fireTableRowsInserted(tradingList.size() -1, tradingList.size() -1);
	}
		    
	public void removeTrading(int rowIndex) {
		tradingList.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}
		
	public void updateTrading(int rowIndex, Trading trading) {
		tradingList.set(rowIndex, trading);
		this.fireTableRowsUpdated(rowIndex, rowIndex);
/*		Double totalProfit = Double.parseDouble(Index.profitTotalText.getText());
		totalProfit += Double.parseDouble(strategy.getProfit());
		StrategyIndex.profitTotalText.setText(totalProfit.toString());*/
	}
}
