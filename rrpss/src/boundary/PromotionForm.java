package boundary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import controller.AlaCarteController;
import controller.MainAppController;
import controller.PromotionController;
import entity.AlaCarte;
import entity.FoodType;
import entity.PromotionSet;
import entity.SetPackage;

public class PromotionForm {
	
	private MainAppController mainControl = new MainAppController();
	private PromotionController control = new PromotionController();
	private AlaCarteController alcontrol = new AlaCarteController();
	private static Scanner sc = new Scanner(System.in);
	private static int itemid;
	private static 	String name, desc;
	private static double price;

	public void displayOption() throws IOException {
		System.out.println("=================================");
		System.out.println("\t Promotion Menu Options ");
		System.out.println("=================================");
		System.out.println("(1) Display Promotion Set ");
		System.out.println("(2) Insert Promotion Set ");
		System.out.println("(3) Update Promotion Set ");
		System.out.println("(4) Delete Promotion Set ");
		System.out.println("(5) Return to Main Menu");
		
		
		int choice = sc.nextInt();
		
		switch(choice) {
		case 1:
			displayPromotionItem();
			break;
		case 2:
			insertPromotionItem();
			
			break;
		case 3:
			updatePromotionItem();
			break;
		case 4:
			deletePromotionItem();
			break;
		case 5:
			break;
		}
	}

	private void insertPromotionItem() throws IOException {
		System.out.println("Enter Promotion Set name: ");
		Scanner scan = new Scanner(System.in);
		String name="";
		name+=scan.nextLine();
		
		ArrayList<AlaCarte> items = selectAlaCarteItems();
			
		
		System.out.println("enter item desc: ");
		Scanner can = new Scanner(System.in);
		String desc="";
		desc+=can.nextLine();
		String newdesc = desc.replace(',', '/');
		System.out.println("enter item price: ");
		price = sc.nextDouble();
//		
//		
		control.addPromotionSet(name, items, newdesc, price);
	}
	
	private ArrayList<AlaCarte> selectAlaCarteItems() throws FileNotFoundException {
		
		ArrayList<AlaCarte> alList = new ArrayList<>();
		System.out.println("Select AlaCarte items to be added to Promotion Set: ");
		for(int j=0;j<alcontrol.getAllFoodItem().size();j++) {
			System.out.println(j+1 + ") " + alcontrol.getAllFoodItem().get(j).getFoodName());
		}
		
		System.out.println("Please enter your choice: ");
		int c = sc.nextInt();
		AlaCarte a = alcontrol.getAllFoodItem().get(c-1);
		alList.add(a);
		
		while(true) {
			System.out.println("======================================================");
			System.out.println("Enter "+ (alcontrol.getAllFoodItem().size()+1) +" to end");
			System.out.println("======================================================");
			System.out.println("Please enter your choice: ");
			c = sc.nextInt();
			
			if(c == alcontrol.getAllFoodItem().size() + 1) {
				break;
			} else {
				a = alcontrol.getAllFoodItem().get(c-1);
				alList.add(a);
			}
		}
		return alList;
	}

	private void displayPromotionItem() throws IOException {
	
		
		System.out.println("=================================");
		System.out.println("\t Promotion Menu ");
		System.out.println("=================================");
		System.out.println("Select the corresponding number "
				+ "\nto see more food details ");
		System.out.println("==================================");
		for(int i=0;i<control.getAllPromotionSets().size();i++) {
			System.out.print(i+1 + ") "  +  control.getAllPromotionSets().get(i).getPackName());
			System.out.print(" $" + control.getAllPromotionSets().get(i).getPackPrice() + "\n");
		}
		
		System.out.print("Please enter your choice: ");
		int c = sc.nextInt();
		
		if(c == control.getAllPromotionSets().size() +1) {
			displayOption();
		} else {
			PromotionSet a = control.getAllPromotionSets().get(c-1);
			System.out.println("==================================");
			System.out.println("\t Promotion Set details ");
			System.out.println("==================================");
			System.out.println("Name: " + a.getPackName());
			System.out.println("Food Items: ");
			for(int j = 0;j<a.getPackItem().size();j++) {
				System.out.println(j+1 + ") " + a.getPackItem().get(j).getFoodName());
			}
			System.out.println("Description: " + a.getPackDesc());
			
			System.out.println("Price: $" + a.getPackPrice());
			System.out.println();
			System.out.println("Press 1 to return to the menu");
			c = sc.nextInt();
			
			if(c == 1) {
				displayPromotionItem();
			}
		}
		
	}

