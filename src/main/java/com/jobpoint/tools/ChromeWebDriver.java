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
import com.jobpoint.controller.BarController;
import com.jobpoint.controller.TradingController;
import com.jobpoint.gui.TradingTableModel;
import com.jobpoint.model.Bar;
import com.jobpoint.model.Chart;
import com.jobpoint.model.Product;

public class ChromeWebDriver {
	
	private final WebDriver driver;
	private final Product product;
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
    		driver.quit();
    	}
		
		Action moveLeftOneStep = new Actions(driver).sendKeys(Keys.ARROW_LEFT).build();
        Action moveRightOneStep = new Actions(driver).sendKeys(Keys.ARROW_RIGHT).build();
        Action jiggle = new Actions(driver).moveByOffset(0, -10).moveByOffset(0, 10).build();
        
        boolean flag = true;
        
        while(flag) {

				read(moveLeftOneStep,moveRightOneStep, jiggle);

        	try {
    			Thread.sleep(1000 * 10);
    		} catch (InterruptedException e1) {
    			// TODO Auto-generated catch block
    			System.out.println("Thread Interuption/stop");
    			e1.printStackTrace();
    			flag = false;
    			driver.quit();
    		}
        }
        
	}
	
	private void read(Action moveLeftOneStep, Action moveRightOneStep, Action jiggle) {
		try {
			
			
			WebElement tableWebElement =  getElementByLocator(By.cssSelector(
					"table.chart-markup-table tr:nth-child(1) td:nth-child(2) canvas:nth-child(2)"));
			WebElement paneWebElement =  getElementByLocator(By.cssSelector(
							".pane-legend-item-value-container .pane-legend-item-value"));
			WebElement priceWebElement =  getElementByLocator(By.cssSelector(
					".dl-header-price"));
			
			List<WebElement> priceListWebElement = null;
			String price = "";
			
			if(priceWebElement != null) {
				priceListWebElement =  getElementListByLocater(
	    	                By.cssSelector(".dl-header-price"));
			}
			
			if(priceListWebElement.size() > 0) {
				price = priceListWebElement.get(0).getText();
			}
			
 	        boolean verifyElement = verifyElementListByLocater(By.cssSelector(
 	        			".pane-legend-item-value-container .pane-legend-item-value"));
 	        
 	        boolean verifyScanLine = verifyScanLine(moveLeftOneStep, moveRightOneStep);
 	        	
 	        
 	        if(verifyElement && verifyScanLine) {
 	        	
 	        	System.out.println("Time now is:" + new SimpleDateFormat("HH:mm").format(new Date()));
 	        	
 	        	List<WebElement> chartData = getElementListByLocater(
 	 	                By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
 	        	
 	        	String bar = chartData.get(11).getText();
 	        	
 	        	System.out.println("Bar now is:" + bar);
 	        	
 	        	int moveStep = 0;
 	        	
 	        	BarController barController = new BarController();
 	        	Bar currentSaveBar = new Bar();
 	        	
 	        	currentSaveBar = barController.getCurrentBar(product.getId());
 	        	
 	        	System.out.println("Current Save Bar:" + currentSaveBar.getValue());
 	        	
 	        		if(!currentSaveBar.getValue().equals("")) {
 	        			
 	 	        		//currentSecondBar = null;
/* 	 	        		List<WebElement> priceIndicators1 = getElementListByLocater(
 	 	    	                By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));*/
 		        		String currentFirstBar = bar;
 		        			
 		        		boolean arrow = false;
 	 	        		if(!currentFirstBar.equals(currentSaveBar.getValue()) ) {
 	 	        			
 	 	        			barController.updateCurrentBar(product.getId(), currentFirstBar);
 	 	        			
 	 	        			moveLeftOneStep.perform();
 	 	        			
	 	 	        		List<WebElement> priceIndicators2 = getElementListByLocater(
	 	     	    	                By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
	 	 	        		String currentSecondBar = priceIndicators2.get(11).getText();
	 	 	        		
	 	 	        		moveRightOneStep.perform();
	 	 	        			
 	 	        			while (!currentSaveBar.getValue().equals(currentSecondBar)) {
 	 	 	        			
 	 	 	        			if(priceIndicators2.get(5).getText().equals("1.0000") || priceIndicators2.get(6).getText().equals("1.0000") ) {
 	 	 	        				
 	 	 	        				barController.updateCurrentBar(product.getId(), currentSecondBar);
 	 	 	        				currentSecondBar = currentSaveBar.getValue();
 	 	 	        				arrow = true;
 	 	 	        				//moveLeftOneStep.perform();
 	 	 	        				//moveStep++;
 	 	 	        			}
 	 	 	        			
 	 	 	        			if(!currentSaveBar.getValue().equals(currentSecondBar)) {
 		 	        				//moveRightOneStep.perform();
 		 	        				//moveStep--;
 	 	 	        				
 	 	 	        				moveLeftOneStep.perform();
 	 	 	        				moveStep++;
 	 	 	        				List<WebElement> priceIndicators3 = getElementListByLocater(
 		 	     	    	                By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
 	 	 	        				currentSecondBar = priceIndicators3.get(11).getText();
 	 	 	        				System.out.println("Current Second Bar:" + currentSecondBar);
 	 	 	        				System.out.println("Ex First Bar:" + currentSaveBar.getValue());
 	 	 	        				
 		 	        			}
 	 	 	        			
 	 	 	        			if(!arrow && currentSaveBar.getValue().equals(currentSecondBar)) {
 	 	 	        				
 	 	 	        				moveRightOneStep.perform();
 	 	 	        				moveStep--;
 	 	 	        			}
 	 	 	        			
 	 	 	        			System.out.println("Move Step:" + moveStep);
 	 	 	        		}
 	 	        			
 	 	        		}
 	 	        		
 	 	        		scanChart(jiggle, moveRightOneStep, moveLeftOneStep, moveStep, price);
	 	        			
	 	   	       		if(moveStep > 0) {
	 	        			barController.updateCurrentBar(product.getId(), currentFirstBar);
	 	   	       			scanChart(jiggle, moveRightOneStep, moveLeftOneStep, 0, price);
	 	   	       		}
 	 	        	//}
 	        	} else {
 	    	        currentSaveBar.setValue(bar);
 	    	        barController.updateCurrentBar(product.getId(), currentSaveBar.getValue());
 	        	}
 	        	
 	        	
 	       		
 	       		
 	       		
     	    }
     	         
 	         
    	}catch(TimeoutException|NumberFormatException|StaleElementReferenceException|IndexOutOfBoundsException e) {
    		
    		    System.out.println(e);
    			getElementByLocator(By.cssSelector(
    					"table.chart-markup-table tr:nth-child(1) td:nth-child(2) canvas:nth-child(2)"));
    			
    			getElementByLocator(By.cssSelector(
						".pane-legend-item-value-container .pane-legend-item-value"));
    			
    			getElementByLocator(By.cssSelector(
    					".dl-header-price"));
    			
    			verifyElementListByLocater(By.cssSelector(
 	        			".pane-legend-item-value-container .pane-legend-item-value"));
    	}	
	}
	
	private void scanChart(Action jiggle, Action moveRightOneStep, Action moveLeftOneStep, int moveStep, String price) {
		List<Chart> chartList = new ArrayList<Chart>();
    			
			for (int i = 0; i < 3; i++) {
    				jiggle.perform();
    				List<WebElement> dataList = getElementListByLocater(
    						By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
    			
    				System.out.println(product.getSymbol()  + "," + dataList.get(5).getText()  + "," +dataList.get(6).getText()  + "," + dataList.get(11).getText());
     		
    				Chart chart = new Chart();
    				chart.setArrowGreen(Float.parseFloat(dataList.get(5).getText()));
    				chart.setArrowRed(Float.parseFloat(dataList.get(6).getText()));
    				chart.setBar(dataList.get(11).getText());
        	
    				chartList.add(chart);
        	
    				try {
    					Thread.sleep(150);
    				} catch (InterruptedException e1) {
    					// TODO Auto-generated catch block
    					System.out.println("Thread Interuption");
    					e1.printStackTrace();
    					driver.quit();
    				}
    				
    				if(i < 2) {
    					moveLeftOneStep.perform();
    				}
    				
			} 
    		
    		TradingController tradingController = new TradingController();
    		
    		if(verifyChart(moveRightOneStep, moveLeftOneStep, moveStep, chartList)) {
	            
        		tradingController.analyzeTrading(product,price,chartList, model);
    		}
    		
    		for(int s = 0; s < 2 + moveStep; s++){
    			moveRightOneStep.perform();
    		}
	}
	
	private boolean verifyChart(Action moveRightOneStep, Action moveLeftOneStep, int moveStep, List<Chart> chartList) {
		boolean flag = false;
		
		for(int s = 0; s < 2 + moveStep; s++){
			moveRightOneStep.perform();
		}
		
		List<WebElement> chartDataCurrent = getElementListByLocater(
                By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
		
		String currentBar = chartDataCurrent.get(11).getText();
		
		moveRightOneStep.perform();
		
		List<WebElement> chartDataRightOne = getElementListByLocater(
                By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
		
		String rightOneBar = chartDataRightOne.get(11).getText();

		
		moveRightOneStep.perform();
		
		List<WebElement> chartDataRightTwo = getElementListByLocater(
                By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
		
		String rightTwoBar = chartDataRightTwo.get(11).getText();

		
		moveLeftOneStep.perform();
		moveLeftOneStep.perform();
		
		for(int s = 0; s < moveStep; s++){
			moveLeftOneStep.perform();
		}
		
		List<WebElement> chartDataFirstBar = getElementListByLocater(
                By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
		
		String firstBar = chartDataFirstBar.get(11).getText();
		
		moveLeftOneStep.perform();
		
		List<WebElement> chartDataSecondBar = getElementListByLocater(
                By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
		
		String secondBar = chartDataSecondBar.get(11).getText();
		
		moveLeftOneStep.perform();
		
		List<WebElement> chartDataThirdBar = getElementListByLocater(
                By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
		
		String thirdBar = chartDataThirdBar.get(11).getText();
		
		String barOne = chartList.get(0).getBar();
		String barTwo = chartList.get(1).getBar();
		String barThree = chartList.get(2).getBar();
		
		if((currentBar.equals(rightOneBar) && currentBar.equals(rightTwoBar))
				&& (firstBar.equals(barOne) && secondBar.equals(barTwo) && thirdBar.equals(barThree))) {
			flag = true;
		}
		
		System.out.println("current bar : " + currentBar);
		System.out.println("rightOne bar : " + rightOneBar);
		System.out.println("rightTwo bar : " + rightTwoBar);
		
		System.out.println("first  bar : " + firstBar);
		System.out.println("One bar : " + barOne);
		System.out.println("Two bar : " + barTwo);
		System.out.println("Three bar : " + barThree);

		return flag;
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
        		dataListCurrent = getElementListByLocater(
                        By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
        		
        		double barCurrrent = Double.parseDouble(dataListCurrent.get(11).getText());
            	
            	moveRightOneStep.perform();
            	
            	dataListRight = getElementListByLocater(
                        By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
            	
            	double barRight = Double.parseDouble(dataListRight.get(11).getText());
            	
            	moveRightOneStep.perform();
            	
            	dataListRight = getElementListByLocater(
                        By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
            	
            	double barRightTwo = Double.parseDouble(dataListRight.get(11).getText());
            	
            	moveLeftOneStep.perform();
            	moveLeftOneStep.perform();
            	moveLeftOneStep.perform();
            	
            	dataListLeft = getElementListByLocater(
                        By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
            
            	double barLeft = Double.parseDouble(dataListLeft.get(11).getText());
            	
            	moveRightOneStep.perform();
            	
        		if(barCurrrent != barRight || barCurrrent != barRightTwo) {
        			while(barCurrrent != barRight || barCurrrent != barRightTwo) {
        				moveRightOneStep.perform();
        				
        				dataListCurrent  = getElementListByLocater(
        		                By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
        				barCurrrent = Double.parseDouble(dataListCurrent.get(11).getText());
        				
        				moveRightOneStep.perform();
        			
        				dataListRight  = getElementListByLocater(
        		                By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
        				
        				barRight = Double.parseDouble(dataListRight.get(11).getText());
        				
        				moveRightOneStep.perform();
            			
        				dataListRight  = getElementListByLocater(
        		                By.cssSelector(".pane-legend-item-value-container .pane-legend-item-value"));
        				
        				barRightTwo = Double.parseDouble(dataListRight.get(11).getText());
        				
        				moveLeftOneStep.perform();
        			}
        			//exFirstBar = null;
        			//moveLeftOneStep.perform();
        		}else {
        			if(barCurrrent == barLeft) {
        				/*exFirstBar = null;*/
        				moveLeftOneStep.perform();
        				try {
        					Thread.sleep(1000 * 1);
        					flag = false;
        				} catch (InterruptedException e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        					flag = false;
        					driver.quit();
        				}
        			}else{
        				if(barCurrrent == barRight) {
        					flag = true;
        				}
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
	
	
	public List<WebElement> getElementListByLocater(final By locator){
		final long startTime = System.currentTimeMillis();
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			    .withTimeout(30, TimeUnit.SECONDS)
			    .pollingEvery(5, TimeUnit.SECONDS)
			    .ignoring( StaleElementReferenceException.class ) ;
		
		boolean found = false;
		List<WebElement> weList = null;
		
		 while ( (System.currentTimeMillis() - startTime) < 91000 ) {
			  // LOGGER.info( "Searching for element. Try number " + (tries++) ); 
			   try {
				   
				   Function<WebDriver, List<WebElement>> function = new Function<WebDriver, List<WebElement>>() {
						public List<WebElement> apply(WebDriver webDriver) {
							List<WebElement> dataContainers  = webDriver.findElements(locator);
	     	                return dataContainers;
						}
					};
					
					wait.until( ExpectedConditions.presenceOfElementLocated( locator ) );
					weList = wait.until(function);
					found = true;
					break;
			   } catch ( StaleElementReferenceException e ) {      
				   //LOGGER.info( "Stale element: \n" + e.getMessage() + "\n");
			   }
			  }

		 
		return weList;
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
	
	
	public Boolean verifyScanLine(final Action moveLeftOneStep, final Action moveRightOneStep) {
		boolean flag = false;
		final long startTime = System.currentTimeMillis();
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				   .withTimeout(30, TimeUnit.SECONDS)
				   .pollingEvery(5, TimeUnit.SECONDS)
				   .ignoring( StaleElementReferenceException.class ) ;
		
		while ( (System.currentTimeMillis() - startTime) < 91000 ) {
			  // LOGGER.info( "Searching for element. Try number " + (tries++) ); 
			   try {
				   Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
						public Boolean apply(WebDriver webDriver) {
	     	                return placeScanLine(moveLeftOneStep, moveRightOneStep);
						}
					};
					wait.until(function);
					flag = true;
					break;
			   } catch ( StaleElementReferenceException e ) {      
			    //LOGGER.info( "Stale element: \n" + e.getMessage() + "\n");
			   }
		}
		
		return flag;
	}
	
	
}
