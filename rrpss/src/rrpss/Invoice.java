package rrpss;

public class Invoice {

	private int invoiceNo;
	private Double subTotal;
	private Double gst;
	private Double serviceCharge;
	private Double discounts;
	private String invoiceDate; // unable to use Date Datatype so need to use String
	private String invoiceTime; // unable to use DateTime Datatype so need to use String

	public void calculateSubTotal() {
		// TODO - implement Invoice.calculateSubTotal
		throw new UnsupportedOperationException();
	}

	public void calculateTotalPrice() {
		// TODO - implement Invoice.calculateTotalPrice
		throw new UnsupportedOperationException();
	}

	public void printInvoice() {
		// TODO - implement Invoice.printInvoice
		throw new UnsupportedOperationException();
	}

	public int getInvoiceNo() {
		return this.invoiceNo;
	}

	/**
	 * 
	 * @param invoiceNo
	 */
	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Double getSubTotal() {
		return this.subTotal;
	}

	/**
	 * 
	 * @param subTotal
	 */
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getGst() {
		return this.gst;
	}

	/**
	 * 
	 * @param gst
	 */
	public void setGst(Double gst) {
		this.gst = gst;
	}

	public Double getServiceCharge() {
		return this.serviceCharge;
	}

	/**
	 * 
	 * @param serviceCharge
	 */
	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public Double getDiscounts() {
		return this.discounts;
	}

	/**
	 * 
	 * @param discounts
	 */
	public void setDiscounts(Double discounts) {
		this.discounts = discounts;
	}

	public String getInvoiceDate() {
		return this.invoiceDate;
	}

	/**
	 * 
	 * @param invoiceDate
	 */
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceTime() {
		return this.invoiceTime;
	}

	/**
	 * 
	 * @param invoiceTime
	 */
	public void setInvoiceTime(String invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

}