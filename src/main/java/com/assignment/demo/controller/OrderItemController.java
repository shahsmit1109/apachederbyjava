package com.assignment.demo.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.assignment.demo.DBConnection;

@Controller
@RequestMapping(value = "orderitem")
public class OrderItemController {

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String createOrderItemMapping(@RequestParam Integer orderId,@RequestParam Integer itemId) throws SQLException {
		System.out.println("Creating order item mapping");
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement statement = conn.prepareStatement("INSERT into items_orders (order_id,item_id) values (?,?)");
		statement.setInt(1, orderId);
		statement.setFloat(2, itemId); 
		Integer noOfRecords = statement.executeUpdate();
		System.out.println("Number of records inserted :- "+noOfRecords);
		return "Successful";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String fetchOrderItem(@RequestParam Integer orderId) throws SQLException {
		System.out.println("Fetching items in order");
		Connection conn = DBConnection.getInstance().getConnection();
		Statement statement = conn.createStatement();
		String sql = "SELECT io.order_id,cust.name,i.name,o.amount,o.created,i.description FROM items_orders io inner join items i on i.id = io.item_id  inner join orders o on o.id = io.order_id inner join customers cust on cust.id = o.customer_id  where order_id = "+orderId;
		ResultSet result = statement.executeQuery(sql);	
		ResultSetMetaData rsmd = result.getMetaData();
		int numberCols = rsmd.getColumnCount();
		for (int i = 1; i <= numberCols; i++) {
			System.out.print(rsmd.getColumnLabel(i) + "\t\t");
		}
		System.out.println("\n-------------------------------------------------");
        while (result.next()) {
        	System.out.println(result.getInt("order_id") + "\t\t" + result.getString("name")+ "\t\t" + result.getString("name")+ "\t\t" + result.getDouble("amount")+ "\t\t" + result.getTimestamp("created") + "\t\t" + result.getString("description"));
        }
		return "Successful";
	}
}