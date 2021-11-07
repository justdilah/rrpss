
package entity;

import java.util.List;

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
	
//	private int saleId;
//	private Double totalRevenue;
//
//	public int getSaleId() {
//		return this.saleId;
//	}
//
//	/**
//	 * 
//	 * @param saleId
//	 */
//	public void setSaleId(int saleId) {
//		this.saleId = saleId;
//	}
//
//	public Double getTotalRevenue() {
//		return this.totalRevenue;
//	}
//
//	/**
//	 * 
//	 * @param totalRevenue
//	 */
//	public void setTotalRevenue(Double totalRevenue) {
//		this.totalRevenue = totalRevenue;
//	}

}