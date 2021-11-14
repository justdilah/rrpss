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



/**
 * This class represents an Order entity of the restaurant
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class Order {

	/**
	 * Initialized filepath of the Order.csv
	 */
	private static final String filename = "DataSet/Order.csv";

	/**
	 * The Id of the order
	 */
	private int orderId;

	/**
	 * The time the order has been placed
	 */
	private LocalTime timeStamp;

	/**
	 * The date the order has been placed
	 */
	private LocalDate date;

	/**
	 * An indication on whether the customer has paid for the order or not
	 */
	private Boolean isPaid;

	/**
	 * The Id of the staff
	 */
	private String staffId;

	/**
	 * Customer object
	 */
	Customer customer;

	/**
	 * Table object
	 */
	Table table;

	/**
	 * List of order items for the order
	 */
	ArrayList <OrderItem> orderItemList;

	/**
	 * Class Constructor
	 */
	public Order() {
		this.orderItemList = new ArrayList<OrderItem>();
	}

	/**
	 * Class Constructor with given id, the time order has been placed, date order has been placed,
	 * id of the staff, customer object and table object
	 * @param id int value to uniquely identify order
	 * @param timeStamp LocalTime variable to represent the time order has been placed
	 * @param date LocalDate variable to represent the date order has been placed
	 * @param isPaid boolean value to indicate whether the customer has paid for the order or not
	 * @param staffId int value to uniquely identify staff
	 * @param cust customer object
	 * @param t table object
	 */
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

	/**This method gets the id of the order
	 * @return id of the order
	 */
	public int getOrderId() {
		return this.orderId;
	}

	/**This method sets the id of the order
	 * @param orderId int value to uniquely identify the order
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	/**This method gets the table object
	 * @return table object
	 */
	public Table getTable() {
		return this.table;
	}

	/**This method sets the table object
	 * @param t table object
	 */
	public void setTable(Table t) {
		this.table = t;
	}


	/**This method gets the time the order has been placed
	 * @return time the order has been placed
	 */
	public LocalTime getTimeStamp() {
		return this.timeStamp;
	}
	/**
	 * This method sets the time the order has been placed
	 * @param time LocalTime variable to represent the time the order has been placed
	 */
	public void setTimeStamp(LocalTime time) {
		this.timeStamp = time;
	}

	/**This method gets the date the order has been placed
	 * @return time the order has been placed
	 */
	public LocalDate getDate() {
		return this.date;
	}

	/**
	 * This method sets the date the order has been placed
	 * @param date the date order has been placed
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * This method gets the id of the staff
	 * @return id of the staff
	 */
	public String getStaffId() {
		return this.staffId;
	}

	/**
	 * This methods sets the id of the staff
	 * @param staffId int value to uniquely identify the staff
	 */
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}


	/**
	 * This method gets the boolean variable to show if the customer has paid the order or not
	 * @return boolean variable represents an indicator if the customer has paid the order or not
	 */
	public Boolean getIsPaid() {
		return this.isPaid;
	}

	/**
	 * This method sets the boolean variable to show if the customer has paid the order or not
	 * @param isPaid boolean value to indicate whether the customer has paid for the order or not
	 */
	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	/**
	 * This method gets the customer object
	 * @return customer object
	 */
	public Customer getCust() {
		return this.customer;
	}

	/**
	 * This method sets the customer object
	 * @param cust customer object
	 */
	public void setCust(Customer cust) {
		this.customer = cust;
	}


	/**This method gets the list of order items
	 * @return list of order items
	 */
	public ArrayList<OrderItem> getOrderItemList() {
		return this.orderItemList;
	}


	/**
	 * This method sets the list of order items
	 * @param orderItemList list of order items
	 */
	public void setOrderItemList(ArrayList<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}


	/**This method adds the order to the order csv
	 * @param list list of orders
	 * @throws IOException Display error message if any I/O error found while inserting into the order records.
	 */
	public static void saveOrder(List list) throws IOException {
		write(filename, list);
	}

	public static void replaceOrder(Order o) throws IOException {

		List l = new ArrayList<>();
		ArrayList<Order> miList = getAllOrders();
		String order = "";

		int size = miList.size();

		for(int i=0;i<size;i++) {

			if(miList.get(i).getOrderId() == o.getOrderId()) {
				miList.set(i, o);
				Order k = miList.get(i);
				order = k.getOrderId() + "," + k.getTimeStamp()
						+  "," + k.getDate() +  "," +  k.getStaffId()
						+ "," + k.getIsPaid() + "," + k.getCust().getCustId()
						+ "," +  k.getTable().getTableNo();
				l.add(order);
			}
			else
			{
				Order k = miList.get(i);
				if(k.getTable() == null)
				{
					order = k.getOrderId() + "," + k.getTimeStamp()
							+  "," + k.getDate() +  "," +  k.getStaffId()
							+ "," + k.getIsPaid() + "," + k.getCust().getCustId()
							+ "," +  0;
				}
				else{
					order = k.getOrderId() + "," + k.getTimeStamp()
							+  "," + k.getDate() +  "," +  k.getStaffId()
							+ "," + k.getIsPaid() + "," + k.getCust().getCustId()
							+ "," +  k.getTable().getTableNo();
				}

				l.add(order);

			}



		}
		replace(filename,l);
	}

	/**This method extracts out all the orders from the order csv
	 * @return list of orders
	 * @throws IOException Display error message if any I/O error found while retrieving into the order records.
	 */
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


	/**This method deletes an order in the order csv
	 * @param orderId int value to uniquely identify the order
	 * @throws IOException Display error message if any I/O error found while inserting into the order records.
	 */
	public static void deleteOrder(int orderId) throws IOException {

		List l = new ArrayList<>();
		ArrayList<Order> miList = getAllOrders();

		OrderItem item = new OrderItem();

		int size = miList.size();

		for(int i=0;i<size;i++) {

			if(miList.get(i).getOrderId() == orderId) {
				OrderItem.removeEntireOrderItemList(orderId);
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


	/**This method reads the order CSV
	 * @param filename The filepath of the CSV file
	 * @return data A list of orders
	 * @throws IOException Display error message if any I/O error found while retrieving into the order records.
	 */
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


	/**This method writes the order CSV
	 * @param filename The filepath of the CSV file
	 * @param data A list of orders
	 * @throws IOException Display error message if any I/O error found while inserting into the order records.
	 */
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

	/**This method writes a new set of data and stores into the order CSV
	 * @param filename The filepath of the CSV file
	 * @param data A list of orders
	 * @throws IOException Display error message if any I/O error found while inserting into the order records.
	 */
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