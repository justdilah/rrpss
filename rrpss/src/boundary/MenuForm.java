package boundary;

import entity.AlaCarte;
import entity.FoodType;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import controller.MenuController;

public class MenuForm {
		
		MenuController mc = new MenuController();
	
		Scanner sc = new Scanner(System.in);

		public void updateMenuItem() throws IOException {
			// TODO - implement MenuForm.updateMenuItem
			System.out.println("=================================");
	        System.out.println("Select the Food Item you would like to update ");
			System.out.println("=================================");
			for(int i=0;i< mc.getAllFoodItem().size() ; i++ ) {
				System.out.print(i+1 + ") ");
				System.out.print(mc.getAllFoodItem().get(i).getFoodName() + " $");
				System.out.print(mc.getAllFoodItem().get(i).getFoodPrice());
				System.out.println("");
			};
			
			int option = sc.nextInt();
			AlaCarte c = mc.getFoodByName(mc.getAllFoodItem().get(option-1).getFoodName());
						
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
					setFoodName(c);
					break;
					
				case 2: 
					setFoodDesc(c);
					break;
				case 3:
					System.out.println("Enter the new food price");
				    double price=sc.nextDouble();
				    c.setFoodPrice(price);
					break;
					
				case 4: 
					System.out.println("Enter the new food type");
					FoodType ft = foodTypeOptions();
					c.setFoodType(ft);
					break;
					
				case 5:
					break;
			}
			
			mc.updateFoodItem(c);

		}
		
		public void setFoodName(AlaCarte c) {
			System.out.println("Enter the new food item name");
			Scanner scan = new Scanner(System.in);
	        String name="";
	        name+=scan.nextLine();
		    c.setFoodName(name);
		}
		
		public void setFoodDesc(AlaCarte c) {
			System.out.println("Enter the new food item description");
			Scanner scan = new Scanner(System.in);
	        String desc="";
	        desc+=scan.nextLine();
		    c.setFoodDesc(desc);
		}
		
		
		public void deleteMenuItem() throws IOException {
			// TODO - implement MenuForm.deleteMenuItem
			System.out.println("=================================");
	        System.out.println("Select the Food Item you would like to delete ");
			System.out.println("=================================");
			for(int i=0;i< mc.getAllFoodItem().size() ; i++ ) {
				System.out.print(i+1 + ") ");
				System.out.print(mc.getAllFoodItem().get(i).getFoodName() + " $");
				System.out.print(mc.getAllFoodItem().get(i).getFoodPrice());
				System.out.println("");
			};
			int choice = sc.nextInt();
			AlaCarte c = mc.getFoodByName(mc.getAllFoodItem().get(choice-1).getFoodName());
			mc.deleteFoodItem(c);
		}
		
		public FoodType foodTypeOptions() {
			
			System.out.println("Choose the food type");
			System.out.println("1) Starters");
			System.out.println("2) Drink");
			System.out.println("3) Main Course");
			System.out.println("4) Dessert ");
			int c = sc.nextInt();
			
			FoodType ft = null;
			
			do {
				switch(c) {
				case 1:
					ft = FoodType.Starters;
					break;
				case 2:
					ft = FoodType.Drinks;
					break;	
				case 3:
					ft = FoodType.Main_Course;
					break;
				case 4: 
					ft = FoodType.Desserts;
					break;
				default: 
					System.out.println("=========================================================");
					System.out.println("\tInvalid input. Please try again!");
			    	System.out.println("=========================================================");
			    	System.out.println("Choose the food type");
					System.out.println("1) Starters");
					System.out.println("2) Beverages");
					System.out.println("3) Main Course");
					System.out.println("4) Dessert ");
					c = sc.nextInt();
					break;
				}
			} while(c > 4 || c < 1);
			return ft;
		
		}
	

		public void insertMenuItem() throws IOException {
			// TODO - implement MenuForm.insertMenuItem
			System.out.println("==================================");
			System.out.println("/t Add a food item");
			System.out.println("==================================");
			System.out.println("Enter the food name");
			String name = sc.next();
			System.out.println("Enter the description");
			String desc = sc.next();
			String newdesc = desc.replace(',', '@');
			System.out.print(newdesc);
			System.out.println("Enter the price");
			double price = sc.nextDouble();
			FoodType ft = foodTypeOptions();
			
			mc.addFoodItem(name, desc, price,ft);
//			1
		}

		public void displayMenu() throws FileNotFoundException {
			// TODO - implement MenuForm.displayMenu
			System.out.println("==================================");
			System.out.println("\t Menu ");
			System.out.println("==================================");
			for(int i=0;i< mc.getAllFoodItem().size() ; i++ ) {
				System.out.print(i + ") ");
				System.out.print(mc.getAllFoodItem().get(i).getFoodName() + " $");
				System.out.print(mc.getAllFoodItem().get(i).getFoodPrice());
				System.out.println("");
			};
			
		}


		
		public void displayOption() throws IOException {
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
