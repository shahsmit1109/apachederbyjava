package com.assignment.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	 private static DBConnection instance;
	    private Connection connection;
	    private String url = "jdbc:derby:ecommercedb;create=true";
	    private String username = "";
	    private String password = "";

	    private DBConnection() throws SQLException {
	        try {
	            this.connection = DriverManager.getConnection(url);
	        } catch (Exception ex) {
	            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
	        }
	    }

	    public Connection getConnection() {
	        return connection;
	    }

	    public static DBConnection getInstance() throws SQLException {
	        if (instance == null) {
	            instance = new DBConnection();
	        } else if (instance.getConnection().isClosed()) {
	            instance = new DBConnection();
	        }

	        return instance;
	    }
	
}
