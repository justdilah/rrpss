package controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import entity.Customer;

public class CustomerController {
	Customer c = new Customer();
	
	public void getCustById(int id) throws IOException {
		c.getCustById(id);
	}
	
	public Boolean isMember(int custID) throws IOException {
		return c.isMember(custID);
	}
	
	public void updateMembership(Customer cust) throws IOException {
		c.updateMembership(cust);
	}
}
