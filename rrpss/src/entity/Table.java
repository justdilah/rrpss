package entity;

import java.util.*;

public class Table {

	private static final String filename = "DataSet/Table.csv";
	
	private int tableNo;
	private TableCapacity seatCapacity;
	private TableStatus status;

	
	public Table(int no, TableCapacity seatCapacity, TableStatus ts,Order order, ArrayList<Reservation> reservation) {
		this.tableNo = no;
		this.seatCapacity = seatCapacity;
		this.status = ts;
	}
	
	public int getTableNo() {
		return this.tableNo;
	}
	
	
	/**
	 * 
	 * @param tableNo
	 */
	public void setTableNo(int tableNo) {
		this.tableNo = tableNo;
	}

	public TableCapacity getSeatCapacity() {
		return seatCapacity;

	}

	/**
	 * 
	 * @param seatCapacity
	 */
	public void setSeatCapacity(TableCapacity seatCapacity) {

	}

	public TableStatus getStatus() {
		return this.status;
	}

	/**
	 * 
	 * @param status
	 */
	public void setStatus(TableStatus status) {
		this.status = status;
	}

}