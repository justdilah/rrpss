package entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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


	public static ArrayList<Customer> getAllCustomerDetails() throws IOException
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
	public static void saveCustomer(String name, String contact) throws IOException
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


	public static void updateMembership(Customer c) throws IOException {
		List l = new ArrayList<>();
		ArrayList<Customer> miList = getAllCustomerDetails();

		OrderItem item = new OrderItem();

		int size = getAllCustomerDetails().size();

		for(int i=0;i<size;i++) {

			if(miList.get(i).getCustId() == c.getCustId()) {
				miList.set(i, c);
			}

			Customer k = miList.get(i);
			String cust = k.getCustId() + "," + k.getPersName() +  "," + k.getPersPhoneNo() +  "," +  k.getMembership();
			l.add(cust);


		}
		replace(filename,l);
	}



	//READ AND WRITE TO CSV
	private static List read(String filename) throws IOException {
		List data = new ArrayList();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String headerLine = reader.readLine();
		String line;
		try {
			while ((line = reader.readLine()) != null) {

				data.add(line);
			}
		} finally {
			reader.close();
		}
		return data;
	}

	private static void write(String filename, List data) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(filename,true));
		try {
			for (int i = 0; i < data.size(); i++) {

				out.write((String) data.get(i)+"\n");
			}
		} finally {
			out.close();
		}
	}

	private static void replace(String filename, List data) throws IOException {

		BufferedWriter out = new BufferedWriter(new FileWriter(filename));
		try {
			out.write("CustID" + "," + "Name" + "," + "PhoneNum" + "," + "isMember" +"\n");
			for (int i = 0; i < data.size(); i++) {

				out.write((String) data.get(i) + "\n");
			}
		} finally {
			out.close();
		}
	}

}