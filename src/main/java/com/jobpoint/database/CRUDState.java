package com.jobpoint.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jobpoint.model.State;
import com.jobpoint.model.Trading;

public class CRUDState {
	
	private Connection connection;
	private Statement statement;
	
	public State findLastRow(String object, int foreign_id) {
		
		State state = new State();
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			statement = connection.createStatement();
			
			String query = "Select * from state where " + object +"_id = " + foreign_id + " ORDER BY id DESC FETCH FIRST ROW ONLY";
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				state.setId(resultSet.getInt("id"));
				state.setProductId(resultSet.getInt("product_id"));
				state.setTradingId(resultSet.getInt("trading_id"));
				state.setIsEnter(resultSet.getBoolean("isEnter"));
				state.setEnterPrice(resultSet.getString("enter_price"));
				state.setType(resultSet.getString("type"));
			}

			if(statement != null) statement.close();
			if(connection != null) connection.close();
			return state;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public int insert(State state) {
		int id = 0;
	
		String query = "insert into state (product_id, trading_id, isEnter, enter_price, type) values " +
						"(?, ?, ?, ?, ?)";

	
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
		
			//statement = connection.createStatement(); 
			PreparedStatement preparedStatement;
		
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, state.getProductId());
			preparedStatement.setInt(2, state.getTradingId());
			preparedStatement.setBoolean(3, state.getIsEnter());
			preparedStatement.setString(4, state.getEnterPrice());
			preparedStatement.setString(5, state.getType());

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
	
	public void update( int id, State state) {
		String query = "Update state Set product_id =  ?, " +
				"trading_id = ?, " +
				"isEnter = ?," +
				"enter_price = ?, " +
				"type = ? " +
				"where id = " + id;
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			PreparedStatement preparedStatement;
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, state.getProductId());
			preparedStatement.setInt(2, state.getTradingId());
			preparedStatement.setBoolean(3, state.getIsEnter());
			preparedStatement.setString(4, state.getEnterPrice());
			preparedStatement.setString(5, state.getType());
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
