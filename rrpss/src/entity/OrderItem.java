package entity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import controller.StoreController;

public class OrderItem {
	
	private static final String filename = "DataSet/OrderItem.csv";
	
	private int orderItemId;
	private int orderItemQty;
	private String orderItemName;
	private Double orderItemPrice;
	private int orderId;
	PromotionSet promoitem;
	AlaCarte food;
	
	public OrderItem(int orderItemId,PromotionSet a, int qty) {
		this.orderItemId = orderItemId;
		this.orderItemName = a.getPackName();
		this.promoitem = a;
		this.orderItemQty = qty;
		this.orderItemPrice = a.getPackPrice() * qty;
	}
	
	public OrderItem(int orderItemId,AlaCarte a, int qty) {
		this.orderItemId = orderItemId;
		this.orderItemName = a.getFoodName();
		this.food = a;
		this.orderItemQty = qty;
		this.orderItemPrice = a.getFoodPrice() * qty;
	}

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
	public void setOrderItemPrice(Double orderItemPrice) {
		this.orderItemPrice = orderItemPrice;
	}
	
	public void getAllOrderItems(int orderId) {
//		ArrayList<PromotionSet> psList= new ArrayList<>();
//		ArrayList stringitems = (ArrayList) StoreController.read(filename); 	
//		
//		for (int i = 0; i < stringitems.size(); i++) {
//			String st = (String) stringitems.get(i);
//			StringTokenizer star = new StringTokenizer(st, ",");
//			String id = star.nextToken().trim();
//			String name = star.nextToken().trim();
//			String items = star.nextToken().trim();
//			String[] pi = items.split("@");
//			packItems = addAlaCarteItems(pi);
//			String desc = star.nextToken().trim();
//			String newdesc = desc.replace('/', ',');
//			String price = star.nextToken().trim();
//			PromotionSet m = new PromotionSet(Integer.parseInt(id),name,packItems,newdesc,Double.valueOf(price));
//			psList.add(m);
//		}
//		return psList;
		
	}
	
	
	public void addOrderItem() throws IOException {
		List l = new ArrayList<>();
		String item = this.getOrderItemId() + "," + this.getOrderItemName() + "," + this.getOrderItemQty() + "," + this.getOrderItemPrice();
		l.add(item);
		saveFoodItem(l);
 	}
	
	public void saveFoodItem(List list) throws IOException {
		StoreController.write(filename, list);
	}

}