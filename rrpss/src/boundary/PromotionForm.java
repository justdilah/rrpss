package boundary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import controller.AlaCarteController;
import controller.MainAppController;
import controller.PromotionController;
import entity.AlaCarte;
import entity.PromotionSet;

public class PromotionForm {
	
	final private MainAppController mainControl = new MainAppController();
	final private PromotionController control = new PromotionController();
	final private AlaCarteController alcontrol = new AlaCarteController();
	final private static Scanner sc = new Scanner(System.in);
	private static double price;
	private static final DecimalFormat df = new DecimalFormat("0.00");

	public void displayOption() throws IOException {
		print("=================================");
		print("\t Promotion Menu Options ");
		print("=================================");
		print("(1) Display Promotion Set ");
		print("(2) Insert Promotion Set ");
		print("(3) Update Promotion Set ");
		print("(4) Delete Promotion Set ");
		print("(5) Return to Main Menu");

		try {
			int choice = Integer.parseInt(sc.nextLine());

			switch (choice) {
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
					sc.close();
					MainAppUI.print();
					break;
			}
		}catch(NumberFormatException e)
		{
			print("Choice is of invalid format, please enter a valid choice");
		}
	}

	private void displayPromotionItem() throws IOException {

		int i, c = -1;

		print("=================================");
		print("\t Promotion Menu ");
		print("=================================");
		print("Select the corresponding number "
				+ "\nto see more food details ");
		print("==================================");
		for (i = 0; i < control.getAllPromotionSets().size(); i++) {
			System.out.print(i + 1 + ") " + control.getAllPromotionSets().get(i).getPackName());
			System.out.print(" $" + control.getAllPromotionSets().get(i).getPackPrice() + "\n");
		}
		print("Enter 0 to return to the previous page.");
		print("Please enter your choice: ");
		do {
			try
			{
				c = Integer.parseInt(sc.nextLine());
				if (c < 0 || c>=control.getAllPromotionSets().size()+1)
					print("Choice does not exist, please enter a valid choice: ");
				else if (c == 0)
					displayOption();
			}
			catch(NumberFormatException ex)
			{
				print("Input is of invalid format, please enter a valid choice: ");
			}

		}while(c>=control.getAllPromotionSets().size()+1 || c <= -1);

		PromotionSet a = control.getAllPromotionSets().get(c-1);
		print("==================================");
		print("\t Promotion Set details ");
		print("==================================");
		print("Name: " + a.getPackName());
		print("Food Items: ");
		for(int j = 0;j<a.getPackItem().size();j++) {
			print(j + 1 + ") " + a.getPackItem().get(j).getFoodName());
		}

		print("Description: " + a.getPackDesc());

		print("Price: $" + a.getPackPrice());
		print("");

		print("Enter 1 to return to the menu");
		do {
			try {
				c = Integer.parseInt(sc.nextLine());
				if (c==0||c>1)
					print("Enter 1 to return to the menu");
			}
			catch(NumberFormatException ex)
			{
				print("Enter 1 to return to the menu");
				c=0;
			}
		}while(c!=1);

		displayPromotionItem();
	}

