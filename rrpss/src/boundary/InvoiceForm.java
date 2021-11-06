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
import controller.TableController;
import entity.Customer;
import entity.Invoice;
import entity.Order;
import entity.OrderItem;
import entity.Table;

public class InvoiceForm {
	
	InvoiceController  ivc= new InvoiceController();
	OrderController or = new OrderController();
	TableController tc = new TableController();
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
		for(int i=0;i<tc.getAllOccupiedTables().size();i++) {
			print(i+1 + ") " + tc.getAllOccupiedTables().get(i).getTableNo());
		}
		
		print("Please enter your choice : ");
		String c = sc.nextLine();
		
		//RETRIEVE SPECIFIC TABLE
		Table table = tc.getAllOccupiedTables().get(Integer.parseInt(c)-1);
		// TODO - implement InvoiceForm.printInvoice
		print("=================================");
        print("\t Invoice ");
		print("=================================");
		

		
		Customer cust = new Customer();
		ArrayList<Order> orders = new ArrayList<>();
		orders = ivc.getUnpaidOrdersByTableNo(table);
		
		cust = or.getUnpaidOrders().get(0).getCust();
		
		double subTotalPrice = 0;
		Customer check = new Customer();
		double discount = 0;
		
	
	
		
		print("Table No : " + table.getTableNo());
		print("Customer Name : " + cust.getPersName());
		print("------------------------------------------------------------");
		
		double st = 0;
		print("Order Items : ");
		int counter = 0;
		if(ivc.checkFileEmpty() > 0) {
			counter = 8000;
		} else {
			
		}
		
		for(int k=0;k<orders.size();k++) {
			ArrayList<OrderItem> orderItemList = orders.get(k).getOrderItemList();
			orders.get(k).setIsPaid(true);
			or.replaceOrder(orders.get(k));
			for(int l=0;l<orderItemList.size();l++) {
				OrderItem oi = orderItemList.get(l);
				System.out.print(oi.getOrderItemName() + "\t\t");
				System.out.print(oi.getOrderItemQty() + "\t");
				System.out.print(oi.getOrderItemPrice());
				System.out.println("");
				st+=oi.getOrderItemPrice();
				
			}
			double gst1 = ivc.calculateGst(st);
			double discount1 =ivc.calculateDiscount(st);
			Invoice i = new Invoice(counter,orders.get(k).getOrderId(),orders.get(k).getDate(),ivc.calculateSubTotalPriceOrder(orderItemList),discount1,gst1,Integer.parseInt(orders.get(k).getStaffId()), table.getTableNo());
			ivc.saveInvoice(i);
			subTotalPrice+=st;
			counter++;
			
		}
		
		if(check.isMember(cust.getCustId())) {
			discount = ivc.calculateDiscount(subTotalPrice);
		} else {
			System.out.println("Would you like to be a member? (Y/N) ");
			String member = sc.nextLine(); 
			switch(member) {
				case "Y":
					cust.setMembership(true);
					cc.updateMembership(cust);
					discount = ivc.calculateDiscount(subTotalPrice);
					break;
				case "N":
					break;
			}
		}
		
		double gst =  0.07 * subTotalPrice;
		
		print("------------------------------------------------------------");
		print("Sub Total Price : " + subTotalPrice);
		print("GST : " + String.format("%.2f",gst));
		print("Discount : " + String.format("%.2f",discount));
		print("Total Price : " + ((subTotalPrice + gst)- discount));
		print("------------------------------------------------------------");
		
		tc.updateTableStatusString("VACANT",table.getTableNo());
		//SAVE IN THE ORDER - IS PAID - DONE 
		//SAVE IN THE TABLE AS VACANT
		
		//TABLE NUMBER BECOMES ZERO?? 
		//SAVE THIS IN THE Invoice.csv
		
	}
	
	public void print(String message)
	{
	    System.out.println(message);
	}

}