package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Invoice {

	private int invoiceNo;
	private Double subTotal;
	private Double gst;
	private Double serviceCharge;
	private Double discounts;
	private LocalDate invoiceDate;
	private LocalDateTime invoiceTime;
	SaleRevenueMonth monthlyreport;

	public int getInvoiceNo() {
		return this.invoiceNo;
	}

	public Double getSubTotal() {
		return this.subTotal;
	}

	/**
	 * 
	 * @param invoiceNo
	 */
	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
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

	public LocalDate getInvoiceDate() {
		return this.invoiceDate;
	}

	/**
	 * 
	 * @param invoiceDate
	 */
	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public LocalDateTime getInvoiceTime() {
		return this.invoiceTime;
	}

	/**
	 * 
	 * @param invoiceTime
	 */
	public void setInvoiceTime(LocalDateTime invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

}