package com.jobpoint.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jobpoint.model.Product;
import com.jobpoint.model.State;
import com.jobpoint.model.Trading;


public class CRUDTrading {
	private Connection connection;
	private Statement statement;
	
	public Trading findById(int id) {
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			statement = connection.createStatement();
			
			Trading trading = new Trading();
			
			String query = "Select * from trading where id = " + id;
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				trading.setId(resultSet.getInt("id"));
				trading.setProductId(resultSet.getInt("product_id"));
				trading.setEnterPrice(resultSet.getString("enter_price"));
				trading.setEnterDate(resultSet.getString("enter_date"));
				trading.setEnterTime(resultSet.getString("enter_time"));
				trading.setExitPrice(resultSet.getString("exit_price"));
				trading.setExitDate(resultSet.getString("exit_date"));
				trading.setExitTime(resultSet.getString("exit_time"));
				trading.setProfit(resultSet.getString("profit"));
				trading.setFirstBarEnter(resultSet.getString("first_bar_enter"));
				trading.setSecondBarEnter(resultSet.getString("second_bar_enter"));
				trading.setThirdBarEnter(resultSet.getString("third_bar_enter"));
				trading.setFirstBarExit(resultSet.getString("first_bar_exit"));
				trading.setSecondBarExit(resultSet.getString("second_bar_exit"));
				trading.setThirdBarExit(resultSet.getString("third_bar_exit"));
				trading.setSituation(resultSet.getBoolean("situation"));
				
			}

			if(statement != null) statement.close();
			if(connection != null) connection.close();
			
			return trading;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public List<Trading> findTopFive(String object, int foreignId, boolean situation){
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			statement = connection.createStatement();
		
			String query = "Select id, product_id, first_bar_enter, second_bar_enter, third_bar_enter, "
					+ " first_bar_exit, second_bar_exit, third_bar_exit, situation "
					+ " from trading where " + object + "_id = " + foreignId + " AND situation = " + situation 
					+ " ORDER BY id DESC FETCH NEXT 5 ROWS ONLY";
			//"SELECT * FROM trading where product_id = 801 AND situation = false  ORDER BY id DESC FETCH NEXT 5 ROWS ONLY
			ResultSet resultSet = statement.executeQuery(query);
		
			List<Trading> tradingList = new ArrayList<Trading>();
		
			while(resultSet.next()) {
				Trading trading = new Trading();
				trading.setId(resultSet.getInt("id"));
				trading.setProductId(resultSet.getInt("product_id"));
				trading.setFirstBarEnter(resultSet.getString("first_bar_enter"));
				trading.setSecondBarEnter(resultSet.getString("second_bar_enter"));
				trading.setThirdBarEnter(resultSet.getString("third_bar_enter"));
				trading.setFirstBarExit(resultSet.getString("first_bar_exit"));
				trading.setSecondBarExit(resultSet.getString("second_bar_exit"));
				trading.setThirdBarExit(resultSet.getString("third_bar_exit"));
				trading.setSituation(resultSet.getBoolean("situation"));
			
				tradingList.add(trading);
			}

			if(statement != null) statement.close();
			if(connection != null) connection.close();
		
			return tradingList;
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public List<Trading> findByForeignId(String object, int foreignId) {
	
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			statement = connection.createStatement();
		
		
		
			String query = "Select * from trading where " + object + "_id = " + foreignId;
			ResultSet resultSet = statement.executeQuery(query);
		
			List<Trading> tradingList = new ArrayList<Trading>();
		
			while(resultSet.next()) {
				Trading trading = new Trading();
				trading.setId(resultSet.getInt("id"));
				trading.setProductId(resultSet.getInt("product_id"));
				trading.setEnterPrice(resultSet.getString("enter_price"));
				trading.setEnterDate(resultSet.getString("enter_date"));
				trading.setEnterTime(resultSet.getString("enter_time"));
				trading.setExitPrice(resultSet.getString("exit_price"));
				trading.setExitDate(resultSet.getString("exit_date"));
				trading.setExitTime(resultSet.getString("exit_time"));
				trading.setProfit(resultSet.getString("profit"));
				trading.setFirstBarEnter(resultSet.getString("first_bar_enter"));
				trading.setSecondBarEnter(resultSet.getString("second_bar_enter"));
				trading.setThirdBarEnter(resultSet.getString("third_bar_enter"));
				trading.setFirstBarExit(resultSet.getString("first_bar_exit"));
				trading.setSecondBarExit(resultSet.getString("second_bar_exit"));
				trading.setThirdBarExit(resultSet.getString("third_bar_exit"));
				trading.setSituation(resultSet.getBoolean("situation"));
			
				tradingList.add(trading);
			}

			if(statement != null) statement.close();
			if(connection != null) connection.close();
		
			return tradingList;
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
	
	}

	public List<Trading> findAll() throws SQLException {
		connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
		statement = connection.createStatement();
	
		String query = "Select * from trading where 1 = 1";

		ResultSet resultSet;
	
		try {
			resultSet = statement.executeQuery(query);			
			List<Trading> tradingList = new ArrayList<Trading>();
		
			while(resultSet.next()) {
				Trading trading = new Trading();
				trading.setId(resultSet.getInt("id"));
				trading.setProductId(resultSet.getInt("product_id"));
				trading.setEnterPrice(resultSet.getString("enter_price"));
				trading.setEnterDate(resultSet.getString("enter_date"));
				trading.setEnterTime(resultSet.getString("enter_time"));
				trading.setExitPrice(resultSet.getString("exit_price"));
				trading.setExitDate(resultSet.getString("exit_date"));
				trading.setExitTime(resultSet.getString("exit_time"));
				trading.setProfit(resultSet.getString("profit"));
				trading.setFirstBarEnter(resultSet.getString("first_bar_enter"));
				trading.setSecondBarEnter(resultSet.getString("second_bar_enter"));
				trading.setThirdBarEnter(resultSet.getString("third_bar_enter"));
				trading.setFirstBarExit(resultSet.getString("first_bar_exit"));
				trading.setSecondBarExit(resultSet.getString("second_bar_exit"));
				trading.setThirdBarExit(resultSet.getString("third_bar_exit"));
				trading.setSituation(resultSet.getBoolean("situation"));
			
				tradingList.add(trading);
			}
		
			if(statement != null) statement.close();
			if(connection != null) connection.close();
		
			return tradingList;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
	}

	public int insert(Trading trading) {
		int id = 0;
	
		String query = "insert into trading (product_id, enter_price, enter_date, enter_time, exit_price, exit_date, " 
						+ "exit_time, profit, situation, first_bar_enter, second_bar_enter, third_bar_enter, "
						+ "first_bar_exit, second_bar_exit, third_bar_exit) values " +
						"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
		
			//statement = connection.createStatement(); 
			PreparedStatement preparedStatement;
		
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, trading.getProductId());
			preparedStatement.setString(2, trading.getEnterPrice());
			preparedStatement.setString(3, trading.getEnterDate());
			preparedStatement.setString(4, trading.getEnterTime());
			preparedStatement.setString(5, trading.getExitPrice());
			preparedStatement.setString(6, trading.getExitDate());
			preparedStatement.setString(7, trading.getExitTime());
			preparedStatement.setString(8, trading.getProfit());
			preparedStatement.setBoolean(9, trading.getSituation());
			preparedStatement.setString(10, trading.getFirstBarEnter());
			preparedStatement.setString(11, trading.getSecondBarEnter());
			preparedStatement.setString(12, trading.getThirdBarEnter());
			preparedStatement.setString(13, trading.getFirstBarExit());
			preparedStatement.setString(14, trading.getSecondBarExit());
			preparedStatement.setString(15, trading.getThirdBarExit());
			

			preparedStatement.executeUpdate();
		
			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			if(resultSet.next()) {
				id = resultSet.getInt(1);
			}
		
			if(preparedStatement != null) preparedStatement.close();
			if(connection != null) connection.close();
			
		} catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;	
	}
	
