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
		int monthchosen=-1,choice=-1;
		print("===========================================================");
        print("\t\t Monthly Revenue Report ");
		print("===========================================================");
		print("Guide: January = 1 ... December = 12");
		print("Which month's revenue report would you like to print?");
		print("Enter 0 to return to the Main Menu");
		print("Enter Month: ");
		
		do{
			try{
				monthchosen = sc.nextInt();
				if(monthchosen<0 || monthchosen > 12)
					print("Month does not exist, please enter a valid month: ");
				
			}catch(NumberFormatException e){
				print("Month chosen is of invalid format, please enter a valid month: ");
			}
		}while(monthchosen<0 || monthchosen >12);
		
		if(monthchosen ==0)
			MainAppUI.print();
		
		sr.generateMonthRevenue(monthchosen);
		print("Enter 0 to return to the Main Menu");
		do{
			try {
				choice = Integer.parseInt(sc.nextLine());
				if (choice != 0)
					print("Enter 0 to return to the Main Menu");
			}catch(NumberFormatException e){
				print("Enter 0 to return to the Main Menu");
			}
		}while(choice!=0);
		MainAppUI.print();
	}
	
	private void print(String message){
		System.out.println(message);
	}
}