package entity;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import controller.ReservationController;

public class Table {

	private static final String filename = "DataSet/Table.csv";

	private static List<Table> tables = new ArrayList<>();

	// initialise variables
	private int tableNo;
	private TableCapacity seatCapacity;
	private TableStatus status;
	private LocalDate tableDate;
	private LocalTime tableTime;
	private Reservation tableRes;


	public Table()
	{

	}

	public Table(int no, int cap)
	{
		tableNo = no;
		seatCapacity = this.returnTScapcity(cap);
		status = TableStatus.VACANT;
	}

	public Table(int no, TableCapacity cap, LocalDate date, LocalTime time, TableStatus tstatus, Reservation res)
	{
		tableNo = no;
		seatCapacity = cap;
		status = tstatus;
		this.tableDate = date;
		this.tableTime = time;
		this.tableRes = res;
	}


// Getter and Setter

	public int getTableNo() {
		return tableNo;
	}

	public void setTableNo(int tableNo) {
		this.tableNo = tableNo;
	}

	public TableCapacity getSeatCapacity() {
		return seatCapacity;
	}

	public void setSeatCapacity(TableCapacity seatCapacity) {
		this.seatCapacity = seatCapacity;
	}

	public TableStatus getStatus() {
		return status;
	}

	public void setStatus(TableStatus status) {
		this.status = status;
	}

	public LocalDate getTableDate() {
		return tableDate;
	}

	public void setTableDate(LocalDate tableDate) {
		this.tableDate = tableDate;
	}

	public LocalTime getTableTime() {
		return tableTime;
	}

	public void setTableTime(LocalTime tableTime) {
		this.tableTime = tableTime;
	}

	public Reservation getTableRes() {
		return tableRes;
	}

	public void setTableRes(Reservation tableRes) {
		this.tableRes = tableRes;
	}

	public Boolean tableExists(int tableNo) throws FileNotFoundException
	{
		ArrayList<Table> tList = getAllTableDetails();

		for(int i=0; i<tList.size();i++)
		{
			if(tList.get(i).getTableNo() == tableNo) {
				return true;
			}
		}
		return false;
	}


	public int returnINTcapcity(TableCapacity cap)
	{
		if (cap.toString().equals("TWO_SEATER"))
			return 2;
		else if (cap.toString().equals("FOUR_SEATER"))
			return 4;
		else if (cap.toString().equals("SIX_SEATER"))
			return 6;
		else if (cap.toString().equals("EIGHT_SEATER"))
			return 8;
		else
			return 10;
	}


	private TableCapacity returnTScapcity(int cap)
	{
		if (cap==2)
			return TableCapacity.TWO_SEATER;
		else if (cap==4)
			return TableCapacity.FOUR_SEATER;
		else if (cap==6)
			return TableCapacity.SIX_SEATER;
		else if (cap==8)
			return TableCapacity.EIGHT_SEATER;
		else
			return TableCapacity.TEN_SEATER;
	}


	// csv
	public static ArrayList<Table> getAllTableDetails() throws FileNotFoundException
	{
		ArrayList<Table> tlist= new ArrayList<>();
		tlist.clear();
		ArrayList stringitems;
		try {
			stringitems = (ArrayList) read(filename);
			for (int i = 0; i < stringitems.size(); i++)
			{
				String st = (String) stringitems.get(i);
				StringTokenizer star = new StringTokenizer(st, ",");

				String tableno = star.nextToken().trim();
				String cap =  star.nextToken().trim();
				String date = star.nextToken().trim();
				String time = star.nextToken().trim();
				String tstatus =  star.nextToken().trim();
				String res_id = star.nextToken().trim();

				ReservationController tempR = new ReservationController();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate convertdate = LocalDate.parse(date,formatter);
				DateTimeFormatter tformatter = DateTimeFormatter.ofPattern("HH:mm");
				LocalTime converttime = LocalTime.parse(time,tformatter);

				Table t = new Table(Integer.parseInt(tableno), TableCapacity.valueOf(cap), convertdate, converttime, TableStatus.valueOf(tstatus), tempR.getResById(Integer.parseInt(res_id)));
				tlist.add(t);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		return tlist;
	}


	// csv
	public Table getAllVacantTableByCapacity(TableCapacity cap) throws FileNotFoundException
	{
		Table t = new Table();
		ArrayList<Table> tList = getAllTableDetails();

		for(int i=0; i<tList.size();i++)
		{
			if(tList.get(i).getSeatCapacity().equals(cap))
				if(tList.get(i).getStatus().equals(TableStatus.VACANT))
				{
					t = tList.get(i);
				}
		}
		return t;
	}


	public static void updateTableStatus(Table t) throws IOException
	{
		List l = new ArrayList<>();
		ArrayList<Table> tList = getAllTableDetails();

		for(int i=0;i<tList.size();i++)
		{
			if(tList.get(i).getTableNo() == t.getTableNo())
			{
				tList.set(i, t);
			}

			Table k = tList.get(i);

			String item = k.getTableNo() + "," + k.getSeatCapacity() + "," + k.getStatus() ;
			l.add(item);
		}

		replace(filename, l);
	}

	public void insertTable(int no, TableCapacity cap, LocalDate date, LocalTime time, int res_id) throws IOException
	{
		//need to update both reservation and table Table

		String res = no + "," + cap.toString() + "," + date +  "," +  time + "," + TableStatus.RESERVED + "," + res_id;
		List l = new ArrayList();
		l.add(res);
		write(filename, l);
	}

	public static void updateTable(Table t, int resid) throws IOException
	{
		List l = new ArrayList<>();
		ArrayList<Table> tList = getAllTableDetails();

		for(int i=0;i<tList.size();i++)
		{
			if(tList.get(i).getTableRes().getResId() == resid)  // && getAllTableDetails().get(i).getTableNo() == t.getTableNo()
			{
				tList.set(i, t);
			}

			Table k = tList.get(i);

			String resitem = k.getTableNo() + "," + k.getSeatCapacity() + "," + k.getTableDate() +  "," +  k.getTableTime()+ "," + k.getStatus() + "," + k.getTableRes().getResId();
			l.add(resitem);
		}

		replace(filename, l);
	}

	public static void deleteTable(int resID) throws IOException
	{
		List l = new ArrayList<>();
		ArrayList<Table> tLists = getAllTableDetails();
		for(int i=0; i<tLists.size(); i++) {
			if(tLists.get(i).getTableRes().getResId() != resID)
			{
				Table t = tLists.get(i);
				String res = t.getTableNo() + "," + t.getSeatCapacity() + "," + t.getTableDate() +  "," +  t.getTableTime() + "," + t.getStatus() + "," + t.getTableRes().getResId();
				l.add(res);
			}
		}
		replace(filename,l);
	}

	//READ AND WRITE TO CSV
	private static List read(String filename) throws IOException {

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

				out.write((String) data.get(i)+"\n");
				//	out.write("\n" + (String) data.get(i)+"\n");
			}
		} finally {
			out.close();
		}
	}


	private static void replace(String filename, List data) throws IOException {

		BufferedWriter out = new BufferedWriter(new FileWriter(filename));
		try {
			out.write("tablno" + "," + "tablcap" + "," + "date" + "," + "time" + "," + "status" + "," + "resid" + "\n");
			for (int i = 0; i < data.size(); i++) {

				out.write((String) data.get(i) + "\n");
			}
		} finally {
			out.close();
		}
	}


}
