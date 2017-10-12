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


public class CRUDProduct {
	private Connection connection;
	private Statement statement;
	
	public Product findById(int id) {
		
		Product product = new Product();
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			statement = connection.createStatement();
			
			
			
			String query = "Select * from product where id = " + id;
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				product.setId(resultSet.getInt("id"));
				product.setName(resultSet.getString("name"));
				product.setSymbol(resultSet.getString("symbol"));
				product.setSymbolDescription(resultSet.getString("symbol_description"));
				product.setChartUrl(resultSet.getString("chart_url"));
				product.setIsMarketOpen(resultSet.getBoolean("isMarketOpen"));
				product.setIsMonday(resultSet.getBoolean("isMonday"));
				product.setIsTuesday(resultSet.getBoolean("isTuesday"));
				product.setIsWednesday(resultSet.getBoolean("isWednesday"));
				product.setIsThursday(resultSet.getBoolean("isThursday"));
				product.setIsFriday(resultSet.getBoolean("isFriday"));
				product.setIsSaturday(resultSet.getBoolean("isSaturday"));
				product.setIsSunday(resultSet.getBoolean("isSunday"));
				product.setOpenTime(resultSet.getString("open_time"));
				product.setCloseTime(resultSet.getString("close_time"));
				product.setIsTradingPause(resultSet.getBoolean("isTradingPause"));
				product.setPauseDay(resultSet.getString("pause_day"));
				product.setPauseTimeFrom(resultSet.getString("pause_time_from"));
				product.setPauseTimeTo(resultSet.getString("pause_time_to"));
				product.setStrategy(resultSet.getString("strategy"));
				product.setPtaUrl(resultSet.getString("pta_url"));
				product.setOrderType(resultSet.getString("order_type"));
				product.setLot(resultSet.getInt("lot"));
			}

			
			if(statement != null) statement.close();
			if(connection != null) connection.close();
			
