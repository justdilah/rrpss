package boundary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import controller.AlaCarteController;
import controller.PromotionController;
import entity.AlaCarte;
import entity.Promotion;

/**This class represents the boundary class of the promotion class
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class PromotionForm {


	/**
	 * Control class for promotion
	 */
	final private PromotionController control = new PromotionController();
	/**
	 * Control class for ala carte
	 */
	final private AlaCarteController alcontrol = new AlaCarteController();
	/**
	 * Scanner class to read the user's inputs
	 */
	final private static Scanner sc = new Scanner(System.in);
	/**
	 * price of the promotion item
	 */
	private static double price;
	/**
	 * initialise DecimalFormat df variable
	 */
	private static final DecimalFormat df = new DecimalFormat("0.00");


	/**This method calls the displayAlaCarteOptions method to display the promotion options
	 * @throws IOException Display error message if any I/O error found while retrieving from the promotion records.
	 */
	public void displayOption() throws IOException {
		int choice = -1;
		do {
			print("=================================");
			print("\t Promotion Menu Options ");
			print("=================================");
			print("(1) Display Promotion Set ");
			print("(2) Insert Promotion Set ");
			print("(3) Update Promotion Set ");
			print("(4) Delete Promotion Set ");
			print("(5) Return to Main Menu");
			print("Please enter your choice: ");

			do {
				try {
					choice = Integer.parseInt(sc.nextLine());

					switch (choice) {
						case 1 -> displayPromotionItem();
						case 2 -> insertPromotionItem();
						case 3 -> updatePromotionItem();
						case 4 -> deletePromotionItem();
						case 5 -> {
							MainAppUI.print();
							sc.close();
						}
					}
				}catch(NumberFormatException e)
				{
					print("Choice is of invalid format, please enter a valid choice");
				}
			} while (choice == -1);
		} while (choice > 5 || choice < 1);
	}

	/**This method calls the getAllPromotionSets method in the controller class to display all the promotion items
	 * @throws IOException Display error message if any I/O error found while retrieving from the promotion records.
	 */
	private void displayPromotionItem() throws IOException {

		int i, c = -1;
		String format = "%-30s%s%n";

		print("===================================");
		print("\t Promotion Menu ");
		print("===================================");
		print("Select item number for more details ");
		print("====================================");
		System.out.printf(format,"Promotion Set Name","Price");
		for (i = 0; i < PromotionController.getAllPromotionSets().size(); i++) {
			System.out.printf(format,i + 1 + ") " + PromotionController.getAllPromotionSets().get(i).getPackName(),
					"$" + df.format(PromotionController.getAllPromotionSets().get(i).getPackPrice()));
		}
		print("Enter 0 to return to the previous page.");
		print("Please enter your choice: ");
		do {
			try
			{
				c = Integer.parseInt(sc.nextLine());
				if (c < 0 || c>= PromotionController.getAllPromotionSets().size()+1)
					print("Choice does not exist, please enter a valid choice: ");
				else if (c == 0)
					displayOption();
			}
			catch(NumberFormatException ex)
			{
				print("Input is of invalid format, please enter a valid choice: ");
			}

		}while(c>= PromotionController.getAllPromotionSets().size()+1 || c <= -1);

		Promotion a = PromotionController.getAllPromotionSets().get(c-1);
		print("==================================");
		print("\t Promotion Set details ");
		print("==================================");
		print("Name: " + a.getPackName());
		print("Food Items: ");
		for(int j = 0;j<a.getPackItems().size();j++) {
			print(j + 1 + ") " + a.getPackItems().get(j).getAlaCarteName());
		}

		print("Description: " + a.getPackDesc());

		print("Price: $" + df.format(a.getPackPrice()));
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

	/**This method calls the addPromotionSet method in the controller class to add a promotion item according
	 * to the user's inputs
	 * @throws IOException Display error message if any I/O error found while inserting into the promotion records.
	 */
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
				Promotion promo = control.getPromotionSetByName(PromotionName);
				if (promo == null)
					setter = false;
				else
					print("Promotion name already exist, please enter another Promotion name: ");
			}
		}while(setter);

		ArrayList<AlaCarte> items;
		ArrayList<Promotion> promos = new ArrayList<>();

		setter = true;
		do{
			items = selectAlaCarteItems();
			int checker = 0;
			for(int i = 0; i< PromotionController.getAllPromotionSets().size(); i++)
			{
				if (PromotionController.getAllPromotionSets().get(i).getPackItems().size() == items.size())
					promos.add(PromotionController.getAllPromotionSets().get(i));
			}

			for (Promotion promo : promos) {
				checker = 0;
				for (int i=0; i<items.size();i++) {
					{
						for(int j=0; j<items.size();j++)
						{
							if(promo.getPackItems().get(i).getAlaCarteName().equals(items.get(j).getAlaCarteName())) {
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
		for (AlaCarte item : items) checkPrice += item.getAlaCartePrice();
		print("Enter Promotion Set Price (Actual Price Combined: $"+df.format(checkPrice)+"):");

		do {
			try {
				price = Double.parseDouble(sc.nextLine());
				fail = (BigDecimal.valueOf(price).scale()>2);
				if(fail)
					print("Please enter the correct format for Price (Actual Price Combined: $"+df.format(checkPrice)+"): ");
				else
				{
					if(price>=checkPrice)
					{
						print("Promotion Price cannot be higher than actual prices of food items combined");
						print("Please enter a valid Promotion Price (Actual Price Combined: $"+df.format(checkPrice)+"): ");
					}
					else
						pass = false;
				}
			}
			catch (NumberFormatException e) {
				print("Price is of invalid format, please enter a valid price (Actual Price Combined: $"+df.format(checkPrice)+"): ");
			}
		}while(fail || pass);

		control.addPromotionSet(PromotionName, items, newdesc, price);

		print("Promotion Name: "+PromotionName
				+"\nItem Description: "+newdesc
				+"\nContaining Items:" );

		for (int i=0,j=1; i<items.size(); i++,j++) {
			print("("+j+") "+items.get(i).getAlaCarteName());
		}
		print("Priced at: $" + df.format(price)
				+"\nHas been added to the Promotions List Successfully.");

		displayOption();
	}

	/**This method calls the updatePromotionSet methods in the controller class
	 * to update a promotion item according to the user's inputs
	 * @throws IOException Display error message if any I/O error found while inserting into the promotion records.
	 */
	private void updatePromotionItem() throws IOException {

		boolean fail;
		int choice = 0, option =-1;

		print("================================================");
		print("Select the Promotion Set you would like to update ");
		print("================================================");

		for(int i = 0; i< PromotionController.getAllPromotionSets().size() ; i++ ) {
			print(i+1 + ") "+ PromotionController.getAllPromotionSets().get(i).getPackName());
		}

		print("Enter 0 to return to the previous page");
		print("Please enter your choice: ");
		do {
			try {
				option = Integer.parseInt(sc.nextLine());
				if(option < 0 || option > PromotionController.getAllPromotionSets().size())
					print("Option does not exist, please enter a valid choice: ");
				else if(option == 0)
					displayOption();
			} catch(NumberFormatException | IOException e) {
				print("Input is of valid format, please enter a valid choice: ");
			}
		} while(option < 0 || option > PromotionController.getAllPromotionSets().size());

		Promotion c = control.getPromotionSetByName(PromotionController.getAllPromotionSets().get(option-1).getPackName());

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
						Promotion promo;
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
						control.updatePromotionSetName(c, name);
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
						control.updatePromotionSetDesc(c, desc);
						print("Promotion Set Description has been successfully changed");
					}
					case 4 -> {
						double ogprice = c.getPackPrice();
						ArrayList<AlaCarte> items = c.getPackItems();
						double checkPrice = 0;
						for (int i=0; i<items.size(); i++)
						{
							checkPrice+= items.get(i).getAlaCartePrice();
						}
						print("Enter the New Promotion Set Price\n" +
								"(Original Price: $"+df.format(ogprice)+")||(AlaCarte Combined Price: $"+df.format(checkPrice)+"): ");
						do {
							try {
								price = Double.parseDouble(sc.nextLine());
								fail = (BigDecimal.valueOf(price).scale() > 2);
								if (fail)
									print("Please input a proper format for the price\n" +
											"(Original Price: $"+df.format(ogprice)+")||(AlaCarte Combined Price: $"+df.format(checkPrice)+"): ");
								else if(price>=checkPrice)
									print("New Price cannot be more expensive than original ala carte prices.\nPlease enter a valid price"+
											"(Original Price: $"+df.format(ogprice)+")||(AlaCarte Combined Price: $"+df.format(checkPrice)+"): ");
							} catch (NumberFormatException e) {
								print("Price is of invalid format.\nPlease input a valid price" +
										"(Original Price: $"+df.format(ogprice)+")||(AlaCarte Combined Price: $"+df.format(checkPrice)+"):");
								fail=true;
							}
						} while (fail || price>=checkPrice);
						control.updatePromotionSetPrice(c, price);
						print("Promotion Set Price $"+df.format(ogprice)+" has been changed to $"+df.format(price));
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

	/**The methods calls getAllAlaCarteItems method in the ala carte controller class to retrieve all the
	 * ala carte items and select specific ala carte items according to the user's inputs
	 * @return list of ala carte items
	 * @throws IOException Display error message if any I/O error found while retrieving from the promotion records.
	 */
	private ArrayList<AlaCarte> selectAlaCarteItems() throws IOException {

		int c=0;
		ArrayList<AlaCarte> alList = new ArrayList<>();
		String format = "%-35s%s%n";
		print("Select AlaCarte items to be added to Promotion Set: ");
		print("====================================================");
		System.out.printf(format,"Food Name", "Price");
		print("====================================================");
		for(int j = 0; j< AlaCarteController.getAllAlaCarteItems().size(); j++) {
			System.out.printf(format,j+1 + ") " + AlaCarteController.getAllAlaCarteItems().get(j).getAlaCarteName(),
					"$"+ df.format(AlaCarteController.getAllAlaCarteItems().get(j).getAlaCartePrice()));
		}

		print("Please enter your choice: ");
		do {
			try {
				c = Integer.parseInt(sc.nextLine());
				if(c==0||c> AlaCarteController.getAllAlaCarteItems().size())
					print("Choice does not exist, please enter a valid choice: ");
			} catch (NumberFormatException e) {
				print("Input is of invalid format, please enter a valid choice: ");
			}
		}while(c==0||c> AlaCarteController.getAllAlaCarteItems().size());

		AlaCarte a = AlaCarteController.getAllAlaCarteItems().get(c-1);
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
				else if (c<=-1||c> AlaCarteController.getAllAlaCarteItems().size())
					print("Choice does not exist, please enter a valid choice");
				else {
					a = AlaCarteController.getAllAlaCarteItems().get(c - 1);
					alList.add(a);
				}
			}
			catch(NumberFormatException e) {
				print("Input is of invalid format, please enter a valid choice");
			}
		}while(true);

		return alList;
	}

	/**This method display the update promotion item options and prompt the user to update the ala carte items or
	 * delete a specific ala carte item in the promotion set
	 * @param p Promotion object
	 * @return list of modified ala carte items of the promotion set
	 * @throws FileNotFoundException Display error message if any I/O error found while retrieving from the promotion records.
	 */
	private ArrayList<AlaCarte> selectPromotionItemOptions(Promotion p) throws FileNotFoundException {

		int choice, c, k;
		String format = "%-25s%s%n";
		String format1 ="%-35s%s%n";
		ArrayList<AlaCarte> alList = p.getPackItems();
		do {
			print("=================================");
			print("Promotion Item Options " + p.getPackName());
			print("=================================");
			print("Current Promotional Set Items");
			print("=================================");
			for (int i = 0; i < p.getPackItems().size(); i++)
				System.out.printf(format, p.getPackItems().get(i).getAlaCarteName(), "$" + df.format(p.getPackItems().get(i).getAlaCartePrice()));
			print("");
			print("Press 0 to end update to the list");
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
						for (int i = 0; i < AlaCarteController.getAllAlaCarteItems().size(); i++) {
							System.out.printf(format1, i + 1 + ")" + AlaCarteController.getAllAlaCarteItems().get(i).getAlaCarteName(),
									"$" + df.format(AlaCarteController.getAllAlaCarteItems().get(i).getAlaCartePrice()));
						}
						print("");
						print("Press 0 to end the addition to the list");
						print("Please enter your choice: ");
						do{
							try{
								c=Integer.parseInt(sc.nextLine());
								if (c == 0 )
									break;
								else if (c<=-1||c> AlaCarteController.getAllAlaCarteItems().size())
									print("Choice does not exist, please enter a valid choice");
								else {
									alList.add(AlaCarteController.getAlaCarteByName(AlaCarteController.getAllAlaCarteItems().get(c - 1).getAlaCarteName()));
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
									System.out.printf(format1,i + 1 + ") "+alList.get(i).getAlaCarteName(),"$"+alList.get(i).getAlaCartePrice());
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

	/**This method calls the deletePromotionSet method in the controller class to delete the specific
	 * promotion item according to the user's inputs
	 * @throws IOException Display error message if any I/O error found while inserting into the promotion records.
	 */
	private void deletePromotionItem() throws IOException {

		String deletedname;
		int option = 0;
		print("==================================================");
		print("Select the Promotion Set you would like to delete ");
		print("==================================================");

		for(int i = 0; i< PromotionController.getAllPromotionSets().size() ; i++ ) {
			print(i+1 + ") "+ PromotionController.getAllPromotionSets().get(i).getPackName());
		}

		print("Enter 0 to return to the previous page");
		print("Please enter your choice: ");
		do {
			try {
				option = Integer.parseInt(sc.nextLine());
				if(option < 0 || option > PromotionController.getAllPromotionSets().size())
					print("Choice does not exist, please enter a valid choice: ");
				else if(option == 0)
					displayOption();
			}
			catch(NumberFormatException e){
				print("Input is of invalid format, please enter a valid choice: ");
			}

		} while(option < 1 || option > PromotionController.getAllPromotionSets().size());

		Promotion c = control.getPromotionSetByName(PromotionController.getAllPromotionSets().get(option-1).getPackName());
		deletedname = c.getPackName();
		control.deletePromotionSet(c);
		print("=================================================");
		print("Promotion Set "+deletedname+" Has Been Deleted Successfully");
		print("=================================================");
		displayOption();
	}

	/**This method is used to replace System.out.println method to a shorter method name
	 * @param message string value
	 */
	public void print(String message)
	{
		System.out.println(message);
	}

}