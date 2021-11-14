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

/**
 * This class represents controller class for the order class and order item class
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class OrderController {


	/**This method calls the saveOrder method in the entity class to add order
	 * @param orderId int value to uniquely identify order
	 * @param timeStamp LocalTime variable to represent the time the order has been placed
	 * @param date LocalDate variable to represent the date the order has been placed
	 * @param staffId inv value to uniquely identify the staff
	 * @param isPaid boolean value to indicate whether the customer has paid for the order or not
	 * @param custPhoneNum string value for the customer phone number
	 * @param tableNo int value to uniquely identify the table
	 * @throws IOException Display error message if any I/O error found while inserting into the order records.
	 */
	public void addOrder(int orderId, LocalTime timeStamp, LocalDate date, int staffId, Boolean isPaid, String custPhoneNum, int tableNo) throws IOException {
		String foodItem = orderId + "," + timeStamp +  "," + date +  "," +  staffId + "," + isPaid + "," + getCustIDByPhoneNum(custPhoneNum) + "," +  tableNo;
		List l = new ArrayList();
		l.add(foodItem);
		Order.saveOrder(l);
	}

	/**This method calls the getAllOrders method in the order entity class to retrieve all the orders
	 * @return list of orders
	 * @throws IOException Display error message if any I/O error found while retrieving from the order records.
	 */
	public static ArrayList<Order> getAllOrders() throws IOException {
		return Order.getAllOrders();
	}

	/**This method calls the deleteOrder method in the order entity class to delete the order in the order csv
	 * @param orderId int value to uniquely identify the order
	 * @throws IOException Display error message if any I/O error found while inserting into the order records.
	 */
	public void deleteOrder(int orderId) throws IOException {
		Order.deleteOrder(orderId);
	}

	/**This method calls the removeOrderItem method in the order item entity class to remove the order item
	 * @param orderId int value to uniquely identify order
	 * @param orderItemId int value to uniquely identify order item
	 * @throws IOException Display error message if any I/O error found while inserting into the order item records.
	 */
	public void removeOrderItem(int orderId, int orderItemId) throws IOException {
		OrderItem.removeOrderItem(orderId, orderItemId);
	}


	/**This method calls the replaceOrder method in the order entity class to replace the order with a new set of data
	 * @param or Order object
	 * @throws IOException Display error message if any I/O error found while inserting into the order records.
	 */
	public void replaceOrder(Order or) throws IOException {
		Order.replaceOrder(or);
	}

	/**This method calls the getAllOrderItems method in the order item entity class to retrieve all the orders
	 * @return list of orders
	 * @throws IOException Display error message if any I/O error found while retrieving from the order item records.
	 */
	public static ArrayList<OrderItem> getAllOrderItems() throws IOException {
		return OrderItem.getAllOrderItems();
	}

	/**This method calls addOrderItem method in the order item entity class to add order item
	 * @param item OrderItem object
	 * @throws IOException Display error message if any I/O error found while inserting into the order item records.
	 */
	public void addOrderItem(OrderItem item) throws IOException {
		OrderItem.addOrderItem(item);
	}

	/**This method calls removeEntireOrderItemList method in the order item entity class to remove the list of order items with the
	 * specific order id
	 * @param orderId int value to uniquely identify the order
	 * @throws IOException Display error message if any I/O error found while retrieving from the order item records.
	 */
	public void removeEntireOrderItemList(int orderId) throws IOException {
		OrderItem.removeEntireOrderItemList(orderId);
	}


	/**This method checks if the order csv has any records in it
	 * @param index int value to represent the number of records in the order csv
	 * @return number of records in the order csv
	 * @throws IOException Display error message if any I/O error found while retrieving from the order records.
	 */
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


	/**This method sorts the ala carte items according to the food type
	 * @param items list of ala carte items
	 * @return sorted list of ala carte items
	 * @throws IOException Display error message if any I/O error found while retrieving from the ala carte records.
	 */
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

	/**This method calls getAllOrderItemByOrderId method in the order item entity class to retrieve all the order items and select the order items
	 * by the given order id
	 * @param id int value uniquely identify order
	 * @return list of order items
	 * @throws IOException Display error message if any I/O error found while retrieving from the order item records.
	 */
	public ArrayList<OrderItem> getOrderItemByOrderId(int id) throws IOException{
		ArrayList<OrderItem> cusOrder = new ArrayList<>();
		ArrayList<OrderItem> oi = getAllOrderItems();
		for (OrderItem orderItem : oi) {
			if (orderItem.getOrderId() == id)
				cusOrder.add(orderItem);
		}
		return cusOrder;
	}


	/**This methods calls getAllOrders method in order entity class to retrieve all the orders and select the order with
	 * specific order id
	 * @param id int value to uniquely identify order
	 * @return Order object
	 * @throws IOException Display error message if any I/O error found while retrieving from the order records.
	 */
	public Order getOrderById(int id) throws IOException {

		ArrayList<Order> order = getAllOrders();
		Order o = null;
		for (Order value : order) {
			if (value.getOrderId() == id)
				o = value;
		}
		return o;
	}

	/**This method calls updateOrderItem method in the order item entity class to update the quantity of the
	 * order item
	 * @param item Order item object
	 * @param qty int value for the quantity of the order item
	 * @throws IOException Display error message if any I/O error found while inserting into the order item records.
	 */
	public void updateOrderItemQty(OrderItem item, int qty) throws IOException
	{
		item.setOrderItemQty(qty);
		OrderItem.updateOrderItem(item);
	}

	/**This method calls the addOrderItem method in the order item entity class to add the order item that is a promotion item
	 * @param id int value to uniquely identify the order item
	 * @param promo Promotion object
	 * @param qty int value for the quantity of the order item
	 * @param orderId int value to uniquely identify the order
	 * @throws IOException Display error message if any I/O error found while inserting into the order item records.
	 */
	public void addPromoOrderItem(int id, Promotion promo, int qty, int orderId) throws IOException {
		OrderItem o = new OrderItem(id,promo,qty,orderId);
		OrderItem.addOrderItem(o);
	}

	/**This method calls the addOrderItem method in the order item entity class to add the order item that is an ala carte item
	 * @param id int value to uniquely identify the order item
	 * @param ala Ala Carte object
	 * @param qty int value for the quantity of the order item
	 * @param orderId int value to uniquely identify the order
	 * @throws IOException Display error message if any I/O error found while inserting into the order item records.
	 */
	public void addAlaCarteOrderItem(int id, AlaCarte ala, int qty, int orderId) throws IOException {
		OrderItem o = new OrderItem(id,ala,qty,orderId);
		OrderItem.addOrderItem(o);
	}

	/**This method calls getAllOrders method in the order entity class to retrieve all the orders and select the orders that
	 * are placed on the current date
	 * @param date the date the order has been placed
	 * @return list of orders
	 * @throws IOException Display error message if any I/O error found while retrieving from the order records.
	 */
	public ArrayList<Order> getCurrentDateOrder(LocalDate date) throws IOException {
		ArrayList<Order> CurrentDateList =  new ArrayList<>();
		ArrayList<Order> orderList = getAllOrders();
		if(orderList !=null) {
			for (Order order : orderList) {
				if (order.getDate().isEqual(date))
					CurrentDateList.add(order);
			}
		}
		else{
			CurrentDateList = null;
		}

		return CurrentDateList;
	}


	/**This method calls getAllOrders method in the order entity class and selects the orders that have not been paid
	 * @return list of unpaid orders
	 * @throws IOException Display error message if any I/O error found while retrieving from the order records.
	 */
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

	/**This method calls the getAllOrders method in the order entity class to retrieve all the orders and selects a specific order by the given id
	 * @param id int value to uniquely identify the order
	 * @return Order object
	 * @throws IOException Display error message if any I/O error found while retrieving from the order records.
	 */
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


	/**This method calls the getAllCustomerDetails in the customer entity class to retrieve all the customer records and select
	 * a specific customer record by the phone number
	 * @param contact string value for the phone number of the customer
	 * @return Customer object
	 * @throws IOException Display error message if any I/O error found while retrieving from the customer records.
	 */
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

	/**This method calls the getAllOrderItems method in the order item entity class to retrieve all the order items and
	 * select the order item by the id
	 * @param id int value uniquely identify the order item
	 * @return Order Item object
	 * @throws IOException Display error message if any I/O error found while retrieving from the order item records.
	 */
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

	/**This method calls the getAllPromotionItems method in the promotion entity class to retrieve all the promotion items
	 * @return list of promotion items
	 * @throws IOException Display error message if any I/O error found while retrieving from the promotion records.
	 */
	public ArrayList<Promotion> getAllPromoSets() throws IOException {
		return Promotion.getAllPromotionItems();
	}

	/**This method calls the getAllAlaCarteItems method in the ala carte entity class to retrieve all the ala carte items
	 * @return list of ala carte items
	 * @throws IOException Display error message if any I/O error found while retrieving from the ala carte item records.
	 */
	public ArrayList<AlaCarte> getAllAlaCartItems() throws IOException {
		ArrayList<AlaCarte> items = AlaCarte.getAllAlaCarteItems();
		return sortAlaCarteItems(items);
	}

	/**This method calls selectOrderItemById method in the order item entity class to retrieve all the order items according
	 * to the order id
	 * @param orderId int value to uniquely identify the order
	 * @param orderItemId int value to uniquely identify the order item
	 * @return Order Item object
	 * @throws IOException Display error message if any I/O error found while retrieving from the order item records.
	 */
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

	/**This method calls getAllOrderItems method in the order item entity class to retrieve all the order items and select
	 * the specific order items by the order id
	 * @param id int value to uniquely identify the order
	 * @return list of order items
	 * @throws IOException Display error message if any I/O error found while retrieving from the order item records.
	 */
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
