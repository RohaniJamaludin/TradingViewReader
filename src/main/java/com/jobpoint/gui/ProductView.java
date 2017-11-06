package com.jobpoint.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.jobpoint.controller.ProductController;
import com.jobpoint.controller.TradingController;
import com.jobpoint.model.Product;
import com.jobpoint.model.Trading;

public class ProductView implements ActionListener, ItemListener{
	
	private JFrame frame;
	private JTable table;
	private TradingTableModel model;
	
	private JTextField nameText, symbolText, symbolDescriptionText, urlChartText, totalProfitText, urlPtaText, lotText;
	private JCheckBox checkBoxMarket, checkBoxPauseTrading, checkBoxMonday, checkBoxTuesday, checkBoxWednesday, checkBoxThursday, checkBoxFriday, checkBoxSaturday, checkBoxSunday;
	private JComboBox<String> dayCombo, strategyCombo, orderTypeCombo;
	private TimePicker openTimePicker, closeTimePicker, pauseTradingTimeFromPicker, pauseTradingTimeToPicker ;
	private JButton saveButton, saveRunButton, stopButton, closeButton;
	private JPanel marketPanel, pauseTradingPanel, buttonPanel;
	private boolean isNew;
	private int rowIndex, id;
	private Thread thread;
	
	
	public ProductView(boolean isNew, Product product, List<Trading> tradingList, int rowIndex) {
		initialize();
		this.isNew = isNew;
		this.rowIndex = rowIndex;
		this.stopButton.setEnabled(false);
		//setupNew();
		if(isNew) {
			setupNew();
			id = 0;
		}else {
			this.id = product.getId();
			frame.setTitle(product.getName());
			populateData(product, tradingList);
		}
	}

