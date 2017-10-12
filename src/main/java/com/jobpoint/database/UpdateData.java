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
/*				+ "exit_price =  'n/a',"  
				+ "exit_date = 'n/a',"  
				+ "exit_time = 'n/a' ," */
				+ "profit = '-88' "
				+ "where id = 101 ");
		/*connection.createStatement().execute("Update state Set  trading_id =  101," + 
				"isEnter = true," + 
				"type = 'buy' ," + 
				"enter_price = '28435' " + 
				"where id = 1 ");
		*/
		if(connection != null) connection.close();
		
		System.out.println("records successfully updated ....");
	}

}
