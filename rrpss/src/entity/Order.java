package entity;

import java.util.*;

public class Order {

	Staff staff;
	Customer customer;
	Collection<OrderItem> orderitem;
	Collection<SetPackage> setpack;
	Collection<AlaCarte> menuitem;
	Invoice invoice;
	private int orderId;
	private String timeStamp;
	private int staffId;
	private Boolean isPaid;

	public int getOrderId() {
		return this.orderId;
	}

	/**
	 * 
	 * @param orderId
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getTimeStamp() {
		return this.timeStamp;
	}

	/**
	 * 
	 * @param timeStamp
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

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

	public Boolean getIsPaid() {
		return this.isPaid;
	}

	/**
	 * 
	 * @param isPaid
	 */
	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

}