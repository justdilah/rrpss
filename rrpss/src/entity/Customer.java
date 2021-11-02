package entity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import controller.StoreController;

import java.util.*;

public class Customer extends Person {
	
	private static final String filename = "DataSet/Customer.csv";

	private int custId;
	private Boolean membership;
//	Collection<Order> orders;

	public Customer(){
		super();
	}
	
	public Customer(int id, String name, String contact, Boolean membership)
	{
		super(name, contact);
		this.custId = id;
		this.membership = membership;
	}
	
	
	
	public int getCustId() {
		return this.custId;
	}

	/**
	 * 
	 * @param custId
	 */
	public void setCustId(int custId) {
		this.custId = custId;
	}

	public Boolean getMembership() {
		return this.membership;
	}

	/**
	 * 
	 * @param membership
	 */
	public void setMembership(Boolean membership) {
		this.membership = membership;
	}
	
	public Customer getCustById(int id) throws FileNotFoundException 
	{
		Customer c = null;
		
		for(int i=0; i<getAllCustomerDetails().size();i++) {
			
			if(getAllCustomerDetails().get(i).getCustId() == id) {
				c = getAllCustomerDetails().get(i);
			}
		}
		return c;
	}
	
	public Customer getCustByContact(String contact) throws FileNotFoundException 
	{
		Customer c = null;
		
		for(int i=0; i<getAllCustomerDetails().size();i++) {
			
			String k = getAllCustomerDetails().get(i).getPersPhoneNo();
			
			if(getAllCustomerDetails().get(i).getPersPhoneNo().equals(contact)) {
				c = getAllCustomerDetails().get(i);
			}
		}
		return c;
	}
	
	public ArrayList<Customer> getAllCustomerDetails() throws FileNotFoundException
	{
		ArrayList<Customer> clist= new ArrayList<>();
		ArrayList stringitems = (ArrayList) StoreController.read(filename); 	
		
		for (int i = 0; i < stringitems.size(); i++) 
		{
			String st = (String) stringitems.get(i);
			StringTokenizer star = new StringTokenizer(st, ",");
			
			String custid = star.nextToken().trim();
			String name =  star.nextToken().trim();
			String contact = star.nextToken().trim();
			String membership = star.nextToken().trim();
		
			Boolean m = null;
			if (membership.equals("TRUE")) // member
				m = true;
			else if (membership.equals("FALSE"))
				m = false;

			Customer c = new Customer(Integer.parseInt(custid), name, contact, m);
		
			clist.add(c);
		}
		return clist;	
	} 
	
	//ADDING TO CSV FILE 
	
	public void saveCustomer(String name, String contact) throws IOException
	{
        int last = getAllCustomerDetails().size();
        int id = getAllCustomerDetails().get(last-1).getCustId()+1;
        
        String cust = id + "," + name + "," + contact + "," + "FALSE";
        List l = new ArrayList();
        l.add(cust);
		StoreController.write(filename, l);
	}
	
	public void editCustomer() throws IOException {
		List l = new ArrayList<>();
		String m = "";
		for(int i=0;i<getAllCustomerDetails().size();i++) {
			if(getAllCustomerDetails().get(i).getMembership() == true) {
				m = "TRUE";
			} else {
				m = "FALSE";
			}
			
			String cust = getAllCustomerDetails().get(i).getCustId() + "," + getAllCustomerDetails().get(i).getPersName() + ","+ getAllCustomerDetails().get(i).getPersPhoneNo() +  "," +  m;
			l.add(cust);
		}
		
		StoreController.replace(filename,l);
	} 
	
	
	

}