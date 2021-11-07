package boundary;

import java.io.IOException;
import java.util.Scanner;

import controller.SaleRevenueMonthController;
import entity.OrderItem;
import entity.Invoice;

public class SaleRevenueMonthForm {
	
	SaleRevenueMonthController sr = new SaleRevenueMonthController();
	
	Scanner sc = new Scanner(System.in);
	
	//SHOW ORDER
	public void displayOption() throws IOException {
		System.out.println("===========================================================");
        System.out.println("\t\t Monthly Revenue Report ");
		System.out.println("===========================================================");
		System.out.println("Guide: January = 1 ... December = 12");
		System.out.println("Which month's revenue report would you like to print?");
		System.out.print("Enter Month: ");
		
		Scanner sc = new Scanner(System.in);
		int monthchosen = sc.nextInt();
		
		if(monthchosen > 0 && monthchosen < 13) {
			sr.generateMonthRevenue(monthchosen);
		}else {
			System.out.print("No such month, pls try again.");
		}
		
	}
}