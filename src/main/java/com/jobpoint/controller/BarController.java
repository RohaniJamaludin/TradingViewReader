package com.jobpoint.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.jobpoint.database.CRUDBar;
import com.jobpoint.model.Bar;

public class BarController {
	
	public void updateCurrentBar(int productId, String barValue) {
		
		CRUDBar crudBar = new CRUDBar();
		Bar barResultSet = crudBar.findByForeignId("product", productId);
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime now = LocalDateTime.now();
		
		Bar bar = new Bar();
		
		bar.setValue(barValue);
		bar.setProductId(productId);
		bar.setDate(dateFormat.format(now));
		bar.setTime(timeFormat.format(now));
		
		if(barResultSet.getId() == 0 ) {
			crudBar.insert(bar);
		}else {
			crudBar.update(barResultSet.getId(), bar);
		}

		//if currentBar is Empty the insert new else update record
		
	}
	
	
	public Bar getCurrentBar(int productId) {
		
		CRUDBar crudBar = new CRUDBar();
		Bar bar = crudBar.findByForeignId("product", productId);
		
		return bar;
	}
	
	public void clearBar(int productId) {
		Bar bar = new Bar();
		CRUDBar crudBar = new CRUDBar();
		bar = crudBar.findByForeignId("product", productId);
		if(bar.getId()>0) {
			bar.setValue("");
			bar.setDate("");
			bar.setTime("");
			crudBar.update(bar.getId(), bar);
		}
		
	}
	

}
