package com.assignment.demo;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.assignment.demo.Models.Item;
import com.assignment.demo.controller.AddressController;
import com.assignment.demo.controller.CustomerController;
import com.assignment.demo.controller.ItemController;
import com.assignment.demo.controller.MetaDataController;
import com.assignment.demo.controller.OrderController;
import com.assignment.demo.controller.OrderItemController;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoApplication.class, args);

		deleteTables();

		MetaDataController metadata = new MetaDataController();
		metadata.createTables();

		// Creating customer
		System.out.println("Create customer API :- POST http://localhost:8080/customer?names={name of customer}");
		CustomerController cont = new CustomerController();
		List<String> names = new ArrayList<String>();
		names.add("Sachin Tendulkar");
		names.add("Saurav Ganguly");
		cont.createCustomer(names);
		System.out.println("");
		TimeUnit.SECONDS.sleep(5);

		// fetching customer
		System.out.println("Fetch customer API :- GET http://localhost:8080/customer");
		List<Integer> customerIds = cont.fetchCustomers();
		System.out.println("");
		TimeUnit.SECONDS.sleep(5);

		// creating address for customer
		System.out.println("Create address API :- POST http://localhost:8080/address?street={insert text here}&city={insert city here}&customerId={Insert customer Id}");
		AddressController add = new AddressController();
		add.createAddressForCustomer("C.S.Road", "Mumbai", customerIds.get(0));
		System.out.println("");
		TimeUnit.SECONDS.sleep(5);
		System.out.println("Fetch address API :- GET http://localhost:8080/address?customerId={insert customer id}");
		add.fetchAddressForCustomer(customerIds.get(0));
		System.out.println("");
		TimeUnit.SECONDS.sleep(5);
		
		// create item
		System.out.println(
				"Create item API :- POST http://localhost:8080/item?name={insert item name here}&description={replace item description here}");
		ItemController item = new ItemController();
		item.createItem("Lays Chips", " Lay's owned by Pepsico");

		TimeUnit.SECONDS.sleep(5);
		item.createItem("Dairy Milk", "Cadbury owned by Mondelez");

		System.out.println("");
		TimeUnit.SECONDS.sleep(5);
		
		//fetch item
		System.out.println("Fetch item API :- GET http://localhost:8080/item");
		List<Item> itemIds = item.fetchItems();
		System.out.println("");
		TimeUnit.SECONDS.sleep(5);
		System.out.println("");
		
		// create order
		System.out.println(
				"Create order API :- POST http://localhost:8080/order?customerId={replace customer id here}&amount={replace order amount here}");
		OrderController order = new OrderController();
		order.createOrder(customerIds.get(0), 100f);
		System.out.println("");
		TimeUnit.SECONDS.sleep(5);
		
		// fetch order
		System.out.println("Fetch order API :- GET http://localhost:8080/order");
		List<Integer> orderIds = order.fetchOrders();
		System.out.println("");
		TimeUnit.SECONDS.sleep(5);
		
		// create order item mapping
		System.out.println(
				"Create order item API :- POST http://localhost:8080/orderitem?orderId={replace orderid here}&itemId={replace itemId here}");
		OrderItemController oi = new OrderItemController();
		oi.createOrderItemMapping(orderIds.get(0), itemIds.get(0).getId());
		System.out.println("");
		TimeUnit.SECONDS.sleep(5);
		System.out
				.println("Fetch order item API :- GET http://localhost:8080/orderitem?orderId={replace orderid here}");
		oi.fetchOrderItem(orderIds.get(0));
		System.out.println("");
		System.out.println("Thanks you for watching the demo");

	}

	private static void deleteTables() throws Exception {
		Connection conn = DBConnection.getInstance().getConnection();
		Statement statement = conn.createStatement();
		String sql = "drop table address";
		try {
			statement.execute(sql);
		} catch (Exception e) {

		}

		sql = "drop table items_orders";
		try {
			statement.execute(sql);
		} catch (Exception e) {

		}
		sql = "drop table orders";
		try {
			statement.execute(sql);
		} catch (Exception e) {

		}
		sql = "drop table customers";
		try {
			statement.execute(sql);
		} catch (Exception e) {

		}
		sql = "drop table items";
		try {
			statement.execute(sql);
		} catch (Exception e) {

		}
	}

}
