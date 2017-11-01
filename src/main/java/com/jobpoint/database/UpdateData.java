package com.jobpoint.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UpdateData {
	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_URL = "jdbc:derby:tradingdb;create=true";

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Connection connection = DriverManager.getConnection(JDBC_URL);
		connection.createStatement().execute("Update trading Set  "
				+ "exit_price =  ' 3.002',"  
				+ "exit_date = '30/10/2017',"  
				+ "exit_time = '22:45' ," 
				+ "profit = '0' "
				+ "where id = 2501 ");
/*		connection.createStatement().execute("Update state Set  product_id =  1001," + 
				"trading_id = 0," + 
				"isEnter = false," + 
				"type = '' ," + 
				"enter_price = '' " + 
				"where id = 1001 ");*/
/*		connection.createStatement().execute("Update trading Set " +
				 "first_bar_enter = '-2.9332',"  +
				 "second_bar_enter = '-4.4282',"  +
				 "third_bar_enter = '-5.7725' "  +
				 "first_bar_exit = ' -0.0386',"  +
				 "second_bar_exit = '-0.0495',"  +
				 "third_bar_exit = '-0.0567' "  +
				"where id = 2501 ");*/
		
		if(connection != null) connection.close();
		
		System.out.println("records successfully updated ....");
	}

}
