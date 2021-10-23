package entity;

import java.util.*;

public class Order {

	private int orderId;
	private String timeStamp;
	private Boolean isPaid;
	private int staffId;
	Staff waiter;
	Customer customer;
	Table table;
	Collection<OrderItem> orderitem;
	Invoice invoice;
	private ArrayList<OrderItem> orderItemList;

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

	public ArrayList<OrderItem> getOrderItemList() {
		return this.orderItemList;
	}

	/**
	 * 
	 * @param orderItemList
	 */
	public void setOrderItemList(ArrayList<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

}