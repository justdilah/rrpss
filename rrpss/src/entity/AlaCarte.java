package entity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
/**
 * This class represents an Ala Carte item
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class AlaCarte {

	/**
	 * Initialized filepath of the AlaCarte.csv
	 */
	private static final String filename = "DataSet/AlaCarte.csv";

	/**
	 * The Id of this Ala Carte
	 */
	private int alaCarteId;

	/**
	 * The name of this Ala Carte
	 */
	private String alaCarteName;

	/**
	 * The description of this Ala Carte
	 */
	private String alaCarteDesc;

	/**
	 * The price of this Ala Carte
	 */
	private Double alaCartePrice;

	/**
	 * The food type of this Ala Carte
	 */
	private FoodType foodType;

	/**
	 * Class Constructor
	 */
	public AlaCarte() {

	}

	/**Class Constructor with the given id, name, description, price and food type
	 *
	 * @param id int value to uniquely identifies the Ala Carte item
	 * @param n string value for the name of the Ala Carte item
	 * @param d string value for the description of the Ala Carte item
	 * @param p double value for the price of the Ala Carte item
	 * @param ft Food Type object for the type of the Ala Carte item
	 */
	public AlaCarte(int id, String n,String d,Double p, FoodType ft) {
		this.alaCarteId = id;
		this.alaCarteName = n;
		this.alaCarteDesc = d;
		this.alaCartePrice = p;
		this.foodType = ft;
	}

	/**This method gets the Ala Carte Id
	 * @return Ala Carte Id
	 */
	public int getAlaCarteId() {
		return this.alaCarteId;
	}

	/**This method sets the id
	 * @param id uniquely identifies the Ala Carte item
	 */
	public void setAlaCarteId(int id) {
		this.alaCarteId = id;
	}

	/**This method gets the Ala Carte name
	 * @return name of the Ala Carte item
	 */
	public String getAlaCarteName() {
		return this.alaCarteName;
	}


	/**This method sets the name for Ala Carte
	 * @param name string value for the  name of the Ala Carte item
	 */
	public void setAlaCarteName(String name) {
		this.alaCarteName = name;
	}

	/**This method gets the description for Ala Carte
	 * @return description of the Ala Carte item
	 */
	public String getAlaCarteDesc() {
		return this.alaCarteDesc;
	}

	/**This method sets the description for Ala Carte
	 * @param desc string value for the description of the Ala Carte item
	 */
	public void setAlaCarteDesc(String desc) {
		this.alaCarteDesc = desc;
	}

	/**This method gets the Ala Carte price
	 * @return price of the Ala Carte item
	 */
	public Double getAlaCartePrice() {
		return this.alaCartePrice;
	}

	/**This method sets the price for the Ala Carte
	 * @param price double value for the price of the Ala Carte item
	 */
	public void setAlaCartePrice(double price) {
		this.alaCartePrice = price;
	}

	/**This method gets the food type of the Ala Carte
	 * @return food type of the Ala Carte item
	 */
	public FoodType getFoodType() {
		return this.foodType;
	}

	/**This method sets the food type for the Ala Carte
	 * @param foodType FoodType object
	 */
	public void setFoodType(FoodType foodType) {
		this.foodType = foodType;
	}


	/**This method extracts the Ala Carte items from the csv file
	 * @return A list of Ala Carte items
	 * @throws IOException Display error message if any I/O error found while retrieving from the ala carte records.
	 */
	public static ArrayList<AlaCarte> getAllAlaCarteItems() throws IOException {
		ArrayList<AlaCarte> miList= new ArrayList<>();
		ArrayList stringitems = (ArrayList) read(filename);

		for (int i = 0; i < stringitems.size(); i++) {
			String st = (String) stringitems.get(i);
			StringTokenizer star = new StringTokenizer(st, ",");
			String menuId = star.nextToken().trim();
			String name = star.nextToken().trim();
			String desc = star.nextToken().trim();
			String newdesc = desc.replace('/', ',');
			String price = star.nextToken().trim();
			String menuType =  star.nextToken().trim();
			String newmt = menuType.replace(" ", "_");
			AlaCarte m = new AlaCarte(Integer.parseInt(menuId),name,newdesc,Double.valueOf(price),FoodType.valueOf(newmt));
			miList.add(m);
		}
		return miList;
	}



	/**Thia method adds Ala Carte item in the csv file
	 * @param name string value for the name of the Ala Carte item
	 * @param desc string value for the description of the Ala Carte item
	 * @param price double value for the price of the Ala Carte item
	 * @param type FoodType object for the type of the Ala Carte item
	 * @throws IOException Display error message if any I/O error found while inserting into the ala carte records.
	 */
	public static void saveAlaCarteItem(String name, String desc, double price, FoodType type) throws IOException {
		ArrayList<AlaCarte> ala = getAllAlaCarteItems();
		int last = ala.size();
		int id = ala.get(last-1).getAlaCarteId()+ 1;
		String newft = type.toString().replace("_"," ");
		String foodItem = id + "," + name + "," + desc +  "," +  price+ "," +newft;
		List l = new ArrayList();
		l.add(foodItem);
		write(filename, l);
	}


	/**This method deletes an Ala Carte item in the csv file
	 * @param a AlaCarte Object
	 * @throws IOException Display error message if any I/O error found while inserting into the ala carte records.
	 */
	public static void deleteAlaCarteItem(AlaCarte a) throws IOException {
		List l = new ArrayList<>();
		ArrayList<AlaCarte> miList = getAllAlaCarteItems();

		for(int i=0;i<miList.size();i++) {
			if(miList.get(i).getAlaCarteId() == a.getAlaCarteId()) {
				miList.remove(i);
			} else {
				AlaCarte k = miList.get(i);
				String newdesc = k.getAlaCarteDesc().replace(',', '/');
				String foodItem = k.getAlaCarteId() + "," + k.getAlaCarteName() + "," + newdesc +  "," +  k.getAlaCartePrice()+ "," +k.getFoodType();
				l.add(foodItem);
			}
		}
		replace(filename, l);
	}

	/**This method updates an Ala Carte item from the csv file
	 * @param a Ala Carte object
	 * @throws IOException Display error message if any I/O error found while inserting into the ala carte records.
	 */
	public static void updateAlaCarteItem(AlaCarte a) throws IOException {
		List l = new ArrayList<>();
		ArrayList<AlaCarte> miList = getAllAlaCarteItems();

		for(int i=0;i<miList.size();i++) {

			if(miList.get(i).getAlaCarteId() == a.getAlaCarteId()) {
				miList.set(i, a);
			}

			AlaCarte k = miList.get(i);
			String newdesc = k.getAlaCarteDesc().replace(',', '/');
			String foodItem = k.getAlaCarteId() + "," + k.getAlaCarteName() + "," + newdesc +  "," +  k.getAlaCartePrice()+ "," +k.getFoodType();
			l.add(foodItem);
		}
		replace(filename, l);
	}


	/**This method reads the Ala Carte CSV
	 * @param filename The filepath of the CSV file
	 * @return The list of records read from the Ala Carte CSV
	 * @throws IOException Display error message if any I/O error found while retrieving from the ala carte records.
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


	/**
	 * This method writes the Ala Carte CSV
	 * @param filename The filepath of the CSV file
	 * @param data A list of Ala Carte items
	 * @throws IOException Display error message if any I/O error found while inserting into the ala carte records.
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

	/**This method writes a new set of data and stores into the order CSV
	 * @param filename The filepath of the CSV file
	 * @param data A list of Ala Carte items
	 * @throws IOException Display error message if any I/O error found while inserting into the ala carte records.
	 */
	private static void replace(String filename, List data) throws IOException {

		BufferedWriter out = new BufferedWriter(new FileWriter(filename));
		try {
			out.write("AlaCarteID" + "," + "Name" + "," + "Desc" + "," + "Price" + "," + "FoodType" + "\n");
			for (int i = 0; i < data.size(); i++) {

				out.write((String) data.get(i) + "\n");
			}
		} finally {
			out.close();
		}
	}
}