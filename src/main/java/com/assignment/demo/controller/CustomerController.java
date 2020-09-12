package com.assignment.demo.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.assignment.demo.DBConnection;

@Controller
@RequestMapping(value = "customer")
public class CustomerController {

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String createCustomer(@RequestParam List<String> names) throws SQLException {
		System.out.println("First creating customer. It has address which is one-to-one related with user");
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement statement = conn.prepareStatement("INSERT INTO customers (name) VALUES (?)");
		for (String string : names) {
			statement.setString(1, string);
			Integer noOfRecords = statement.executeUpdate();
			System.out.println("Number of records inserted :- "+noOfRecords);
		}
		return "Successful";
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Integer> fetchCustomers() throws SQLException {
		System.out.println("Fetching customers");
		List<Integer> customerIds = new ArrayList<Integer>();
		Connection conn = DBConnection.getInstance().getConnection();
		Statement statement = conn.createStatement();
		String sql = "SELECT * FROM customers";
		ResultSet result = statement.executeQuery(sql);
		ResultSetMetaData rsmd = result.getMetaData();
		int numberCols = rsmd.getColumnCount();
		for (int i = 1; i <= numberCols; i++) {
			System.out.print(rsmd.getColumnLabel(i) + "\t\t");
		}
		System.out.println("\n-------------------------------------------------");
		while (result.next()) {
			customerIds.add(result.getInt("id"));
			System.out.println(result.getInt("id") + "\t\t" + result.getString("name"));
		}
		return customerIds;
	}
}
