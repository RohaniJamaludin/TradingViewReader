package com.jobpoint.tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;
import com.jobpoint.controller.TradingController;
import com.jobpoint.gui.TradingTableModel;
import com.jobpoint.model.Chart;
import com.jobpoint.model.Product;

public class ChromeWebDriver {
	
	private final WebDriver driver;
	private final Product product;
	private String exFirstBar;
    private String currentSecondBar;
    private TradingTableModel model;
	public ChromeWebDriver(Product product, TradingTableModel model) {
		//String chromeDriverPath = null;
		//assert chromeDriverPath != null;
		
		this.product = product;
		this.model = model;
	    System.setProperty("webdriver.chrome.driver", "\\src\\chromedriver.exe");
	    ChromeOptions options = new ChromeOptions();
	    options.addArguments("user-data-dir=" + System.getProperty("user.home") + "/AppData/Local/Google/Chrome/Trading View/" + product.getSymbol() +"/Default");
	    options.addArguments("--start-maximized");
        
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
	}
	
	public void browse() {
		driver.get(product.getChartUrl());
		
		try {
    		Thread.sleep(1000 * 10 );
    	} catch (InterruptedException e1) {
    		// TODO Auto-generated catch block
    		e1.printStackTrace();
    	}
		
		Action moveLeftOneStep = new Actions(driver).sendKeys(Keys.ARROW_LEFT).build();
        Action moveRightOneStep = new Actions(driver).sendKeys(Keys.ARROW_RIGHT).build();
        
        boolean flag = true;
        
        while(flag) {
        	read(moveLeftOneStep,moveRightOneStep);
        }
        
        
        
	}
	
