package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import entity.*;

public class OrderController {


	public void addOrder(int orderId, LocalTime timeStamp, LocalDate date, int staffId, Boolean isPaid, String custPhoneNum, int tableNo) throws IOException {
		String foodItem = orderId + "," + timeStamp +  "," + date +  "," +  staffId + "," + isPaid + "," + getCustIDByPhoneNum(custPhoneNum) + "," +  tableNo;
		List l = new ArrayList();
		l.add(foodItem);
		Order.saveOrder(l);
	}

	public static ArrayList<Order> getAllOrders() throws IOException {
		return Order.getAllOrders();
	}

	public void deleteOrder(int orderId) throws IOException {
		// TODO - implement OrderController.deleteOrder
		Order.deleteOrder(orderId);
	}

	public void removeOrderItem(int orderId, int orderItemId) throws IOException {
		OrderItem.removeOrderItem(orderId, orderItemId);
	}


	public void replaceOrder(Order or) throws IOException {
		Order.replaceOrder(or);
	}

	//RETRIEVE ALL ORDER ITEMS
	public static ArrayList<OrderItem> getAllOrderItems() throws IOException {
		return OrderItem.getAllOrderItems();
	}

	//ORDER ITEMS
	public void addOrderItem(OrderItem item) throws IOException {
		OrderItem.addOrderItem(item);
	}

	public void removeEntireOrderItemList(int orderId) throws IOException {
		OrderItem.removeEntireOrderItemList(orderId);
	}

	//Not Used
//	public void addOrderItem(int orderId, int orderItemId) throws IOException {
//		oi.addOrderItem(orderId, orderItemId);
//	}

	public int getIndexFromOICsv(int index) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("DataSet/Order.csv"));
		int counter = 0;
		while(reader.readLine()!=null){
			counter++;
		}
		if (counter == 1)
			return index;
		else
		{
			index = getAllOrders().size()+1;
			return index;
		}
	}


	public ArrayList<AlaCarte> sortAlaCarteItems(ArrayList<AlaCarte> items) throws IOException{

		ArrayList<AlaCarte> sorted = new ArrayList<>();
		for (AlaCarte item: items) {
			if (item.getFoodType() == FoodType.Starters)
				sorted.add(item);
		}

		for (AlaCarte item: items){
			if(item.getFoodType() == FoodType.Main_Course)
				sorted.add(item);
		}

		for (AlaCarte item: items){
			if (item.getFoodType() == FoodType.Desserts)
				sorted.add(item);
		}

		for (AlaCarte item: items){
			if (item.getFoodType() == FoodType.Drinks)
				sorted.add(item);
		}
		return sorted;
	}

	public ArrayList<OrderItem> getOrderItemByOrderId(int id) throws IOException{
		ArrayList<OrderItem> cusOrder = new ArrayList<>();
		ArrayList<OrderItem> oi = getAllOrderItems();
		for (OrderItem orderItem : oi) {
			if (orderItem.getOrderId() == id)
				cusOrder.add(orderItem);
		}
		return cusOrder;
	}


	public Order getOrderById(int id) throws IOException {

		ArrayList<Order> order = getAllOrders();
		Order o = null;
		for (Order value : order) {
			if (value.getOrderId() == id)
				o = value;
		}
		return o;
	}

	public void updateOrderItemQty(OrderItem item, int qty) throws IOException
	{
		item.setOrderItemQty(qty);
		OrderItem.updateOrderItem(item);
	}

	public void addPromoOrderItem(int id, Promotion promo, int qty, int orderId) throws IOException {
		OrderItem o = new OrderItem(id,promo,qty,orderId);
		OrderItem.addOrderItem(o);
	}

	public void addAlaCarteOrderItem(int id, AlaCarte ala, int qty, int orderId) throws IOException {
		OrderItem o = new OrderItem(id,ala,qty,orderId);
		OrderItem.addOrderItem(o);
	}

	public ArrayList<Order> getCurrentDateOrder(LocalDate date) throws IOException {
		ArrayList<Order> CurrentDateList =  new ArrayList<>();
		ArrayList<Order> orderList = getAllOrders();
		for(Order order: orderList)
		{
			if(order.getDate().isEqual(date))
				CurrentDateList.add(order);
		}

		return CurrentDateList;
	}

	//Newly Shifted Methods from Entity Order and OrderItem

	public static ArrayList<Order> getUnpaidOrders() throws IOException {
		ArrayList<Order> storeOrder = getAllOrders();
		ArrayList<Order> unpaidList = new ArrayList<>();
		int counter = 0;
		if(storeOrder != null) {
			for (Order order : storeOrder) {
				if (!order.getIsPaid()) {
					counter++;
					unpaidList.add(order);
				}
			}
		}
		if(counter==0) {
			unpaidList = null;
		}
		return unpaidList;
	}

	public Order selectOrderById(int id) throws IOException {
		ArrayList<Order> ord = getAllOrders();
		Order o = null;
		for(Order order: ord){
			if(order.getOrderId() == id){
				o=order;
			}
		}
		return o;
	}


	public int getCustIDByPhoneNum(String contact) throws IOException{
		int id = -1;
		ArrayList<Customer> cust = Customer.getAllCustomerDetails();
		Customer c = null;
		for(Customer cus: cust)
		{
			if(cus.getPersPhoneNo().equals(contact)){
				c = cus;
			}
		}

		if(c!=null)
			return c.getCustId();
		else
			return id;
	}

	public OrderItem selectOrderItemById(int id) throws IOException {
		ArrayList<OrderItem> ordi = getAllOrderItems();
		OrderItem o = null;
		for(OrderItem ord: ordi){
			if(ord.getOrderItemId() == id){
				o = ord;
			}
		}
		return o;
	}

	public ArrayList<Promotion> getAllPromoSets() throws FileNotFoundException {
		return Promotion.getAllPromotionItems();
	}

	public ArrayList<AlaCarte> getAllAlaCartItems() throws IOException {
		ArrayList<AlaCarte> items = AlaCarte.getAllAlaCarteItems();
		return sortAlaCarteItems(items);
	}

	public OrderItem selectOrderItemById(int orderId,int orderItemId) throws IOException {
		OrderItem c = null;
		ArrayList<OrderItem> orderi =  getOrderItemsByOrderId(orderId);
		for(OrderItem odi : orderi)
		{
			if(odi.getOrderItemId() == orderItemId)
				c = odi;
		}
		return c;
	}

	public static ArrayList<OrderItem> getOrderItemsByOrderId(int id) throws IOException{
		ArrayList<OrderItem> orderi = getAllOrderItems();
		ArrayList<OrderItem> storeL = new ArrayList<>();
		for (OrderItem odi: orderi)
		{
			if(odi.getOrderId() == id)
				storeL.add(odi);
		}
		return storeL;
	}

}
