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

public class Invoice {

	private int invoiceNo;
	private double subTotal;
	private double gst;
	private double serviceCharge;
	private double discounts;
	private double totalPrice;
	private int staffID;
	private LocalDate invoiceDate;
	private LocalTime invoiceTime;
	SaleRevenueMonth monthlyreport;
	private int tableNo;
	private int orderID;

	private static final String filename = "DataSet/Invoice.csv";
	
	public Invoice() {
		
	}
	
	public Invoice(int invoiceNo,int orderID, LocalDate date, double subTotal, double discount, double gst, int staffID, int tableNo) {
		this.invoiceDate = date;
		this.invoiceNo = invoiceNo;
		this.orderID = orderID;
		this.staffID = staffID;
		this.tableNo = tableNo;
		this.gst = gst;
		this.subTotal = subTotal;
		this.discounts = discount;
	}
	
	
	public int getInvoiceNo() {
		return this.invoiceNo;
	}
	
	public int getOrderID() {
		return this.orderID;
	}
	
	public LocalDate getDate() {
		return this.invoiceDate;
	}
	
	public double getTotalPrice() {
		return this.totalPrice;
	}
	
	public int getStaffID() {
		return this.staffID;
	}
	
	public int getTableNo() {
		return this.tableNo;
	}
	

	public Double getSubTotal() {
		return this.subTotal;
	}

	/**
	 * 
	 * @param invoiceNo
	 */
	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	/**
	 * 
	 * @param subTotal
	 */
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getGst() {
		return this.gst;
	}

	/**
	 * 
	 * @param gst
	 */
	public void setGst(Double gst) {
		this.gst = gst;
	}

	public Double getServiceCharge() {
		return this.serviceCharge;
	}

	/**
	 * 
	 * @param serviceCharge
	 */
	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public Double getDiscounts() {
		return this.discounts;
	}

	/**
	 * 
	 * @param discounts
	 */
	public void setDiscounts(Double discounts) {
		this.discounts = discounts;
	}

	public LocalDate getInvoiceDate() {
		return this.invoiceDate;
	}

	/**
	 * 
	 * @param invoiceDate
	 */
	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public LocalTime getInvoiceTime() {
		return this.invoiceTime;
	}

	/**
	 * 
	 * @param invoiceTime
	 */
	public void setInvoiceTime(LocalTime invoiceTime) {
		this.invoiceTime = invoiceTime;
	}
	
	public void saveInvoice(List l) throws IOException {
		write(filename,l);
	}
	
	public void updateStatus(Table table, Order order) throws IOException {
		Table t = new Table();
		Order o = new Order();
		t.updateTableStatusString("VACANT",table.getTableNo());
		order.setIsPaid(true);
		
	}
	
	//RETRIEVE ALL THE INVOICE RECORDS 
	public ArrayList<Invoice> getAllInvoice() throws IOException {
		ArrayList<Invoice> invoiceList= new ArrayList<>();
		ArrayList stringitems = (ArrayList) read(filename);
		if(stringitems.size() > 0) {
			OrderItem o = new OrderItem();
			Customer c = new Customer();
			for (int i = 0; i < stringitems.size(); i++) {
				String st = (String) stringitems.get(i);
				StringTokenizer star = new StringTokenizer(st, ",");
				String invoiceId = star.nextToken().trim();
				String orderId = star.nextToken().trim();
				String dateOrdered = star.nextToken().trim();
				LocalDate date = LocalDate.parse(dateOrdered);
				String subTotal = star.nextToken().trim();
				double subt = Double.parseDouble(subTotal);
				String discount = star.nextToken().trim();
				double dis = Double.parseDouble(discount);
				String gst = star.nextToken().trim();
				double gst2 = Double.parseDouble(gst);
				String staffId = star.nextToken().trim();	
				int stId = Integer.parseInt(staffId);
				String tableNo = star.nextToken().trim();
				int no = Integer.parseInt(tableNo);
				
				Invoice p = new Invoice(Integer.parseInt(invoiceId),Integer.parseInt(orderId), date, subt, dis, gst2, stId, no);
				invoiceList.add(p);
			}
		} else {
			invoiceList = null;
		}
		return invoiceList;
	}
	
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
	
	
	
	//READ AND WRITE TO CSV
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
	
//	private void replace(String filename, List data) throws IOException {
//		
//		BufferedWriter out = new BufferedWriter(new FileWriter(filename));
//		try {
//			out.write("InvoiceID" + "," + "OrderID" + "," + "TotalPrice" + "," + "DateOrdered" + "," + "StaffID" + "\n");
//			for (int i = 0; i < data.size(); i++) {
//				out.write((String) data.get(i) + "\n");
//			}
//		} finally {
//			out.close();
//		}
//	}

}