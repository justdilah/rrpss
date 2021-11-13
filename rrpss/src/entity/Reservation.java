package entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import controller.CustomerController;
import controller.StaffController;


/**
 * This class represents the Reservation entity of the restaurant.
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class Reservation {

	/**
	 * The filepath of the CSV file
	 */
	private static final String filename = "DataSet/Reservation.csv";


	/**
	 * Customer who the reservation belongs to
	 */
	Customer customer;

	/**
	 * Staff who made the reservation
	 */
	Staff staff;

	/**
	 * The unique identifier of a reservation
	 */
	private int resId;

	/**
	 * The number of pax
	 */
	private int resNoPax;

	/**
	 * The name of the customer who the reservation belong to
	 */
	private String resName;

	/**
	 * The contact of the customer who the reservation belong to
	 */
	private String resContact;

	/**
	 * Default constructor of Reservation
	 */
	public Reservation()
	{

	}

	/**
	 * Reservation constructor with parameters
	 * @param id The reservation id
	 * @param name The name of the customer who the reservation belongs to
	 * @param contact The contact of the customer who the reservation belong to
	 * @param pax The number of pax
	 * @param c The customer object who the reservation belongs to
	 * @param s The staff object who made the reservation
	 */
	public Reservation(int id, String name, String contact, int pax, Customer c, Staff s)
	{
		this.resId = id;
		this.resName = name;
		this.resContact = contact;
		this.resNoPax = pax;
		this.customer = c;
		this.staff = s;
	}

	/**
	 * Get the customer object
	 * @return The customer object
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Set the customer object
	 * @param customer The customer object who the reservation belongs to
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * Get the staff object
	 * @return The staff object
	 */
	public Staff getStaff() {
		return staff;
	}

	/**
	 * Set the staff object
	 * @param staff The staff object who made the reservation
	 */
	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	/**
	 * Get the reservation id
	 * @return The reservation id
	 */
	public int getResId() {
		return resId;
	}

	/**
	 * Set the reservation id
	 * @param resId The reservation id
	 */
	public void setResId(int resId) {
		this.resId = resId;
	}

	/**
	 * Get the number of pax
	 * @return The number of pax
	 */
	public int getResNoPax() {
		return resNoPax;
	}

	/**
	 * Set the number of pax
	 * @param resNoPax The number of pax
	 */
	public void setResNoPax(int resNoPax) {
		this.resNoPax = resNoPax;
	}

	/**
	 * Get the customer name who the reservation belong to
	 * @return The name of the customer who the reservation belongs to
	 */
	public String getResName() {
		return resName;
	}

	/**
	 * Set the customer name who the reservation belong to
	 * @param resName The name of the customer who the reservation belongs to
	 */
	public void setResName(String resName) {
		this.resName = resName;
	}

	/**
	 * Get the customer contact who the reservation belong to
	 * @return The customer contact who the reservation belongs to
	 */
	public String getResContact() {
		return resContact;
	}

	/**
	 * Set the customer contact who the reservation belong to
	 * @param resContact The contact of the customer who the reservation belongs to
	 */
	public void setResContact(String resContact) {
		this.resContact = resContact;
	}

	/**
	 * This method reads the CSV file to retrieve all details from the reservation CSV.
	 * @return The whole list of reservation details
	 * @throws IOException Display error message if any I/O error found while retrieving into the reservation records.
	 */
	public static ArrayList<Reservation> getAllReservationDetails() throws IOException
	{
		ArrayList<Reservation> rlist= new ArrayList<>();
		ArrayList stringitems = (ArrayList)read(filename);

		for (int i = 0; i < stringitems.size(); i++)
		{
			String st = (String) stringitems.get(i);
			StringTokenizer star = new StringTokenizer(st, ",");

			String resid = star.nextToken().trim();
			String name =  star.nextToken().trim();
			String contact =  star.nextToken().trim();
			String pax = star.nextToken().trim();
			String custid =  star.nextToken().trim();
			String staffid =  star.nextToken().trim();

			Customer c;
			CustomerController cc = new CustomerController();
			c=cc.getCustById(Integer.parseInt(custid));

			Staff s = new Staff();
			StaffController sc = new StaffController();
			s=sc.getStaffById(Integer.parseInt(staffid));

			Reservation  r = new Reservation(Integer.parseInt(resid), name, contact, Integer.parseInt(pax), c, s);
			rlist.add(r);
		}
		return rlist;
	}


	/**
	 * This method saves a reservation into the reservation CSV
	 * @param name The name of the customer who the reservation belongs to
	 * @param resContact The contact of the customer who the reservation belong to
	 * @param resNoPax The number of pax
	 * @param custid The customer id
	 * @param staffid The staff id
	 * @return The reservation id of the newly made reservation.
	 * @throws IOException Display error message if any I/O error found while inserting into the reservation records.
	 */
	public static int saveReservation(String name, String resContact, int resNoPax, int custid, int staffid) throws IOException
	{
		ArrayList<Reservation> rList = getAllReservationDetails();
		int last = rList.size();
		int id;

		if (last == 0)
		{
			id = 30001;
		}
		else
		{
			id = rList.get(last-1).getResId()+1;
		}

		String res = id + "," + name + "," + resContact +  "," +  resNoPax + "," + custid + "," + staffid;
		List l = new ArrayList();
		l.add(res);
		write(filename, l);
		return id;
	}

	/**
	 * This method updates the specific reservation and store into the reservation CSV
	 * @param r A reservation object
	 * @throws IOException Display error message if any I/O error found while updating into the reservation records.
	 */
	public static void updateReservation(Reservation r) throws IOException
	{
		List l = new ArrayList<>();
		ArrayList<Reservation> rList = getAllReservationDetails();

		for(int i=0;i<rList.size();i++)
		{
			if(rList.get(i).getResId() == r.getResId())
			{
				rList.set(i, r);
			}

			Reservation k = rList.get(i);

			String resitem = k.getResId() + "," + k.getResName() + "," + k.getResContact() +  "," +  k.getResNoPax()+ "," + k.customer.getCustId() + "," + k.staff.getStaffId();
			l.add(resitem);
		}

		replace(filename, l);
	}

	/**
	 * This method delete the specific reservation from the reservation CSV
	 * @param r A reservation object
	 * @throws IOException Display error message if any I/O error found while deleting from the reservation records.
	 */
	public static void deleteReservation(Reservation r) throws IOException
	{
		List l = new ArrayList<>();
		ArrayList<Reservation> rList = getAllReservationDetails();

		for(int i=0; i<rList.size(); i++)
		{
			if(rList.get(i).getResId() == r.getResId())
			{
				//			rList.remove(i);
			}
			else
			{
				Reservation k = rList.get(i);

				String resitem = k.getResId() + "," + k.getResName() + "," + k.getResContact() +  "," +  k.getResNoPax() + "," + k.customer.getCustId()+ "," + k.staff.getStaffId();
				l.add(resitem);
			}

		}
		replace(filename, l);
	}


	/**
	 * This method reads the reservation CSV
	 * @param filename The filepath of the CSV file
	 * @return The list of records read from the reservation CSV
	 * @throws IOException Display error message if any I/O error found while retrieving the reservation records.
	 */
	private static List read(String filename) throws IOException
	{
		List data = new ArrayList();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String headerLine = reader.readLine();
		String line;

		try {
			while ((line = reader.readLine()) != null)
			{
				data.add(line);
			}
		} finally {
			reader.close();
		}
		return data;
	}

	/**
	 * This method writes the reservation CSV
	 * @param filename The filepath of the CSV file
	 * @param data A list of reservations records
	 * @throws IOException Display error message if any I/O error found while inserting into the reservation records.
	 */
	private static void write(String filename, List data) throws IOException
	{
		BufferedWriter out = new BufferedWriter(new FileWriter(filename,true));
		try {
			for (int i = 0; i < data.size(); i++)
			{
				out.write((String) data.get(i)+"\n");
			}
		} finally {
			out.close();
		}
	}


	/**
	 * This method writes a new set of data and stores into the reservation CSV
	 * @param filename The filepath of the CSV file
	 * @param data A list of reservation records
	 * @throws IOException Display error message if any I/O error found while inserting into the reservation records.
	 */
	private static void replace(String filename, List data) throws IOException
	{
		BufferedWriter out = new BufferedWriter(new FileWriter(filename));
		try {
			out.write("resId" + "," + "resName" + "," + "resContact" + "," + "resNoPax" + "," + "customer" + "," + "staff" + "\n");

			for (int i = 0; i < data.size(); i++)
			{
				out.write((String) data.get(i) + "\n");
			}
		} finally {
			out.close();
		}
	}




}