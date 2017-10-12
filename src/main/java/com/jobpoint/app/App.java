package com.jobpoint.app;

import java.awt.EventQueue;

import com.jobpoint.database.CreateDatabase;
import com.jobpoint.gui.MainWindow;


public class App 
{
    public static void main( String[] args )
    {
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateDatabase.startDatabase();
					new MainWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
}

