package com.jobpoint.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jobpoint.model.Bar;

public class CRUDBar {
	
	private Connection connection;
	private Statement statement;
	
	public Bar findByForeignId(String object, int foreignId) {
		
		Bar bar = new Bar();
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			statement = connection.createStatement();
			
			String query = "Select * from bar where " + object +"_id = " + foreignId + " ORDER BY id DESC FETCH FIRST ROW ONLY";
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				bar.setId(resultSet.getInt("id"));
				bar.setProductId(resultSet.getInt("product_id"));
				bar.setValue(resultSet.getString("value"));
				bar.setDate(resultSet.getString("date"));
				bar.setTime(resultSet.getString("time"));
			}

			if(statement != null) statement.close();
			if(connection != null) connection.close();
			return bar;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void insert(Bar bar) {
		
		String query = "insert into bar (product_id, value, date, time) values " +
				"(?, ?, ?, ?)";

		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);

			//statement = connection.createStatement(); 
			PreparedStatement preparedStatement;

			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, bar.getProductId());
			preparedStatement.setString(2, bar.getValue());
			preparedStatement.setString(3, bar.getDate());
			preparedStatement.setString(4, bar.getTime());

			preparedStatement.executeUpdate();

			if(preparedStatement != null) preparedStatement.close();
			if(connection != null) connection.close();
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update( int id, Bar bar) {
		String query = "Update bar Set  " +
				"product_id = ?, " +
				"value = ?," +
				"date = ?, " +
				"time = ? " +
				"where id = " + id;
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			PreparedStatement preparedStatement;
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, bar.getProductId());
			preparedStatement.setString(2, bar.getValue());
			preparedStatement.setString(3, bar.getDate());
			preparedStatement.setString(4, bar.getTime());
			preparedStatement.executeUpdate();
			
/*			statement = connection.createStatement();
			statement.execute(query);*/
			
			if(preparedStatement != null) preparedStatement.close();
			if(connection != null) connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
