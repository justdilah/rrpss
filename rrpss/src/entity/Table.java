package entity;

import java.util.*;

public class Table {

	ArrayList<Reservation> reservation;
	ArrayList<Invoice> invoice;
	private int tableNo;
	private TableCapacity seatCapacity;
	private TableStatus status;

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
		return this.seatCapacity;
	}

	/**
	 * 
	 * @param seatCapacity
	 */
	public void setSeatCapacity(TableCapacity seatCapacity) {
		this.seatCapacity = seatCapacity;
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