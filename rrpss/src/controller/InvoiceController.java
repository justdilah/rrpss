package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import entity.Invoice;

public class InvoiceController {

	Invoice i = new Invoice();
	public int calculateSubTotal() {
		// TODO - implement InvoiceController.calculateSubTotal
		throw new UnsupportedOperationException();
	}

	public int calculateTotalPrice() {
		// TODO - implement InvoiceController.calculateTotalPrice
		throw new UnsupportedOperationException();
	}
	
	public void saveInvoice(Invoice i) throws IOException {
		List l = new ArrayList();
		
		String invoice = i.getInvoiceNo() + "," + i.getOrderID() + "," + i.getDate() + "," + i.getSubTotal() + "," + i.getDiscounts() + "," + i.getGst() + "," + i.getStaffID() + "," + i.getTableNo();
		l.add(invoice);
		i.saveInvoice(l);
	}

}