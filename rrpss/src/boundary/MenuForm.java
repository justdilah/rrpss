package boundary;

import java.util.*;

import controller.MenuController;

public class MenuForm {
		
		MenuController mc = new MenuController();
		
		
		Scanner sc = new Scanner(System.in);
		public void addFoodItem() {
			System.out.println("Add a food item");
			System.out.println("Enter the name of the food");
			String name = sc.next();
			System.out.println("Enter the description of the food");
			String desc = sc.next();
			System.out.println("Enter the price of the food");
			Double price = sc.nextDouble();
			System.out.println("Enter the food type");
			String foodtype = sc.next();
		}
		
		public void displayMenu() {
			System.out.println("==================================");
			System.out.println("\t Menu ");
			System.out.println("==================================");
		}
		
		public void deleteFoodItem() {
			
		}
		
		public void editFoodItem() {
			System.out.println("=================================");
	        System.out.println("\t Edit Food Item Options ");
			System.out.println("=================================");
			System.out.println("1) Edit Food Item Name");
			System.out.println("2) Edit Food Description ");
			System.out.println("3) Edit Food Price ");
			System.out.println("4) Edit Food Type");
			System.out.println("5) Back");
			System.out.print("Please enter your choice : ");
			int choice = sc.nextInt();
			
			switch(choice) {
				case 1:
					break;
				case 2: 
					break;
				case 3:
					break;
				case 4: 
					break;
				case 5:
					break;
			}
		}
		
		public void displayForm() {
			System.out.println("=================================");
	        System.out.println("\t Menu Options ");
			System.out.println("=================================");
			System.out.println("1) Display Menu ");
			System.out.println("2) Add Food Item ");
			System.out.println("3) Edit Food Description ");
			System.out.println("4) Delete Food Item ");
			System.out.println("5) Back");
			
			Scanner sc = new Scanner(System.in);
			int choice = sc.nextInt();
			
			switch(choice) {
			case 1:
				displayMenu();
				break;
			case 2:
				addFoodItem();
				
				break;
			case 3:
				editFoodItem();
				
				break;
			case 4:
				deleteFoodItem();
				break;
			case 5:
				break;
		}
			
		}	
		
			
}
