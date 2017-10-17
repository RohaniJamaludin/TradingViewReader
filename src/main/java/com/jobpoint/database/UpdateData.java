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
				+ "exit_price =  '1306.5',"  
				+ "exit_date = '16/10/2017',"  
				+ "exit_time = '14:51' ," 
				+ "profit = '0' "
				+ "where id = 601 ");*/
		connection.createStatement().execute("Update state Set  trading_id =  0," + 
				"isEnter = false," + 
				"type = '' ," + 
				"enter_price = '' " + 
				"where id = 801 ");
		
		if(connection != null) connection.close();
		
		System.out.println("records successfully updated ....");
	}

}