	private void insertPromotionItem() throws IOException {

		boolean setter = true, fail = true, pass=true;
		String PromotionName, desc;
		print("Enter Promotion Set name: ");

		do {
			PromotionName = sc.nextLine();
			if (PromotionName==null || PromotionName.trim().isEmpty()) {
				print("Promotion name must not be empty, please enter a name: ");
			}
			else
			{
				PromotionSet promo = control.getPromotionSetByName(PromotionName);
				if (promo == null)
					setter = false;
				else
					print("Promotion name already exist, please enter another Promotion name: ");
			}
		}while(setter);

		ArrayList<AlaCarte> items;
		ArrayList<PromotionSet> promos = new ArrayList<>();
		
		setter = true;
		do{
			items = selectAlaCarteItems();
			int checker = 0;
			for(int i=0; i< control.getAllPromotionSets().size();i++)
			{
				if (control.getAllPromotionSets().get(i).getPackItem().size() == items.size())
					promos.add(control.getAllPromotionSets().get(i));
			}

			for (PromotionSet promo : promos) {
				checker = 0;
				for (int i=0; i<items.size();i++) {
					{
						for(int j=0; j<items.size();j++)
						{
							if(promo.getPackItem().get(i).getFoodName().equals(items.get(j).getFoodName())) {
								checker++;
								break;
							}
						}
					}
				}
				if (checker == items.size())
					break;
			}
			if(checker == items.size()) {
				print("Items selected already exists in another promotion, please select again: ");
				items.clear();
				promos.clear();
			}
			else
				setter = false;
		}while(setter);


		setter = true;
		print("Enter Item Description: ");
		do{
			desc = sc.nextLine();
			if (desc == null || desc.trim().isEmpty()) {
				print("Item Description must not be empty, please enter a description: ");
			}
			else
				setter = false;
		}while(setter);

		String newdesc = desc.replace(',', '/');
		double checkPrice=0;
		for (AlaCarte item : items) checkPrice += item.getFoodPrice();
		print("Enter Promotion Set Price (Actual Price Combined: $"+checkPrice+"):");

		do {
			try {
				price = Double.parseDouble(sc.nextLine());
				fail = (BigDecimal.valueOf(price).scale()>2);
				if(fail)
					print("Please enter the correct format for Price (Actual Price Combined: $"+checkPrice+"): ");
				else
				{
					if(price>=checkPrice)
					{
						print("Promotion Price cannot be higher than actual prices of food items combined");
						print("Please enter a valid Promotion Price (Actual Price Combined: $"+checkPrice+"): ");
					}
					else
						pass = false;
				}
			}
			catch (NumberFormatException e) {
				print("Price is of invalid format, please enter a valid price (Actual Price Combined: $"+checkPrice+"): ");
			}
		}while(fail || pass);

		control.addPromotionSet(PromotionName, items, newdesc, price);

		print("Promotion Name: "+PromotionName
				+"\nItem Description: "+newdesc
				+"\nContaining Items:" );

		for (int i=0,j=1; i<items.size(); i++,j++) {
			print("("+j+") "+items.get(i).getFoodName());
		}
		print("Priced at: $" + price
				+"\nHas been added to the Promotions List Successfully.");

		displayOption();
	}

