package com.jobpoint.controller;

import com.jobpoint.database.CRUDState;
import com.jobpoint.database.CRUDTrading;
import com.jobpoint.gui.ProductView;
import com.jobpoint.model.Product;
import com.jobpoint.model.State;

public class StateController {
	
	public State getLastRowState(Product product){
		
		State state = new State();
		CRUDState crudState = new CRUDState();
		
		state = crudState.findLastRow("product", product.getId());
		return state; 
	}
	
	public void saveState(State state) {
		CRUDState crudState = new CRUDState();
		crudState.insert(state);
	}
	
	public void updateState(State state) {
		CRUDState crudState = new CRUDState();
		crudState.update(state.getId(), state);
	}

}