	private void updatePromotionItem() throws FileNotFoundException {
		System.out.println("================================================");
        System.out.println("Select the Promotion Set you would like to update ");
		System.out.println("================================================");
		
		for(int i=0;i< control.getAllPromotionSets().size() ; i++ ) {
			System.out.print(i+1 + ") ");
			System.out.print(control.getAllPromotionSets().get(i).getPackName());
			System.out.println("");
		};
		int option = 0;
		do {
			
			System.out.print("Please enter your choice: ");		
			option = sc.nextInt();
			
		} while(option < 1 || option > control.getAllPromotionSets().size());
		
		PromotionSet c = control.getPromotionSetByName(control.getAllPromotionSets().get(option-1).getPackName());
		System.out.println("=================================");
        System.out.println("Update Promotion Set Options ");
		System.out.println("=================================");
		System.out.println("1) Edit Promotion Set Name");
		System.out.println("2) Edit Promotion Set Items ");
		System.out.println("3) Edit Promotion Set Description ");
		System.out.println("4) Edit Promotion Set Price");
		System.out.println("5) Back");
		System.out.println("\n");
		
		System.out.print("Please enter your choice : ");
		int choice = sc.nextInt();
		do {
			try {
				switch(choice) {
				case 1:
					System.out.println("Enter the new promotion set name");
					Scanner scan = new Scanner(System.in);
			        String name="";
			        name+=scan.nextLine();
				    control.updatePromoItemName(c, name);
					break;
					
				case 2: 
					control.updateAlaCarteItems(c,selectPromotionItemOptions(c));
					
					break;
				case 3:
					System.out.println("Enter the new promotion set description");
					Scanner can = new Scanner(System.in);
			        String desc="";
			        desc+=can.nextLine();
				    control.updatePromoItemDesc(c,desc);
					break;
					
				case 4: 
					System.out.println("Enter the new promotion set price");
				    double price=sc.nextDouble();
				    control.updatePromoPrice(c, price);
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
				updatePromotionItem();
			}
			
		}while(choice > 5 || choice < 1);
//		control.updatePromoItemName(name, desc,p);
		
		
	}
	
	//NEED TO CHECK IF IT IS LEFT WITH 1, WONT ALLOW
	private ArrayList<AlaCarte> selectPromotionItemOptions(PromotionSet p) throws FileNotFoundException {
		ArrayList<AlaCarte> alList = p.getPackItem();
		
		System.out.println("=================================");
        System.out.println("Promotion Item Options ");
		System.out.println("=================================");
		System.out.println("1) Add Ala Carte item in the promotion set");
        System.out.println("2) Delete an Ala Carte item in the promotion set");
		System.out.print("Please enter your choice : ");
		int choice = sc.nextInt();
		do {
			switch(choice) {
				case 1: 
					for(int i=0;i< alcontrol.getAllFoodItem().size() ; i++ ) {
						System.out.print(i+1 + ") ");
						System.out.print(alcontrol.getAllFoodItem().get(i).getFoodName());
						System.out.println("");
					};
					
					System.out.print("Please enter your choice: ");
					int c = sc.nextInt();
					AlaCarte a = new AlaCarte();
					alList.add(a.selectFoodByName(alcontrol.getAllFoodItem().get(c-1).getFoodName()));
					break;
				case 2:
					for(int i=0;i< alList.size() ; i++ ) {
						System.out.print(i+1 + ") ");
						System.out.print(alcontrol.getAllFoodItem().get(i).getFoodName());
						System.out.println("");
					};
					
					System.out.print("Please enter your choice: ");
					int k = sc.nextInt();
					
					AlaCarte b = new AlaCarte();
					alList.remove(k-1);
					break;
				default:
					System.out.println("=================================");
			        System.out.println("Invalid input! Please try again! ");
					System.out.println("=================================");
					System.out.println("1) Add Ala Carte item in the promotion set");
			        System.out.println("2) Delete an Ala Carte item in the promotion set");
			        sc.nextInt();
			        break;
			}
		} while (choice>2 || choice<1);
		return alList;
		
	}

	private void deletePromotionItem() throws IOException {
		
		System.out.println("================================================");
        System.out.println("Select the Promotion Set you would like to delete ");
		System.out.println("================================================");
		
		for(int i=0;i< control.getAllPromotionSets().size() ; i++ ) {
			System.out.print(i+1 + ") ");
			System.out.print(control.getAllPromotionSets().get(i).getPackName());
			System.out.println("");
		};
		
		int option = 0;
		do {
			
			System.out.print("Please enter your choice: ");		
			option = sc.nextInt();
			
		} while(option < 1 || option > control.getAllPromotionSets().size());
		
//		try {
			PromotionSet c = control.getPromotionSetByName(control.getAllPromotionSets().get(option-1).getPackName());
			System.out.println(c.getPackName());
			control.deletePromoSet(c);
			System.out.println("==================================");
			System.out.println("Food item deleted successfully");
			System.out.println("==================================");
			displayOption();
//		} catch (Exception ex) {
//			System.out.println("==================================");
//			System.out.println("Unsuccessful. Please try again");
//			System.out.println("==================================");
//			deletePromotionItem();
//		}
	}

}