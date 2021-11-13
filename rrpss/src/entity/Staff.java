package entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This class represents the Staff entity of the restaurant.
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class Staff extends Person {

	/**
	 * The filepath of the CSV file
	 */
	private static final String filename = "DataSet/Staff.csv";

	/**
	 * The unique identifier of a staff
	 */
	private int staffId;

	/**
	 * The job title of the staff
	 */
	private String staffTitle;

	/**
	 * The gender of the staff
	 */
	private char staffGender;

	/**
	 * Default constructor of Staff that inherits its parent class, Person
	 */
	public Staff()
	{
		super();
	}

	/**
	 * Staff constructor with parameters
	 * @param id The staff id
	 * @param name The name of the staff
	 * @param gender The gender of the staff
	 * @param contact The contact of the staff
	 * @param title The job title of the staff
	 */
	public Staff(int id, String name, char gender, String contact, String title)
	{
		super(name, contact);
		this.staffId = id;
		this.staffTitle = title;
		this.staffGender = gender;
	}

	/**
	 * Get the staff id
	 * @return The staff id
	 */
	public int getStaffId() {
		return staffId;
	}

	/**
	 * Set the staff id
	 * @param staffId The staff id
	 */
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	/**
	 * Get the staff job title
	 * @return The staff job title
	 */
	public String getStaffTitle() {
		return staffTitle;
	}

	/**
	 * Set the staff job title
	 * @param staffTitle The staff Title
	 */
	public void setStaffTitle(String staffTitle) {
		this.staffTitle = staffTitle;
	}

	/**
	 * Get the staff gender
	 * @return The staff gender
	 */
	public char getStaffGender() {
		return staffGender;
	}

	/**
	 * Set the staff gender
	 * @param staffGender The staff gender
	 */
	public void setStaffGender(char staffGender) {
		this.staffGender = staffGender;
	}

	/**
	 * This method read the CSV file to retrieve all details from the staff CSV
	 * @return The whole list of staff details
	 * @throws IOException Display error message if any I/O error found while retrieving the staff records.
	 */
	public static ArrayList<Staff> getAllStaffDetails() throws IOException
	{
		ArrayList<Staff> slist= new ArrayList<>();
		ArrayList stringitems = (ArrayList)read(filename);

		for (int i = 0; i < stringitems.size(); i++)
		{
			String st = (String) stringitems.get(i);
			StringTokenizer star = new StringTokenizer(st, ",");

			String staffid = star.nextToken().trim();
			String name =  star.nextToken().trim();
			String gender =  star.nextToken().trim();
			String contact = star.nextToken().trim();
			String title = star.nextToken().trim();

			char g  = gender.charAt(0);

			Staff s = new Staff(Integer.parseInt(staffid), name, g, contact, title);
			slist.add(s);
		}
		return slist;
	}


	/**
	 * This method reads the staff CSV
	 * @param filename The filepath of the CSV file
	 * @return The list of records read from the staff CSV
	 * @throws IOException Display error message if any I/O error found while retrieving the staff records.
	 */
	private static List read(String filename) throws IOException {
		List data = new ArrayList();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String headerLine = reader.readLine();
		//   System.out.println(headerLine);
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
}