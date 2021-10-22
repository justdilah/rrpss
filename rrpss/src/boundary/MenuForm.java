package boundary;

import java.io.FileNotFoundException;
import java.util.*;

import controller.MenuController;
import entity.MenuItemType;

public class MenuForm {
		
		MenuController mc = new MenuController();
	
		Scanner sc = new Scanner(System.in);

		public void updateMenuItem() {
			// TODO - implement MenuForm.updateMenuItem
			System.out.println("=================================");
	        System.out.println("\t Update Food Item Options ");
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
			throw new UnsupportedOperationException();
		}

		public void deleteMenuItem() {
			// TODO - implement MenuForm.deleteMenuItem
			System.out.println("Enter food item to be deleted");
			Scanner sc = new Scanner(System.in);
			String name = sc.next(); 
			mc.deleteFoodItem(0);
			throw new UnsupportedOperationException();
		}
	

		public void insertMenuItem() {
			// TODO - implement MenuForm.insertMenuItem
			System.out.println("Add a food item");
			System.out.println("Enter the name of the food");
			String name = sc.next();
			System.out.println("Enter the description of the food");
			String desc = sc.next();
			System.out.println("Enter the price of the food");
			double price = sc.nextDouble();
			System.out.println("Enter the food type");
			String foodtype = sc.next();
			mc.addFoodItem(name, desc, price, MenuItemType.valueOf(foodtype));
			throw new UnsupportedOperationException();
		}

		public void displayMenu() throws FileNotFoundException {
			// TODO - implement MenuForm.displayMenu
			System.out.println("==================================");
			System.out.println("\t Menu ");
			System.out.println("==================================");
			for(int i=0;i< mc.getAllFoodItem().size() ; i++ ) {
				System.out.print(i + ") ");
				System.out.print(mc.getAllFoodItem().get(i).getMenuName() + " $");
				System.out.print(mc.getAllFoodItem().get(i).getMenuPrice());
				System.out.println("");
			};
			
		}


		
		public void displayOption() throws FileNotFoundException {
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
				insertMenuItem();
				
				break;
			case 3:
				updateMenuItem();
				
				break;
			case 4:
				deleteMenuItem();
				break;
			case 5:
				break;
		}
			
		}	
		
			
}
