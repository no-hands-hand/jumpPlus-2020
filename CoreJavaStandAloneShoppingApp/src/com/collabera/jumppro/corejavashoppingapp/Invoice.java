package com.collabera.jumppro.corejavashoppingapp;

import java.sql.Date;
import java.util.List;
import java.util.Random;

public class Invoice {
	
	User customer;
	int invoiceId;
	Date invoiceDate;
	
	List<Item> itemList;
	double totalCost;
	
	public Invoice(User customer, List<Item> itemList) {
		
	}
	
	public User getCustomer() {
		return customer;
	}
	public void setCustomer(User customer) {
		this.customer = customer;
	}
	public int getInvoiceNum() {
		return invoiceId;
	}
	public void setInvoiceNum(int invoiceId) {
		this.invoiceId = invoiceId;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public List<Item> getItemList() {
		return itemList;
	}
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
}
