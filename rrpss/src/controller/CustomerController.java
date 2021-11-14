package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import entity.Customer;
import entity.Table;

public class CustomerController {

	public Boolean isMember(int custID) throws IOException
	{
		ArrayList<Customer> cList = Customer.getAllCustomerDetails();

		for(int i=0; i<cList.size();i++)
		{
			if(cList.get(i).getCustId() == custID && cList.get(i).getMembership()) {
				return true;
			}
		}
		return false;
	}

	public Boolean custExists(String contact) throws IOException
	{
		if(getCustByContact(contact)!=null)
		{
			return true;
		}
		return false;
	}

	public Customer getCustById(int id) throws IOException
	{
		Customer c = new Customer();
		ArrayList<Customer> cList = Customer.getAllCustomerDetails();

		for(int i=0; i<cList.size();i++) {

			if(cList.get(i).getCustId() == id) {
				c = cList.get(i);
			}
		}
		return c;
	}

	public Customer getCustByContact(String contact) throws IOException
	{
		Customer c = new Customer();
		ArrayList<Customer> cList = Customer.getAllCustomerDetails();

		for(int i=0; i<cList.size();i++) {

			String k = cList.get(i).getPersPhoneNo();

			if(cList.get(i).getPersPhoneNo().equals(contact)) {
				c = cList.get(i);
			}
		}
		return c;
	}

	public void saveCustomer(String name, String contact) throws IOException
	{
		Customer.saveCustomer(name, contact);
	}

	public void updateMembership(Customer cust) throws IOException {
		Customer.updateMembership(cust);
	}
}