package entity;

import java.util.*;

public class Staff extends Person {

	private int staffId;
	private String staffTitle;
	Collection<Order> order;

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