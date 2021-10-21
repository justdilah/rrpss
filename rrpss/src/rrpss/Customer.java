package rrpss;

import java.util.*;

public class Customer extends Person {

	Collection<Order> order;
	private int custId;
	private Boolean membership;

	public int getCustId() {
		return this.custId;
	}

	/**
	 * 
	 * @param custId
	 */
	public void setCustId(int custId) {
		this.custId = custId;
	}

	public Boolean getMembership() {
		return this.membership;
	}

	/**
	 * 
	 * @param membership
	 */
	public void setMembership(Boolean membership) {
		this.membership = membership;
	}

}