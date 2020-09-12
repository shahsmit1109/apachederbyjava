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
@RequestMapping(value = "address")
public class AddressController {

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String createAddressForCustomer(@RequestParam String street,@RequestParam String city,@RequestParam Integer customerId) throws SQLException {
		System.out.println("Creating address");
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement statement = conn.prepareStatement("INSERT into address (street,city,customer_id) values (?,?,?)");
		statement.setString(1, street);
		statement.setString(2, city); 
		statement.setInt(3, customerId);
		Integer noOfRecords = statement.executeUpdate();
		System.out.println("Number of records inserted :- "+noOfRecords);
		return "Successful";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String fetchAddressForCustomer(@RequestParam Integer customerId) throws SQLException {
		System.out.println("Fetching address for customer");
		Connection conn = DBConnection.getInstance().getConnection();
		Statement statement = conn.createStatement();
		String sql = "SELECT customers.id,customers.name,address.street,address.city FROM address,customers where address.customer_id = customers.id and address.customer_id = "+customerId;
		ResultSet result = statement.executeQuery(sql);
		ResultSetMetaData rsmd = result.getMetaData();
		int numberCols = rsmd.getColumnCount();
		for (int i = 1; i <= numberCols; i++) {
			System.out.print(rsmd.getColumnLabel(i) + "\t\t");
		}
		System.out.println("\n-------------------------------------------------"); 
        while (result.next()) {
        	System.out.println(result.getInt("id") + "\t\t" + result.getString("name") + "\t\t" + result.getString("street") + "\t\t" + result.getString("city"));
        }
		return "Successful";
	}
}