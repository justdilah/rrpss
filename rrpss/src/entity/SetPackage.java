package entity;

import java.util.*;

public class SetPackage {

	ArrayList <Order> order;
	private Double packPrice;
	private ArrayList<AlaCarte> packItem;
	private String packDesc;
	private Date packStartDate;
	private Date packEndDate;

	public Double getPackPrice() {
		return this.packPrice;
	}

	/**
	 * 
	 * @param packPrice
	 */
	public void setPackPrice(Double packPrice) {
		this.packPrice = packPrice;
	}

	public ArrayList<AlaCarte> getPackItem() {
		return this.packItem;
	}

	/**
	 * 
	 * @param packItem
	 */
	public void setPackItem(ArrayList<AlaCarte> packItem) {
		this.packItem = packItem;
	}

	public String getPackDesc() {
		return this.packDesc;
	}

	/**
	 * 
	 * @param packDesc
	 */
	public void setPackDesc(String packDesc) {
		this.packDesc = packDesc;
	}

	public Date getPackStartDate() {
		return this.packStartDate;
	}

	/**
	 * 
	 * @param packStartDate
	 */
	public void setPackStartDate(Date packStartDate) {
		this.packStartDate = packStartDate;
	}

	public Date getPackEndDate() {
		return this.packEndDate;
	}

	/**
	 * 
	 * @param packEndDate
	 */
	public void setPackEndDate(Date packEndDate) {
		this.packEndDate = packEndDate;
	}
	
	public void add(String desc)
	{
		this.packDesc = desc;
	}

}