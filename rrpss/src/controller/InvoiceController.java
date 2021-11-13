package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import entity.Customer;
import entity.Invoice;
import entity.Order;
import entity.OrderItem;
import entity.Staff;
import entity.Table;

public class InvoiceController {


	public int checkFileEmpty() throws IOException
	{
		ArrayList<Invoice> iList = InvoiceController.getAllInvoice();
		int count = iList.size();
		return count;
	}


	public static ArrayList<Invoice> getAllInvoice() throws IOException
	{
		return Invoice.getAllInvoice();
	}

	public Invoice getInvoiceById(int invno) throws IOException {
		ArrayList<Invoice> inv = getAllInvoice();
		Invoice i = new Invoice();
		for (Invoice invs: inv) {
			if (invs.getInvoiceNo() == invno) {
				i = invs;
			}
		}
		return i;
	}


	public ArrayList<Order> getUnpaidOrdersByTableNo(Table t) throws IOException
	{
		ArrayList<Order> orders = new ArrayList<>();
		int checker = 0;
		for(int k=0;k<OrderController.getUnpaidOrders().size();k++) {
			if(OrderController.getUnpaidOrders().get(k).getTable().getTableNo() == t.getTableNo())
			{
				orders.add(OrderController.getUnpaidOrders().get(k));
				checker++;
			}
		}
		if(checker == 0) {
			orders = null;
		}
		return orders;
	}


	public void saveInvoice(Invoice i) throws IOException
	{
		List l = new ArrayList();

		int staffId = i.getStaff().getStaffId();
		int orderId = i.getOrder().getOrderId();
		int custId = i.getCustomer().getCustId();

		String invoice = i.getInvoiceNo() + "," + i.getSubTotal() + ","
				+ i.getServiceCharge() + "," + i.getDiscounts()
				+ "," + i.getTotalPrice() + "," + i.getGst() + ","
				+i.getInvoiceDate() + "," +i.getInvoiceTime() + staffId
				+ "," + orderId + "," + custId + "," +i.getTableNo();
		l.add(invoice);
		Invoice.saveInvoice(l);
	}


	//FOR THE ORDER ITEMS
	public double calculateSubTotalPriceOrder(ArrayList<OrderItem> itemList)
	{
		double subTotalPrice = 0;

		for(int i=0;i<itemList.size();i++)
		{
			subTotalPrice += itemList.get(i).getOrderItemPrice();
		}
		return subTotalPrice;
	}

	public double calculateDiscount(double subTotalPrice)
	{
		return 0.15 * subTotalPrice;
	}

	public double calculateGst(double subTotalPrice)
	{
		return 0.07 * subTotalPrice;
	}

	public double calculateService(double subTotalPrice)
	{
		return 0.10 * subTotalPrice;
	}

	public double calculateTotal(double sub, double service, double gst)
	{
		return sub+service+gst;
	}

//
//	public int calculateTotalPrice() {
//		// TODO - implement InvoiceController.calculateTotalPrice
//		throw new UnsupportedOperationException();
//	}






}