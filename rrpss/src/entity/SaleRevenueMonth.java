
package entity;

import java.util.List;

/**
 * This class represents the Sale Revenue by Month entity of the restaurant.
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class SaleRevenueMonth {

	private Double totalRevenue;
	private List<Integer> invoiceID;

	public Double getTotalRevenue() {
		return this.totalRevenue;
	}

	public void setTotalRevenue(Double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
	
	public List<Integer> getInvoiceID() {
		return invoiceID;
	}
	
	public void setInvoiceID(List<Integer> invoiceID) {
		this.invoiceID = invoiceID;
	}


}