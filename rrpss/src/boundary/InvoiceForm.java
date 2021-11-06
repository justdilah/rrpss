package boundary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import controller.InvoiceController;
import entity.Customer;
import entity.Invoice;
import entity.Order;
import entity.OrderItem;
import entity.Table;

public class InvoiceForm {
	
	InvoiceController  ivc= new InvoiceController();
	
	public void print(String message)
	{
	    System.out.println(message);
	}
	
	public void displayOption() throws IOException {
		printInvoice();
	}

	public void printInvoice() throws IOException {
		// TODO - implement InvoiceForm.printInvoice
		print("=================================");
        print("\t Generating Invoice ");
		print("=================================");
		
		print("Please select Table Number : "); 
		
		Scanner sc = new Scanner(System.in);
		Table t = new Table();
		for(int i=0;i<t.getAllOccupiedTables().size();i++) {
			print(i+1 + ") " + t.getAllOccupiedTables().get(i).getTableNo());
		}
		
		print("Please enter your choice : ");
		String c = sc.nextLine();
		
		print("=================================");
        print("\t Invoice ");
		print("=================================");
		
		Table table = t.getAllOccupiedTables().get(Integer.parseInt(c)-1);
		Order o = new Order();
		
		Customer cust = new Customer();
		
		ArrayList<Order> orders = new ArrayList<>();
		for(int k=0;k<o.getAllOrders().size();k++) {
			if(o.getUnpaidOrders().get(k).getTable().getTableNo() == t.getAllOccupiedTables().get(Integer.parseInt(c)-1).getTableNo()) {
				
				orders.add(o.getUnpaidOrders().get(k));
				cust = o.getUnpaidOrders().get(k).getCust();
			}
		}
		
//		t.updateTableStatusString("VACANT",table.getTableNo());
		
		
		double subTotalPrice = 0;
		Customer check = new Customer();
		double discount = 0;
		
		for(int k=0;k<orders.size();k++) {
			ArrayList<OrderItem> orderItemList = orders.get(k).getOrderItemList();
			for(int l=0;l<orderItemList.size();l++) {
				subTotalPrice+=orderItemList.get(l).getOrderItemPrice();
			}
		}
		
		if(check.isMember(cust.getCustId())) {
			discount = 0.10 * subTotalPrice;
		} else {
			System.out.println("Would you like to be a member? (Y/N) ");
			String member = sc.nextLine(); 
			switch(member) {
				case "Y":
					cust.setMembership(true);
					discount = 0.10 * subTotalPrice;
					break;
				case "N":
					cust.setMembership(false);
					break;
			}
		}
		
		double gst =  0.07 * subTotalPrice;
	
		int counter = 8000;
		print("Table No : " + table.getTableNo());
		print("Customer Name : " + cust.getPersName());
		print("------------------------------------------------------------");
		
		double st = 0;
		print("Order Items : ");
		for(int k=0;k<orders.size();k++) {
			ArrayList<OrderItem> orderItemList = orders.get(k).getOrderItemList();
//			orders.get(k).setIsPaid(true);
			
			o.replaceOrder(orders.get(k));
			for(int l=0;l<orderItemList.size();l++) {
				OrderItem oi = orderItemList.get(l);
				System.out.print(oi.getOrderItemName() + "\t\t");
				System.out.print(oi.getOrderItemQty() + "\t");
				System.out.print(oi.getOrderItemPrice());
				System.out.println("");
				st+=oi.getOrderItemPrice();
				
			}
			double gst1 = 0.07 * st;
			double discount1 = 0.10 * st;
			Invoice i = new Invoice(counter,orders.get(k).getOrderId(),orders.get(k).getDate(),st,discount1,gst1,Integer.parseInt(orders.get(k).getStaffId()), table.getTableNo());
			ivc.saveInvoice(i);
			counter++;
			
		}
		
		
		double priceOrder = 0;
		for(int p=0;p<orders.size();p++) {
//			ArrayList<OrderItem> orderItemList1 = orders.get(p).getOrderItemList();
//			for(int y=0;y<orderItemList1.size();y++) {
//				priceOrder += orderItemList1.get(y).getOrderItemPrice();
//			})
			
			
			
		}
		
		print("------------------------------------------------------------");
		print("Sub Total Price : " + subTotalPrice);
		print("GST : " + String.format("%.2f",gst));
		print("Discount : " + String.format("%.2f",discount));
		print("Total Price : " + ((subTotalPrice + gst)- discount));
		print("------------------------------------------------------------");
		//SAVE IN THE ORDER - IS PAID - DONE 
		//SAVE IN THE TABLE AS VACANT
		
		//TABLE NUMBER BECOMES ZERO?? 
		//SAVE THIS IN THE Invoice.csv
		
	}

}