package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import entity.AlaCarte;
import entity.Order;
import entity.OrderItem;
import entity.PromotionSet;
//import entity.TableCapacity;

public class OrderController {
	
	Order o = new Order();
	OrderItem oi = new OrderItem();
	
	public void addOrder(int orderId, LocalTime timeStamp,LocalDate date, int staffId, Boolean isPaid, String custPhoneNum, int tableNo) throws IOException {
		String foodItem = orderId + "," + timeStamp +  "," + date +  "," +  staffId + "," + isPaid + "," + o.getCustIDByPhoneNum(custPhoneNum) + "," +  tableNo;
		List l = new ArrayList();
		l.add(foodItem);
		o.saveOrder(l);
	}
		
	public void addPromoOrderItem(int id, PromotionSet i, int qty, int orderId) throws IOException {
		OrderItem o = new OrderItem(id,i,qty,orderId);
		oi.addOrderItem(o);
	}
	
	public void addAlaCarteOrderItem(int id, AlaCarte a, int qty, int orderId) throws IOException {
		OrderItem o = new OrderItem(id,a,qty,orderId);
		oi.addOrderItem(o);
	}
	
//	public void setStaffID(String id) {
//		o.setStaffId(id);
//	}
//	
//	public void setTimeStamp(LocalTime t) {
//		o.setTimeStamp(t);
//	}
//	
//	public void setisPaid(Boolean b) {
//		o.setIsPaid(b);
//	}
//	
//	public void setOrderId(int id) {
//		o.setOrderId(id);
//	}
//	
//	public int getOrderId() {
//		return o.getOrderId();
//	}
	
//	public int getTableId() {
//		return o.getTable().getTableNo();
//	}
	
//	public void setTableId(int id) {
////		o.setTableId(id);
//	}
	
	/**
	 *     
	 * @param orderId
	 * @throws IOException 
	 */
	public ArrayList<Order> getAllOrders() throws IOException {
		return o.getAllOrders();
	}
	
	public ArrayList<Order> getUnpaidOrders() throws IOException {
		return o.getUnpaidOrders();
	}

	public void deleteOrder(int orderId) throws IOException {
		// TODO - implement OrderController.deleteOrder
		o.deleteOrder(orderId);
	}
	
	public void removeOrderItem(int orderId, int orderItemId) throws IOException {
		OrderItem i = new OrderItem();
		i.removeOrderItem(orderId, orderItemId);
	}
	
	
	public void replaceOrder(Order or) throws IOException { 
		o.replaceOrder(or);
	}
	
	
//	public void addPromoOrderItemtoList(int id, PromotionSet i,int qty, int orderId) throws IOException {
//		OrderItem o = new OrderItem(id,i,qty,orderId);
//		oi.addOrderItem(o);
//	}
//	
//	public void addAlaCarteOrderItemtoList(int id, AlaCarte a, int qty,int orderId) throws IOException {
//		OrderItem o = new OrderItem(id,a,qty, orderId);
//		oi.addOrderItem(o);
//	}
//	
	
	public ArrayList<PromotionSet> getAllPromoSets() throws FileNotFoundException {
		return oi.getAllPromoSets();
	}
	
	public ArrayList<AlaCarte> getAllAlaCartItems() throws FileNotFoundException {
		return oi.getAllAlaCartItems();
	}
	
	//RETRIEVE ALL ORDER ITEMS
	public ArrayList<OrderItem> getAllOrderItems() throws IOException {
		return oi.getAllOrderItems();
	}
	
	//ORDER ITEMS
	public void addOrderItem(OrderItem o) throws IOException {
		oi.addOrderItem(o);
	}
	
	public void removeEntireOrderItemList(int orderId) throws IOException {
		oi.removeEntireOrderItemList(orderId);
	}
	
	public void addOrderItem(int orderId, int orderItemId) throws IOException {
	    oi.addOrderItem(orderId, orderItemId);
	}
	

}
