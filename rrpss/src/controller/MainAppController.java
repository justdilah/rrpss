package controller;

import java.io.IOException;

import boundary.AlaCarteForm;
import boundary.InvoiceForm;
import boundary.OrderForm;
import boundary.PromotionForm;
import boundary.ReservationForm;
import boundary.SaleRevenueMonthForm;


/**
 * This class represents the Main Application controller of the restaurant.
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class MainAppController {

	/**
	 * This methods calls the AlaCarte form
	 * @throws IOException Display error message if any I/O error found while retrieving the alacarte records.
	 */
	public void getAlaCarteForm() throws IOException {
		AlaCarteForm al = new AlaCarteForm();
		al.displayOption();
	}

	/**
	 * This methods calls the Promotion form
	 * @throws IOException Display error message if any I/O error found while retrieving the promotion records.
	 */
	public void getPromotionForm() throws IOException {
		PromotionForm pf = new PromotionForm();
		pf.displayOption();
	}

	/**
	 * This methods calls the Order form
	 * @throws IOException Display error message if any I/O error found while retrieving the order records.
	 */
	public void getOrderForm() throws IOException {
		OrderForm or = new OrderForm();
		or.displayOption();
	}

	/**
	 * This methods calls the Reservation form
	 * @throws IOException Display error message if any I/O error found while retrieving the reservation records.
	 */
	public void getReservationForm() throws IOException {
		ReservationForm r = new ReservationForm();
		r.displayOption();
	}

	/**
	 * This methods calls the Reservation form to check for late or past reservations
	 * @throws IOException Display error message if any I/O error found while retrieving the reservation records.
	 */
	public void checkReservation() throws IOException {
		ReservationForm r = new ReservationForm();
		r.checkReservation();
	}

	/**
	 * This methods calls the SalesRevenueMonth form
	 * @throws IOException Display error message if any I/O error found while retrieving the sale revenue month records.
	 */
	public void getSalesRevenueMonthForm() throws IOException {
		SaleRevenueMonthForm r = new SaleRevenueMonthForm();
		r.displayOption();
	}

	/**
	 * This methods calls the Invoice form
	 * @throws IOException Display error message if any I/O error found while retrieving the invoice records.
	 */
	public void getInvoiceForm() throws IOException {
		InvoiceForm i = new InvoiceForm();
		i.displayOption();
	}
}