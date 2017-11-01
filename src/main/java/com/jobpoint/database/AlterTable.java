package com.jobpoint.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class AlterTable {
	
	public static final String JDBC_URL = "jdbc:derby:tradingdb;create=true";
	public static final String SQL_STATEMENT = "ALTER TABLE APP.TRADING ADD third_bar_exit varchar(20)";
	/*		+ "second_bar_enter double, "
			+ "third_bar_enter double, "
			+ "first_bar_exit double, "
			+ "second_bar_exit double, "
			+ "third_bar_exit double";*/
		
	//public static final String SQL_STATEMENT = "select * from product where id = 601";
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = DriverManager.getConnection(JDBC_URL);
		Statement statement = connection.createStatement();
		connection.createStatement().execute(SQL_STATEMENT);
		
		if(statement != null) statement.close();
		if(connection != null) connection.close();
		
		System.out.println("records successfully inserted ....");
			

	}

}
