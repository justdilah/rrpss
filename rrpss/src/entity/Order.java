package entity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

import controller.StoreController;

public class Order {
	private static final String filename = "DataSet/Order.csv";
	
	private int orderId;
	private LocalTime timeStamp;
	private Boolean isPaid;
	private String staffId;
	Staff waiter;
	Customer customer;
	Table table;
	ArrayList <OrderItem> orderItemList;
	Invoice invoice;
	
	
	public Order() {
		this.orderItemList = new ArrayList<OrderItem>();
	}
	
	public Order(Table t, Customer cust, String staffid) {
		this.orderItemList = new ArrayList<OrderItem>();
		this.table = t;
		this.staffId = staffid;
	}
	
	public Order(int id, LocalTime timeStamp, Boolean isPaid, String staffId, Customer cust) {
		this.orderId = id;
		this.timeStamp = timeStamp;
		this.isPaid = isPaid;
		this.staffId = staffId;
		this.customer = cust;
	}

	public int getOrderId() {
		return this.orderId;
	}
	
	public void setTable(Table t) {
		this.table = t;
	}
	
	public Table getTable() {
		return this.table;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public int getCustIDByPhoneNumber(String pn) throws FileNotFoundException {
		Customer c = new Customer();
		
		for(int i = 0;i<c.getAllCustomerDetails().size();i++) {
			if(c.getAllCustomerDetails().get(i).getPersPhoneNo().equals(pn)) {
				return c.getAllCustomerDetails().get(i).getCustId();
			}
		}
		return 0;
	}
	

	
	public void addPromoOrderItemtoList(int id, PromotionSet i,int qty) throws IOException {
		OrderItem o = new OrderItem(id,i,qty);
		o.addOrderItem();
		this.orderItemList.add(o);
	}
	
	public void addAlaCarteOrderItemtoList(int id, AlaCarte a, int qty) throws IOException {
		OrderItem o = new OrderItem(id,a,qty);
		o.addOrderItem();
		this.orderItemList.add(o);
	}
	
	public LocalTime getTimeStamp() {
		return this.timeStamp;
	}	
	
	/**
	 * 
	 * @param t
	 */
	public void setTimeStamp(LocalTime t) {
		this.timeStamp = t;
	}

	public String getStaffId() {
		return this.staffId;
	}

	/**
	 * 
	 * @param staffId
	 */
	public void setStaffId(String staffId) {
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
	
	public void saveFoodItem(List list) throws IOException {
		StoreController.write(filename, list);
	}

	// GET ALL THE ORDERS
	public ArrayList<Order> getAllOrders() throws FileNotFoundException {
		ArrayList<Order> psList= new ArrayList<>();
		
		ArrayList stringitems = (ArrayList) StoreController.read(filename); 	
		
		OrderItem o = new OrderItem();
		Customer c = new Customer();
		for (int i = 0; i < stringitems.size(); i++) {
			String st = (String) stringitems.get(i);
			StringTokenizer star = new StringTokenizer(st, ",");
			String orderId = star.nextToken().trim();
			String timeOrdered = star.nextToken().trim();
			String staffId = star.nextToken().trim();
			String isPaid = star.nextToken().trim();	
			String custid = star.nextToken().trim(); 
			Order m = new Order(Integer.parseInt(orderId),LocalTime.now(), Boolean.valueOf(isPaid),staffId,c.getCustById(Integer.parseInt(custid)));
			m.setOrderItemList(o.getAllOrderItems(Integer.parseInt(orderId)));
			psList.add(m);
		}
		return psList;
	}
	

}