	private void initialize() {

		frame = new JFrame("New Product");
		
		
		/*************************Product Panel Start**************************/
		
		GridBagConstraints c = new GridBagConstraints();
        c.gridheight = 1;
        c.weighty = 0.1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.BASELINE;
		
        JPanel productPanel = new JPanel(new GridBagLayout());
        productPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        productPanel.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
        c.weightx = 3;
        productPanel.add(new JLabel("Product Details"), c);
        
        c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 7;
        c.weightx = 7;
        productPanel.add(new JSeparator(), c);
		
        c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
        c.weightx = 2;
        productPanel.add(new JLabel("Name"), c);
		nameText = new JTextField();
        c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 5;
        c.weightx = 5;
        productPanel.add(nameText, c);

        c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
        c.weightx = 2;
        productPanel.add(new JLabel("Symbol"), c);
		symbolText = new JTextField();
        c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 5;
        c.weightx = 5;
        productPanel.add(symbolText, c);
		
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
        c.weightx = 2;
        productPanel.add(new JLabel("Symbol Description"), c);
        symbolDescriptionText = new JTextField();
        c.gridx = 2;
		c.gridy = 4;
		c.gridwidth = 5;
        c.weightx = 5;
        productPanel.add(symbolDescriptionText, c);
		
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
        c.weightx = 2;
        productPanel.add(new JLabel("Chart URL"), c);
        urlChartText = new JTextField();
        c.gridx = 2;
		c.gridy = 5;
		c.gridwidth = 5;
        c.weightx = 5;
        productPanel.add(urlChartText, c);
        urlChartText.setText("https://www.tradingview.com/chart");
        
        c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
        c.weightx = 2;
        productPanel.add(new JLabel("PTA URL"), c);
        urlPtaText = new JTextField();
        c.gridx = 2;
		c.gridy = 6;
		c.gridwidth = 5;
        c.weightx = 5;
        productPanel.add(urlPtaText, c);
        
        String[] strategies = {"Arrow Only", "Arrow + Momentum"}; 
        
        c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 2;
        c.weightx = 2;
        productPanel.add(new JLabel("Strategy"), c);
        strategyCombo = new JComboBox<String>(strategies);
        c.gridx = 2;
		c.gridy = 7;
		c.gridwidth = 5;
        c.weightx = 5;
        productPanel.add(strategyCombo, c);
        strategyCombo.setSelectedIndex(0);
        
        String[] orderTypes = {"Limit", "Market"};
        
        c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 2;
        c.weightx = 2;
        productPanel.add(new JLabel("Order Type"), c);
        orderTypeCombo = new JComboBox<String>(orderTypes);
        c.gridx = 2;
		c.gridy = 8;
		c.gridwidth = 5;
        c.weightx = 5;
        productPanel.add(orderTypeCombo, c);
        orderTypeCombo.setSelectedIndex(0);
        
        c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 2;
        c.weightx = 2;
        productPanel.add(new JLabel("No. of Lot"), c);
		lotText = new JTextField();
        c.gridx = 2;
		c.gridy = 9;
		c.gridwidth = 5;
        c.weightx = 5;
        productPanel.add(lotText, c);
        lotText.setText("1");
		
		
        /******************Market Opening/Closing Start************************/
		c.gridx = 0;
		c.gridy = 10;
		c.gridwidth = 3;
        c.weightx = 3;
        checkBoxMarket = new JCheckBox("Market Open/Close");
        checkBoxMarket.addItemListener(this);
        productPanel.add(checkBoxMarket, c);
        
        
		c.gridx = 0;
		c.gridy = 11;
		c.gridwidth = 7;
        c.weightx = 7;
		c.gridheight = 2;
        c.weighty = 2;
        c.gridheight = 1;
        c.weighty = 0.1;
        marketPanel = new JPanel(new GridBagLayout());
        marketPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        productPanel.add(marketPanel, c);
		
        marketPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#C0C0C0")));
	
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridheight = 1;
        c2.weighty = 0.1;
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.anchor = GridBagConstraints.BASELINE;
        
        c2.gridx = 0;
		c2.gridy = 0;
		c2.gridwidth = 2;
        c2.weightx = 2;
        marketPanel.add(new JLabel("Day"), c2);
        
        checkBoxMonday = new JCheckBox("Monday");
        c2.gridx = 0;
		c2.gridy = 1;
		c2.gridwidth = 1;
        c2.weightx = 1;
        c2.weighty = 0.3;
        marketPanel.add(checkBoxMonday, c2);
        
        checkBoxTuesday = new JCheckBox("Tuesday");
        c2.gridx = 1;
		c2.gridy = 1;
		c2.gridwidth = 1;
        c2.weightx = 1;
        marketPanel.add(checkBoxTuesday, c2);
        
        checkBoxWednesday = new JCheckBox("Wednesday");
        c2.gridx = 2;
		c2.gridy = 1;
		c2.gridwidth = 1;
        c2.weightx = 1;
        marketPanel.add(checkBoxWednesday, c2);
        
        checkBoxThursday = new JCheckBox("Thursday");
        c2.gridx = 3;
		c2.gridy = 1;
		c2.gridwidth = 1;
        c2.weightx = 1;
        marketPanel.add(checkBoxThursday, c2);
        
        checkBoxFriday = new JCheckBox("Friday");
        c2.gridx = 4;
		c2.gridy = 1;
		c2.gridwidth = 1;
        c2.weightx = 1;
        marketPanel.add(checkBoxFriday, c2);
        
        checkBoxSaturday = new JCheckBox("Saturday");
        c2.gridx = 5;
		c2.gridy = 1;
		c2.gridwidth = 1;
        c2.weightx = 1;
        marketPanel.add(checkBoxSaturday, c2);
        
        checkBoxSunday = new JCheckBox("Sunday");
        c2.gridx = 6;
		c2.gridy = 1;
		c2.gridwidth = 1;
        c2.weightx = 1;
        marketPanel.add(checkBoxSunday, c2);
        
        TimePickerSettings tpSetting = new TimePickerSettings();
        tpSetting.setAllowEmptyTimes(false);
        tpSetting.setDisplayToggleTimeMenuButton(false);
        tpSetting.setDisplaySpinnerButtons(true);
        tpSetting.setFormatForDisplayTime("HH:mm");
        
        c2.gridx = 0;
		c2.gridy = 3;
		c2.gridwidth = 1;
        c2.weightx = 2;
        marketPanel.add(new JLabel("Time"), c2);
        
        c2.gridx = 0;
		c2.gridy = 4;
		c2.gridwidth = 1;
        c2.weightx = 2;
        marketPanel.add(new JLabel("From"), c2);
        
        openTimePicker = new TimePicker(tpSetting);
        c2.gridx = 1;
		c2.gridy = 4;
		c2.gridwidth = 1;
        c2.weightx = 1;
        marketPanel.add(openTimePicker, c2);
        
        c2.gridx = 3;
		c2.gridy = 4;
		c2.gridwidth = 1;
        c2.weightx = 1;
        marketPanel.add(new JLabel("To"), c2);
        
        closeTimePicker = new TimePicker(tpSetting);
        c2.gridx = 4;
		c2.gridy = 4;
		c2.gridwidth = 2;
        c2.weightx = 2;
        marketPanel.add(closeTimePicker, c2);
        
        /************ Market Opening/Closing Panel End***************/
        
        
        /************ Pause Trading Panel Start***************/
        
        c.gridx = 0;
		c.gridy = 12;
		c.gridwidth = 3;
        c.weightx = 3;
        checkBoxPauseTrading = new JCheckBox("Pause Trading");
        checkBoxPauseTrading.addItemListener(this);
        productPanel.add(checkBoxPauseTrading, c);
		
		c.gridx = 0;
		c.gridy = 13;
		c.gridwidth = 7;
        c.weightx = 7;
		c.gridheight = 2;
        c.weighty = 2;
        c.gridheight = 1;
        c.weighty = 0.1;
        pauseTradingPanel = new JPanel(new GridBagLayout());
        pauseTradingPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        productPanel.add(pauseTradingPanel, c);
        
        pauseTradingPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#C0C0C0")));

        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridheight = 1;
        c3.weighty = 0.1;
        c3.fill = GridBagConstraints.HORIZONTAL;
        c3.anchor = GridBagConstraints.BASELINE;
        
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}; 

