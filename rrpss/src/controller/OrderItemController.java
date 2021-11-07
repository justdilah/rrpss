package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import entity.AlaCarte;
import entity.OrderItem;
import entity.PromotionSet;


//Changes might be required || Refactoring needed
public class OrderItemController {
	OrderItem oi = new OrderItem();
	
	public ArrayList<PromotionSet> getAllPromoSets() throws FileNotFoundException {
		return oi.getAllPromoSets();
	}
	
	public ArrayList<AlaCarte> getAllAlaCartItems() throws FileNotFoundException {
		return oi.getAllAlaCartItems();
	}
	
	public ArrayList<OrderItem> getAllOrderItems() throws IOException {
		return oi.getAllOrderItems();
	}
	
	public void addOrderItem(OrderItem o) throws IOException {
		oi.addOrderItem(o);
	}
	
	public void removeOrderItem(int orderId, int orderItemId) throws IOException {
		oi.removeOrderItem(orderId, orderItemId);
	}
	
	public void removeEntireOrderItemList(int orderId) throws IOException {
		oi.removeEntireOrderItemList(orderId);
	}
	
	public void addOrderItem(int orderId, int orderItemId) throws IOException {
	    oi.addOrderItem(orderId, orderItemId);
	}

	public void updateOrderItemQty(OrderItem o, int qty) throws IOException
	{
		o.setOrderItemQty(qty);
		oi.updateOrderItem(o);
	}
}
