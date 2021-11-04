package boundary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import controller.OrderController;
import entity.AlaCarte;
import entity.Order;
import entity.OrderItem;
import entity.PromotionSet;

public class OrderForm {
	
	OrderController or = new OrderController();
	
	Scanner sc = new Scanner(System.in);
	
	//SHOW ORDER
	public void displayOption() throws IOException {
		System.out.println("=================================");
        System.out.println("\t Order Options ");
		System.out.println("=================================");
		System.out.println("1) Display Orders ");
		System.out.println("2) Create an Order ");
		System.out.println("3) Update an Order ");
		System.out.println("4) Delete an Order ");
		System.out.println("5) Back");
		
		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();
		do {
			switch(choice) {
			case 1:
				displayOrder();
				break;
			case 2:				
				insertOrder();
				break;
			case 3:
				updateOrder();
				break;
			case 4:
				deleteOrder();
				break;
			case 5:
				MainAppUI.print();
				break;
			default:
				System.out.println("=========================================================");
				System.out.println("\tInvalid input. Please try again!");
		    	System.out.println("=========================================================");
				System.out.println("1) Display Orders ");
				System.out.println("2) Create an Order ");
				System.out.println("3) Update an Order ");
				System.out.println("4) Delete an Order ");
				System.out.println("5) Back");
				choice = sc.nextInt();
				break;
			} 
		}while(choice > 4 || choice < 1);
	}

	//CREATE ORDER
	private void insertOrder() throws IOException {
		try {
			System.out.println("=================================");
	        System.out.println("\t Create Order ");
			System.out.println("=================================");
			
			System.out.println("Please enter Table Number: ");
			String tableNo = sc.next();
			
			insertOrderItems(or.getAllOrders().size()+1, 0);
			
			System.out.println("Please enter your Staff ID: ");
			String id = sc.next();
			or.setStaffID(id);
			
			or.setTimeStamp(LocalTime.now());
			
			or.setisPaid(false);
			
			or.setOrderId(or.getAllOrders().size()+1);
			
			System.out.println("Please enter Customer Phone Number: ");
			Scanner scan = new Scanner(System.in);
			String pn = "";
			pn+=scan.nextLine();	
			or.addOrder(pn);
			
			System.out.println("==================================");
			System.out.println("Order deleted successfully");
			System.out.println("==================================");
			
		} catch (Exception ex) {
			System.out.println("==================================");
			System.out.println("Unsuccessful. Please try again");
			System.out.println("==================================");
			insertOrder();
		}	
	}
	
	//INSERT ORDER ITEMS TO THE ORDER
	private void insertOrderItems(int orderId, int counter) throws IOException {
		System.out.println("Select Menu Items ");
		System.out.println("=================================");
        System.out.println("\t Ala Carte Items ");
		System.out.println("=================================");
		
		ArrayList<AlaCarte> alaCarteItems = or.getAllAlaCartItems();
		
		for(int i=0;i< alaCarteItems.size() ; i++ ) {
			System.out.print(i+1 + ") ");
			System.out.print(alaCarteItems.get(i).getFoodName());
			System.out.println("");		
		}
		
		
		ArrayList<PromotionSet> promotionSetItems = or.getAllPromoSets();
		System.out.println("=================================");
        System.out.println("\t Promotion Sets ");
		System.out.println("=================================");

		for(int i=0;i<promotionSetItems.size();i++) {
			System.out.print(promotionSetItems.size() + 1 + i+ ") "  +  promotionSetItems.get(i).getPackName());
			System.out.println("");
		}
		
		ArrayList<String> names = new ArrayList<>();
		
		System.out.println("Please enter your choice: ");
		String c = sc.next();
		
		System.out.print("Enter the qty: ");
		int qty = sc.nextInt();
		
		int choice = Integer.parseInt(c);
		
		if(choice <= alaCarteItems.size()) {
			AlaCarte a = alaCarteItems.get(choice-1);
			names.add(a.getFoodName());
			or.addAlaCarteOrderItem(++counter,a, qty, orderId);
		} else {
			PromotionSet p = promotionSetItems.get(choice-1-alaCarteItems.size());
			names.add(p.getPackName());
			or.addPromoOrderItem(++counter,p, qty, orderId);
		}
		
		System.out.println("=================================");
		System.out.println("Menu items that are added");
		System.out.println("=================================");
		for(int k = 0; k<names.size();k++) {
			System.out.println(names.get(k));
		}
		
		while(true) {
			System.out.println("====================================");
			System.out.println("Please enter # to complete the order");
			System.out.println("====================================");
			
			System.out.println("Please enter your choice: ");
			c = sc.next();
			
			if(c.equals('#')) {
				
				break;
				
			} else {
				
				System.out.print("Enter the qty: ");
				qty = sc.nextInt();
				
			}
			
			choice = Integer.parseInt(c);
			
			if(choice <= alaCarteItems.size()) {
				names.add(alaCarteItems.get(choice-1).getFoodName());
				or.addAlaCarteOrderItem(++counter,alaCarteItems.get(choice-1), qty, orderId);
			} else {
				names.add(promotionSetItems.get(choice-1-alaCarteItems.size()).getPackName());
				or.addPromoOrderItem(++counter,promotionSetItems.get(choice-1-alaCarteItems.size()), qty, orderId);
			}
				
			System.out.println("=================================");
			System.out.println("Menu items that are added");
			System.out.println("=================================");
			for(int k = 0; k<names.size();k++) {
				System.out.println(names.get(k));
			}
		}	
	}

