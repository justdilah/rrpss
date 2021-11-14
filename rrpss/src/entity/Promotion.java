package entity;

import controller.AlaCarteController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**This class represents the promotion item
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 2
 *
 */
public class Promotion {


	/**
	 * Initialized filepath of the Promotion.csv
	 */
	private static final String filename = "DataSet/PromotionSet.csv";

	/**
	 * The Id of the promotion item
	 */
	private int packId;
	/**
	 * The name of the promotion item
	 */
	private String packName;
	/**
	 * The price of the promotion item
	 */
	private double packPrice;
	/**
	 * The list of ala carte items
	 */
	private static ArrayList<AlaCarte> packItems;
	/**
	 * The description of the promotion item
	 */
	private String packDesc;

	/**
	 * Class constructor
	 */
	public Promotion() {

	}

	/**Class Constructor with the given id, name, list of ala carte items, description and price
	 * @param id int value to uniquely identify the promotion item
	 * @param name string value for the name of th promotion item
	 * @param packItems list of the ala carte items
	 * @param desc string value for the description of the promotion item
	 * @param price double value for the price of the promotion item
	 */
	public Promotion(int id, String name, ArrayList<AlaCarte> packItems, String desc, double price) {
		this.packId = id;
		this.packName = name;
		this.packItems = packItems;
		this.packDesc = desc;
		this.packPrice = price;
	}

	/**This method gets the id of the promotion item
	 * @return promotion id
	 */
	public int getPackId() {
		return this.packId;
	}


	/**
	 * This method sets the id of the promotion item
	 * @param packId int value to uniquely identifies the promotion item
	 */
	public void setPackId(int packId) {
		this.packId = packId;
	}

	/**This methods get the name of the promotion item
	 * @return name of the promotion item
	 */
	public String getPackName() {
		return this.packName;
	}


	/**This method sets the name of the promotion item
	 * @param name string value for the name of the promotion item
	 */
	public void setPackName(String name) {
		this.packName = name;
	}


	/**This method gets the price of the promotion item
	 * @return price of the promotion item
	 */
	public double getPackPrice() {
		return this.packPrice;
	}

	/**
	 * This method sets the price of the promotion item
	 * @param packPrice double value for the price of the promotion item
	 */
	public void setPackPrice(double packPrice) {
		this.packPrice = packPrice;
	}

	/**This method gets the list of ala carte items
	 * @return list of the ala carte items
	 */
	public ArrayList<AlaCarte> getPackItems() {
		return this.packItems;
	}

	/**
	 * This method sets the list of the ala carte items
	 * @param packItem list of the ala carte items
	 */
	public void setPackItems(ArrayList<AlaCarte> packItem) {
		this.packItems = packItem;
	}

	/**This method gets the description of the promotion item
	 * @return description of the promotion item
	 */
	public String getPackDesc() {
		return this.packDesc;
	}

	/**
	 * This method sets the description of the promotion item
	 * @param packDesc string value for the description of the promotion item
	 */

	public void setPackDesc(String packDesc) {
		this.packDesc = packDesc;
	}


	/**This method adds the promotion item to the csv file
	 * @param name string value for the name of the promotion item
	 * @param pslist list of the ala carte items
	 * @param desc string value for the description of the promotion item
	 * @param p double value for the price of the promotion item
	 * @throws IOException  Display error message if any I/O error found while inserting into the promotion records.
	 */
	public static void savePromotionSetItem(String name, ArrayList<AlaCarte> pslist, String desc, double p) throws IOException {
		ArrayList<Promotion> promos = getAllPromotionItems();
		int last = promos.size();
		int id = promos.get(last-1).getPackId()+ 1;
		String packitems ="";
		for(int i=0;i<pslist.size();i++) {
			if(i == pslist.size()-1) {
				packitems += pslist.get(i).getAlaCarteName();
			} else {
				packitems += pslist.get(i).getAlaCarteName() + "@";
			}
		}

		String item = id + "," + name + "," + packitems + "," + desc +  "," +  p;
		List l = new ArrayList();
		l.add(item);
		write(filename, l);
	}

	/**This method retrieves the ala carte items based on the given list of names of the ala carte items
	 * @param n list of names of the ala carte items
	 * @return list of ala carte objects
	 * @throws IOException Display error message if any I/O error found while inserting into the promotion records.
	 */
	private static ArrayList<AlaCarte> addAlaCarteItems(String[] n) throws IOException {
		ArrayList<AlaCarte> al = new ArrayList<>();

		for(int k=0;k< n.length; k++) {
			al.add(AlaCarteController.getAlaCarteByName(n[k]));
		}
		return al;
	}


