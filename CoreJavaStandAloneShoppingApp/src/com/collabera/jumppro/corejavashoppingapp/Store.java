package com.collabera.jumppro.corejavashoppingapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Store {
	
	private String myConnString = "jdbc:mysql://127.0.0.1:3306/ecommerce_java";
	private String userName = "root";
	private String password = "root";
	
	List<User> users;
	List<Item> inventory;
	List<Invoice> invoices;
	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public List<Item> getInventory() {
		return inventory;
	}
	public void setInventory(List<Item> inventory) {
		this.inventory = inventory;
	}
	public List<Invoice> getInvoices() {
		return invoices;
	}
	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}
	
	public Store() throws SQLException {
		users = getUsersFromDb();
		inventory = getInventoryFromDb();
		invoices = getInvoicesFromDb();
	}
	
	public List<User> getUsersFromDb() throws SQLException {
		List<User> dbUsers = new ArrayList<>();
		Connection myConn = null;
		Statement myStat = null;
		ResultSet rs = null;
		
		try {
			
			myConn = DriverManager.getConnection(myConnString, userName, password);
			
			myStat = myConn.createStatement();
			
			rs = myStat.executeQuery("select * from users");
			
			while (rs.next()) {
				User temp = new User();
				temp.setFirstName(rs.getString("f_name"));
				temp.setLastName(rs.getString("l_name"));
				temp.setUserId(rs.getInt("user_id"));
				temp.setEmail(rs.getString("email"));
				temp.setPassword(rs.getString("password"));
				temp.setCredit(rs.getDouble("credit"));
				
				dbUsers.add(temp);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if(myConn != null) {
				myConn.close();
			}
			if (myStat != null) {
				myStat.close();
			}
		}
		return dbUsers;
		
	}
	
	public List<Item> getInventoryFromDb() throws SQLException {
		List<Item> dbInventory = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStat = null;
		ResultSet rs = null;
		
		try {
			
			myConn = DriverManager.getConnection(myConnString, userName, password);
			
			myStat = myConn.createStatement();
			
			rs = myStat.executeQuery("select * from items");
			
			while (rs.next()) {
				Item temp = new Item();
				temp.setItemName(rs.getString("item_name"));
				temp.setItemCode(rs.getString("item_code"));
				temp.setQuantity(rs.getInt("quantity"));
				temp.setPrice(rs.getDouble("price"));
				
				dbInventory.add(temp);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if(myConn != null) {
				myConn.close();
			}
			if (myStat != null) {
				myStat.close();
			}
		}
		
		
		return dbInventory;
	}
	
	public List<Invoice> getInvoicesFromDb() throws SQLException {
		List<Invoice> dbInvoices = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStat = null;
		ResultSet rs = null;
		
		try {
			
			myConn = DriverManager.getConnection(myConnString, userName, password);
			
			myStat = myConn.createStatement();
			
			rs = myStat.executeQuery("select * from invoices");
			
			while (rs.next()) {
				Invoice temp = new Invoice();
				temp.setUserId(rs.getInt("user_id"));
				temp.setInvoiceId(rs.getInt("invoice_id"));
				temp.setInvoiceDate(rs.getDate("created_date"));
				temp.setTotalCost(rs.getDouble("total_cost"));
				temp.setItemList(getItemListByInvoice(rs.getInt("invoice_id")));
				dbInvoices.add(temp);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if(myConn != null) {
				myConn.close();
			}
			if (myStat != null) {
				myStat.close();
			}
		}
		
		return dbInvoices;
	}
	
	public List<Item> getItemListByInvoice(int invoiceId) throws SQLException {
		List<Item> itemsByInvoice = new ArrayList<>();
		
		Connection myConn = null;
		PreparedStatement myStat = null;
		ResultSet rs = null;
		
		try {
			
			myConn = DriverManager.getConnection(myConnString, userName, password);
			
			myStat = myConn.prepareStatement("select item_invoice_link.item_code, items.item_name, items.price, item_invoice_link.amt_purchased from item_invoice_link inner join items where item_invoice_link.item_id = items.item_id and invoice_id = ?");
			
			myStat.setInt(1, invoiceId);
			
			//rs = myStat.executeQuery("select * from item_invoice_link where invoice_id = " + invoiceId);
			rs = myStat.executeQuery();
			
			while (rs.next()) {
				Item temp = new Item();
				temp.setItemName(rs.getString("items.item_name"));
				temp.setItemCode(rs.getString("item_invoice_link.item_code"));
				temp.setPrice(rs.getDouble("items.price"));
				temp.setQuantity(rs.getInt("item_invoice_link.amt_purchased"));
				
				itemsByInvoice.add(temp);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if(myConn != null) {
				myConn.close();
			}
			if (myStat != null) {
				myStat.close();
			}
		}
		
		return itemsByInvoice;
	}
	
	public static void printItemList(Store store) {
		String format = "%-15s";
		if (store.getInventory().isEmpty() || (store.getInventory() == null)) {
			System.out.println("Inventory is empty or could not be retrieved!");
		} else {
			System.out.println("\tStandalone eCommerce App");
			System.out.println("+========================================+");
			System.out.printf(format, "  Item Name");
			System.out.printf(format, "Item Code");
			System.out.printf(format, "Price");
			System.out.printf(format, "Quantity");
			System.out.println();
			for (int i = 0; i < store.getInventory().size(); i++) {
				System.out.printf(format, "| " + (i+1) + ". " + store.getInventory().get(i).getItemName());
				System.out.printf(format, store.getInventory().get(i).getItemCode());
				System.out.printf(format, store.getInventory().get(i).getPrice());
				System.out.printf(format, store.getInventory().get(i).getQuantity());
				System.out.println("|");
			}
			System.out.println("+========================================+");
		}
		
		
	}
}
