package entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import controller.ReservationController;

/**
 * This class represents a table for the restaurant.
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class Table {

	/**
	 * The filepath of the CSV file.
	 */
	private static final String filename = "DataSet/Table.csv";


	/**
	 * Unique table number assigned to each table.
	 */
	private int tableNo;

	/**
	 * Maximum capacity of the table.
	 */
	private TableCapacity seatCapacity;

	/**
	 * Current status of the table.
	 */
	private TableStatus status;

	/**
	 * The date of reservation of the table.
	 */
	private LocalDate tableDate;

	/**
	 * The time of reservation of the table.
	 */
	private LocalTime tableTime;

	/**
	 * The reservation belongs to the table.
	 */
	private Reservation tableRes;

	/**
	 * Default constructor of Reservation
	 */
	public Table()
	{

	}

	/**
	 * Constructor for the different tables available in the restaurant.
	 * @param no int value that uniquely identify a table.
	 * @param cap int value that indicates the maximum capacity of a table.
	 */
	public Table(int no, int cap)
	{
		tableNo = no;
		seatCapacity = this.returnTScapcity(cap);
		status = TableStatus.VACANT;
	}

	/**
	 * Constructor for the reserved table.
	 * @param no int value that uniquely representing a table.
	 * @param cap TableCapcity value that indicates the maximum seater of a table.
	 * @param date LocalDate value that indicate the date of the reservation.
	 * @param time LocalTime value that indicate the time of the reservation.
	 * @param tstatus TableStatus value represents the current status of the table.
	 * @param res Reservation object that uniquely belongs to a table.
	 */
	public Table(int no, TableCapacity cap, LocalDate date, LocalTime time, TableStatus tstatus, Reservation res)
	{
		tableNo = no;
		seatCapacity = cap;
		status = tstatus;
		this.tableDate = date;
		this.tableTime = time;
		this.tableRes = res;
	}


	/**
	 * This method gets the unique table number and return it.
	 * @return tableNo
	 */
	public int getTableNo() {
		return tableNo;
	}

	/**
	 * This method sets the table number.
	 * @param tableNo int value that represents the table number to be set.
	 */
	public void setTableNo(int tableNo) {
		this.tableNo = tableNo;
	}

	/**
	 * This method gets the unique table number and return it.
	 * @return seatCapacity
	 */
	public TableCapacity getSeatCapacity() {
		return seatCapacity;
	}

	/**
	 * This method sets the table capacity.
	 * @param seatCapacity TableCapacity value that represents the capacity to be set.
	 */
	public void setSeatCapacity(TableCapacity seatCapacity) {
		this.seatCapacity = seatCapacity;
	}

	/**
	 * This method gets the table current status and return it.
	 * @return status
	 */
	public TableStatus getStatus() {
		return status;
	}

	/**
	 * This method sets the table status.
	 * @param status TableStatus value that represents the status to be set.
	 */
	public void setStatus(TableStatus status) {
		this.status = status;
	}

	/**
	 * This method gets the table date and return it.
	 * @return tableDate
	 */
	public LocalDate getTableDate() {
		return tableDate;
	}

	/**
	 * This method sets the table date.
	 * @param tableDate LocalDate value that represents the date to be set.
	 */
	public void setTableDate(LocalDate tableDate) {
		this.tableDate = tableDate;
	}

	/**
	 * This method gets the table time and return it.
	 * @return tableTime
	 */
	public LocalTime getTableTime() {
		return tableTime;
	}

	/**
	 * This method sets the table time.
	 * @param tableTime LocalTime value that represents the time to be set.
	 */
	public void setTableTime(LocalTime tableTime) {
		this.tableTime = tableTime;
	}

	/**
	 * This method gets the table reservation and return it.
	 * @return tableRes
	 */
	public Reservation getTableRes() {
		return tableRes;
	}

	/**
	 * This method sets the table reservation.
	 * @param tableRes Reservation object that to be set.
	 */
	public void setTableRes(Reservation tableRes) {
		this.tableRes = tableRes;
	}


	/**
	 * This methods reads the csv file and check whether the given table number exists and return a boolean value.
	 * @param tableNo int value that represents an unique table to be compared and checked.
	 * @return True if found such table, or false otherwise.
	 * @throws FileNotFoundException Display error message if any I/O error found while retrieving into the table records.
	 */
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

	/**
	 * This method convert an given enum TableCapacity value into a integer value and return it.
	 * @param cap TableCapcity value to be converted into integer.
	 * @return Value that corresponds to each different capacity value.
	 */
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


	/**
	 * This method convert an given integer value back to a TableCapcity enum value and return it.
	 * @param cap int value to be converted into TableCapacity enum value.
	 * @return Value that corresponds to each different capacity value.
	 */
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


	/**
	 * This method reads the CSV file to retrieve all details from the table CSV and return them.
	 * @return The whole list of reserved table details
	 * @throws FileNotFoundException Display error message if any I/O error found while retrieving the Table Records.
	 */
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

	/**
	 * This method updates the given table and store into table CSV.
	 * @param t Table Object that represents the table to be updated.
	 * @throws IOException Display error message if any I/O errors found while updating into the table records.
	 */

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

			String item = k.getTableNo() + "," + k.getSeatCapacity() + "," + k.getTableDate() +  "," +  k.getTableTime()+ "," + k.getStatus() + "," + k.getTableRes().getResId();
			l.add(item);
		}

		replace(filename, l);
	}

	/**
	 * This methods inserts a new table into table CSV given the parameters.
	 * @param no int value represents the unique table number of a table to be added.
	 * @param cap TableCapacity value represents the maximum capacity of the table.
	 * @param date LocalDate value that represents the date of the reservation.
	 * @param time LocalTime value that represents the time of the reservation.
	 * @param res_id int value that represents the unique reservation belongs to the table.
	 * @throws IOException Display error message if any I/O errors found while inserting into the table records.
	 */
	public void insertTable(int no, TableCapacity cap, LocalDate date, LocalTime time, int res_id) throws IOException
	{
		//need to update both reservation and table Table

		String res = no + "," + cap.toString() + "," + date +  "," +  time + "," + TableStatus.RESERVED + "," + res_id;
		List l = new ArrayList();
		l.add(res);
		write(filename, l);
	}

	/**
	 * This method updates the given table (identified by its reservationID) with a new table value.
	 * @param t Table object contains all the new information to be updated.
	 * @param resid int value to identify which table to be updated.
	 * @throws IOException Display error message if any I/O errors found while updating into the table records.
	 */
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

	/**
	 * This method  delete the specific table (identified by its reservationID) from the table CSV.
	 * @param resID int value to identify which table to be deleted.
	 * @throws IOException Display error message if any I/O errors found while deleting into the table records.
	 */
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

	/**
	 * This method reads the table CSV
	 * @param filename The filepath of the CSV file
	 * @return The list of records read from the table CSV
	 * @throws IOException Display error message if any I/O error found while accessing the table records.
	 */
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

	/**
	 * This method writes the reservation CSV
	 * @param filename The filepath of the CSV file
	 * @param data A list of table records
	 * @throws IOException Display error message if any I/O error found while inserting into the table records.
	 */
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


	/**
	 * This method writes a new set of data and stores into the table CSV.
	 * @param filename The filepath of the CSV file
	 * @param data A list of table records
	 * @throws IOException Display error message if any I/O error found while inserting into the table records.
	 */
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
