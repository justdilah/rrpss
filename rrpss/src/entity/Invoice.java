package entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import controller.CustomerController;
import controller.OrderController;
import controller.ReservationController;
import controller.StaffController;

/**
 * This class represents the Invoice entity of the restaurant.
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class Invoice {


	/**
	 * The filepath of the CSV file
	 */
	private static final String filename = "DataSet/Invoice.csv";

	/**
	 * The unique identifier of invoice
	 */
	private int invoiceNo;
	/**
	 * The sub-total of the order
	 */
	private double subTotal;
	/**
	 * The service charge of the order
	 */
	private double serviceCharge;
	/**
	 * The discount given to the customer
	 */
	private double discounts;
	/**
	 * The total price to be paid
	 */
	private double totalPrice;
	/**
	 * The GST amount of the order
	 */
	private double gst;
	/**
	 * The date when the invoice is printed
	 */
	private LocalDate invoiceDate;
	/**
	 * The time when the invoice is printed
	 */
	private LocalTime invoiceTime;
	/**
	 * Customer who the invoice belongs to
	 */
	Customer customer;
	/**
	 * Staff who prints the invoice
	 */
	Staff staff;
	/**
	 * Order that is tied to the invoice
	 */
	Order order;
	/**
	 * The table number that is tied to the invoice
	 */
	private int tableNo;

	/**
	 * Get the invoice number
	 * @return The invoice number
	 */
	public int getInvoiceNo() {
		return invoiceNo;
	}

	/**
	 * Set the invoice number
	 * @param invoiceNo The invoice number
	 */
	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	/**
	 * Get the sub-total
	 * @return The sub-total price
	 */
	public double getSubTotal() {
		return subTotal;
	}

	/**
	 * Set the sub-total
	 * @param subTotal The sub-total price
	 */
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	/**
	 * Get the service charge
	 * @return The service charge
	 */
	public double getServiceCharge() {
		return this.serviceCharge;
	}

	/**
	 * Set the service charge
	 * @param serviceCharge The service charge
	 */
	public void setServiceCharge(double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	/**
	 * Get the discount
	 * @return The discount amount
	 */
	public double getDiscounts() {
		return this.discounts;
	}

	/**
	 * Set the discount
	 * @param discounts The discount amount
	 */
	public void setDiscounts(double discounts) {
		this.discounts = discounts;
	}

	/**
	 * Get the invoice date
	 * @return The invoice date
	 */
	public LocalDate getInvoiceDate() {
		return this.invoiceDate;
	}

	/**
	 * Set the invoice date
	 * @param invoiceDate The invoice date
	 */
	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	/**
	 * Get the total price
	 * @return The total price
	 */
	public double getTotalPrice() {
		return this.totalPrice;
	}

	/**
	 * Set the total price
	 * @param totalPrice The total price
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * Get the GST
	 * @return The GST amount
	 */
	public double getGst(){
		return this.gst;
	}

	/**
	 * Set the GST
	 * @param gst The GST amount
	 */
	public void setGst(double gst){
		this.gst = gst;
	}

	/**
	 * Get the invoice time
	 * @return The invoice time
	 */
	public LocalTime getInvoiceTime() {
		return invoiceTime;
	}

	/**
	 * Set the invoice time
	 * @param invoiceTime The invoice time
	 */
	public void setInvoiceTime(LocalTime invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	/**
	 * Get staff object
	 * @return The staff object
	 */
	public Staff getStaff() {
		return staff;
	}

	/**
	 * Set the staff object
	 * @param staff The staff object
	 */
	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	/**
	 * Get the table number
	 * @return The table number
	 */
	public int getTableNo() {
		return this.tableNo;
	}

	/**
	 * Set the table number
	 * @param tableNo The table number
	 */
	public void setTableNo(int tableNo) {
		this.tableNo = tableNo;
	}

	/**
	 * Get the order object
	 * @return The order object
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * Set the order object
	 * @param order The order object
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * Get the customer object
	 * @return The customer object
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Set the customer object
	 * @param customer The customer object
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * Default constructor of Invoice
	 */
	public Invoice() {

	}

	/**
	 * Invoice constructor with parameters
	 * @param no The invoice number
	 * @param subtotal The sub-total of the order
	 * @param servicecharge The service charge of the order
	 * @param discount The discount of the order
	 * @param total The total price of the invoice
	 * @param gst The GST of the invoice
	 * @param date The date when the invoice is printed
	 * @param time The time when the invoice is printed
	 * @param s The staff object who prints the invoice
	 * @param o The order object that is tired to the invoice
	 * @param c The customer who the invoice belongs to
	 * @param tableNo The table number that is tied to the order
	 */
	public Invoice(int no, double subtotal, double servicecharge, double discount, double total, double gst, LocalDate date, LocalTime time, Staff s, Order o, Customer c, int tableNo)
	{
		this.invoiceNo = no;
		this.subTotal = subtotal;
		this.serviceCharge = servicecharge;
		this.discounts = discount;
		this.totalPrice = total;
		this.gst = gst;
		this.invoiceDate = date;
		this.invoiceTime = time;
		this.staff = s;
		this.order = o;
		this.customer = c;
		this.tableNo = tableNo;
	}


	/**
	 * The method reads the CSV file to retrieve all details from the invoice CSV
	 * @return The whole list of invoice details
	 * @throws IOException Display error message if any I/O error found while retrieving into the invoice records.
	 */
	public static ArrayList<Invoice> getAllInvoice() throws IOException
	{
		ArrayList<Invoice> ilist= new ArrayList<>();
		ilist.clear();
		ArrayList stringitems;

		try {
			stringitems = (ArrayList) read(filename);

			for (int i = 0; i < stringitems.size(); i++)
			{
				String st = (String) stringitems.get(i);
				StringTokenizer star = new StringTokenizer(st, ",");

				String ino = star.nextToken().trim();
				String sub = star.nextToken().trim();
				String service = star.nextToken().trim();
				String discount = star.nextToken().trim();
				String total = star.nextToken().trim();
				String gst = star.nextToken().trim();
				String date = star.nextToken().trim();
				String time = star.nextToken().trim();
				String staffid = star.nextToken().trim();
				String orderno= star.nextToken().trim();
				String custid = star.nextToken().trim();
				String tableno = star.nextToken().trim();

				Customer c = new Customer();
				CustomerController cc = new CustomerController();
				c=cc.getCustById(Integer.parseInt(custid));

				Staff s = new Staff();
				StaffController sc = new StaffController();
				s=sc.getStaffById(Integer.parseInt(staffid));

				Order o = new Order();
				OrderController oc = new OrderController();
				o=oc.getOrderById(Integer.parseInt(orderno));


				String contact = cc.getCustById(Integer.parseInt(custid)).getPersPhoneNo();
				int resid = ReservationController.getReservationByContact(contact).getResId();


				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate convertdate = LocalDate.parse(date,formatter);

				DateTimeFormatter tformatter = DateTimeFormatter.ofPattern("HH:mm");
				LocalTime converttime = LocalTime.parse(time,tformatter);


				Invoice p = new Invoice(Integer.parseInt(ino), Double.parseDouble(sub), Double.parseDouble(service), Double.parseDouble(discount),
						Double.parseDouble(total), Double.parseDouble(gst), convertdate, converttime, s, o, c, Integer.parseInt(tableno));
				ilist.add(p);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		return ilist;
	}

	/**
	 * This method saves a invoice record into the invoice CSV
	 * @param l A list that stores the record of the invoice
	 * @throws IOException Display error message if any I/O error found while inserting into the invoice records.
	 */
	public static void saveInvoice(List l) throws IOException
	{
		write(filename,l);
	}


	/**
	 * This method reads the invoice CSV
	 * @param filename The filepath of the CSV file
	 * @return The list of records read from the invoice CSV
	 * @throws IOException Display error message if any I/O error found while retrieving the invoice records.
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



	/**
	 * This method writes the invoice CSV
	 * @param filename The filepath of the CSV file
	 * @param data A list of invoice records
	 * @throws IOException Display error message if any I/O error found while inserting into the invoice records.
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



}