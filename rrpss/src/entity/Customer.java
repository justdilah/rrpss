package entity;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Customer extends Person {

	private static final String filename = "DataSet/Customer.csv";

	private int custId;
	private Boolean membership;

	public Customer()
	{
		super();
	}

	public Customer(int id, String name, String contact, Boolean membership)
	{
		super(name, contact);
		this.custId = id;
		this.membership = membership;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public Boolean getMembership() {
		return membership;
	}

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
		ArrayList stringitems = (ArrayList)read(filename); 	

		for (int i = 0; i < stringitems.size(); i++) 
		{
			String st = (String) stringitems.get(i);
			StringTokenizer star = new StringTokenizer(st, ",");

			String custid = star.nextToken().trim();
			String name =  star.nextToken().trim();
			String contact = star.nextToken().trim();
			String membership = star.nextToken().trim();

			Boolean m = null;

			if (membership.equals("TRUE")) // is member
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
		write(filename, l);
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

		replace(filename,l);
	}

	//READ AND WRITE TO CSV
	private List read(String filename) throws FileNotFoundException {
		List data = new ArrayList();
		Scanner scanner = new Scanner(new FileInputStream(filename));
		try {
			while (scanner.hasNextLine()) {
				data.add(scanner.nextLine());
			}
		} finally {
			scanner.close();
		}
		return data;
	}

	private void write(String filename, List data) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(filename,true));
		try {
			for (int i = 0; i < data.size(); i++) {

				out.write((String) data.get(i)+"\n");
			}
		} finally {
			out.close();
		}
	}

	private void replace(String filename, List data) throws IOException {

		BufferedWriter out = new BufferedWriter(new FileWriter(filename));
		try {
			for (int i = 0; i < data.size(); i++) {

				out.write((String) data.get(i) + "\n");
			}
		} finally {
			out.close();
		}
	}	

}
