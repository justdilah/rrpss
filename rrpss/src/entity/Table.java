package entity;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Table {
	
	private static final String filename = "DataSet/Table.csv";
	
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
	

	public Table()
	{
		
	}
	
	public Table(int no, TableCapacity cap, TableStatus tstatus)
	{
		tableNo = no;
		seatCapacity = cap;
		status = tstatus;
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
	
//	public Table checkTableAvail(int pax) throws IOException 
//	{
//		Table t = new Table();
//
//		if (pax >=1 && pax <= 2)
//		{
//			seatCapacity = TableCapacity.valueOf("TWO_SEATER");
//			t = getAllVacantTableByCapacity(seatCapacity);
//			
//			if (t == null)
//			{
//				System.out.println("Table of 2 is not available.");
//				return t;
//				// what to do here ?
//			}
//			
//			updateTableStatus(t);			
//		}
//		else if (pax >=3 && pax <=4)
//		{
//			seatCapacity = TableCapacity.valueOf("FOUR_SEATER");
//			t = getAllVacantTableByCapacity(seatCapacity);
//			if (t == null)
//			{
//				System.out.println("Table of 4 is not available.");
//				return t;
//				// what to do here ?
//			}
//			
//			updateTableStatus(t);	
//		}
//		else if (pax >=5 && pax <=6)
//		{
//			seatCapacity = TableCapacity.valueOf("SIX_SEATER");
//			t= getAllVacantTableByCapacity(seatCapacity);
//			if (t == null)
//			{
//				System.out.println("Table of 6 is not available.");
//				return t;
//				// what to do here ?
//			}
//			
//			updateTableStatus(t);	
//		}
//		else if (pax >=7 && pax <=8)
//		{
//			seatCapacity = TableCapacity.valueOf("EIGHT_SEATER");
//			t= getAllVacantTableByCapacity(seatCapacity);
//			if (t == null)
//			{
//				System.out.println("Table of 8 is not available.");
//				return t;
//				// what to do here ?
//			}
//			
//			updateTableStatus(t);	
//		}
//		else if (pax >=9 && pax <=10)
//		{
//			seatCapacity = TableCapacity.valueOf("TEN_SEATER");
//			t= getAllVacantTableByCapacity(seatCapacity);
//			if (t == null)
//			{
//				System.out.println("Table of 10 is not available.");
//				return t;
//				// what to do here ?
//			}
//			
//			updateTableStatus(t);	
//		}
//		return t;
//	}
	
	
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
	
	
	
	// csv
	public ArrayList<Table> getAllTableDetails() throws FileNotFoundException
	{
		ArrayList<Table> tlist= new ArrayList<>();
		ArrayList stringitems = (ArrayList)read(filename); 	

		for (int i = 0; i < stringitems.size(); i++) 
		{
			String st = (String) stringitems.get(i);
			StringTokenizer star = new StringTokenizer(st, ",");

			String tableno = star.nextToken().trim();
			String cap =  star.nextToken().trim();
			String tstatus =  star.nextToken().trim();

			Table t = new Table(Integer.parseInt(tableno), TableCapacity.valueOf(cap), TableStatus.valueOf(tstatus));
			tlist.add(t);
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

//	private void write(String filename, List data) throws IOException {
//		BufferedWriter out = new BufferedWriter(new FileWriter(filename,true));
//		try {
//			for (int i = 0; i < data.size(); i++) {
//
//				out.write((String) data.get(i)+"\n");
//			}
//		} finally {
//			out.close();
//		}
//	}

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
