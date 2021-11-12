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

public class Table {
	
	private static final String filename = "DataSet/Table.csv";

	private List<Table> tables = new ArrayList<>();
	
	// enumeration of table capacity 
	private enum TableCapacity {
		TWO_SEATER,
		FOUR_SEATER,
		SIX_SEATER,
		EIGHT_SEATER,
		TEN_SEATER
	}
	
	// enumeration of table status 
	private enum TableStatus {
		VACANT,
		OCCUPIED,
		RESERVED
	}
	
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

	public Boolean tableExists(int tableNo) throws FileNotFoundException {
		for(int i=0; i<getAllTableDetails().size();i++) {

			if(getAllTableDetails().get(i).getTableNo() == tableNo) {
				return true;
			}
		}
		return false;
	}
	
	public Table getTableByTableNo(int tableNo) throws FileNotFoundException {
		Table t = null;
		for(int i=0; i<getAllTableDetails().size();i++) {

			if(getAllTableDetails().get(i).getTableNo() == tableNo) {
				t = getAllTableDetails().get(i);
			}
		}
		return t;
	}



	public void createCapTable()
	{
		addTable(1, 2);
		addTable(2, 2);
		addTable(3, 4);
		addTable(4, 4);
		addTable(5, 6);
		addTable(6, 6);
		addTable(7, 8);
		addTable(8, 8);
		addTable(9, 10);
		addTable(10, 10);
	}


	public void addTable(int id, int max)
	{
		tables.add(new Table(id, max));
	}


