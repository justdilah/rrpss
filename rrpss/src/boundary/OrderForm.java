package boundary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import controller.OrderController;
import entity.AlaCarte;
import entity.Customer;
import entity.Order;
import entity.OrderItem;
import entity.PromotionSet;
import entity.Staff;
import entity.Table;

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
		
		System.out.println("Please enter your choice: ");
		
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
			int tableNo = sc.nextInt();
			
			Table t = new Table();
			while(!t.tableExists(tableNo)) {
				System.out.println("Table Number does not exist");
				System.out.println("Please Try again");
				System.out.println("Please enter Table Number: ");
				tableNo = sc.nextInt();
			}
			
			int index = 1;
			
			BufferedReader reader = new BufferedReader(new FileReader("DataSet/Order.csv"));
			int counter = 0;
			
			while(reader.readLine()!=null) {
				counter++;
			}
	
			if (counter == 1) { 
				insertOrderItems(index, 0);
			} else {
				index = or.getAllOrders().size() + 1;
				insertOrderItems(index, 0);
			}
					
			
			System.out.println("Please enter your Staff ID: ");
			int staffid = sc.nextInt();
			Staff s = new Staff();
			
			while(!s.isIdExists(staffid)) {
				System.out.println("Staff ID does not exist");
				System.out.println("Please Try again");
				System.out.println("Please enter your Staff ID: ");
				staffid = sc.nextInt();
			}
			
			System.out.println("Please enter Customer Phone Number: ");
			String phoneNum = sc.next();
			
			Customer c = new Customer();
			while(!c.custExists(phoneNum)) {
				System.out.println("Phone Number does not exist");
				System.out.println("Please Try again");
				System.out.println("Please enter Customer Phone Number: ");
				phoneNum = sc.next();
			}
			
			or.addOrder(index, LocalTime.now(),LocalDate.now(), staffid, false, phoneNum, tableNo);
			System.out.println("==================================");
			System.out.println("Order added successfully");
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
			System.out.println();		
		}
		
		ArrayList<PromotionSet> promotionSetItems = or.getAllPromoSets();
		System.out.println("=================================");
        System.out.println("\t Promotion Sets ");
		System.out.println("=================================");

		for(int i=0;i<promotionSetItems.size();i++) {
			System.out.print(alaCarteItems.size() + 1 + i+ ") "  +  promotionSetItems.get(i).getPackName());
			System.out.println();
		}
		
		ArrayList<String> names = new ArrayList<>();
		
		System.out.println("Please enter your choice: ");
		int c = sc.nextInt();
		
		System.out.print("Enter the qty: ");
		int qty = sc.nextInt();
		
		if(c <= alaCarteItems.size()) {
			AlaCarte a = alaCarteItems.get(c-1);
			names.add(a.getFoodName());
			or.addAlaCarteOrderItem(++counter,a, qty, orderId);
		} else {
			PromotionSet p = promotionSetItems.get(c-1-alaCarteItems.size());
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
			System.out.println("Please enter 100 to complete the order");
			System.out.println("====================================");
			
			System.out.println("Please enter your choice: ");
			c = sc.nextInt();
			
			if(c == 100) {
				break;
			} else {
				System.out.print("Enter the qty: ");
				qty = sc.nextInt();
			}
						
			if(c <= alaCarteItems.size()) {
				names.add(alaCarteItems.get(c-1).getFoodName());
				or.addAlaCarteOrderItem(++counter,alaCarteItems.get(c-1), qty, orderId);
			} else {
				names.add(promotionSetItems.get(c-1-alaCarteItems.size()).getPackName());
				or.addPromoOrderItem(++counter,promotionSetItems.get(c-1-alaCarteItems.size()), qty, orderId);
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
	private void displayOrder() throws IOException {
			System.out.println("=========================================================");
			System.out.println("\tDisplay Orders");
	    	System.out.println("=========================================================");
	    	if(or.getAllOrders() != null) {
	    		ArrayList<Order> orderList = or.getAllOrders();
				for(int i = 0;i<orderList.size();i++) {
					double totalPrice = 0;
					System.out.println("Order ID : " + orderList.get(i).getOrderId());
					System.out.println("Time Ordered : " + orderList.get(i).getTimeStamp());
					System.out.println("Date Ordered : " + orderList.get(i).getDate());
					System.out.println("Payment Completed? : " + orderList.get(i).getIsPaid());
					System.out.println("Staff ID : " + orderList.get(i).getStaffId());
					System.out.println("Table No : " + orderList.get(i).getTable().getTableNo());
					
					ArrayList<OrderItem> orderItemList = orderList.get(i).getOrderItemList();
					for(int k=0;k<orderItemList.size();k++) {
						System.out.print(orderItemList.get(k).getOrderItemName() + " ");
						System.out.print(orderItemList.get(k).getOrderItemQty());
						System.out.println();
						totalPrice+= orderItemList.get(k).getOrderItemPrice();
					}
					System.out.println("Total Price : " + totalPrice);
				}
	    	} else {
	    		System.out.println("================================================");
		        System.out.println("There are no orders to be displayed ");
				System.out.println("================================================");
				displayOption();
	    	}
			
	}

	//FOR UPDATE ORDER  
	private void updateOrder() throws IOException {
		System.out.println("================================================");
        System.out.println("Select the Order you would like to update ");
		System.out.println("================================================");
		
		if(or.getAllOrders() != null) {
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
		} else {
			System.out.println("================================================");
	        System.out.println("There are no orders to be updated ");
			System.out.println("================================================");
			displayOption();
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
		
		if(or.getAllOrders() != null) {
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
		} else {
			System.out.println("================================================");
	        System.out.println("There are no orders to be deleted ");
			System.out.println("================================================");
			displayOption();
		}
		
	}

}
