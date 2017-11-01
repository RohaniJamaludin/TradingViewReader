package com.jobpoint.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryData {
	
	public static final String JDBC_URL = "jdbc:derby:tradingdb;create=true";
	public static final String SQL_STATEMENT = "select * from trading";
	/*public static final String SQL_STATEMENT = "SELECT id, product_id, first_bar_enter, second_bar_enter, third_bar_enter," + 
	         " first_bar_exit, second_bar_exit, third_bar_exit FROM trading "
	         + " where product_id = 801 AND situation = false  ORDER BY id DESC FETCH NEXT 5 ROWS ONLY";*/
	
		
	//public static final String SQL_STATEMENT = "select * from product where id = 601";
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement statement = connection.createStatement();
		connection.createStatement().execute(SQL_STATEMENT);
		ResultSet resultSet = statement.executeQuery(SQL_STATEMENT); 
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		int columnCount = resultSetMetaData.getColumnCount();
		for(int x = 1; x <= columnCount; x++) {
			System.out.format("%20s", resultSetMetaData.getColumnName(x)+ " | ");
		}
		
		while(resultSet.next()) {
			System.out.println("");
			for(int x = 1; x <= columnCount; x++) {
				System.out.format("%20s", resultSet.getString(x) + " | ");
			}
		}
		
		if(statement != null) statement.close();
		if(connection != null) connection.close();
			

	}

}
