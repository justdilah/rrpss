package controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import entity.Invoice;
import entity.Order;
import entity.OrderItem;
import entity.Table;

/**
 * This class represents the Invoice controller of the restaurant.
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class InvoiceController {

	/**
	 * Initialise a DecimalFormat to get two decimal place
	 */
	DecimalFormat df= new DecimalFormat("0.00");

	/**
	 * This methods gets the number of records in the invoice CSV
	 * @return The number of records in the invoice CSV
	 * @throws IOException Display error message if any I/O error found while retrieving the invoice records.
	 */
	public int checkFileEmpty() throws IOException
	{
		ArrayList<Invoice> iList = InvoiceController.getAllInvoice();
		int count = iList.size();
		return count;
	}

	/**
	 * This methods calls the getAllInvoice() in the entity class to retrieve the entire list of invoice
	 * @return The whole list of invoice details
	 * @throws IOException Display error message if any I/O error found while retrieving the invoice records.
	 */
	public static ArrayList<Invoice> getAllInvoice() throws IOException
	{
		return Invoice.getAllInvoice();
	}

	/**
	 * This method gets a specific invoice object by its own invoice id
	 * @param invno The unique identifier of invoice
	 * @return A invoice object
	 * @throws IOException Display error message if any I/O error found while retrieving the invoice records.
	 */
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

	/**
	 * This method gets list of unpaid order by their table number.
	 * @param t The table object used to get list of unpaid order
	 * @return A order object
	 * @throws IOException Display error message if any I/O error found while retrieving the invoice records.
	 */
	public ArrayList<Order> getUnpaidOrdersByTableNo(Table t) throws IOException
	{
		ArrayList<Order> unpaidOrder = OrderController.getUnpaidOrders();
		ArrayList<Order> orders = new ArrayList<>();
		int checker = 0;
		if (unpaidOrder ==  null)
			return null;
		else {
			for (int k = 0; k < OrderController.getUnpaidOrders().size(); k++) {
				if (OrderController.getUnpaidOrders().get(k).getTable().getTableNo() == t.getTableNo()) {
					orders.add(OrderController.getUnpaidOrders().get(k));
					checker++;
				}
			}
			if (checker == 0) {
				orders = null;
			}
		}
		return orders;
	}

	/**
	 * This method calls saveInvoice() in the entity class to retrieve the entire list of invoice
	 * @param i The invoice object
	 * @throws IOException Display error message if any I/O error found while inserting into the invoice records.
	 */
	public void saveInvoice(Invoice i) throws IOException
	{
		List l = new ArrayList();

		int staffId = i.getStaff().getStaffId();
		int orderId = i.getOrder().getOrderId();
		int custId = i.getCustomer().getCustId();

		String invoice = i.getInvoiceNo() + "," + i.getSubTotal() + ","
				+ i.getServiceCharge() + "," + i.getDiscounts()
				+ "," + i.getTotalPrice() + "," + i.getGst() + ","
				+i.getInvoiceDate() + "," +i.getInvoiceTime() +","+ staffId
				+ "," + orderId + "," + custId + "," +i.getTableNo();
		l.add(invoice);
		Invoice.saveInvoice(l);
	}


	/**
	 * This method calculate the sub-total price of the orders
	 * @param itemList The list of order items
	 * @return The sub-total price of the order
	 */
	public double calculateSubTotalPriceOrder(ArrayList<OrderItem> itemList)
	{
		double subTotalPrice = 0;

		for(int i=0;i<itemList.size();i++)
			subTotalPrice += itemList.get(i).getOrderItemPrice();

		return Double.parseDouble(df.format(subTotalPrice));
	}

	/**
	 * This method calculate the discount amount of the order
	 * @param subTotalPrice The sub-total amount
	 * @return The discount amount of the order
	 */
	public double calculateDiscount(double subTotalPrice)
	{
		return Double.parseDouble(df.format(0.15 * subTotalPrice));
	}

	/**
	 * This method calculate the GST amount of the order
	 * @param subTotalPrice The sub-total amount
	 * @return The GST amount of the order
	 */
	public double calculateGst(double subTotalPrice)
	{
		return Double.parseDouble(df.format(0.07 * subTotalPrice));
	}

	/**
	 * The method calculate the service charge of the order
	 * @param subTotalPrice The sub-total amount
	 * @return The service charge of the order
	 */
	public double calculateService(double subTotalPrice)
	{
		return Double.parseDouble(df.format(0.10 * subTotalPrice));
	}

	/**
	 * The method calculate the total price of the order
	 * @param sub The sub-total amount
	 * @param service The service charge amount
	 * @param gst The GST amount
	 * @return The total price of the order
	 */
	public double calculateTotal(double sub, double service, double gst)
	{
		return Double.parseDouble(df.format(sub+service+gst));
	}


}