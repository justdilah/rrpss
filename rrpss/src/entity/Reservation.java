package entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Reservation {

	private static final String filename = "DataSet/Reservation.csv";

	Customer customer;
	Staff staff;
	private int resId;
	private int resNoPax;
	private String resName;
	private String resContact;


	public Reservation()
	{

	}

	public Reservation(int id, String name, String contact, int pax, Customer c, Staff s)
	{
		this.resId = id;
		this.resName = name;
		this.resContact = contact;
		this.resNoPax = pax;
		this.customer = c;
		this.staff = s;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public int getResId() {
		return resId;
	}

	public void setResId(int resId) {
		this.resId = resId;
	}

	public int getResNoPax() {
		return resNoPax;
	}

	public void setResNoPax(int resNoPax) {
		this.resNoPax = resNoPax;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getResContact() {
		return resContact;
	}

	public void setResContact(String resContact) {
		this.resContact = resContact;
	}


	public Reservation getResByContact(String contact) throws IOException
	{
		Reservation r = new Reservation();

		for(int i=0; i<getAllReservationDetails().size();i++) {

			if(getAllReservationDetails().get(i).getResContact().equals(contact)) {
				r = getAllReservationDetails().get(i);
			}
		}
		return r;
	}

	public Reservation getResById(Integer id) throws IOException
	{
		Reservation r = new Reservation();
		ArrayList<Reservation> rList = getAllReservationDetails();

		for(int i=0; i<rList.size();i++) {

			if(rList.get(i).getResId()==id) {
				r = rList.get(i);
				break;
			}
		}
		return r;
	}


	// csv
	public ArrayList<Reservation> getAllReservationDetails() throws IOException
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

			Customer c = new Customer();
			c=c.getCustById(Integer.parseInt(custid));

			Staff s = new Staff();
			s=s.getStaffById(Integer.parseInt(staffid));

			Reservation  r = new Reservation(Integer.parseInt(resid), name, contact, Integer.parseInt(pax), c, s);
			rlist.add(r);
		}
		return rlist;
	}



	//ADDING TO CSV FILE
	public int saveReservation(String name, String resContact, int resNoPax, int custid, int staffid) throws IOException
	{
		int last = getAllReservationDetails().size();
		int id;

		if (last == 0)
		{
			id = 30001;
		}
		else
		{
			id = getAllReservationDetails().get(last-1).getResId()+1;
		}

		String res = id + "," + name + "," + resContact +  "," +  resNoPax + "," + custid + "," + staffid;
		List l = new ArrayList();
		l.add(res);
		write(filename, l);
		return id;
	}



	//FOR UPDATE
	public void updateReservation(Reservation r) throws IOException
	{
		List l = new ArrayList<>();
		ArrayList<Reservation> rList = getAllReservationDetails();

		for(int i=0;i<getAllReservationDetails().size();i++)
		{
			if(getAllReservationDetails().get(i).getResId() == r.getResId())
			{
				rList.set(i, r);
			}

			Reservation k = rList.get(i);

			String resitem = k.getResId() + "," + k.getResName() + "," + k.getResContact() +  "," +  k.getResNoPax()+ "," + k.customer.getCustId() + "," + k.staff.getStaffId();
			l.add(resitem);
		}

		replace(filename, l);
	}

	public void deleteLateReservation(ArrayList<Integer> deleteList) throws IOException {
		Reservation tempRest = new Reservation();
		for (Integer resID : deleteList) {
			tempRest = this.getResById(resID);
			String name = tempRest.getResName();
			this.deleteReservation(tempRest);
			System.out.println("NOTE: ");
			System.out.println("=========================================================================================");
			System.out.printf("%s 's reservation has been removed because he/she is late more than 10 minutes \n", name);
			System.out.println("=========================================================================================");
		}
	}

	public void deletePastReservation(ArrayList<Integer> deleteList) throws IOException {
		Reservation tempRest = new Reservation();
		for (Integer resID : deleteList) {
			tempRest = this.getResById(resID);
			String name = tempRest.getResName();
			this.deleteReservation(tempRest);
			System.out.printf("%s 's reservation has been removed because the reservation date is before today \n",
					name);
		}
	}

	public void deleteReservation(Reservation r) throws IOException
	{
		List l = new ArrayList<>();
		ArrayList<Reservation> rList = getAllReservationDetails();

		for(int i=0; i<rList.size(); i++)
//		for(int i=0; i<getAllReservationDetails().size(); i++)
		{
			if(getAllReservationDetails().get(i).getResId() == r.getResId())
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


	//READ AND WRITE TO CSV
	private List read(String filename) throws IOException {

		List data = new ArrayList();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String headerLine = reader.readLine();
		//  System.out.println(headerLine);
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

	private void write(String filename, List data) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(filename,true));
		try {
			for (int i = 0; i < data.size(); i++) {

				//out.write("\n" +(String) data.get(i)+"\n");
				out.write((String) data.get(i)+"\n");
			}
		} finally {
			out.close();
		}
	}



	private void replace(String filename, List data) throws IOException {

		BufferedWriter out = new BufferedWriter(new FileWriter(filename));
		try {
			out.write("resId" + "," + "resName" + "," + "resContact" + "," + "resNoPax" + "," + "customer" + "," + "staff" + "\n");

			for (int i = 0; i < data.size(); i++) {

				out.write((String) data.get(i) + "\n");
			}
		} finally {
			out.close();
		}
	}




}