	public int returnINTcapcity(TableCapacity cap) {
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


	private TableCapacity returnTScapcity(int cap) {
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


	public Table getTableByResId(int resid) throws IOException
	{
		Table t = null;

		for(int i=0; i<getAllTableDetails().size();i++) {

			if(getAllTableDetails().get(i).getTableRes().getResId() == resid) {
				t = getAllTableDetails().get(i);
			}
		}

		return t;
	}


	// csv
	public ArrayList<Table> getAllTableDetails() throws FileNotFoundException
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

				Reservation tempR = new Reservation();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate convertdate = LocalDate.parse(date,formatter);
				DateTimeFormatter tformatter = DateTimeFormatter.ofPattern("HH:mm");
				LocalTime converttime = LocalTime.parse(time,tformatter);

				Table t = new Table(Integer.parseInt(tableno), TableCapacity.valueOf(cap), convertdate, converttime,
						TableStatus.valueOf(tstatus), tempR.getResById(Integer.parseInt(res_id)));
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

		for(int i=0; i<getAllTableDetails().size();i++) 
		{
			if(getAllTableDetails().get(i).getSeatCapacity().equals(cap))
					if(getAllTableDetails().get(i).getStatus().equals(TableStatus.VACANT)) 
					{
						t = getAllTableDetails().get(i);
					}
		}
		return t;
	}
	
	public ArrayList<Table> getAllReservedAndOccupiedTables() throws FileNotFoundException
	{
		ArrayList<Table> tableList = new ArrayList<>();
		
		int checker = 0;

		for(int i=0; i<getAllTableDetails().size();i++) 
		{
				if(getAllTableDetails().get(i).getStatus().equals(TableStatus.OCCUPIED) || getAllTableDetails().get(i).getStatus().equals(TableStatus.RESERVED)) 
				{
					tableList.add(getAllTableDetails().get(i));
					checker++;
				}
		}
		
		if(checker == 0) {
			tableList = null;
		}
		return tableList;
	}
	
	public ArrayList<Table> getAllOccupiedTables() throws FileNotFoundException
	{
		ArrayList<Table> tableList = new ArrayList<>();
		
		int checker = 0;

		for(int i=0; i<getAllTableDetails().size();i++) 
		{
				if(getAllTableDetails().get(i).getStatus().equals(TableStatus.OCCUPIED)) 
				{
					tableList.add(getAllTableDetails().get(i));
					checker++;
				}
		}
		
		if(checker == 0) {
			tableList = null;
		}
		return tableList;
	}

	public Boolean checkTableReserved(int tableNo) throws FileNotFoundException {

	
		if(getTableByTableNo(tableNo).getStatus().equals(TableStatus.RESERVED)) 
		{
			return true;
		}
		return false;
	}
	
	public void updateTableStatusString(String status,int tableNo) throws IOException 
	{
		TableStatus t = null;
		switch(status) {
			case "OCCUPIED":
				t = TableStatus.OCCUPIED;
				break;
			case "VACANT":
				t = TableStatus.VACANT;
				break;
			case "RESERVED":
				t = TableStatus.RESERVED;
				break;
		}
		Table table = getTableByTableNo(tableNo);
		table.setStatus(t);
		updateTableStatus(table);
		
	}

	
	public void updateTableStatus(Table t) throws IOException 
	{
		List l = new ArrayList<>();
		ArrayList<Table> tList = getAllTableDetails();

		for(int i=0;i<getAllTableDetails().size();i++)
		{	
			if(getAllTableDetails().get(i).getTableNo() == t.getTableNo()) 
			{
				tList.set(i, t);
			}

			Table k = tList.get(i);

			String item = k.getTableNo() + "," + k.getSeatCapacity() + "," + k.getStatus() ;
			l.add(item);
		}

		replace(filename, l);		
	}


	public ArrayList<Table> getAllRservedTables() throws FileNotFoundException
	{
		ArrayList<Table> tableList = new ArrayList<>();

		int checker = 0;

		for(int i=0; i<getAllTableDetails().size();i++)
		{
			if(getAllTableDetails().get(i).getStatus().equals(TableStatus.RESERVED))
			{
				tableList.add(getAllTableDetails().get(i));
				checker++;
			}
		}

		if(checker == 0) {
			tableList = null;
		}
		return tableList;
	}

	public Table reserveTable(LocalDate date, LocalTime time, int numOfGuests, int resid) throws IOException
	{
		Table temp = new Table();

		// get first available table
		for (Table table : tables)
		{
			int max = table.returnINTcapcity(table.getSeatCapacity());

			if ( max >= numOfGuests && table.isFree(date,time,table.getTableNo()))
			{
				table.insertTable(table.getTableNo(), table.getSeatCapacity(), date, time, resid);

				temp = table;
				return temp;
			}
		}
		return temp;
	}


	public Table checkAvailTable(LocalDate date, LocalTime time, int numOfGuests, int resid) throws IOException
	{
		Table temp = new Table();

		for (Table table : tables)
		{
			int max = table.returnINTcapcity(table.getSeatCapacity());

			if ( max >= numOfGuests && table.isFree(date,time,table.getTableNo()))
			{
				temp = table;
				return temp;
			}
		}
		return temp;
	}

	public void insertTable(int no, TableCapacity cap, LocalDate date, LocalTime time, int res_id) throws IOException
	{
		//need to update both reservation and table Table

		String res = no + "," + cap.toString() + "," + date +  "," +  time + "," + TableStatus.RESERVED + "," + res_id;
		List l = new ArrayList();
		l.add(res);
		write(filename, l);
	}

	public boolean isFree(LocalDate arrDate, LocalTime arrTime, int tableNo) throws IOException
	{
		boolean free = true;
		ArrayList<Table> tList = getAllTableDetails();

		for(int i=0; i<tList.size();i++)
		{ //if cannot find any matching table = the table is vacant
			if(tList.get(i).getTableNo()==tableNo) //if tableno is equal
			{
				if(tList.get(i).getTableDate().equals(arrDate)) { //if date is equal then compare time
					if(tList.get(i).getTableTime().equals(arrTime))  //means this specific table is taken
						//if(!getAllTableDetails().get(i).getStatus().equals(TableStatus.VACANT))
						return false;
					else if((arrTime.isAfter(tList.get(i).getTableTime())) &&
							(arrTime.isBefore(tList.get(i).getTableTime().plusHours(2)))) { //means the user selected time within 2 hours of some reservation
						return false;
					}
				}
			}
			else {
				//do nothing
			}

		}
		return free;
	}

	public void updateTable(Table t, int resid) throws IOException
	{
		List l = new ArrayList<>();
		ArrayList<Table> kList = getAllTableDetails();

		for(int i=0;i<getAllTableDetails().size();i++)
		{
			if(getAllTableDetails().get(i).getTableRes().getResId() == resid)  // && getAllTableDetails().get(i).getTableNo() == t.getTableNo()
			{
				kList.set(i, t);
			}

			Table k = kList.get(i);

			String resitem = k.getTableNo() + "," + k.getSeatCapacity() + "," + k.getTableDate() +  "," +  k.getTableTime()+ "," + k.getStatus() + "," + k.getTableRes().getResId();
			l.add(resitem);
		}

		replace(filename, l);
	}

	public ArrayList<Integer> deletePastReservationTable() throws IOException
	{
		ArrayList<Integer> listOfDelete = new ArrayList<Integer>();
		try {
			ArrayList<Table> tlist= new ArrayList<>(this.getAllTableDetails());
			for (int i=0;i<tlist.size();i++) {
				LocalDate tableDate = tlist.get(i).getTableDate(); //get the current table date
				LocalTime table = tlist.get(i).getTableTime(); //get the current table time
				LocalDate todayDate = LocalDate.now();
				LocalTime currTime = LocalTime.now();
				int temp_resID;
				if(tableDate.isBefore(todayDate)){ // those table reservation that is before today's date
					temp_resID = tlist.get(i).getTableRes().getResId();
					this.deleteTable(temp_resID);
					listOfDelete.add(temp_resID);
				}
				else {
					//do nothing
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return listOfDelete;
	}

	public ArrayList<Integer> deleteLateComerTable() throws IOException
	{
		ArrayList<Integer> listOfDelete = new ArrayList<Integer>();
		try {
			ArrayList<Table> tlist= new ArrayList<>(this.getAllTableDetails());
			for (int i=0;i<tlist.size();i++) {
				LocalDate tableDate = tlist.get(i).getTableDate(); //get the current table date
				LocalTime tableTime = tlist.get(i).getTableTime(); //get the current table time
				LocalDate todayDate = LocalDate.now();
				LocalTime currTime = LocalTime.now();
				TableStatus tempStatus = tlist.get(i).getStatus();
				int temp_resID;
				if((tableDate.equals(todayDate)) && (tempStatus.equals(TableStatus.RESERVED))) { //those reservation that is booked today AND those reserved one
					int late = checkLateOrNot(currTime,tableTime); //if -1 means OK, if not: late
					if (late!=-1) { // this table needs to be deleted
						temp_resID = tlist.get(i).getTableRes().getResId();
						this.deleteTable(temp_resID);
						listOfDelete.add(temp_resID);
					}
				}
				else {
					//do nothing
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return listOfDelete;
	}

	public int checkLateOrNot(LocalTime currentT, LocalTime resT)
	{
		int result = -1; // default value to indicate the table reservation is before current time, so is don't need to delete it
		if(currentT.isAfter(resT)) { //for those table reservation time that is before current time, we ignore them. Thus we only check those after (potential late res)
			int late_mins = (int) resT.until(currentT, ChronoUnit.MINUTES); //this will return how many minutes they are late
			if (late_mins>10) { //if the customer is 10 minutes late
				return late_mins; // return a value that is not -1, meaning that reservation should be deleted.
			}
		}
		return result; //return default value -1
	}

	public void deleteTable(int resID) throws IOException
	{
		List l = new ArrayList<>();
		ArrayList<Table> tLists = getAllTableDetails();
		for(int i=0; i<tLists.size(); i++) {
			if(tLists.get(i).getTableRes().getResId() == resID) {
				//do nothing
			}
			else {
				Table t = tLists.get(i);
				String res = t.getTableNo() + "," + t.getSeatCapacity() +
						"," + t.getTableDate() +  "," +  t.getTableTime() + "," + t.getStatus() + "," + t.getTableRes().getResId();
				l.add(res);
			}
		}
		replace(filename,l);
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

				out.write((String) data.get(i)+"\n");
				//	out.write("\n" + (String) data.get(i)+"\n");
			}
		} finally {
			out.close();
		}
	}


	private void replace(String filename, List data) throws IOException {

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