	public void update( int id, Trading trading) {
		String query = "Update trading Set product_id =  ?, " +
				"enter_price = ?, " +
				"enter_date = ?," +
				"enter_time = ?, " +
				"exit_price = ?, " +
				"exit_date = ?," +
				"exit_time = ?, " +
				"profit = ?, " +
				"situation = ?, " +
				"first_bar_enter = ?, " +
				"second_bar_enter = ?, " +
				"third_bar_enter = ?, " +
				"first_bar_exit = ?, " +
				"second_bar_exit = ?, " +
				"third_bar_exit = ? " +
				"where id = " + id;
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			PreparedStatement preparedStatement;
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, trading.getProductId());
			preparedStatement.setString(2, trading.getEnterPrice());
			preparedStatement.setString(3, trading.getEnterDate());
			preparedStatement.setString(4, trading.getEnterTime());
			preparedStatement.setString(5, trading.getExitPrice());
			preparedStatement.setString(6, trading.getExitDate());
			preparedStatement.setString(7, trading.getExitTime());
			preparedStatement.setString(8, trading.getProfit());
			preparedStatement.setBoolean(9, trading.getSituation());
			preparedStatement.setString(10, trading.getFirstBarEnter());
			preparedStatement.setString(11, trading.getSecondBarEnter());
			preparedStatement.setString(12, trading.getThirdBarEnter());
			preparedStatement.setString(13, trading.getFirstBarExit());
			preparedStatement.setString(14, trading.getSecondBarExit());
			preparedStatement.setString(15, trading.getThirdBarExit());
			preparedStatement.executeUpdate();

			if(preparedStatement != null) preparedStatement.close();
			if(connection != null) connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
