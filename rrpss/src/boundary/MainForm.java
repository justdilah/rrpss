package boundary;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import controller.MainController;

public class MainForm {
	public static void main(String[] args) throws IOException, ParseException {
		
		MainController mc = new MainController();
			
		int choice = -1;
		Scanner sc = new Scanner(System.in);
		
		do {
		System.out.println("=================================");
        System.out.println("\t Welcome to RRPSS");
		System.out.println("=================================");
		System.out.println("1) Reservation");
		System.out.println("2) Orders ");
		System.out.println("3) Sales Revenue Report ");
		System.out.println("4) Exit ");
		
			
			do {
				try {
					System.out.print("Please enter your choice : ");
					choice = sc.nextInt();
					
				}catch(InputMismatchException e) {
					//System.out.println("");
					System.out.println("=================================");
					System.out.println("Invalid Entry has been entered. ");
					System.out.println("Please enter numbers only. ");
					System.out.println("=================================");
			        System.out.println("\t Welcome to RRPSS");
					System.out.println("=================================");
					System.out.println("1) Reservation");
					System.out.println("2) Orders ");
					System.out.println("3) Sales Revenue Report ");
				}
				sc.nextLine();
			}while (choice == -1);
				
			
			switch (choice) {
			
			case 1: 
				mc.getMenuForm();
				break;
				
			case 2: 
				break;
				
			case 3:
				break; 
			
			default:
		    	System.out.println("=========================================================");
				System.out.println("\tInvalid input. Please enter again!");
		    	System.out.println("=========================================================");

			}
		}while (choice <4||choice>0);
	}
}