	private void read(Action moveLeftOneStep, Action moveRightOneStep) {
		try {
			
			WebElement tableWebElement =  getElementByLocator(By.cssSelector(
					"table.chart-markup-table tr:nth-child(1) td:nth-child(2) canvas:nth-child(2)"));
			
			System.out.println("Web Element " + tableWebElement + "exist");
			
			WebElement paneWebElement =  getElementByLocator(By.cssSelector(
							".pane-legend-item-value-container .pane-legend-item-value"));
			
			System.out.println("Web Element " + paneWebElement + "exist"); 
			
			WebElement priceWebElement =  getElementByLocator(By.cssSelector(
					".dl-header-price"));
			
			System.out.println("Web Element " + priceWebElement + "exist"); 
			
			List<WebElement> priceListWebElement = null;
			String price = "";
			if(priceWebElement != null) {
				priceListWebElement =  driver.findElements(
	    	                By.cssSelector(".dl-header-price"));
			}
			
			if(priceListWebElement.size() > 0) {
				price = priceListWebElement.get(0).getText();
			}
			
 	        boolean verifyElement = verifyElementListByLocater(By.cssSelector(
 	        			".pane-legend-item-value-container .pane-legend-item-value"));
 	        	
 	        if(verifyElement) {
 	        	System.out.println("Time now is:" + new SimpleDateFormat("HH:mm").format(new Date()));
 	        	
 	        	while(!placeScanLine(moveLeftOneStep, moveRightOneStep)) {
 	        		
 	        	}
 	        	
 	        	int moveStep = 0;
 	        	
 	        	if(product.getSymbol().equals("HSI")) {
 	        		if(exFirstBar != null) {
 	 	        		currentSecondBar = null;
 	 	        		List<WebElement> priceIndicators1 = driver.findElements(
 	 	    	                By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
 		        			String currentFirstBar = priceIndicators1.get(11).getText();
 	 	        		if(!currentFirstBar.equals(exFirstBar) ) {
 	 	        			while (!exFirstBar.equals(currentSecondBar)) {
 	 	 	        			moveLeftOneStep.perform();
 	 	 	        			List<WebElement> priceIndicators2 = driver.findElements(
 	 	     	    	                By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
 	 	 	        			currentSecondBar = priceIndicators2.get(11).getText();
 	 	 	        			moveStep++;
 	 	 	        			
 	 	 	        			if(exFirstBar.equals(currentSecondBar)) {
 		 	        				moveRightOneStep.perform();
 		 	        				moveStep--;
 		 	        			}
 	 	 	        			
 	 	 	        			if(priceIndicators2.get(5).getText().equals("1.0000") || priceIndicators2.get(6).getText().equals("1.0000") ) {
 	 	 	        				currentSecondBar = exFirstBar;
 	 	 	        			}

 	 	 	        		}
 	 	        		}
 	 	        	}
 	        	}
 	        	
 	        	List<Chart> chartList = new ArrayList<Chart>();
 	       		for (int i = 0; i < 3; i++) {		
 	       			List<WebElement> dataList = driver.findElements(
 	                By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
 	        		System.out.println(dataList.get(5).getText()  + "," +dataList.get(6).getText()  + "," + dataList.get(11).getText());
 	        		
 	        		Chart chart = new Chart();
    	        	chart.setArrowGreen(Float.parseFloat(dataList.get(5).getText()));
    	        	chart.setArrowRed(Float.parseFloat(dataList.get(6).getText()));
    	        	chart.setBar(Float.parseFloat(dataList.get(11).getText()));
    	        	
    	        	chartList.add(chart);
 	        		moveLeftOneStep.perform();
 	        	} 
 	       		
 	       		TradingController tradingController = new TradingController();
 	       		tradingController.analyzeTrading(product,price,chartList, model);
 	       		
 	       		for(int s = 0; s < 3 + moveStep; s++){
 	       			moveRightOneStep.perform();
 	       		}
 	       		
 	       		List<WebElement> priceIndicators3 = driver.findElements(
	                By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
 	       		
 	       		if(product.getSymbol().equals("HSI")) {
 	       			exFirstBar = priceIndicators3.get(11).getText();
 	       		}
     	    }
     	         
 	         
    	}catch(TimeoutException|NumberFormatException|StaleElementReferenceException|IndexOutOfBoundsException e) {
    			getElementByLocator(By.cssSelector(
    					"table.chart-markup-table tr:nth-child(1) td:nth-child(2) canvas:nth-child(2)"));
    			
    			getElementByLocator(By.cssSelector(
						".pane-legend-item-value-container .pane-legend-item-value"));
    			
    			getElementByLocator(By.cssSelector(
    					".dl-header-price"));
    			
    			verifyElementListByLocater(By.cssSelector(
 	        			".pane-legend-item-value-container .pane-legend-item-value"));
    		
    	}
		
    	try {
			Thread.sleep(1000 * 20);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			System.out.println("Thread Interuption");
			e1.printStackTrace();
		}
	}
	
	private boolean placeScanLine(Action moveLeftOneStep, Action moveRightOneStep) {
    	boolean flag = false;
    	
    	WebElement mainCanvas;
    	
    	mainCanvas = getElementByLocator(By.cssSelector(
    			"table.chart-markup-table tr:nth-child(1) td:nth-child(2) canvas:nth-child(2)"));
                
    	
    	if(mainCanvas!=null) {
    		Action moveToEdge = new Actions(driver)
                    .moveToElement(mainCanvas)
                    .build();
            moveToEdge.perform();
            
            List<WebElement> dataListCurrent;
        	List<WebElement> dataListRight;
        	List<WebElement> dataListLeft;
        	
        	try {
        		dataListCurrent = driver.findElements(
                        By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
        		
        		double barCurrrent = Double.parseDouble(dataListCurrent.get(11).getText());
            	
            	moveRightOneStep.perform();
            	
            	dataListRight = driver.findElements(
                        By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
            	
            	double barRight = Double.parseDouble(dataListRight.get(11).getText());
            	
            	moveRightOneStep.perform();
            	
            	dataListRight = driver.findElements(
                        By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
            	
            	double barRightTwo = Double.parseDouble(dataListRight.get(11).getText());
            	
            	moveLeftOneStep.perform();
            	moveLeftOneStep.perform();
            	moveLeftOneStep.perform();
            	
            	dataListLeft = driver.findElements(
                        By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
            
            	double barLeft = Double.parseDouble(dataListLeft.get(11).getText());
            	
            	moveRightOneStep.perform();
            	
        		if(barCurrrent != barRight && barCurrrent != barRightTwo) {
        			while(barCurrrent != barRight && barCurrrent != barRightTwo) {
        				moveRightOneStep.perform();
        				
        				dataListCurrent  = driver.findElements(
        		                By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
        				barCurrrent = Double.parseDouble(dataListCurrent.get(11).getText());
        				
        				moveRightOneStep.perform();
        			
        				dataListRight  = driver.findElements(
        		                By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
        				
        				barRight = Double.parseDouble(dataListRight.get(11).getText());
        				
        				moveRightOneStep.perform();
            			
        				dataListRight  = driver.findElements(
        		                By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
        				
        				barRightTwo = Double.parseDouble(dataListRight.get(11).getText());
        				
        				moveLeftOneStep.perform();
        			}
        			
        			moveLeftOneStep.perform();
        		}else {
        			if(barCurrrent == barLeft) {
        				moveLeftOneStep.perform();
        				try {
        					Thread.sleep(1000 * 1);
        					flag = false;
        				} catch (InterruptedException e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        					flag = false;
        				}
        			}else{
        				flag = true;
        			}
        		}
        		
        	}catch(IndexOutOfBoundsException e) {

        	}
    		
    	}
		
    	return flag;
    }
	
	public WebElement getElementByLocator( final By locator ) {
		  //LOGGER.info( "Get element by locator: " + locator.toString() );  
		  final long startTime = System.currentTimeMillis();
		  Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		    .withTimeout(30, TimeUnit.SECONDS)
		    .pollingEvery(5, TimeUnit.SECONDS)
		    .ignoring( StaleElementReferenceException.class ) ;
		  //int tries = 0;
		  boolean found = false;
		  WebElement we = null;
		  while ( (System.currentTimeMillis() - startTime) < 91000 ) {
		  // LOGGER.info( "Searching for element. Try number " + (tries++) ); 
		   try {
		    we = wait.until( ExpectedConditions.presenceOfElementLocated( locator ) );
		    found = true;
		    break;
		   } catch ( StaleElementReferenceException e ) {      
		    //LOGGER.info( "Stale element: \n" + e.getMessage() + "\n");
		   }
		  }
		  //long endTime = System.currentTimeMillis();
		  //long totalTime = endTime - startTime;
		  if ( found ) {
		   //LOGGER.info("Found element after waiting for " + totalTime + " milliseconds." );
		  } else {
		   //LOGGER.info( "Failed to find element after " + totalTime + " milliseconds." );
		  }
		  return we;
		}
	
	public Boolean verifyElementListByLocater(final By locator){
		
		final long startTime = System.currentTimeMillis();
		
		boolean found = false;
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				   .withTimeout(30, TimeUnit.SECONDS)
				   .pollingEvery(5, TimeUnit.SECONDS)
				   .ignoring( StaleElementReferenceException.class ) ;
		
		while ( (System.currentTimeMillis() - startTime) < 91000 ) {
			  // LOGGER.info( "Searching for element. Try number " + (tries++) ); 
			   try {
				   Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
						public Boolean apply(WebDriver webDriver) {
							List<WebElement> dataContainers  = webDriver.findElements(locator);
	     	                return dataContainers.size() == 13 && !dataContainers.get(5).getText().equals("n/a");
						}
					};
					wait.until(function);
					found = true;
					break;
			   } catch ( StaleElementReferenceException e ) {      
			    //LOGGER.info( "Stale element: \n" + e.getMessage() + "\n");
			   }
		}
		
	
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		if ( found ) {
			   System.out.println("Found element after waiting for " + totalTime + " milliseconds." );
			} else {
			   //LOGGER.info( "Failed to find element after " + totalTime + " milliseconds." );
		}
		
		return found;
	}
	
	
	
}
