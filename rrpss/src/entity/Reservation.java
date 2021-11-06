package entity;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

	Table reserveTable;
	Customer customer;
	Staff staff;
	private int resId;
	private LocalDate resDate;
	private LocalTime resTime;
	private int resNoPax;
	private String resName;
	private String resContact;


	public Reservation()
	{

	}

	public Reservation(int id, String name, String contact, int pax, LocalDate date, LocalTime time, Customer c, Staff s)
	{
		this.resId = id;
		this.resName = name;
		this.resContact = contact;
		this.resNoPax = pax;
		this.resDate = date;
		this.resTime = time;
		this.customer = c;
		this.staff = s;
	}

	public Reservation(int id, String name, String contact, int pax, LocalDate date, LocalTime time, Customer c, Staff s, Table t)
	{
		this.resId = id;
		this.resName = name;
		this.resContact = contact;
		this.resNoPax = pax;
		this.resDate = date;
		this.resTime = time;
		this.customer = c;
		this.staff = s;
		this.reserveTable = t;
	}



	public Table getReserveTable() {
		return reserveTable;
	}

	public void setReserveTable(Table reserveTable) {
		this.reserveTable = reserveTable;
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

	public LocalDate getResDate() {
		return resDate;
	}

	public void setResDate(LocalDate resDate) {
		this.resDate = resDate;
	}

	public LocalTime getResTime() {
		return resTime;
	}

	public void setResTime(LocalTime resTime) {
		this.resTime = resTime;
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


	public void allocateTable(int pax) throws IOException
	{
		Table t = new Table();
//		t.checkTableAvail(pax);
		
		
	}


	public Reservation getResByContact(String contact) throws NumberFormatException, IOException 
	{	
		Reservation r = new Reservation();

		for(int i=0; i<getAllReservationDetails().size();i++) {

			if(getAllReservationDetails().get(i).getResContact().equals(contact)) {
				r = getAllReservationDetails().get(i);
			}
		}
		return r;
	}


	// csv
	public ArrayList<Reservation> getAllReservationDetails() throws NumberFormatException, IOException
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
			String date = star.nextToken().trim();
			String time = star.nextToken().trim();
			String custid =  star.nextToken().trim();
			String staffid =  star.nextToken().trim();
			//			String table = star.nextToken().trim();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate convertdate = LocalDate.parse(date,formatter);
			DateTimeFormatter tformatter = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime converttime = LocalTime.parse(time,tformatter);

			Customer c = new Customer();
			c=c.getCustById(Integer.parseInt(custid));

			Staff s = new Staff();
			s=s.getStaffById(Integer.parseInt(staffid));


			Reservation  r = new Reservation(Integer.parseInt(resid), name, contact, Integer.parseInt(pax), convertdate, converttime, c, s);
			rlist.add(r);
		}
		return rlist;	
	}


	//ADDING TO CSV FILE 
	public void saveReservation(String name, String resContact, int resNoPax, LocalDate resDate, LocalTime resTime, int custid, int staffid) throws IOException
	{
		int last = getAllReservationDetails().size();
		int id = getAllReservationDetails().get(last-1).getResId()+1;

		String res = id + "," + name + "," + resContact +  "," +  resNoPax+ "," + resDate + "," + resTime + "," + custid + "," + staffid;
		List l = new ArrayList();
		l.add(res);
		write(filename, l);
	}



	public void deleteReservation(Reservation r) throws IOException 
	{	
		List l = new ArrayList<>();
		ArrayList<Reservation> rList = getAllReservationDetails();

		for(int i=0; i<getAllReservationDetails().size(); i++)
		{
			if(getAllReservationDetails().get(i).getResId() == r.getResId())
			{
				rList.remove(i);
			} 
			else
			{
				Reservation k = rList.get(i);

				String resitem = k.getResId() + "," + k.getResName() + "," + k.getResContact() +  "," +  k.getResNoPax()+ "," +k.getResDate() + "," + k.getResTime()+ "," + k.customer.getCustId()+ "," + k.staff.getStaffId();
				l.add(resitem);
			}

		}
		replace(filename, l);
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

			String resitem = k.getResId() + "," + k.getResName() + "," + k.getResContact() +  "," +  k.getResNoPax()+ "," +k.getResDate() + "," + k.getResTime()+ "," + k.customer.getCustId() + "," + k.staff.getStaffId();
			l.add(resitem);
		}

		replace(filename, l);		
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
