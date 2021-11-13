package entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * This class represents the Customer entity of the restaurant.
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class Customer extends Person {

	/**
	 * The filepath of the CSV file
	 */
	private static final String filename = "DataSet/Customer.csv";

	/**
	 * The unique identifier of a reservation
	 */
	private int custId;

	/**
	 * Variable to state if the customer is a member of the restaurant
	 */
	private Boolean membership;

	/**
	 * Default constructor of Customer that inherits its parent class, Person
	 */
	public Customer()
	{
		super();
	}

	/**
	 * Customer constructor with parameters
	 * @param id The customer id
	 * @param name The name of the customer
	 * @param contact The contact of the customer
	 * @param membership The membership status of the customer
	 */
	public Customer(int id, String name, String contact, boolean membership)
	{
		super(name, contact);
		this.custId = id;
		this.membership = membership;
	}

	/**
	 * Get the customer id
	 * @return The customer id
	 */
	public int getCustId() {
		return this.custId;
	}

	/**
	 * Set the customer id
	 * @param custId The customer id
	 */
	public void setCustId(int custId) {
		this.custId = custId;
	}

	/**
	 * Get the status of the customer membership
	 * @return The customer membership status
	 */
	public boolean getMembership() {
		return this.membership;
	}

	/**
	 * Set the customer membership
	 * @param membership The customer membership status
	 */
	public void setMembership(boolean membership) {
		this.membership = membership;
	}


	/**
	 * This method read the CSV file to retrieve all details from the customer CSV
	 * @return The whole list of customer details
	 * @throws IOException Display error message if any I/O error found while retrieving the customer records.
	 */
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

			boolean m = membership.equals("true");

			Customer c = new Customer(Integer.parseInt(custid), name, contact, m);
			clist.add(c);
		}
		return clist;
	}

	/**
	 * This method saves a customer into the customer CSV
	 * @param name The name of the customer
	 * @param contact The contact of the customer
	 * @throws IOException Display error message if any I/O error found while inserting into the customer records.
	 */
	public static void saveCustomer(String name, String contact) throws IOException
	{
		int last = getAllCustomerDetails().size();
		int id = getAllCustomerDetails().get(last-1).getCustId()+1;

		String cust = id + "," + name + "," + contact + "," + "false";
		List l = new ArrayList();
		l.add(cust);
		write(filename, l);
	}

	/**
	 * This method updates the specific customer membership and store into the customer CSV
	 * @throws IOException Display error message if any I/O error found while updating into the customer records
	 */
	public void editCustomer() throws IOException {
		List l = new ArrayList<>();
		String m = "";
		for(int i=0;i<getAllCustomerDetails().size();i++) {
			if(getAllCustomerDetails().get(i).getMembership() == true) {
				m = "true";
			} else {
				m = "false";
			}

			String cust = getAllCustomerDetails().get(i).getCustId() + "," + getAllCustomerDetails().get(i).getPersName() + ","+ getAllCustomerDetails().get(i).getPersPhoneNo() +  "," +  m;
			l.add(cust);
		}

		replace(filename,l);
	}


	/**
	 * This method updates the specific customer membership status and store it into the customer CSV
	 * @param c A customer object
	 * @throws IOException Display error message if any I/O error found while updating into the customer records.
	 */
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



	/**
	 * This method reads the customer CSV
	 * @param filename The filepath of the CSV file
	 * @return The list of records read from the customer CSV
	 * @throws IOException Display error message if any I/O error found while retrieving the customer records.
	 */
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

	/**
	 * This method writes the customer CSV
	 * @param filename The filepath of the CSV file
	 * @param data A list of customer records
	 * @throws IOException Display error message if any I/O error found while inserting into the customer records.
	 */
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

	/**
	 * This method writes a new set of data and stores into the customer CSV
	 * @param filename The filepath of the CSV file
	 * @param data A list of customer records
	 * @throws IOException Display error message if any I/O error found while inserting into the customer records.
	 */
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