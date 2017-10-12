package com.jobpoint.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.jobpoint.model.Product;

public class ProductTableModel extends AbstractTableModel {
	
private static final long serialVersionUID = 1L;
	
	private List<Product> productList;
	private final String[] columnNames = new String[] {
             "ID","Product Name", "Symbol", "Symbol Description" };
	
	public ProductTableModel(List<Product> productList) {
		this.productList = productList;
	}
	
	
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return productList.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Product row = productList.get(rowIndex);
		
		
        if(0 == columnIndex) {
            return row.getId();
        }
        else if(1 == columnIndex) {
            return row.getName();
        }
        else if(2 == columnIndex) {
            return row.getSymbol();
        }
        else if(3 == columnIndex) {
        	return row.getSymbolDescription();
        }
      
        return null;
	}
	
	public String getColumnName(int column){
		return columnNames[column];
	}
	 
	public void addProduct(Product product) {
		productList.add(product);
		fireTableRowsInserted(productList.size() -1, productList.size() -1);
	}
		    
	public void removeProduct(int rowIndex) {
		productList.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}
		
	public void updateProduct(int rowIndex, Product product) {
		productList.set(rowIndex, product);
		this.fireTableRowsUpdated(rowIndex, rowIndex);
	}
	

}
