package com.jobpoint.tools;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PTA {
	private final String USER_AGENT = "Mozilla/5.0";
	
	public void sendGet(String urlPta, String symbol, String price, int lot, String orderType, String type ) throws Exception {
		int actionType;
		
		switch(orderType) {
		case  "Limit" : actionType = 0;
		break;
		case  "Market" : actionType = 2;
		break;
		default : actionType = 0;
		}
		
		
		String url = urlPta + symbol+"?price="+price+"&lot_size="+lot+"&action=0&action_type="+actionType+"&type="+type;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}
	public static void main( String[] args )
    {
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PTA pta = new PTA();
					pta.sendGet("http://127.0.0.1:8872/", "HSI" , "1.00", 2, "Limit", "0" );
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }

}
