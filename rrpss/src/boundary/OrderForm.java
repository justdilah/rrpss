package boundary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import controller.AlaCarteController;
import controller.OrderController;
import controller.PromotionController;
import entity.AlaCarte;

public class OrderForm {
	
	AlaCarteController al = new AlaCarteController();
	PromotionController ps = new PromotionController();
	OrderController or = new OrderController();
	
	Scanner sc = new Scanner(System.in);

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

	private void insertOrder() throws IOException {
		System.out.println("=================================");
        System.out.println("\t Creating an Order ");
		System.out.println("=================================");
		System.out.println("Select Menu Items ");
		
		System.out.println("=================================");
        System.out.println("\t Ala Carte Items ");
		System.out.println("=================================");
		
		for(int i=0;i< al.getAllFoodItem().size() ; i++ ) {
			System.out.print(i+1 + ") ");
			System.out.print(al.getAllFoodItem().get(i).getFoodName());
			System.out.println("");		
		}
		
		
		System.out.println("=================================");
        System.out.println("\t Promotion Sets ");
		System.out.println("=================================");

		for(int i=0;i<ps.getAllPromotionSets().size();i++) {
			System.out.print(al.getAllFoodItem().size() + 1 + i+ ") "  +  ps.getAllPromotionSets().get(i).getPackName());
			System.out.println("");
		}
		ArrayList<String> names = new ArrayList<>();
		System.out.println("Please enter your choice: ");
		
		int c = sc.nextInt();
		
		int counter = 0;
		
		System.out.print("Enter the qty: ");
		int qty = sc.nextInt();
		if(c <= al.getAllFoodItem().size()) {
			names.add(al.getAllFoodItem().get(c-1).getFoodName());
			or.addAlaCarteOrderItem(++counter,al.getAllFoodItem().get(c-1), qty);
		} else {
			System.out.println(c-1-al.getAllFoodItem().size());
			names.add(ps.getAllPromotionSets().get(c-1-al.getAllFoodItem().size()).getPackName());
			or.addPromoOrderItem(++counter,ps.getAllPromotionSets().get(c-1-al.getAllFoodItem().size()), qty);
		}
		
		while(true) {
			
			
			System.out.println("=================================");
			System.out.println("Please enter 100 to end");
			System.out.println("=================================");
			
			System.out.println("Please enter your choice: ");
			c = sc.nextInt();
			
			if(c == 100) {
				break;
			} else {
				System.out.print("Enter the qty: ");
				qty = sc.nextInt();
				
			}
			
			if(c <= al.getAllFoodItem().size()) {
				names.add(al.getAllFoodItem().get(c-1).getFoodName());
				or.addAlaCarteOrderItem(++counter,al.getAllFoodItem().get(c-1), qty);
			} else {
				System.out.println(c-1-al.getAllFoodItem().size());
				names.add(ps.getAllPromotionSets().get(c-1-al.getAllFoodItem().size()).getPackName());
				or.addPromoOrderItem(++counter,ps.getAllPromotionSets().get(c-1-al.getAllFoodItem().size()), qty);
			}
			
		
			
			System.out.println("=================================");
			System.out.println("Menu items that are added");
			System.out.println("=================================");
			for(int k = 0; k<names.size();k++) {
				System.out.println(names.get(k));
			}
			
	
			
		}
		
		System.out.println("Please enter your Staff ID");
		String id = sc.next();
		or.setStaffID(id);
		
		or.setTimeStamp(LocalTime.now());
		
		or.setisPaid(false);
		
		System.out.println("Please enter Customer Phone Number");
		Scanner scan = new Scanner(System.in);
		String pn = "";
		pn+=scan.nextLine();	

		
		or.addOrder(pn);
		
		System.out.println("Successful i guess");
		
		
	}

	private void displayOrder() throws FileNotFoundException {
			System.out.println("=========================================================");
			System.out.println("\tOrders!");
	    	System.out.println("=========================================================");
	    	
			for(int i = 0;i<or.getAllOrders().size();i++) {
				double totalPrice = 0;
				System.out.println("Order ID : " + or.getAllOrders().get(i).getOrderId());
				System.out.println("Time Ordered : " + or.getAllOrders().get(i).getTimeStamp());
				System.out.println("Payment Completed? : " + or.getAllOrders().get(i).getIsPaid());
				System.out.println("Staff ID : " + or.getAllOrders().get(i).getStaffId());
				for(int k=0;k<or.getAllOrders().get(i).getOrderItemList().size();k++) {
					System.out.print(or.getAllOrders().get(i).getOrderItemList().get(k).getOrderItemName() + " ");
					System.out.print(or.getAllOrders().get(i).getOrderItemList().get(k).getOrderItemQty());
					totalPrice+= or.getAllOrders().get(i).getOrderItemList().get(k).getOrderItemPrice();
				}
				System.out.println("Total Price : " + totalPrice);
			}
	}

	public void updateOrder() {
		
	}

	private void deleteOrder() {
		System.out.println("================================================");
        System.out.println("Select the Order you would like to delete ");
		System.out.println("================================================");
		System.out.print("Please enter your choice: ");
		int choice = sc.nextInt();
		try {
			System.out.println("==================================");
			System.out.println("Order deleted successfully");
			System.out.println("==================================");
			
			displayOption();
		} catch (Exception ex) {
			System.out.println("==================================");
			System.out.println("Unsuccessful. Please try again");
			System.out.println("==================================");
		}
	}

}
