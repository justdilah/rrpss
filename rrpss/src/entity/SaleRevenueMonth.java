
package entity;
public class SaleRevenueMonth {

	private int saleId;
	private Double totalRevenue;

	public int getSaleId() {
		return this.saleId;
	}

	/**
	 * 
	 * @param saleId
	 */
	public void setSaleId(int saleId) {
		this.saleId = saleId;
	}

	public Double getTotalRevenue() {
		return this.totalRevenue;
	}

	/**
	 * 
	 * @param totalRevenue
	 */
	public void setTotalRevenue(Double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

}