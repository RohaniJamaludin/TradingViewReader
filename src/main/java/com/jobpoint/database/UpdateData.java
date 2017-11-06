package com.jobpoint.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UpdateData {
	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_URL = "jdbc:derby:tradingdb;create=true";

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Connection connection = DriverManager.getConnection(JDBC_URL);
/*		connection.createStatement().execute("Update trading Set  "
				+ "exit_price =  '1280.0',"  
				+ "exit_date = '02/11/2017',"  
				+ "exit_time = '09:30' ," 
				+ "profit = '0' "
				+ "where id = 2802 ");*/
		connection.createStatement().execute("Update state Set " + 
				"trading_id = 0," + 
				"isEnter = false," + 
				"type = '' ," + 
				"enter_price = '' " + 
				"where id = 1201 ");
/*		connection.createStatement().execute("Update trading Set " +
				 "first_bar_enter = '-2.9332',"  +
				 "second_bar_enter = '-4.4282',"  +
				 "third_bar_enter = '-5.7725' "  +
				 "first_bar_exit = '0.2950',"  +
				 "second_bar_exit = '-0.3904',"  +
				 "third_bar_exit = '-1.0393' "  +
				"where id = 2802 ");*/
		
		if(connection != null) connection.close();
		
		System.out.println("records successfully updated ....");
	}

}
