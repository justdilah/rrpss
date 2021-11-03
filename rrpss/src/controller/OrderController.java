package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import entity.AlaCarte;
import entity.Order;
import entity.PromotionSet;
import entity.TableCapacity;

public class OrderController {
	
	Order o = new Order();
	
	public void addOrder(int orderId, String timeStamp, int staffId, Boolean isPaid) {
		// TODO - implement OrderController.addOrder
		throw new UnsupportedOperationException();
	}
	
	public void addPromoOrderItem(int id, PromotionSet i, int qty) throws IOException {
		o.addPromoOrderItemtoList(id, i, qty);
	}
	
	public void addAlaCarteOrderItem(int id, AlaCarte a, int qty) throws IOException {
		o.addAlaCarteOrderItemtoList(id, a, qty);
	}
	
	public void setStaffID(String id) {
		o.setStaffId(id);
	}
	
	public void setTimeStamp(LocalTime t) {
		o.setTimeStamp(t);
	}
	
	public void setisPaid(Boolean b) {
		o.setIsPaid(b);
	}
	
	public void setTableCapacity(int cap) {
		TableCapacity t = TableCapacity.TWO_SEATER;
		if(cap <= 2) {
			t = TableCapacity.TWO_SEATER;
		} else if (cap <= 4) {
			t = TableCapacity.FOUR_SEATER;
		} else if (cap <= 6) {
			t = TableCapacity.SIX_SEATER;
		} else if (cap <= 8) {
			t = TableCapacity.EIGHT_SEATER;
		} else {
			t = TableCapacity.TEN_SEATER;
		}
	}
	
//	public ArrayList<PromotionSet> getAllOrderItems() throws FileNotFoundException {
//		return o.getAllPromotionItems();
//	}
	
	public void addOrder(String pn) throws IOException {
		String foodItem = "1" + "," + o.getTimeStamp() +  "," +  o.getStaffId() + "," + o.getIsPaid() + "," + o.getCustIDByPhoneNumber(pn);
		List l = new ArrayList();
		l.add(foodItem);
		o.saveFoodItem(l);
	}

	/**
	 *     
	 * @param orderId
	 */
	public void getOrder(int orderId) {
		// TODO - implement OrderController.getOrder
		throw new UnsupportedOperationException();
	}

	public ArrayList<Order> getAllOrders() throws FileNotFoundException {
		// TODO - implement OrderController.getAllOrder
		return o.getAllOrders();
	}

	/**
	 * 
	 * @param orderId
	 */
	public void deleteOrder(int orderId) {
		// TODO - implement OrderController.deleteOrder
		throw new UnsupportedOperationException();
	}
	
	public void addOrderItem(int orderItemId, String orderItemName, int orderItemQty, Double orderItemPrice, int orderId) {
		// TODO - implement OrderController.addOrderItem
		
	}

	/**
	 * 
	 * @param orderItemId
	 */
	public void removeOrderItem(int orderItemId) {
		// TODO - implement OrderController.removeOrderItem
		throw new UnsupportedOperationException();
	}

}
