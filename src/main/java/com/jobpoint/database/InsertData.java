package com.jobpoint.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InsertData {
	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_URL = "jdbc:derby:tradingdb;create=true";

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		//Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		connection.createStatement().execute("insert into trading (product_id, enter_price, exit_price, profit, enter_date, enter_time, exit_date, exit_time, situation, "
				+ "first_bar_enter, second_bar_enter, third_bar_enter, first_bar_exit, second_bar_exit, third_bar_exit ) values " +
	/*			"(101, '971'0', '967'4', '3'/6', '25/09/2017', '23:27', '30/09/2017',  '00:06', false), " +
				"(101, '975'4', '959'2', '-16'2', '30/09/2017', '01:12', '02/10/2017',  '21:36', true), " +
				"(101, '967'6', '964'4', '-3.2', '05/10/2017', '22:27', '06/10/2017',  '22:12', true), " +
				"(801, '1273.3', '1273.6', '-0.3', '03/10/2017', '18:31', '03/10/2017',  '18:36', false), " +
				"(801, '1274.0', '1274.0', '0.0', '03/10/2017', '18:42', '03/10/2017',  '18:51', true), " +
				"(801, '1277.0', '1281.9', '4.9', '03/10/2017', '23:06', '04/10/2017',  '20:18', true), " +
				"(801, '1278.1', '1278.1', '0.0', '04/10/2017', '21:04', '05/10/2017',  '13:21', false), " +
				"(801, '1280.8', '1276.3', '-4.5', '05/10/2017', '16:48', '05/10/2017',  '22:42', true), " +
				"(801, '1274.6', '1274.7', '0.1', '06/10/2017', '00:03', '06/10/2017',  '00:03', true), " +
				"(801, '1266.0', '1282.4', '16.4', '06/10/2017', '20:45', '09/10/2017',  '09:43', true), " +
				"(901, '2.940', '2.945', '0.005', '04/10/2017', '20:18', '04/10/2017',  '23:04', true), " +
				"(901, '2.954', '2.937', '-0.017', '04/10/2017', '23:29', '06/10/2017',  '00:21', true), " +*/
				"(801, '0', '0', '0', '30/10/2017', '15:48', '30/10/2017',  '15:48', false, '4.1854', '4.9386', '5.3286', '4.1854', '4.9386', '5.3286' ) ");
		
		System.out.println("records successfully inserted ....");
	}

}
