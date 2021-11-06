package controller;

import java.io.IOException;

import boundary.AlaCarteForm;
import boundary.InvoiceForm;
import boundary.OrderForm;
import boundary.PromotionForm;
import boundary.ReservationForm;



public class MainAppController {
	
	// To navigate to different forms
	public void getAlaCarteForm() throws IOException {
		AlaCarteForm al = new AlaCarteForm();
		al.displayOption();
	}
	
	public void getPromotionForm() throws IOException {
		PromotionForm pf = new PromotionForm();
		pf.displayOption(); 
	}
	
	public void getOrderForm() throws IOException {
		OrderForm or = new OrderForm();
		or.displayOption();
	}
	
	public void getReservationForm() throws IOException {
		ReservationForm r = new ReservationForm();
		r.displayOption();
	}
	
	public void getSalesRevenueMonthForm() {
//		SalesRevenueMonth r = new SalesRevenueMonth();
//		r.displayOption();
	}
	
	public void getInvoiceForm() throws IOException {
		InvoiceForm i = new InvoiceForm();
		i.displayOption();
	}
}
