package entity;

import controller.AlaCarteController;
import controller.PromotionController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
/**
 * This class represents an Ala Carte item for the restaurant.
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class OrderItem {

	/**
	 * The filepath of the CSV file.
	 */
	private static final String filename = "DataSet/OrderItem.csv";

	/**
	 * Unique ID for each different orderItem.
	 */
	private int orderItemId;

	/**
	 * The amount of orderItem quantity.
	 */
	private int orderItemQty;

	/**
	 * The name of the orderItem.
	 */
	private String orderItemName;

	/**
	 * The price of the orderItem.
	 */
	private Double orderItemPrice;

	/**
	 * The orderId of the current orderItem.
	 */
	private int orderId;

	/**
	 * An orderItem can be a promotion item
	 */
	Promotion promoitem;

	/**
	 * An orderItem can be a alaCarte item
	 */
	AlaCarte food;


	/**
	 * Default constructor of Reservation
	 */
	public OrderItem() {

	}

	/**
	 * Constructor for the promotion orderItem.
	 * @param orderItemId The oderItemID.
	 * @param a The promotion of the orderItem.
	 * @param qty The quantity of the orderItem.
	 * @param orderId The orderID of the orderItem.
	 */
	public OrderItem(int orderItemId,Promotion a, int qty, int orderId) {
		this.orderItemId = orderItemId;
		this.orderItemName = a.getPackName();
		this.promoitem = a;
		this.orderItemQty = qty;
		this.orderItemPrice = a.getPackPrice() * qty;
		this.orderId = orderId;
	}

	/**
	 * Constructor for the AlaCarte orderItem.
	 * @param orderItemId The oderItemID.
	 * @param a The AlaCarte of the orderItem.
	 * @param qty The quantity of the orderItem.
	 * @param orderId The orderID of the orderItem.
	 */
	public OrderItem(int orderItemId,AlaCarte a, int qty, int orderId) {
		this.orderItemId = orderItemId;
		this.orderItemName = a.getAlaCarteName();
		this.food = a;
		this.orderItemQty = qty;
		this.orderItemPrice = a.getAlaCartePrice() * qty;
		this.orderId = orderId;
	}

	/**
	 * Get the orderItemId.
	 * @return orderItemId
	 */
	public int getOrderItemId() {
		return this.orderItemId;
	}

	/**
	 * Set the orderItemId.
	 * @param orderItemId The unique orderItemId to identify each of them.
	 */
	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	/**
	 * Get the orderItem quantity.
	 * @return orderItemQty
	 */
	public int getOrderItemQty() {
		return this.orderItemQty;
	}

	/**
	 * Set the orderItem quantity.
	 * @param orderItemQty The orderItem quantity to be set.
	 */
	public void setOrderItemQty(int orderItemQty) {
		this.orderItemQty = orderItemQty;
	}

	/**
	 * Get the orderItem name.
	 * @return orderItemName
	 */
	public String getOrderItemName() {
		return this.orderItemName;
	}

	/**
	 * Set the orderItem name.
	 * @param orderItemName The orderItem name to be set.
	 */
	public void setOrderItemName(String orderItemName) {
		this.orderItemName = orderItemName;
	}

	/**
	 * Get the orderItem price.
	 * @return orderItemPrice
	 */
	public Double getOrderItemPrice() {
		return this.orderItemPrice;
	}

	/**
	 * Set the orderItem price.
	 * @param orderItemPrice The orderItem price to be set.
	 */
	public void setOrderItemPrice(Double orderItemPrice) {
		this.orderItemPrice = orderItemPrice;
	}

	/**
	 * Get the order ID of the orderItem.
	 * @return orderId
	 */
	public int getOrderId() {
		return this.orderId;
	}

	/**
	 * Set the order ID of the orderItem.
	 * @param id The order ID to be set.
	 */
	public void setOrderId(int id) {
		this.orderId = id;
	}


	/**
	 * This methods reads the CSV file to retrieve all details from orderItem CSV.
	 * @return The whole list of orderItem details.
	 * @throws IOException Display error message if any I/O error found while retrieving the orderItem Records.
	 */
	public static ArrayList<OrderItem> getAllOrderItems() throws IOException {
		Promotion p = new Promotion();
		AlaCarte a = new AlaCarte();
		OrderItem m;
		ArrayList<OrderItem> itemList= new ArrayList<>();
		ArrayList stringitems = (ArrayList) read(filename); 	
		
		for (int i = 0; i < stringitems.size(); i++) {
			String st = (String) stringitems.get(i);
			StringTokenizer star = new StringTokenizer(st, ",");
			String sequenceId = star.nextToken().trim();
			String name = star.nextToken().trim();
			String qty = star.nextToken().trim();
			String totalPrice = star.nextToken().trim();
			String orderId = star.nextToken().trim();
			
				if(AlaCarteController.getAlaCarteByName(name)!=null) {
					AlaCarte al = AlaCarteController.getAlaCarteByName(name);
					m = new OrderItem(Integer.parseInt(sequenceId),al,Integer.parseInt(qty),Integer.parseInt(orderId));
				} else {
					p = PromotionController.selectPromotionSetByName(name);
					m = new OrderItem(Integer.parseInt(sequenceId),p,Integer.parseInt(qty),Integer.parseInt(orderId));
				}
				itemList.add(m);
			}
			return itemList;
		}

	/**
	 * This method saves a reservation into orderItem CSV.
	 * @param o The reservation to be added.
	 * @throws IOException Display error message if any I/O errors found while inserting into the orderItem records.
	 */
	public static void addOrderItem(OrderItem o) throws IOException {
		List l = new ArrayList<>();
		String item = o.getOrderItemId() + "," + o.getOrderItemName() + "," + o.getOrderItemQty() + "," + o.getOrderItemPrice() + "," + o.getOrderId();
		l.add(item);
		saveOrderItem(l);
 	}

	/**
	 * This method updates the specific orderItem and store into the orderItem CSV.
	 * @param o The orderItem to be updated.
	 * @throws IOException Display error message if any I/O errors found while updating into the orderItem records.
	 */
	 public static void updateOrderItem(OrderItem o) throws IOException{
		List l= new ArrayList<>();
		ArrayList<OrderItem> oList = getAllOrderItems();
		for (int i=0; i<oList.size();i++){
			if(oList.get(i).getOrderItemId()==o.getOrderItemId() && oList.get(i).getOrderId() == o.getOrderId())
			{
				oList.set(i,o);
			}

			OrderItem oi = oList.get(i);
			String oiitem = oi.getOrderItemId() + "," + oi.getOrderItemName() + "," + oi.getOrderItemQty() + "," + oi.getOrderItemPrice() + "," + oi.getOrderId();
			l.add(oiitem);
		}
		replace(filename, l);
	 }

	/**
	 * This method deletes the specific orderItem from orderItem CSV for a specific order.
	 * @param orderId The order that the orderItem is going to be removed from.
	 * @param orderItemId The orderItem to be removed.
	 * @throws IOException Display error message if any I/O errors found while deleting into the orderItem records.
	 */
	public static void removeOrderItem(int orderId, int orderItemId) throws IOException {
		List l = new ArrayList<>();
		ArrayList<OrderItem> miList = getAllOrderItems();
		
		int size = getAllOrderItems().size();
		
		for(int i=0;i<size-1;i++) {
			
			if(miList.get(i).getOrderItemId() == orderItemId && miList.get(i).getOrderId() == orderId) {
				miList.remove(i);
			}
			
			OrderItem k = miList.get(i);
			
			String foodItem = k.getOrderItemId() + "," + k.getOrderItemName() + "," + k.getOrderItemQty() + "," + k.getOrderItemPrice() +  "," + k.getOrderId();
			l.add(foodItem);
		}
		
		replace(filename,l);
	}

	/**
	 * This method deletes the entire orderItem from orderItem CSV for a specific order.
	 * @param orderId The order that the orderItem is going to be removed from.
	 * @throws IOException Display error message if any I/O errors found while deleting into the orderItem records.
	 */
	public static void removeEntireOrderItemList(int orderId) throws IOException {
		List l = new ArrayList<>();
		ArrayList<OrderItem> miList = getAllOrderItems();
		
		int size = getAllOrderItems().size();
		
		for(int i=0;i<size;i++) {
			if(miList.get(i).getOrderId() != orderId) {
				OrderItem k = miList.get(i);
				
				String foodItem = k.getOrderItemId() + "," + k.getOrderItemName() + "," + k.getOrderItemQty() + "," + k.getOrderItemPrice() +  "," + k.getOrderId();
				l.add(foodItem);
			}
		}
		
		replace(filename,l);
	}


	/**
	 * This methods writes and insert into OrderItem CSV.
	 * @param list The list of data to be added.
	 * @throws IOException Display error message if any I/O error found while inserting into the orderItem records.
	 */
	public static void saveOrderItem(List list) throws IOException {
		write(filename, list);
	}


	/**
	 * This method writes a new set of data and stores into the orderItem CSV.
	 * @param filename The filepath of the CSV file
	 * @param data A list of orderItem records
	 * @throws IOException Display error message if any I/O error found while inserting into the orderItem records.
	 */
	private static void replace(String filename, List data) throws IOException {
		
		BufferedWriter out = new BufferedWriter(new FileWriter(filename));
		try {
			out.write("SequenceID" + "," + "OrderItemName" + "," + "Qty" + "," + "Price" + "," + "OrderID" + "\n");
			for (int i = 0; i < data.size(); i++) {
				
				out.write((String) data.get(i) + "\n");
			}
		} finally {
			out.close();
		}
	}

	/**
	 * This method reads the orderItem CSV
	 * @param filename The filepath of the CSV file
	 * @return The list of records read from the orderItem CSV
	 * @throws IOException Display error message if any I/O error found while accessing the orderItem records.
	 */
	private static List read(String filename) throws IOException {
		List data = new ArrayList();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String headerLine = reader.readLine();
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				data.add(line);
			}
		} finally {
			reader.close();
		}
		return data;
	}

	/**
	 * This method writes the orderItem CSV
	 * @param filename The filepath of the CSV file
	 * @param data A list of orderItem records
	 * @throws IOException Display error message if any I/O error found while inserting into the orderItem records.
	 */
	private static void write(String filename, List data) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(filename,true));

		try {
			for (int i = 0; i < data.size(); i++) {
				
				out.write((String) data.get(i)+"\n");
			}
		} finally {
			out.close();
		}
	}
}