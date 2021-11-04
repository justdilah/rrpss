package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import entity.AlaCarte;
import entity.Order;
import entity.OrderItem;
import entity.PromotionSet;
import entity.TableCapacity;

public class OrderController {
	
	Order o = new Order();
	
	public void addOrder(int orderId, String timeStamp, int staffId, Boolean isPaid) {
		// TODO - implement OrderController.addOrder
		throw new UnsupportedOperationException();
	}
		
	public void addPromoOrderItem(int id, PromotionSet i, int qty, int orderId) throws IOException {
		o.addPromoOrderItemtoList(id, i, qty,orderId);
	}
	
	public void addAlaCarteOrderItem(int id, AlaCarte a, int qty, int orderId) throws IOException {
		o.addAlaCarteOrderItemtoList(id, a, qty, orderId);
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
	
	public void setOrderId(int id) {
		o.setOrderId(id);
	}
	
	public int getOrderId() {
		return o.getOrderId();
	}
	
	public void addOrder(String pn) throws IOException {
		String foodItem = o.getOrderId() + "," + o.getTimeStamp() +  "," +  o.getStaffId() + "," + o.getIsPaid() + "," + o.getCustIDByPhoneNum(pn);
		List l = new ArrayList();
		l.add(foodItem);
		o.saveOrder(l);
	}

	/**
	 *     
	 * @param orderId
	 */
	public ArrayList<Order> getAllOrders() throws FileNotFoundException {
		return o.getAllOrders();
	}
	
	public ArrayList<PromotionSet> getAllPromoSets() throws FileNotFoundException {
		return o.getAllPromoSets();
	}
	
	public ArrayList<AlaCarte> getAllAlaCartItems() throws FileNotFoundException {
		return o.getAllAlaCartItems();
	}
	

	/**
	 * 
	 * @param orderId
	 * @throws IOException 
	 */
	public void deleteOrder(int orderId) throws IOException {
		// TODO - implement OrderController.deleteOrder
		o.deleteOrder(orderId);
	}
	
	/**
	 * 
	 * @param orderItemId
	 * @throws IOException 
	 */
	public void removeOrderItem(int orderId, int orderItemId) throws IOException {
		OrderItem i = new OrderItem();
		i.removeOrderItem(orderId, orderItemId);
	}
	

}
