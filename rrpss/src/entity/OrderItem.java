package entity;

import controller.AlaCarteController;
import controller.PromotionController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class OrderItem {
	
	private static final String filename = "DataSet/OrderItem.csv";
	
	private int orderItemId;
	private int orderItemQty;
	private String orderItemName;
	private Double orderItemPrice;
	private int orderId;
	Promotion promoitem;
	AlaCarte food;
	
	public OrderItem() {
		
	}
	
	public OrderItem(int orderItemId,Promotion a, int qty, int orderId) {
		this.orderItemId = orderItemId;
		this.orderItemName = a.getPackName();
		this.promoitem = a;
		this.orderItemQty = qty;
		this.orderItemPrice = a.getPackPrice() * qty;
		this.orderId = orderId;
	}
	
	public OrderItem(int orderItemId,AlaCarte a, int qty, int orderId) {
		this.orderItemId = orderItemId;
		this.orderItemName = a.getAlaCarteName();
		this.food = a;
		this.orderItemQty = qty;
		this.orderItemPrice = a.getAlaCartePrice() * qty;
		this.orderId = orderId;
	}

	public int getOrderItemId() {
		return this.orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public int getOrderItemQty() {
		return this.orderItemQty;
	}

	public void setOrderItemQty(int orderItemQty) {
		this.orderItemQty = orderItemQty;
	}

	public String getOrderItemName() {
		return this.orderItemName;
	}

	public void setOrderItemName(String orderItemName) {
		this.orderItemName = orderItemName;
	}

	public Double getOrderItemPrice() {
		return this.orderItemPrice;
	}

	public void setOrderItemPrice(Double orderItemPrice) {
		this.orderItemPrice = orderItemPrice;
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int id) {
		this.orderId = id;
	}


	// GET ALL THE ORDER ITEMS
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
	
	public static void addOrderItem(OrderItem o) throws IOException {
		List l = new ArrayList<>();
		String item = o.getOrderItemId() + "," + o.getOrderItemName() + "," + o.getOrderItemQty() + "," + o.getOrderItemPrice() + "," + o.getOrderId();
		l.add(item);
		saveFoodItem(l);
 	}

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

	//Not used
//	public void addOrderItem(int orderId, int orderItemId) throws IOException {
//		List l = new ArrayList<>();
//		ArrayList<OrderItem> miList = getOrderItems(orderId);
//		OrderItem o = new OrderItem();
//
//		for(int i=0;i<getOrderItems(orderId).size()-1;i++) {
//
//
//			if(getOrderItems(orderId).get(i).getOrderItemId() == orderItemId) {
//				miList.add(getOrderItems(orderId).get(i));
//			}
//
//			OrderItem k = miList.get(i);
//			String foodItem = k.getOrderItemId() + "," + k.getOrderItemName() + "," + k.getOrderItemQty() + "," + k.getOrderItemPrice() +  "," + orderId;
//			l.add(foodItem);
//		}
//
//		replace(filename,l);
//	}
	
	public static void saveFoodItem(List list) throws IOException {
		write(filename, list);
	}

	
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