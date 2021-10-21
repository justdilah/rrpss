package rrpss;

import java.util.*;

public class Staff extends Person {

	ArrayList<Order> order;
	private int staffId;
	private String staffTitle;

	public int getStaffId() {
		return this.staffId;
	}

	/**
	 * 
	 * @param staffId
	 */
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getStaffTitle() {
		return this.staffTitle;
	}

	/**
	 * 
	 * @param staffTitle
	 */
	public void setStaffTitle(String staffTitle) {
		this.staffTitle = staffTitle;
	}

}