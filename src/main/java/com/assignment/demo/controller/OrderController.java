package com.assignment.demo.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.assignment.demo.DBConnection;

@Controller
@RequestMapping(value = "order")
public class OrderController {

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String createOrder(@RequestParam Integer customerId,@RequestParam Float amount) throws SQLException {
		System.out.println("Creating order");
		Connection conn = DBConnection.getInstance().getConnection();
		//Statement statement = conn.createStatement();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		PreparedStatement statement = conn.prepareStatement("INSERT into orders (customer_id,amount,created) values (?,?,?)");
		statement.setInt(1, customerId);
		statement.setFloat(2, amount); 
		statement.setTimestamp(3, timestamp);
		Integer noOfRecords = statement.executeUpdate();
		System.out.println("Number of records inserted :- "+noOfRecords);
		return "Successful";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Integer> fetchOrders() throws SQLException {
		System.out.println("Fetching orders");
		List<Integer> orderIds = new ArrayList<Integer>();
		Connection conn = DBConnection.getInstance().getConnection();
		Statement statement = conn.createStatement();
		String sql = "SELECT * FROM orders o inner join customers cust on cust.id = o.customer_id";
		ResultSet result = statement.executeQuery(sql);
		ResultSetMetaData rsmd = result.getMetaData();
		int numberCols = rsmd.getColumnCount();
		for (int i = 1; i <= numberCols; i++) {
			System.out.print(rsmd.getColumnLabel(i) + "\t\t");
		}
		System.out.println("\n-------------------------------------------------"); 
        while (result.next()) {
        	orderIds.add(result.getInt("id"));
        	System.out.println(result.getInt("customer_id") + "\t\t" + result.getString("name")+ "\t\t" + result.getDouble("amount")+ "\t\t" + result.getTime("created"));
        }
		return orderIds;
	}
}