package entity;


import java.util.*;

public class OrderItem {

	private int orderItemId;
	private int orderItemQty;
	private String orderItemName;
	private Double orderItemPrice;
	Collection<PromotionSet> promoitem;
	Collection<AlaCarte> food;

	public int getOrderItemId() {
		return this.orderItemId;
	}

	/**
	 * 
	 * @param orderItemId
	 */
	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public int getOrderItemQty() {
		return this.orderItemQty;
	}

	/**
	 * 
	 * @param orderItemQty
	 */
	public void setOrderItemQty(int orderItemQty) {
		this.orderItemQty = orderItemQty;
	}

	public String getOrderItemName() {
		return this.orderItemName;
	}

	/**
	 * 
	 * @param orderItemName
	 */
	public void setOrderItemName(String orderItemName) {
		this.orderItemName = orderItemName;
	}

	public Double getOrderItemPrice() {
		return this.orderItemPrice;
	}

	/**
	 * 
	 * @param orderItemPrice
	 */
	public void setOrderItemPrice(double orderItemPrice) {
		this.orderItemPrice = orderItemPrice;
	}

}