        c3.gridx = 0;
		c3.gridy = 0;
		c3.gridwidth = 1;
        c3.weightx = 1;
        pauseTradingPanel.add(new JLabel("Day"), c3);
        dayCombo = new JComboBox<String>(days);
        c3.gridx = 1;
		c3.gridy = 0;
		c3.gridwidth = 2;
        c3.weightx = 2;
        pauseTradingPanel.add(dayCombo, c3);
        dayCombo.setSelectedIndex(0);
        
        
        c3.gridx = 0;
        c3.gridy = 1;
        c3.gridwidth = 1;
        c3.weightx = 2;
        pauseTradingPanel.add(new JLabel("From"), c3);
        
        pauseTradingTimeFromPicker = new TimePicker(tpSetting);
        c3.gridx = 1;
        c3.gridy = 1;
        c3.gridwidth = 1;
        c3.weightx = 1;
        pauseTradingPanel.add(pauseTradingTimeFromPicker, c3);
        
        c3.gridx = 3;
        c3.gridy = 1;
        c3.gridwidth = 1;
        c3.weightx = 1;
        pauseTradingPanel.add(new JLabel("   To"), c3);
        
        pauseTradingTimeToPicker = new TimePicker(tpSetting);
        c3.gridx = 4;
        c3.gridy = 1;
        c3.gridwidth = 2;
        c3.weightx = 2;
        pauseTradingPanel.add(pauseTradingTimeToPicker, c3);
		
        frame.getContentPane().add(productPanel, BorderLayout.PAGE_START);
        
        
        /**********************Pause Trading Panel End**************************/
        
