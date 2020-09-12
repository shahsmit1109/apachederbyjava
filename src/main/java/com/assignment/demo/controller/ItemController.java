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
import com.assignment.demo.Models.Item;

@Controller
@RequestMapping(value = "items")
public class ItemController {

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String createItem(@RequestParam String name,@RequestParam String description) throws SQLException {
		System.out.println("Creating items");
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement statement = conn.prepareStatement("INSERT into items (name,description) values (?,?)");
		statement.setString(1, name);
		statement.setString(2, description); 
		Integer noOfRecords = statement.executeUpdate();
		System.out.println("Number of records inserted :- "+noOfRecords);
		return "Successful";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Item> fetchItems() throws SQLException {
		System.out.println("Fetching Items");
		List<Item> itemIds = new ArrayList<Item>();
		Connection conn = DBConnection.getInstance().getConnection();
		Statement statement = conn.createStatement();
		String sql = "SELECT * FROM items";
		ResultSet result = statement.executeQuery(sql);
		ResultSetMetaData rsmd = result.getMetaData();
		int numberCols = rsmd.getColumnCount();
		for (int i = 1; i <= numberCols; i++) {
			System.out.print(rsmd.getColumnLabel(i) + "\t\t");
		}
		System.out.println("\n-------------------------------------------------");
        while (result.next()) {
        	itemIds.add(new Item(result.getInt("id"),result.getString("name"),result.getString("description")));
        	System.out.println(result.getInt("id") + "\t\t" + result.getString("name")+ "\t\t" + result.getString("description"));
        }
		return itemIds;
	}
}