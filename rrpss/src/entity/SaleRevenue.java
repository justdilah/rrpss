package entity;

public class SaleRevenue {

	private int saleId;
	private String saleStartPeriod; // unable to use Date Datatype so need to use String
	private String saleEndPeriod; // unable to use Date Datatype so need to use String
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

	public String getSaleStartPeriod() {
		return this.saleStartPeriod;
	}

	/**
	 * 
	 * @param saleStartPeriod
	 */
	public void setSaleStartPeriod(String saleStartPeriod) {
		this.saleStartPeriod = saleStartPeriod;
	}

	public String getSaleEndPeriod() {
		return this.saleEndPeriod;
	}

	/**
	 * 
	 * @param saleEndPeriod
	 */
	public void setSaleEndPeriod(String saleEndPeriod) {
		this.saleEndPeriod = saleEndPeriod;
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