        c.gridx = 0;
		c.gridy = 14;
		c.gridwidth = 1;
        c.weightx = 1;
        productPanel.add(new JLabel("Total Profit"), c);
        c.gridx = 1;
		c.gridy = 14;
		c.gridwidth = 2;
        c.weightx = 2;
        totalProfitText = new JTextField();
        productPanel.add(totalProfitText, c);
        totalProfitText.setText("0.00");
        
        /*************************Product Panel End**************************/
        
        
        
        
        /*************************Trading Panel Start**************************/
        
        TradingController tradingController = new TradingController();
		List<Trading> tradingList = tradingController.getAllTradingByForeignId("product", id);
		 
		model = new TradingTableModel(tradingList);
		
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(800, 100));
		table.setFillsViewportHeight(true);
		
		TableColumnModel tableColumnModel = table.getColumnModel();
		tableColumnModel.removeColumn(tableColumnModel.getColumn(0));
        
        JPanel tradingPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tradingPanel.add(scrollPane, BorderLayout.PAGE_START);
		
		frame.getContentPane().add(tradingPanel, BorderLayout.CENTER);
		
		/*************************Trading Panel End**************************/
		
		
		
		/***********************Button Panel Start**************************/
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		frame.getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		buttonPanel.add(saveButton);
		
		saveRunButton = new JButton("Run");
		saveRunButton.addActionListener(this);
		buttonPanel.add(saveRunButton);
		
		stopButton = new JButton("Stop");
		stopButton.addActionListener(this);
		buttonPanel.add(stopButton);
		
		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		buttonPanel.add(closeButton);
		/***************************Button Panel End ************************/
		