	private void updatePromotionItem() throws FileNotFoundException {

		boolean fail;
		int choice = 0, option =0;

		print("================================================");
		print("Select the Promotion Set you would like to update ");
		print("================================================");

		for(int i=0;i< control.getAllPromotionSets().size() ; i++ ) {
			print(i+1 + ") "+control.getAllPromotionSets().get(i).getPackName());
		}

		print("Enter 0 to return to the previous page");
		print("Please enter your choice: ");
		do {
			try {
				option = Integer.parseInt(sc.nextLine());
				if(option < 0 || option > control.getAllPromotionSets().size())
					print("Option does not exist, please enter a valid choice: ");
				else if(option == 0)
					displayOption();
			} catch(NumberFormatException | IOException e) {
				print("Input is of valid format, please enter a valid choice: ");
			}
		} while(option < 0 || option > control.getAllPromotionSets().size());

		PromotionSet c = control.getPromotionSetByName(control.getAllPromotionSets().get(option-1).getPackName());

		while (true) {
			print("===========================================");
			print("Update Promotion Set Options For "+ c.getPackName());
			print("===========================================");
			print("1) Edit Promotion Set Name");
			print("2) Edit Promotion Set Items");
			print("3) Edit Promotion Set Description");
			print("4) Edit Promotion Set Price");
			print("5) Back");

			print("Please enter your choice : ");
			do {
				try {
					choice = Integer.parseInt(sc.nextLine());
					if (choice <= 0 || choice > 5)
						print("Choice does not exist, please enter a valid choice: ");

				} catch (NumberFormatException e) {
					print("Choice is not of valid format, please enter a valid choice: ");
				}
			} while (choice <= 0 || choice > 5);

			try {
				switch (choice) {
					case 1 -> {
						String name;
						String ogname = c.getPackName();
						PromotionSet promo = new PromotionSet();
						print("Enter The New Promotion Set Name (Original Name: "+ogname+"): ");
						do {
							name = sc.nextLine();
							promo = control.getPromotionSetByName(name);
							if (name.trim().isEmpty())
								print("New Promotion Set Name cannot be empty, please enter a valid name (Original Name: "+ogname+"): ");
							else if(name.equals(ogname)) {
								print("New Promotion Set Name cannot be the same as the Original Promotion Name");
								print("Please enter a different Promotion Set Name");
							}
							else if(promo != null)
								print("Promotion Set name already exist, please enter another name (Original Name: "+ogname+"):");

						} while (name.trim().isEmpty() || name.equals(ogname)||promo!=null);
						control.updatePromoItemName(c, name);
						print("Promotion Set Name "+ogname+" has been changed to "+name);
					}
					case 2 -> control.updateAlaCarteItems(c, selectPromotionItemOptions(c));
					case 3 -> {
						String ogdesc;
						String desc;
						ogdesc = c.getPackDesc();
						print("Enter the New Promotion Set Description (Original Description: "+ogdesc+" ): ");
						do {
							desc = sc.nextLine();
							if (desc.trim().isEmpty())
								print("New Description cannot be empty, please enter a valid description (Original Description: "+ogdesc+" ): ");
						} while (desc.trim().isEmpty());
						control.updatePromoItemDesc(c, desc);
						print("Promotion Set Description has been successfully changed");
					}
					case 4 -> {
						double ogprice = c.getPackPrice();
						ArrayList<AlaCarte> items = c.getPackItem();
						double checkPrice = 0;
						for (int i=0; i<items.size(); i++)
						{
							checkPrice+= items.get(i).getFoodPrice();
						}
						print("Enter the New Promotion Set Price\n" +
								"(Original Price: $"+ogprice+")||(AlaCarte Combined Price: $"+checkPrice+"): ");
						do {
							try {
								price = Double.parseDouble(sc.nextLine());
								fail = (BigDecimal.valueOf(price).scale() > 2);
								if (fail)
									print("Please input a proper format for the price\n" +
											"(Original Price: $"+ogprice+")||(AlaCarte Combined Price: $"+checkPrice+"): ");
								else if(price==ogprice)
									print("New Price cannot be the same as the old price.\nPlease input a valid price" +
											"(Original Price: $"+ogprice+")||(AlaCarte Combined Price: $"+checkPrice+"): ");
								else if(price>=checkPrice)
									print("New Price cannot be more expensive than original ala carte prices.\nPlease enter a valid price"+
											"(Original Price: $"+ogprice+")||(AlaCarte Combined Price: $"+checkPrice+"): ");
							} catch (NumberFormatException e) {
								print("Price is of invalid format.\nPlease input a valid price" +
										"(Original Price: $"+ogprice+")||(AlaCarte Combined Price: $"+checkPrice+"):");
								fail=true;
							}
						} while (fail || price == ogprice || price>=checkPrice);
						control.updatePromoPrice(c, price);
						print("Promotion Set Price $"+ogprice+" has been changed to $"+price);
					}
				}
				if (choice == 5)
					break;
			}catch(IOException e)
			{
				print("==================================");
				print("Unsuccessful, Please Try Again");
				print("==================================");
			}
		}

		try {
			displayOption();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ArrayList<AlaCarte> selectAlaCarteItems() throws FileNotFoundException {

		int c=0;
		ArrayList<AlaCarte> alList = new ArrayList<>();
		String format = "%-35s%s%n";
		print("Select AlaCarte items to be added to Promotion Set: ");
		print("====================================================");
		System.out.printf(format,"Food Name", "Price");
		print("====================================================");
		for(int j=0;j<alcontrol.getAllFoodItem().size();j++) {
			System.out.printf(format,j+1 + ") " + alcontrol.getAllFoodItem().get(j).getFoodName(),"$"+alcontrol.getAllFoodItem().get(j).getFoodPrice());
		}

		print("Please enter your choice: ");
		do {
			try {
				c = Integer.parseInt(sc.nextLine());
				if(c==0||c>alcontrol.getAllFoodItem().size())
					print("Choice does not exist, please enter a valid choice: ");
			} catch (NumberFormatException e) {
				print("Input is of invalid format, please enter a valid choice: ");
			}
		}while(c==0||c>alcontrol.getAllFoodItem().size());

		AlaCarte a = alcontrol.getAllFoodItem().get(c-1);
		alList.add(a);

		print("======================================================");
		print("Enter 0 to stop adding");
		print("======================================================");
		c = -1;
		do{
			try {
				print("Please enter your choice: ");
				c = Integer.parseInt(sc.nextLine());
				if (c == 0)
					break;
				else if (c<=-1||c>alcontrol.getAllFoodItem().size())
					print("Choice does not exist, please enter a valid choice");
				else {
					a = alcontrol.getAllFoodItem().get(c - 1);
					alList.add(a);
				}
			}
			catch(NumberFormatException e) {
				print("Input is of invalid format, please enter a valid choice");
			}
		}while(true);

		return alList;
	}

	private ArrayList<AlaCarte> selectPromotionItemOptions(PromotionSet p) throws FileNotFoundException {

		int choice =0, c=0, k=0;
		String format = "%-25s%s%n";
		String format1 ="%-35s%s%n";
		ArrayList<AlaCarte> alList = p.getPackItem();
		do {
			print("=================================");
			print("Promotion Item Options " + p.getPackName());
			print("=================================");
			print("Current Promotional Set Items");
			print("=================================");
			for (int i = 0; i < p.getPackItem().size(); i++)
				System.out.printf(format, p.getPackItem().get(i).getFoodName(), "$" + p.getPackItem().get(i).getFoodPrice());
			print("");
			print("1) Add Ala Carte item in the promotion set");
			print("2) Delete an Ala Carte item in the promotion set");
			print("Please enter your choice :");
			do {
				try {
					choice = Integer.parseInt(sc.nextLine());
					if (choice<0 || choice > 2)
						print("Choice does not exist, please enter a valid choice: ");
					else if(choice == 0)
						break;
				} catch (NumberFormatException e) {
					print("Choice is of invalid format, please enter a valid choice: ");
					choice = -1;
				}
			} while (choice < 0 || choice > 2);

			try {
				switch (choice) {
					case 1 -> {
						AlaCarte a = new AlaCarte();
						print("Select AlaCarte items to be added to Promotion Set: ");
						print("====================================================");
						System.out.printf(format1, "Food Name", "Price");
						print("====================================================");
						for (int i = 0; i < alcontrol.getAllFoodItem().size(); i++) {
							System.out.printf(format1, i + 1 + ")" + alcontrol.getAllFoodItem().get(i).getFoodName(),
									"$" + alcontrol.getAllFoodItem().get(i).getFoodPrice());
						}
						print("");
						print("Press 0 to end the addition to the list");
						print("Please enter your choice: ");
						do{
							try{
								c=Integer.parseInt(sc.nextLine());
								if (c == 0 )
									break;
								else if (c<=-1||c>alcontrol.getAllFoodItem().size())
									print("Choice does not exist, please enter a valid choice");
								else {
									alList.add(a.selectFoodByName(alcontrol.getAllFoodItem().get(c - 1).getFoodName()));
									print("Press 0 to end the addition to the list");
								}

							}catch (NumberFormatException e) {
								print("Choice is of invalid format, please enter a valid choice: ");
							}
						}while(true);
					}

					case 2 -> {
						do{
							try{
								print("====================================================");
								System.out.printf(format1, "Food Name", "Price");
								print("====================================================");
								for(int i=0; i< alList.size(); i++){
									System.out.printf(format1,i + 1 + ") "+alList.get(i).getFoodName(),"$"+alList.get(i).getFoodPrice());
								}
								print("Press 0 to end the deletion to the list");
								print("Please enter your choice: ");

								k = Integer.parseInt(sc.nextLine());
								if(k==0)
									break;
								else if(k<=-1||k>alList.size())
									print("Choice does not exist, please enter a valid choice");
								else
									alList.remove(k-1);

							}catch(NumberFormatException e) {
								print("Choice is of a invalid format, please enter a valid choice");
							}
						}while(true);
					}
				}
			} catch (IOException e) {
				print("Unable to process");
			}
		} while (choice != 0);

		return alList;
	}

	private void deletePromotionItem() throws IOException {

		String deletedname;
		int option = 0;
		print("==================================================");
        print("Select the Promotion Set you would like to delete ");
		print("==================================================");
		
		for(int i=0;i< control.getAllPromotionSets().size() ; i++ ) {
			print(i+1 + ") "+control.getAllPromotionSets().get(i).getPackName());
		};

		print("Enter 0 to return to the previous page");
		print("Please enter your choice: ");
		do {
			try {
				option = Integer.parseInt(sc.nextLine());
				if(option < 0 || option > control.getAllPromotionSets().size())
					print("Choice does not exist, please enter a valid choice: ");
				else if(option == 0)
					displayOption();
			}
			catch(NumberFormatException e){
				print("Input is of invalid format, please enter a valid choice: ");
			}
			
		} while(option < 1 || option > control.getAllPromotionSets().size());
		
		PromotionSet c = control.getPromotionSetByName(control.getAllPromotionSets().get(option-1).getPackName());
		deletedname = c.getPackName();
		control.deletePromoSet(c);
		print("=================================================");
		print("Promotion Set "+deletedname+" Has Been Deleted Successfully");
		print("=================================================");
		displayOption();
	}

	public void print(String message)
	{
		System.out.println(message);
	}

}