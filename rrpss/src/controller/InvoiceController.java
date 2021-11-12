package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import entity.Invoice;
import entity.Order;
import entity.OrderItem;
import entity.Table;

public class InvoiceController {
	OrderController or = new OrderController();
	Invoice i = new Invoice();
	
	public int checkFileEmpty() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("DataSet/Invoice.csv"));
		int counter = 0;
		
		while(reader.readLine()!=null) {
			counter++;
		}
		return counter;
	}
	
	public ArrayList<Order> getUnpaidOrdersByTableNo(Table table) throws IOException {
		ArrayList<Order> orders = new ArrayList<>();
		int checker = 0;
		for(int k=0;k<or.getUnpaidOrders().size();k++) {
			if(or.getUnpaidOrders().get(k).getTable().getTableNo() == table.getTableNo()) {
				orders.add(or.getUnpaidOrders().get(k));
				checker++;
			}
		}
		if(checker == 0) {
			orders = null;
		}
		return orders;
	}
	
	//FOR THE ORDER ITEMS
	public double calculateSubTotalPriceOrder(ArrayList<OrderItem> itemList) {
		double subTotalPrice = 0;
		for(int i=0;i<itemList.size();i++) {
			subTotalPrice += itemList.get(i).getOrderItemPrice();
		}
		return subTotalPrice;
	}
	
	public double calculateDiscount(double subTotalPrice) {
		return 0.10 * subTotalPrice;
	}
	
	public double calculateGst(double subTotalPrice) {
		return 0.07 * subTotalPrice;
	}
//
//	public int calculateTotalPrice() {
//		// TODO - implement InvoiceController.calculateTotalPrice
//		throw new UnsupportedOperationException();
//	}
	
	public void saveInvoice(Invoice i) throws IOException {
		List l = new ArrayList();
		
		String invoice = i.getInvoiceNo() + "," + i.getOrderID() + "," + i.getDate() + "," + i.getSubTotal() + "," + i.getDiscounts() + "," + i.getGst() + "," + i.getStaffID() + "," + i.getTableNo();
		l.add(invoice);
		i.saveInvoice(l);
	}
	
	public ArrayList<Invoice> getAllInvoice() throws IOException {
		return i.getAllInvoice();
	}
	

}