/*		frame.addWindowListener(new WindowAdapter() {
			public void  windowClosed(WindowEvent event) {
				System.out.println("Closed");

				if (driver != null) {
					//driver.manage().window().setPosition(new Point(-2000, 0));
					driver.quit();
				}
					
			}
		});
		*/
		
		frame.setSize(600,600);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		
		int w = frame.getSize().width;
		int h = frame.getSize().height;
	    int x = (dimension.width-w)/2;
	    int y = (dimension.height-h)/2;
	    frame.setLocation(x, y);
	    
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private void setupNew() {
		toggleMarketPanel(checkBoxMarket.isSelected());
		togglePauseTradingPanel(checkBoxPauseTrading.isSelected());
	}

	private void populateData(Product product, List<Trading> tradingList) {
		nameText.setText(product.getName());
		symbolText.setText(product.getSymbol());
		symbolDescriptionText.setText(product.getSymbolDescription());
		urlChartText.setText(product.getChartUrl());
		urlPtaText.setText(product.getPtaUrl());
		strategyCombo.setSelectedItem(product.getStrategy());
		orderTypeCombo.setSelectedItem(product.getOrderType());
		lotText.setText(String.valueOf(product.getLot()));

		checkBoxMarket.setSelected(product.getIsMarketOpen());
		toggleMarketPanel(product.getIsMarketOpen());

		checkBoxMonday.setSelected(product.getIsMonday());
		checkBoxTuesday.setSelected(product.getIsTuesday());
		checkBoxWednesday.setSelected(product.getIsWednesday());
		checkBoxThursday.setSelected(product.getIsThursday());
		checkBoxFriday.setSelected(product.getIsFriday());
		checkBoxSaturday.setSelected(product.getIsSaturday());
		checkBoxSunday.setSelected(product.getIsSunday());
		
		openTimePicker.setText(product.getOpenTime());
		closeTimePicker.setText(product.getCloseTime());
		
		
		switch(product.getPauseDay()) {
		case "Monday" : dayCombo.setSelectedIndex(0);
		break;
		case "Tuesday" : dayCombo.setSelectedIndex(1);
		break;
		case "Wednesday" : dayCombo.setSelectedIndex(2);
		break;
		case "Thursday" : dayCombo.setSelectedIndex(3);
		break;
		case "Friday" : dayCombo.setSelectedIndex(4);
		break;
		case "Saturday" : dayCombo.setSelectedIndex(5);
		break;
		case "Sunday" : dayCombo.setSelectedIndex(6);
		break;
		default : dayCombo.setSelectedIndex(0);
		break;
		}
		
		pauseTradingTimeFromPicker.setText(product.getPauseTimeFrom());
		pauseTradingTimeToPicker.setText(product.getPauseTimeTo());
		
		checkBoxPauseTrading.setSelected(product.getIsTradingPause());
		togglePauseTradingPanel(product.getIsTradingPause());
		
		String profit;
		for(Trading trading : tradingList ) {
			
			model.addTrading(trading);
		}
		
		/*model = new TradingTableModel(tradingList);
		
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(800, 800));
		table.setFillsViewportHeight(true);*/
		
	}
	
	private Product saveProduct() {
		Product product = new Product();
		product.setName(nameText.getText());
		product.setSymbol(symbolText.getText());
		product.setSymbolDescription(symbolDescriptionText.getText());
		product.setChartUrl(urlChartText.getText());
		product.setPtaUrl(urlPtaText.getText());
		product.setOrderType(orderTypeCombo.getSelectedItem().toString());
		product.setLot(Integer.parseInt(lotText.getText()));
		product.setStrategy(strategyCombo.getSelectedItem().toString());
		product.setIsMarketOpen(checkBoxMarket.isSelected());
		product.setIsMonday(checkBoxMonday.isSelected());
		product.setIsTuesday(checkBoxTuesday.isSelected());
		product.setIsWednesday(checkBoxWednesday.isSelected());
		product.setIsThursday(checkBoxThursday.isSelected());
		product.setIsFriday(checkBoxFriday.isSelected());
		product.setIsSaturday(checkBoxSaturday.isSelected());
		product.setIsSunday(checkBoxSunday.isSelected());
		product.setOpenTime(openTimePicker.getText());
		product.setCloseTime(closeTimePicker.getText());
		product.setIsTradingPause(checkBoxPauseTrading.isSelected());
		product.setPauseDay(dayCombo.getSelectedItem().toString());
		product.setPauseTimeFrom(pauseTradingTimeFromPicker.getText());
		product.setPauseTimeTo(pauseTradingTimeToPicker.getText());
		
		ProductController productController = new ProductController();
		if(isNew) {
			id = productController.saveProduct(product);
			frame.setTitle(product.getName());
			isNew = false;
			rowIndex = MainWindow.model.getRowCount()-1;
		}else {
			product.setId(id);
			productController.updateProduct(product, rowIndex);
		}
		
		return product;
	}
	
	private void toggleMarketPanel(boolean isSelected) {
		for (Component marketComponent : marketPanel.getComponents() ){
			marketComponent.setEnabled(isSelected);
		}

	}
	
	private void togglePauseTradingPanel(boolean isSelected) {
		for (Component pauseTradingComponent : pauseTradingPanel.getComponents() ){
			pauseTradingComponent.setEnabled(isSelected);
		}

	}
	
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource().equals(saveButton)) {
			saveProduct();
		}
		
		if(event.getSource().equals(saveRunButton)) {
			Product product = saveProduct();
			ProductController productController = new ProductController();
			thread = productController.runProduct(product, model);
			saveRunButton.setEnabled(false);
			stopButton.setEnabled(true);
		} 
		
		if(event.getSource().equals(stopButton)) {
			ProductController productController = new ProductController();
			productController.stopProduct(thread, id);
			stopButton.setEnabled(false);
			saveRunButton.setEnabled(true);
			
		}
		
		if(event.getSource().equals(closeButton)) {
			System.out.println("Cancel");
			frame.dispose();
		}
	}

	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(checkBoxMarket)) {
			toggleMarketPanel(checkBoxMarket.isSelected());
		}
		
		if(e.getSource().equals(checkBoxPauseTrading)) {
			togglePauseTradingPanel(checkBoxPauseTrading.isSelected());
		}
		
	}
	
}