	/**This method extracts the promotion items from the csv file
	 * @return A list of promotion items
	 * @throws IOException Display error message if any I/O error found while retrieving from the promotion records.
	 */
	public static ArrayList<Promotion> getAllPromotionItems() throws IOException {
		ArrayList<Promotion> psList= new ArrayList<>();
		ArrayList stringitems = (ArrayList) read(filename);

		for (int i = 0; i < stringitems.size(); i++) {
			String st = (String) stringitems.get(i);
			StringTokenizer star = new StringTokenizer(st, ",");
			String id = star.nextToken().trim();
			String name = star.nextToken().trim();
			String items = star.nextToken().trim();
			String[] pi = items.split("@");
			packItems = addAlaCarteItems(pi);
			String desc = star.nextToken().trim();
			String newdesc = desc.replace('/', ',');
			String price = star.nextToken().trim();
			Promotion m = new Promotion(Integer.parseInt(id),name,packItems,newdesc,Double.valueOf(price));
			psList.add(m);
		}
		return psList;
	}



	/**This method is used to update the promotion set from promotion csv
	 * @param promo Promotion object
	 * @throws IOException Display error message if any I/O error found while inserting into the promotion records.
	 */
	public static void updatePromotionSet(Promotion promo) throws IOException {
		List l = new ArrayList<>();
		ArrayList<Promotion> miList = getAllPromotionItems();

		for(int i=0;i<getAllPromotionItems().size();i++) {


			if(getAllPromotionItems().get(i).getPackId() == promo.getPackId()) {
				miList.set(i, promo);
			}

			Promotion k = miList.get(i);

			String packitems ="";
			for(int j=0;j<k.getPackItems().size();j++) {
				if(j == k.getPackItems().size()-1) {
					packitems += k.getPackItems().get(j).getAlaCarteName();
				} else {
					packitems += k.getPackItems().get(j).getAlaCarteName() + "@";
				}
			}
			String newdesc = k.getPackDesc().replace(',', '/');
			String foodItem = k.getPackId() + "," + k.getPackName() + "," + packitems + "," + newdesc +  "," +  k.getPackPrice();
			l.add(foodItem);
		}

		replaceAll(l);
	}

	/**This method is used to delete a promotion item in the promotion csv
	 * @param promo Promotion object
	 * @throws IOException Display error message if any I/O error found while inserting into the promotion records.
	 */
	public static void deletePromotionSet(Promotion promo) throws IOException {
		List l = new ArrayList<>();
		ArrayList<Promotion> miList = getAllPromotionItems();

		for(int i=0;i<getAllPromotionItems().size()-1;i++) {


			if(getAllPromotionItems().get(i).getPackId() == promo.getPackId()) {
				miList.remove(i);
			}

			Promotion k = miList.get(i);

			String packitems ="";
			for(int j=0;j<k.getPackItems().size();j++) {
				if(j == k.getPackItems().size()-1) {
					packitems += k.getPackItems().get(j).getAlaCarteName();
				} else {
					packitems += k.getPackItems().get(j).getAlaCarteName() + "@";
				}
			}
			String newdesc = k.getPackDesc().replace(',', '/');
			String foodItem = k.getPackId() + "," + k.getPackName() + "," + packitems + "," + newdesc +  "," +  k.getPackPrice();
			l.add(foodItem);
		}

		replaceAll(l);
	}

	/**This method replaces the entire promotion csv file with a new set of data
	 * @param list list of promotion items
	 * @throws IOException Display error message if any I/O error found while inserting into the promotion records.
	 */
	private static void replaceAll(List list) throws IOException {
		replace(filename, list);
	}

	/**This method reads the promotion CSV
	 * @param filename The filepath of the CSV file
	 * @return The list of records read from the promotion CSV
	 * @throws IOException  Display error message if any I/O error found while retrieving from the promotion records.
	 */
	private static List read(String filename) throws IOException {
		List data = new ArrayList();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String headerLine = reader.readLine();
		String line;
		try {
			while ((line = reader.readLine()) != null) {

				data.add(line);
			}
		} finally {
			reader.close();
		}
		return data;
	}


	/**This method writes the promotion CSV
	 * @param filename The filepath of the CSV file
	 * @param data A list of promotion items
	 * @throws IOException Display error message if any I/O error found while inserting into the promotion records.
	 */
	private static void write(String filename, List data) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(filename,true));
		try {
			for (int i = 0; i < data.size(); i++) {

				out.write((String) data.get(i)+"\n");
			}
		} finally {
			out.close();
		}
	}


	/**This method writes a new set of data and stores into the promotion CSV
	 * @param filename The filepath of the CSV file
	 * @param data A list of promotion items
	 * @throws IOException Display error message if any I/O error found while inserting into the promotion records.
	 */
	private static void replace(String filename, List data) throws IOException {

		BufferedWriter out = new BufferedWriter(new FileWriter(filename));
		try {
			out.write("PromoID" + "," + "Name" + "," + "Items" + "," + "Desc" + "," + "Price" + "\n");
			for (int i = 0; i < data.size(); i++) {

				out.write((String) data.get(i) + "\n");
			}
		} finally {
			out.close();
		}
	}

}