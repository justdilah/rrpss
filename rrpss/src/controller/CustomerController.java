package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import entity.Customer;
import entity.Table;

/**
 * This class represents the Customer controller of the restaurant.
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class CustomerController {

	/**
	 * This method checks a specific customer membership
	 * @param custID The customer id
	 * @return Boolean Expression (true: is a member and false: is not a member)
	 * @throws IOException Display error message if any I/O error found while retrieving the customer records.
	 */
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

	/**
	 * This method checks if a customer is an existing or new customer
	 * @param contact The customer contact
	 * @return Boolean Express (true: is an existing customer and false: is a new customer)
	 * @throws IOException Display error message if any I/O error found while retrieving the customer records.
	 */
	public Boolean custExists(String contact) throws IOException
	{
		if(getCustByContact(contact)!=null)
		{
			return true;
		}
		return false;
	}

	/**
	 * This methods gets a specific customer object by the customer object
	 * @param id The customer id
	 * @return A customer object
	 * @throws IOException Display error message if any I/O error found while retrieving the customer records.
	 */
	public Customer getCustById(int id) throws IOException
	{
		Customer c = null;
		ArrayList<Customer> cList = Customer.getAllCustomerDetails();

		for(int i=0; i<cList.size();i++) {

			if(cList.get(i).getCustId() == id) {
				c = cList.get(i);
			}
		}
		return c;
	}

	/**
	 * This method gets a specific customer object by the customer contact
	 * @param contact The customer contact
	 * @return A customer object
	 * @throws IOException Display error message if any I/O error found while retrieving the customer records.
	 */
	public Customer getCustByContact(String contact) throws IOException
	{
		Customer c = null;
		ArrayList<Customer> cList = Customer.getAllCustomerDetails();

		for(int i=0; i<cList.size();i++) {

			String k = cList.get(i).getPersPhoneNo();

			if(cList.get(i).getPersPhoneNo().equals(contact)) {
				c = cList.get(i);
			}
		}
		return c;
	}

	/**
	 * This method calls the saveCustomer() in the entity class to retrieve the entire list of customer
	 * @param name The customer name
	 * @param contact The customer contact
	 * @throws IOException Display error message if any I/O error found while inserting into the customer records.
	 */
	public void saveCustomer(String name, String contact) throws IOException
	{
		Customer.saveCustomer(name, contact);
	}

	/**
	 * This method updateMembership() in the entity class to update the customer
	 * @param cust The customer object
	 * @throws IOException Display error message if any I/O error found while updating into the customer records.
	 */
	public void updateMembership(Customer cust) throws IOException {
		Customer.updateMembership(cust);
	}
}