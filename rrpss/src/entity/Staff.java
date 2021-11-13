package entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Staff extends Person {

	private static final String filename = "DataSet/Staff.csv";

	private int staffId;
	private String staffTitle;
	private char staffGender;

	public Staff()
	{
		super();
	}

	public Staff(int id, String name, char gender, String contact, String title)
	{
		super(name, contact);
		this.staffId = id;
		this.staffTitle = title;
		this.staffGender = gender;
	}


	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getStaffTitle() {
		return staffTitle;
	}

	public void setStaffTitle(String staffTitle) {
		this.staffTitle = staffTitle;
	}

	public char getStaffGender() {
		return staffGender;
	}

	public void setStaffGender(char staffGender) {
		this.staffGender = staffGender;
	}

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


	//READ AND WRITE TO CSV
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