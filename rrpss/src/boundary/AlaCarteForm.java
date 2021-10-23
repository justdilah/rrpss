package boundary;

import entity.AlaCarte;
import entity.FoodType;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import controller.MenuController;

public class AlaCarteForm {
		
		MenuController mc = new MenuController();
	
		Scanner sc = new Scanner(System.in);

		public void updateMenuItem() throws IOException {
			System.out.println("================================================");
	        System.out.println("Select the Food Item you would like to update ");
			System.out.println("================================================");
			
			for(int i=0;i< mc.getAllFoodItem().size() ; i++ ) {
				System.out.print(i+1 + ") ");
				System.out.print(mc.getAllFoodItem().get(i).getFoodName() + " $");
				System.out.print(mc.getAllFoodItem().get(i).getFoodPrice());
				System.out.println("");
			};
			int option = 0;
			do {
				
				System.out.print("Please enter your choice: ");		
				option = sc.nextInt();
				
			} while(option < 1 || option > mc.getAllFoodItem().size());
			
			AlaCarte c = mc.getFoodByName(mc.getAllFoodItem().get(option-1).getFoodName());
						
			System.out.println("=================================");
	        System.out.println("Update Food Item Options ");
			System.out.println("=================================");
			System.out.println("1) Edit Food Item Name");
			System.out.println("2) Edit Food Description ");
			System.out.println("3) Edit Food Price ");
			System.out.println("4) Edit Food Type");
			System.out.println("5) Back");
			System.out.println("\n");
			
			System.out.print("Please enter your choice : ");
			int choice = sc.nextInt();
			do {
				try {
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
					    mc.updateFoodItemPrice(c, price);
						break;
						
					case 4: 
						System.out.println("Enter the new food type");
						FoodType ft = foodTypeOptions();
						mc.updateFoodItemType(c, ft);
						break;
						
					case 5:
						displayOption();
						break;
						
					default:
						System.out.println("=========================================================");
						System.out.println("\tInvalid input. Please try again!");
				    	System.out.println("=========================================================");
				    	System.out.println("1) Edit Food Item Name");
						System.out.println("2) Edit Food Description ");
						System.out.println("3) Edit Food Price ");
						System.out.println("4) Edit Food Type");
						System.out.println("5) Back");
						choice = sc.nextInt();
						break;
					}
					System.out.println("==================================");
					System.out.println("Food item updated successfully");
					System.out.println("==================================");
					displayOption();
				} catch (Exception ex) {
					System.out.println("==================================");
					System.out.println("Unsuccessful. Please try again");
					System.out.println("==================================");
					updateMenuItem();
				}
				
			}while(choice > 5 || choice < 1);

		}
		
		public void setFoodName(AlaCarte c) throws IOException {
			System.out.println("Enter the new food item name");
			Scanner scan = new Scanner(System.in);
	        String name="";
	        name+=scan.nextLine();
		    mc.updateFoodItemName(c, name);
		}
		
		public void setFoodDesc(AlaCarte c) throws IOException {
			System.out.println("Enter the new food item description");
			Scanner scan = new Scanner(System.in);
	        String desc="";
	        desc+=scan.nextLine();
	        System.out.println(desc);
		    mc.updateFoodItemDesc(c,desc);
		}
		
		
		public void deleteMenuItem() throws IOException {
			System.out.println("================================================");
	        System.out.println("Select the Food Item you would like to delete ");
			System.out.println("================================================");
			for(int i=0;i< mc.getAllFoodItem().size() ; i++ ) {
				System.out.print(i+1 + ") ");
				System.out.print(mc.getAllFoodItem().get(i).getFoodName() + " $");
				System.out.print(mc.getAllFoodItem().get(i).getFoodPrice());
				System.out.println("");
			};
			System.out.print("Please enter your choice: ");
			int choice = sc.nextInt();
			try {
				AlaCarte c = mc.getFoodByName(mc.getAllFoodItem().get(choice-1).getFoodName());
				mc.deleteFoodItem(c);
				System.out.println("==================================");
				System.out.println("Food item deleted successfully");
				System.out.println("==================================");
				displayOption();
			} catch (Exception ex) {
				System.out.println("==================================");
				System.out.println("Unsuccessful. Please try again");
				System.out.println("==================================");
				deleteMenuItem();
			}
			
		}
		
		public FoodType foodTypeOptions() {
			
			System.out.println("Choose the food type");
			System.out.println("1) Starters");
			System.out.println("2) Drink");
			System.out.println("3) Main Course");
			System.out.println("4) Dessert ");
			System.out.print("Please enter your choice: ");
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
					System.out.println("\t Invalid input. Please try again!");
			    	System.out.println("=========================================================");
			    	System.out.println("Choose the food type");
					System.out.println("1) Starters");
					System.out.println("2) Beverages");
					System.out.println("3) Main Course");
					System.out.println("4) Dessert ");
					System.out.print("Please enter your choice: ");
					c = sc.nextInt();
					break;
				}
			} while(c > 4 || c < 1);
			return ft;
		
		}
	

		public void insertMenuItem() throws IOException {
			System.out.println("==================================");
			System.out.println("\t Add a food item");
			System.out.println("==================================");
			System.out.println("Enter the food name");
			Scanner can = new Scanner(System.in);
			String name ="";
	        name+=can.nextLine();
	        
			System.out.println("Enter the description");
			Scanner scan = new Scanner(System.in);
			String desc="";
			desc+=scan.nextLine();
	        
			String newdesc = desc.replace(',', '/');
			System.out.println("Enter the price");	
			double price = sc.nextDouble();
			
			FoodType ft = foodTypeOptions();

			try {
				mc.addFoodItem(name, newdesc, price,ft);
				System.out.println("==================================");
				System.out.println( name +  " added successfully");
				System.out.println("==================================");
				displayOption();
			} catch (Exception ex) {
				System.out.println("==================================");
				System.out.println("Unsuccessful. Please try again");
				System.out.println("==================================");
				insertMenuItem();
			}
			
			
			
			
			
		}

		public void displayMenu() throws IOException {
			System.out.println("==================================");
			System.out.println("\t Menu ");
			System.out.println("==================================");
			System.out.println("Select the corresponding food item number "
					+ "\nto see more food details ");
			System.out.println("==================================");
			for(int i=0;i< mc.getAllFoodItem().size() ; i++ ) {
				System.out.print(i+1 + ") ");
				System.out.print(mc.getAllFoodItem().get(i).getFoodName() + " $");
				System.out.print(mc.getAllFoodItem().get(i).getFoodPrice());
				System.out.println("");
			};
			
			System.out.println(mc.getAllFoodItem().size() + 1 + ") Back ");
			System.out.print("Please enter your choice: ");
			int c = sc.nextInt();
			
			if(c == mc.getAllFoodItem().size()+1) {
				displayOption();
			} else {
				AlaCarte a = mc.getAllFoodItem().get(c-1);
				System.out.println("==================================");
				System.out.println("\t Food item details ");
				System.out.println("==================================");
				System.out.println("Name: " + a.getFoodName());
				System.out.println("Description: " + a.getFoodDesc());
				System.out.println("Price: $" + a.getFoodPrice());
				System.out.println("Food Type: " + a.getFoodType());
				System.out.println();
				System.out.println("Press 1 to return to the menu");
				c = sc.nextInt();
				
				if(c == 1) {
					displayMenu();
				}
				
			}
		}


		
		public void displayOption() throws IOException {
			System.out.println("=================================");
	        System.out.println("\t Menu Options ");
			System.out.println("=================================");
			System.out.println("1) Display Menu ");
			System.out.println("2) Add a Food Item ");
			System.out.println("3) Update Food Item Details ");
			System.out.println("4) Delete Food Item ");
			System.out.println("5) Back");
			
			Scanner sc = new Scanner(System.in);
			int choice = sc.nextInt();
			do {
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
					MainAppUI.print();
					break;
				default:
					System.out.println("=========================================================");
					System.out.println("\tInvalid input. Please try again!");
			    	System.out.println("=========================================================");
					System.out.println("1) Display Menu ");
					System.out.println("2) Add Food Item ");
					System.out.println("3) Edit Food Description ");
					System.out.println("4) Delete Food Item ");
					System.out.println("5) Back");
					choice = sc.nextInt();
					break;
				} 
			}while(choice > 4 || choice < 1);
			
		}	
		
			
}
