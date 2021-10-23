package entity;

import java.util.*;

public class PromotionSet {

	private int packId;
	private Double packPrice;
	private ArrayList<AlaCarte> packItem;
	private String packDesc;
	Collection<OrderItem> orderitem;

	public int getPackId() {
		return this.packId;
	}

	/**
	 * 
	 * @param packId
	 */
	public void setPackId(int packId) {
		this.packId = packId;
	}

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

}