			return product;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public List<Product> findAll() throws SQLException {
		connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
		statement = connection.createStatement();
		
		String query = "Select * from product where 1 = 1";

		ResultSet resultSet;
		
		try {
			resultSet = statement.executeQuery(query);			
			List<Product> productList = new ArrayList<Product>();
			
			while(resultSet.next()) {
				Product product = new Product();
				product.setId(resultSet.getInt("id"));
				product.setName(resultSet.getString("name"));
				product.setSymbol(resultSet.getString("symbol"));
				product.setSymbolDescription(resultSet.getString("symbol_description"));
				product.setChartUrl(resultSet.getString("chart_url"));
				product.setIsMarketOpen(resultSet.getBoolean("isMarketOpen"));
				product.setIsMonday(resultSet.getBoolean("isMonday"));
				product.setIsTuesday(resultSet.getBoolean("isTuesday"));
				product.setIsWednesday(resultSet.getBoolean("isWednesday"));
				product.setIsThursday(resultSet.getBoolean("isThursday"));
				product.setIsFriday(resultSet.getBoolean("isFriday"));
				product.setIsSaturday(resultSet.getBoolean("isSaturday"));
				product.setIsSunday(resultSet.getBoolean("isSunday"));
				product.setOpenTime(resultSet.getString("open_time"));
				product.setCloseTime(resultSet.getString("close_time"));
				product.setIsTradingPause(resultSet.getBoolean("isTradingPause"));
				product.setPauseDay(resultSet.getString("pause_day"));
				product.setPauseTimeFrom(resultSet.getString("pause_time_from"));
				product.setPauseTimeTo(resultSet.getString("pause_time_to"));
				product.setPauseTimeFrom(resultSet.getString("strategy"));
				product.setPauseTimeTo(resultSet.getString("pta_url"));
				product.setOrderType(resultSet.getString("order_type"));
				product.setLot(resultSet.getInt("lot"));
				
				productList.add(product);
			}
			
			if(statement != null) statement.close();
			if(connection != null) connection.close();
			
			return productList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public int insert(Product product) {
		int id = 0;
		
		String query = "insert into product (name, symbol, symbol_description, chart_url, pta_url, strategy, order_type, lot,  isMarketOpen, " +
						"isMonday, isTuesday, isWednesday, isThursday, isFriday, isSaturday, isSunday, open_time, close_time, " +
						"isTradingPause, pause_day, pause_time_from, pause_time_to) values " + 
						"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			
			//statement = connection.createStatement(); 
			PreparedStatement preparedStatement;
			
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getSymbol());
			preparedStatement.setString(3, product.getSymbolDescription());
			preparedStatement.setString(4, product.getChartUrl());
			preparedStatement.setString(5, product.getPtaUrl());
			preparedStatement.setString(6, product.getStrategy());
			preparedStatement.setString(7, product.getOrderType());
			preparedStatement.setInt(8, product.getLot());
			preparedStatement.setBoolean(9, product.getIsMarketOpen());
			preparedStatement.setBoolean(10, product.getIsMonday());
			preparedStatement.setBoolean(11, product.getIsTuesday());
			preparedStatement.setBoolean(12, product.getIsWednesday());
			preparedStatement.setBoolean(13, product.getIsThursday());
			preparedStatement.setBoolean(14, product.getIsFriday());
			preparedStatement.setBoolean(15, product.getIsSaturday());
			preparedStatement.setBoolean(16, product.getIsSunday());
			preparedStatement.setString(17, product.getOpenTime());
			preparedStatement.setString(18, product.getCloseTime());
			preparedStatement.setBoolean(19, product.getIsTradingPause());
			preparedStatement.setString(20, product.getPauseDay());
			preparedStatement.setString(21, product.getPauseTimeFrom());
			preparedStatement.setString(22, product.getPauseTimeTo());
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
	
	public void update(int id, Product product) {
		
		String query = "Update product Set name =  ?, " +
				"symbol = ?, " +
				"symbol_description = ?," +
				"chart_url = ?, " +
				"pta_url = ?, " +
				"strategy = ?, " +
				"order_type = ?, " +
				"lot = ?, " +
				"isMarketOpen = ?, " +
				"isMonday = ?, " +
				"isTuesday = ?, " +
				"isWednesday = ?, " +
				"isThursday = ?, " +
				"isFriday = ?, " +
				"isSaturday = ?, " +
				"isSunday = ?, " +
				"open_time = ?, " +
				"close_time = ?, " +
				"isTradingPause = ?, "+
				"pause_day = ?, " +
				"pause_time_from = ?, " +
				"pause_time_to = ? " +
				"where id = " + id;
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			PreparedStatement preparedStatement;
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getSymbol());
			preparedStatement.setString(3, product.getSymbolDescription());
			preparedStatement.setString(4, product.getChartUrl());
			preparedStatement.setString(5, product.getPtaUrl());
			preparedStatement.setString(6, product.getStrategy());
			preparedStatement.setString(7, product.getOrderType());
			preparedStatement.setInt(8, product.getLot());
			preparedStatement.setBoolean(9, product.getIsMarketOpen());
			preparedStatement.setBoolean(10, product.getIsMonday());
			preparedStatement.setBoolean(11, product.getIsTuesday());
			preparedStatement.setBoolean(12, product.getIsWednesday());
			preparedStatement.setBoolean(13, product.getIsThursday());
			preparedStatement.setBoolean(14, product.getIsFriday());
			preparedStatement.setBoolean(15, product.getIsSaturday());
			preparedStatement.setBoolean(16, product.getIsSunday());
			preparedStatement.setString(17, product.getOpenTime());
			preparedStatement.setString(18, product.getCloseTime());
			preparedStatement.setBoolean(19, product.getIsTradingPause());
			preparedStatement.setString(20, product.getPauseDay());
			preparedStatement.setString(21, product.getPauseTimeFrom());
			preparedStatement.setString(22, product.getPauseTimeTo());
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
	
	public boolean delete(int id) {
		String query = "Delete from product where id = " + id;
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			statement = connection.createStatement();
			statement.execute(query);
			if(statement != null) statement.close();
			if(connection != null) connection.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
