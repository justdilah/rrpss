package boundary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import controller.CustomerController;
import controller.InvoiceController;
import controller.OrderController;
import controller.ResTableController;
import entity.Customer;
import entity.Invoice;
import entity.Order;
import entity.OrderItem;
import entity.Staff;
import entity.Table;

public class InvoiceForm {
	
	InvoiceController  ivc= new InvoiceController();
	OrderController or = new OrderController();
	ResTableController tc = new ResTableController();
	CustomerController cc = new CustomerController();
	
	public void displayOption() throws IOException {
		printInvoice();
	}

	public void printInvoice() throws IOException {
		
		print("=================================");
        print("\t Generating Invoice ");
		print("=================================");
		
		print("Please select Table Number : "); 
		Scanner sc = new Scanner(System.in);
		
		//RETRIEVE OCCUPIED TABLES 
		if(tc.getAllOccupiedTables().size() > 0) {
			for(int i=0;i<tc.getAllOccupiedTables().size();i++) {
				print(i+1 + ") Table No " + tc.getAllOccupiedTables().get(i).getTableNo());
			}
			
			print("Please enter your choice : ");
			String c = sc.nextLine();
			
			//RETRIEVE SPECIFIC TABLE
			Table table = tc.getAllOccupiedTables().get(Integer.parseInt(c)-1);
		
			
			Customer cust = new Customer();
			ArrayList<Order> orders = new ArrayList<>();
			
			//RETURN ALL THE UNPAID ORDERS BY THAT TABLE NO 
			orders = ivc.getUnpaidOrdersByTableNo(table);
			
			//RETURN CUST
			cust = orders.get(0).getCust();
			System.out.println(cust.getCustId());
			
			//RETURN UNPAID ORDERS
			String staffID = or.getUnpaidOrders().get(0).getStaffId();
			
			double subTotalPrice = 0;
			Customer check = new Customer();
			double discount = 0;
			
			//ACTS AS A CHECKER TO CHECK IF CUST IS A MEMBER
			Boolean checkMember = false;
					

			System.out.println(check.isMember(cust.getCustId()));
			//TO ASK THE CUSTOMER IF THEY WANT TO BE A MEMBER
			if(check.isMember(cust.getCustId())) {
				checkMember = true;
			} else {
				System.out.println("Would you like to be a member? (Y/N) ");
				String member = sc.nextLine(); 
				switch(member) {
					case "Y":
						cust.setMembership(true);
						cc.updateMembership(cust);
						checkMember = true;
						break;
					case "N":
						break;
				}
			}
			
		
			print("=================================");
	        print("\t Invoice ");
			print("=================================");
			
			print("Table No : " + table.getTableNo());
			print("Customer Name : " + cust.getPersName());
			print("Staff ID : " + staffID);
			print("------------------------------------------------------------");
			
			//PRINTS OUT THE ORDER ITEMS
			double st = 0;
			print("Order Items : ");
			int counter = 0;
			if(ivc.checkFileEmpty() == 1) {
				counter = 8000;
			} else {
				int last = ivc.getAllInvoice().size() - 1;
				counter = ivc.getAllInvoice().get(last).getInvoiceNo() + 1;
			}
			
			double discount1 = 0;
			
			//RETURNS THE ORDERS 
			for(int k=0;k<orders.size();k++) {
				ArrayList<OrderItem> orderItemList = orders.get(k).getOrderItemList();
				Order o = orders.get(k);
				o.setIsPaid(true);
				or.replaceOrder(o);
				
				for(int l=0;l<orderItemList.size();l++) {
					OrderItem oi = orderItemList.get(l);
					System.out.print(oi.getOrderItemName() + " ");
					System.out.print(oi.getOrderItemQty() + " ");
					System.out.print("$" + oi.getOrderItemPrice());
					System.out.println("");
					subTotalPrice+=oi.getOrderItemPrice();
				}
				
				
				double stp = ivc.calculateSubTotalPriceOrder(orderItemList);
				double gst1 = ivc.calculateGst(stp);
				
				if(checkMember) {
					discount1 =ivc.calculateDiscount(stp);
				} else {
					discount1 = 0;
				}
				
				Invoice i = new Invoice(counter,orders.get(k).getOrderId(),orders.get(k).getDate(),stp,discount1,gst1,Integer.parseInt(orders.get(k).getStaffId()), table.getTableNo());
				ivc.saveInvoice(i);
				counter++;
				
			}
			
			//GENERALLY FOR ORDERS
			if(checkMember) {
				discount = ivc.calculateDiscount(subTotalPrice);
			} else {
				discount = 0;
			}
			
			double gst =  ivc.calculateGst(subTotalPrice);
			
			print("------------------------------------------------------------");
			print("Sub Total Price : $" + subTotalPrice);
			print("GST : $" + String.format("%.2f",gst));
			print("Discount : $" + String.format("%.2f",discount));
			print("Total Price : $" + String.format("%.2f",((subTotalPrice + gst)- discount)));
			print("------------------------------------------------------------");
			tc.updateTableStatusString("VACANT",table.getTableNo());
			
		} else {
			print("============================================================");
			print("There are no occupied tables");
			print("============================================================");
		}
		
	}
	
	public void print(String message)
	{
	    System.out.println(message);
	}

}