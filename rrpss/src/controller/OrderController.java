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

	Order o = new Order();
	OrderItem oi = new OrderItem();

	public void addOrder(int orderId, LocalTime timeStamp,LocalDate date, int staffId, Boolean isPaid, String custPhoneNum, int tableNo) throws IOException {
		String foodItem = orderId + "," + timeStamp +  "," + date +  "," +  staffId + "," + isPaid + "," + o.getCustIDByPhoneNum(custPhoneNum) + "," +  tableNo;
		List l = new ArrayList();
		l.add(foodItem);
		o.saveOrder(l);
	}
		
	public void addPromoOrderItem(int id, Promotion i, int qty, int orderId) throws IOException {
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
		oi.removeOrderItem(orderId, orderItemId);
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

	public ArrayList<Promotion> getAllPromoSets() throws FileNotFoundException {
		return oi.getAllPromoSets();
	}

	public ArrayList<AlaCarte> getAllAlaCartItems() throws IOException {
		ArrayList<AlaCarte> items = oi.getAllAlaCartItems();
		return SortAlaCarteItems(items);
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


	public ArrayList<AlaCarte> SortAlaCarteItems(ArrayList<AlaCarte> items) throws IOException{

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


	public Order getOrderById(int id) throws IOException {

		ArrayList<Order> order = getAllOrders();
		Order o = null;
		for (int i=0; i<order.size();i++)
		{
			if (order.get(i).getOrderId() == id)
				o = order.get(i);

		}
		return o;
	}


}
