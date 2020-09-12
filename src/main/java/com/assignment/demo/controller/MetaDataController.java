package com.assignment.demo.controller;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.assignment.demo.DBConnection;

@Controller
@RequestMapping(value = "metadata")
public class MetaDataController {

	@RequestMapping(name = "/tables",method = RequestMethod.POST)
	@ResponseBody
	public void createTables() throws SQLException {
		
		Connection conn = DBConnection.getInstance().getConnection();
		Statement statement = conn.createStatement();

		if (!doesTableExists("customers", conn)) {
			String sql = "CREATE TABLE customers (id int primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name varchar(256))";
			statement.execute(sql);
			System.out.println("Created table customer.");
		}

		if (!doesTableExists("address", conn)) {
			String sql = "CREATE TABLE address (id int primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), street varchar(256),city varchar(256), customer_id int, FOREIGN KEY (customer_id) REFERENCES customers(id))";
			statement.execute(sql);
			System.out.println("Created table address.");
		}

		if (!doesTableExists("orders", conn)) {
			String sql = "CREATE TABLE orders (id int primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), customer_id int,amount REAL,created TIMESTAMP, FOREIGN KEY (customer_id) REFERENCES customers(id))";
			statement.execute(sql);
			System.out.println("Created table orders.");
		}

		if (!doesTableExists("items", conn)) {
			String sql = "CREATE TABLE items (id int primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name varchar(128), description varchar(512))";
			statement.execute(sql);
			System.out.println("Created table items.");
		}

		if (!doesTableExists("items_orders", conn)) {
			String sql = "CREATE TABLE items_orders (order_id int , item_id int, FOREIGN KEY (order_id) REFERENCES orders(id),FOREIGN KEY (item_id) REFERENCES items(id))";
			statement.execute(sql);
			System.out.println("Created table items orders.");
		}
	}
	
	private static boolean doesTableExists(String tableName, Connection conn) throws SQLException {
		DatabaseMetaData meta = conn.getMetaData();
		ResultSet result = meta.getTables(null, null, tableName.toUpperCase(), null);

		return result.next();
	}
}