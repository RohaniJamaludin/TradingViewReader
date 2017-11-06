package com.jobpoint.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.jobpoint.database.CRUDProduct;
import com.jobpoint.gui.MainWindow;
import com.jobpoint.gui.ProductView;
import com.jobpoint.gui.TradingTableModel;
import com.jobpoint.model.Product;
import com.jobpoint.model.State;
import com.jobpoint.model.Trading;
import com.jobpoint.tools.ChromeWebDriver;

public class ProductController {
	
	public List<Product> getAllProduct(){
		CRUDProduct crudProduct = new CRUDProduct();
		List<Product> productList = new ArrayList<Product>();
		try {
			productList = crudProduct.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return productList; 
	}
	
	public void newProduct() {
		new ProductView(true, null,null,0);
	}
	
	public void editProduct(int id, int rowIndex) {
		
		Product product = new Product();
		List<Trading> tradingList = new ArrayList<Trading>();
		
		CRUDProduct crudProduct = new CRUDProduct();
		product = crudProduct.findById(id);
		
		TradingController tradingController = new TradingController();
		tradingList = tradingController.getAllTradingByForeignId("product", id);
		new ProductView(false, product, tradingList, rowIndex);
	}
	
	public int saveProduct(Product product) {
		
		CRUDProduct crudProduct = new CRUDProduct();
		int id = crudProduct.insert(product);
		product.setId(id);
		
		State state = new State();
		state.setProductId(id);
		
		StateController stateController = new StateController();
		stateController.saveState(state);	
		
		MainWindow.model.addProduct(product);
		
		return id;
	}
	
	public void updateProduct(Product product, int rowIndex) {
		CRUDProduct crudProduct = new CRUDProduct();
		crudProduct.update(product.getId(), product);
		MainWindow.model.updateProduct(rowIndex, product);
	}
	
	public boolean deleteProduct(int id, int rowIndex) {
		CRUDProduct crudProduct = new CRUDProduct();
		
		int response = JOptionPane.showConfirmDialog(null, 
	            "Do you want to delete selected row?", 
	            "Confirm", JOptionPane.YES_NO_OPTION, //
	            JOptionPane.QUESTION_MESSAGE);
	    if (response == JOptionPane.YES_OPTION) {
	    	if(crudProduct.delete(id)) {
				
				MainWindow.model.removeProduct(rowIndex);
				return true;
			}
	    }  
	    
	    return false;
		
	}
	public Thread runProduct(final Product product, final TradingTableModel model) {
		final ChromeWebDriver chromeWebDriver = new ChromeWebDriver(product, model);
		
		Runnable myRunnable = new Runnable(){

		     public void run(){
		        System.out.println("Runnable running");
		        chromeWebDriver.browse();
		     }
		   };

		   Thread thread = new Thread(myRunnable);
		   thread.start();
		   
		   return thread;
	}
	
	public void stopProduct(Thread thread, int productId) {
		BarController barController = new BarController();
		barController.clearBar(productId);
		thread.interrupt();
	}
	

}
