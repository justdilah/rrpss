package entity;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
/**
 * Represents an Ala Carte item for the restaurant. 
 * @author Dilah
 * 
 * @since 2021-10-13
 */
public class AlaCarte {
	
	private static final String filename = "DataSet/AlaCarte.csv";
	
	private int alaCarteId;
	private String alaCarteName;
	private String alaCarteDesc;
	private Double alaCartePrice;
	private FoodType foodType;
	
	/**
	 * 
	 */
	public AlaCarte() {
		
	}
	
	/**
	 * @param id
	 * @param n
	 * @param d
	 * @param p
	 * @param ft
	 */
	public AlaCarte(int id, String n,String d,Double p, FoodType ft) {
		this.alaCarteId = id;
		this.alaCarteName = n;
		this.alaCarteDesc = d;
		this.alaCartePrice = p;
		this.foodType = ft;
	}

	/**
	 * @return
	 */
	public int getAlaCarteId() {
		return this.alaCarteId;
	}

	/**
	 * @param id
	 */
	public void setAlaCarteId(int id) {
		this.alaCarteId = id;
	}

	/**
	 * @return
	 */
	public String getAlaCarteName() {
		return this.alaCarteName;
	}


	/**
	 * @param name
	 */
	public void setAlaCarteName(String name) {
		this.alaCarteName = name;
	}

	/**
	 * @return
	 */
	public String getAlaCarteDesc() {
		return this.alaCarteDesc;
	}

	/**
	 * @param desc
	 */
	public void setAlaCarteDesc(String desc) {
		this.alaCarteDesc = desc;
	}

	/**
	 * @return
	 */
	public Double getAlaCartePrice() {
		return this.alaCartePrice;
	}

	/**
	 * @param price
	 */
	public void setAlaCartePrice(double price) {
		this.alaCartePrice = price;
	}

	/**
	 * @return
	 */
	public FoodType getFoodType() {
		return this.foodType;
	}

	/**
	 * @param foodType
	 */
	public void setFoodType(FoodType foodType) {
		this.foodType = foodType;
	}

	
	// EXTRACT OUT FROM CSV FILE //Static Method //Main Read Methods
	/**
	 * @return
	 * @throws IOException 
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
	

	//ADDING TO CSV FILE (Reduced Time)
	/**
	 * @param menuName
	 * @param menuDesc
	 * @param menuPrice
	 * @param menuType
	 * @throws IOException
	 */
	public static void saveAlaCarteItem(String menuName, String menuDesc, double menuPrice, FoodType menuType) throws IOException {
		ArrayList<AlaCarte> ala = getAllAlaCarteItems();
		int last = ala.size();
		int id = ala.get(last-1).getAlaCarteId()+ 1;
		String newft = menuType.toString().replace("_"," ");
		String foodItem = id + "," + menuName + "," + menuDesc +  "," +  menuPrice+ "," +newft;
		List l = new ArrayList();
		l.add(foodItem);
		write(filename, l);
	}
	
	//FOR DELETE (Reduced Time)
	/**
	 * @param a
	 * @throws IOException
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
	
	//FOR UPDATE 
	/**
	 * @param a
	 * @throws IOException
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
	
	//READ AND WRITE TO CSV (No Change)
	
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