	//VIEW ORDERS
	private void displayOrder() throws FileNotFoundException {
			System.out.println("=========================================================");
			System.out.println("\tDisplay Orders");
	    	System.out.println("=========================================================");
	    	ArrayList<Order> orderList = or.getAllOrders();
	    	
			for(int i = 0;i<orderList.size();i++) {
				double totalPrice = 0;
				System.out.println("Order ID : " + orderList.get(i).getOrderId());
				System.out.println("Time Ordered : " + orderList.get(i).getTimeStamp());
				System.out.println("Payment Completed? : " + orderList.get(i).getIsPaid());
				System.out.println("Staff ID : " + orderList.get(i).getStaffId());
				
				ArrayList<OrderItem> orderItemList = orderList.get(i).getOrderItemList();
				for(int k=0;k<orderItemList.size();k++) {
					System.out.print(orderItemList.get(k).getOrderItemName() + " ");
					System.out.print(orderItemList.get(k).getOrderItemQty());
					System.out.println();
					totalPrice+= orderItemList.get(k).getOrderItemPrice();
				}
				System.out.println("Total Price : " + totalPrice);
			}
			
	}

	//FOR UPDATE ORDER  
	private void updateOrder() throws IOException {
		System.out.println("================================================");
        System.out.println("Select the Order you would like to update ");
		System.out.println("================================================");
		
		for(int i=0;i<or.getAllOrders().size() ; i++ ) {
			System.out.print(i+1 + ") ");
			System.out.print(or.getAllOrders().get(i).getOrderId());
			System.out.println("");
		};
		
		System.out.print("Please enter your choice: ");
		
		int choice = sc.nextInt();
		try {			
			updateOptions(or.getAllOrders().get(choice-1));
			System.out.println("==================================");
			System.out.println("Order updated successfully");
			System.out.println("==================================");
			
			displayOption();
		} catch (Exception ex) {
			System.out.println("==================================");
			System.out.println("Unsuccessful. Please try again");
			System.out.println("==================================");
		}
	}
	
	
	//FOR UPDATE PORTION
	private void updateOptions(Order o) throws IOException {
		System.out.println("===================================");
		System.out.println("Update Options");
		System.out.println("===================================");
		System.out.println("1) Add Order Item from Order");
		System.out.println("2) Remove Order Item frome Order");
		System.out.println("===================================");
		System.out.print("Please enter your choice: ");
		int choice = sc.nextInt();

		switch(choice) {
			case 1:
				addOrderItem(o,o.getOrderItemList().size());
				break;
			case 2:				
				deleteOrderItem(o);
				break;
			default: 
				break;
		}
	}
	
	//FOR UPDATE PORTION
	private void addOrderItem(Order o, int counter) throws IOException {
		System.out.println("===================================");
		System.out.println("Select the order item to be added");
		System.out.println("===================================");
		insertOrderItems(o.getOrderId(),counter);
		System.out.print("Please enter your choice: ");
		int choice = sc.nextInt();
		
	}
	
	//FOR UPDATE PORTION
	private void deleteOrderItem(Order o) throws IOException {
		System.out.println("===================================");
		System.out.println("Select the order item to be removed");
		System.out.println("===================================");
		for(int i=0;i<o.getOrderItemList().size() ; i++ ) {
			System.out.print(i+1 + ") ");
			System.out.print(o.getOrderItemList().get(i).getOrderItemName());
			System.out.println("");
		};
		System.out.print("Please enter your choice: ");
		int choice = sc.nextInt();
		or.removeOrderItem(o.getOrderId(),o.getOrderItemList().get(choice-1).getOrderItemId());
	}
	
	
	
	//DELETE ORDER - DELETES BOTH ORDER AND ORDER ITEMS RELATED TO THE ORDER
	private void deleteOrder() throws IOException {
		System.out.println("================================================");
        System.out.println("Select the Order you would like to delete ");
		System.out.println("================================================");
		for(int i=0;i<or.getAllOrders().size() ; i++ ) {
			System.out.print(i+1 + ") ");
			System.out.print(or.getAllOrders().get(i).getOrderId());
			System.out.println("");
		};
		System.out.print("Please enter your choice: ");
		int choice = sc.nextInt();
		do {
			try {
				or.deleteOrder(or.getAllOrders().get(choice-1).getOrderId());
				System.out.println("==================================");
				System.out.println("Order deleted successfully");
				System.out.println("==================================");
				
				displayOption();
			} catch (Exception ex) {
				System.out.println("==================================");
				System.out.println("Unsuccessful. Please try again");
				System.out.println("==================================");
				deleteOrder();
			}
		} while (choice > or.getAllOrders().size() || choice < 1);
	}

}
