 package entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import controller.OrderController;
import controller.OrderItemController;
import controller.StoreController;

public class Order {
	
	OrderItemController oiControl = new OrderItemController();
	
	private static final String filename = "DataSet/Order.csv";
	private int orderId;
	private LocalTime timeStamp;
	private LocalDate date;
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
	
	public Order(int id, LocalTime timeStamp, LocalDate date, Boolean isPaid, String staffId, Customer cust, Table t) {
		this.orderId = id;
		this.timeStamp = timeStamp;
		this.isPaid = isPaid;
		this.staffId = staffId;
		this.customer = cust;
		this.table = t;
		this.date = date;
		Invoice invoice = new Invoice();
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
	
	public LocalDate getDate() {
		return this.date;
	}	
	
	/**
	 * 
	 * @param t
	 */
	public void setDate(LocalDate t) {
		this.date = t;
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
	
	public Customer getCust() {
		return this.customer;
	}

	
	public ArrayList<OrderItem> getOrderItemList() {
		return this.orderItemList;
	}
	
	public ArrayList<Order> getUnpaidOrders() throws IOException {
		ArrayList<Order> unpaidList = new ArrayList<>();
		int counter = 0;
		if(getAllOrders() != null) {
			for(int i=0;i<getAllOrders().size();i++) {
				if(getAllOrders().get(i).getIsPaid() == false) {
					counter++;
					unpaidList.add(getAllOrders().get(i));
				}
			}
		}
		if(counter==0) {
			unpaidList = null;
		}
		return unpaidList;
	}
	
	public void addPromoOrderItemtoList(int id, PromotionSet i,int qty, int orderId) throws IOException {
		OrderItem o = new OrderItem(id,i,qty,orderId);
		oiControl.addOrderItem(o);
	}
	
	public void addAlaCarteOrderItemtoList(int id, AlaCarte a, int qty,int orderId) throws IOException {
		OrderItem o = new OrderItem(id,a,qty, orderId);
		oiControl.addOrderItem(o);
	}
	
	// RETURN PROMOTION SET ITEMS
	public ArrayList<PromotionSet> getAllPromoSets() throws FileNotFoundException {
		return oiControl.getAllPromoSets();
	}
	
	// RETURN ALA CARTE ITEMS
	public ArrayList<AlaCarte> getAllAlaCartItems() throws FileNotFoundException {
		return oiControl.getAllAlaCartItems();
	}
	
	
	public void removeOrderItemFromList(int orderId, OrderItem o) throws IOException {
		OrderItem i = new OrderItem();
		i.removeOrderItem(orderId,o.getOrderItemId());
	}
	
	public Order selectOrderById(int id) throws IOException {
		Order o = null;
		
		for(int i=0; i<getAllOrders().size();i++) {
						
			if(getAllOrders().get(i).getOrderId() == id) {
				o = getAllOrders().get(i);
			}
		}
		return o;
	}
	
	public int getCustIDByPhoneNum(String contact) throws IOException {
		Customer c = new Customer();
		
		for(int i=0; i<c.getAllCustomerDetails().size();i++) {
			
			String k = c.getAllCustomerDetails().get(i).getPersPhoneNo();
			if(c.getAllCustomerDetails().get(i).getPersPhoneNo().equals(contact)) {
				c = c.getAllCustomerDetails().get(i);
			}
		}
		return c.getCustId();
	}
	
	public OrderItem selectOrderItemById(int id) throws FileNotFoundException {
		OrderItem o = null;
		
		for(int i=0; i<getOrderItemList().size();i++) {
						
			if(getOrderItemList().get(i).getOrderItemId() == id) {
				o = getOrderItemList().get(i);
			}
		}
		return o;
	}

	/**
	 * 
	 * @param orderItemList
	 */
	public void setOrderItemList(ArrayList<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	
	// SAVE THE ORDER
	public void saveOrder(List list) throws IOException {
		write(filename, list);
	}
	
	public void replaceOrder(Order o) throws IOException {
		
		List l = new ArrayList<>();
		ArrayList<Order> miList = getAllOrders();
		
		OrderItem item = new OrderItem();
		
		int size = getAllOrders().size();
		
		for(int i=0;i<size;i++) {
			
			if(miList.get(i).getOrderId() == o.getOrderId()) {
				miList.set(i, o);
			} 
			
			Order k = miList.get(i);
			String order = k.getOrderId() + "," + k.getTimeStamp() +  "," + k.getDate() +  "," +  k.getStaffId() + "," + k.getIsPaid() + "," + k.getCust().getCustId() + "," +  k.getTable().getTableNo();
			l.add(order);
			
			
		}
		replace(filename,l);
	}
	
	// REMOVE ORDER ITEM FROM THE ORDER ITEM LIST
	public void removeOrderItem(int orderId, int orderItemId) throws IOException {
		ArrayList<OrderItem> orderItemList = selectOrderById(orderId).getOrderItemList();
		orderItemList.remove(selectOrderItemById(orderItemId));
	}

	// GET ALL THE ORDERS
	public ArrayList<Order> getAllOrders() throws IOException {
		ArrayList<Order> psList= new ArrayList<>();
		ArrayList stringitems = (ArrayList) read(filename);
		Table t = new Table();
		if(stringitems.size() > 0) {
			OrderItem o = new OrderItem();
			Customer c = new Customer();
			for (int i = 0; i < stringitems.size(); i++) {
				String st = (String) stringitems.get(i);
				StringTokenizer star = new StringTokenizer(st, ",");
				String orderId = star.nextToken().trim();
				String timeOrdered = star.nextToken().trim();
				String dateOrdered = star.nextToken().trim();
				String staffId = star.nextToken().trim();
				String isPaid = star.nextToken().trim();	
				String custid = star.nextToken().trim(); 
				String tableNo = star.nextToken().trim();
				int no = Integer.parseInt(tableNo);
				
				DateTimeFormatter tformatter = DateTimeFormatter.ofPattern("HH:mm");
				LocalTime converttime = LocalTime.parse(timeOrdered,tformatter);
				
				LocalDate date = LocalDate.parse(dateOrdered);
				
				Order m = new Order(Integer.parseInt(orderId),converttime,date, Boolean.valueOf(isPaid),staffId,c.getCustById(Integer.parseInt(custid)),t.getTableByTableNo(no));
				m.setOrderItemList(o.getOrderItems(Integer.parseInt(orderId)));
				psList.add(m);
			}
		} else {
			psList = null;
		}
		return psList;
	}
	
	// DELETE THE ORDER
	public void deleteOrder(int orderId) throws IOException {		
		
		List l = new ArrayList<>();
		ArrayList<Order> miList = getAllOrders();
		
		OrderItem item = new OrderItem();
		
		int size = getAllOrders().size();
		
		for(int i=0;i<size;i++) {
			
			if(miList.get(i).getOrderId() == orderId) {
				item.removeEntireOrderItemList(orderId);
				miList.remove(i);
			} else {
				Order k = miList.get(i);
				String foodItem = k.getOrderId() + "," + k.getTimeStamp() + "," + k.getDate() +"," + k.getStaffId() + "," + k.getIsPaid() +  "," + k.getCust().getCustId() + "," + k.getTable().getTableNo();
				l.add(foodItem);
			}
			
			
		}
		replace(filename,l);
	}
	
	//READ AND WRITE TO CSV
	private List read(String filename) throws IOException {
		List data = new ArrayList();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String headerLine = reader.readLine();
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
			}
		} finally {
			out.close();
		}
	}
	
	private void replace(String filename, List data) throws IOException {
		
		BufferedWriter out = new BufferedWriter(new FileWriter(filename));
		try {
			out.write("OrderID" + "," + "TimeOrdered" + "," + "DateOrdered" + "," + "StaffID" + "," + "IsPaid" + "," +  "CustID" + "," +  "TableNo" + "\n");
			for (int i = 0; i < data.size(); i++) {
				
				out.write((String) data.get(i) + "\n");
			}
		} finally {
			out.close();
		}
	}
	

}