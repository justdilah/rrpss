package boundary;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import controller.MainAppController;

public class MainAppUI {
	
	public static void main(String[] args) throws IOException 
	{
		print();
	}
	
	public static void print() throws IOException
	{
		MainAppController mc = new MainAppController();
		
		int choice = -1;
		Scanner sc = new Scanner(System.in);
		
		do {
		System.out.println("=================================");		
        System.out.println("\t Welcome to RRPSS");
		System.out.println("=================================");
		System.out.println("(1) Reservation");
		System.out.println("(2) Orders");
		System.out.println("(3) Sales Revenue Report");
		System.out.println("(4) Menu");
		System.out.println("(5) Promotion");
		System.out.println("(6) Payment");
		System.out.println("(7) Exit");
		
			
			do {
				try {
					System.out.print("Please enter your choice : ");
					choice = sc.nextInt();
					
				}catch(InputMismatchException e) 
				{	
					System.out.println("=================================");
					System.out.println("Invalid Entry has been entered. ");
					System.out.println("Please enter numbers only. ");
					System.out.println("=================================");
			        System.out.println("\t Welcome to RRPSS");
					System.out.println("=================================");
					System.out.println("(1) Reservation");
					System.out.println("(2) Orders");
					System.out.println("(3) Sales Revenue Report");
					System.out.println("(4) Menu");
					System.out.println("(5) Promotion");
					System.out.println("(6) Exit");
				}
				sc.nextLine();
			}while (choice == -1);
				
			
			switch (choice) {
			
				case 1: 
					mc.getReservationForm();
//					ReservationForm rf = new ReservationForm();
//					rf.displayOption();
					break;
					
				case 2: 
					mc.getOrderForm();
//					OrderForm of = new OrderForm();
//					of.displayOption();
					break;
					
				case 3:
					mc.getSalesRevenueMonthForm();
//					SalesRevenueMonth srmf = new SalesRevenueMonth();
//					srmf.displayOption();
					break; 
					
				case 4: 
					mc.getAlaCarteForm();
//					AlaCarteForm acf = new AlaCarteForm();
//					acf.displayOption();
					break;
					
				case 5:
					mc.getPromotionForm();
//					PromotionForm pf = new PromotionForm();
//					pf.displayOption(); 
					break; 
				
				case 6:
					mc.getInvoiceForm();
					break;
				
				case 7: 
					break;
				
				default:
			    	System.out.println("=========================================================");
					System.out.println("\tInvalid input. Please enter again!");
			    	System.out.println("=========================================================");
			    	System.out.println("(1) Reservation");
					System.out.println("(2) Orders ");
					System.out.println("(3) Sales Revenue Report ");
					System.out.println("(4) Menu");
					System.out.println("(5) Promotion");
					System.out.println("(6) Exit ");
					choice = sc.nextInt();
			}
		}while (choice <1||choice>6);
	}
	
}

