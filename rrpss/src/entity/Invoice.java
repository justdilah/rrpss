package entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import controller.CustomerController;
import controller.OrderController;
import controller.ResTableController;
import controller.ReservationController;
import controller.StaffController;

public class Invoice {


	private static final String filename = "DataSet/Invoice.csv";

	private int invoiceNo;
	private double subTotal;
	private double serviceCharge;
	private double discounts;
	private double totalPrice;
	private double gst;
	private LocalDate invoiceDate;
	private LocalTime invoiceTime;
	Customer customer;
	Staff staff;
	Order order;
	private int tableNo;

	public int getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public double getServiceCharge() {
		return this.serviceCharge;
	}

	public void setServiceCharge(double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public double getDiscounts() {
		return this.discounts;
	}

	public void setDiscounts(double discounts) {
		this.discounts = discounts;
	}

	public LocalDate getInvoiceDate() {
		return this.invoiceDate;
	}

	public double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getGst(){return this.gst;}

	public void setGst(double gst){this.gst = gst;}

	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public LocalTime getInvoiceTime() {
		return invoiceTime;
	}

	public void setInvoiceTime(LocalTime invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public int getTableNo() {
		return this.tableNo;
	}

	public void setTableNo(int tableNo) {
		this.tableNo = tableNo;
	}


	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Invoice() {

	}

//	public Invoice(int no, double subtotal, double discount, double total, LocalDate date, Staff s, Order o)
//	{
//		this.invoiceNo = no;
//		this.subTotal = subtotal;
//		this.discounts = discount;
//		this.totalPrice = total;
//		this.invoiceDate = date;
//		this.staff = s;
//		this.order = o;
//	}


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


	//RETRIEVE ALL THE INVOICE RECORDS
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
				String custid = star.nextToken().trim();;
				String staffid = star.nextToken().trim();
				String tablno = star.nextToken().trim();
				String orderno= star.nextToken().trim();
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

	public static void saveInvoice(List l) throws IOException
	{
		write(filename,l);
	}

	public void updateStatus(Table t, Order o) throws IOException {
		o.setIsPaid(true);
	}



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



	//READ AND WRITE TO CSV
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