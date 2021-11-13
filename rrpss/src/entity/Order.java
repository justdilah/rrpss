 package entity;

import controller.CustomerController;
import controller.OrderController;
import controller.ResTableController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;



public class Order {

	private static final String filename = "DataSet/Order.csv";
	private int orderId;
	private LocalTime timeStamp;
	private LocalDate date;
	private Boolean isPaid;
	private String staffId;
//	Staff waiter;
	Customer customer;
	Table table;
	ArrayList <OrderItem> orderItemList; 
//	Invoice invoice;
	
	public Order() {
		this.orderItemList = new ArrayList<OrderItem>();
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
	
	public void setCust(Customer cust) {
		this.customer = cust;
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
	
	// SAVE THE ORDER
	public static void saveOrder(List list) throws IOException {
		write(filename, list);
	}
	
	public static void replaceOrder(Order o) throws IOException {
		
		List l = new ArrayList<>();
		ArrayList<Order> miList = getAllOrders();

		int size = miList.size();
		
		for(int i=0;i<size;i++) {
			
			if(miList.get(i).getOrderId() == o.getOrderId()) {
				miList.set(i, o);
			} 
			
			Order k = miList.get(i);
			String order = k.getOrderId() + "," + k.getTimeStamp()
					+  "," + k.getDate() +  "," +  k.getStaffId()
					+ "," + k.getIsPaid() + "," + k.getCust().getCustId()
					+ "," +  k.getTable().getTableNo();
			l.add(order);
			
			
		}
		replace(filename,l);
	}

	// GET ALL THE ORDERS
	public static ArrayList<Order> getAllOrders() throws IOException {
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

				CustomerController cc = new CustomerController();
				LocalDate date = LocalDate.parse(dateOrdered);
				ResTableController tc = new ResTableController();
				Order m = new Order(Integer.parseInt(orderId),converttime,date,
						Boolean.valueOf(isPaid),staffId,cc.getCustById(Integer.parseInt(custid)),tc.getTableByTableNo(no));
				m.setOrderItemList(OrderController.getOrderItemsByOrderId(Integer.parseInt(orderId)));
				psList.add(m);
			}
		} else {
			psList = null;
		}
		return psList;
	}
	
	// DELETE THE ORDER
	public static void deleteOrder(int orderId) throws IOException {
		
		List l = new ArrayList<>();
		ArrayList<Order> miList = getAllOrders();
		
		OrderItem item = new OrderItem();
		
		int size = miList.size();
		
		for(int i=0;i<size;i++) {
			
			if(miList.get(i).getOrderId() == orderId) {
				item.removeEntireOrderItemList(orderId);
				miList.remove(i);
			} else {
				Order k = miList.get(i);
				String foodItem = k.getOrderId() + "," + k.getTimeStamp()
						+ "," + k.getDate() +"," + k.getStaffId() + ","
						+ k.getIsPaid() +  "," + k.getCust().getCustId() + ","
						+ k.getTable().getTableNo();
				l.add(foodItem);
			}
		}
		replace(filename,l);
	}
	
	//READ AND WRITE TO CSV
	private static List read(String filename) throws IOException {
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
	
	private static void write(String filename, List data) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(filename,true));
		try {
			for (int i = 0; i < data.size(); i++) {
				
				out.write((String) data.get(i)+"\n");
			}
		} finally {
			out.close();
		}
	}
	
	private static void replace(String filename, List data) throws IOException {
		
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