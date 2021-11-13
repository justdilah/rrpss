package entity;
import java.io.BufferedWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class AlaCarte {
	
	private static final String filename = "DataSet/AlaCarte.csv";
	private int alaCarteId;
	private String alaCarteName;
	private String alaCarteDesc;
	private Double alaCartePrice;
	private FoodType foodType;
	
	public AlaCarte() {
		
	}
	
	public AlaCarte(int id, String n,String d,Double p, FoodType ft) {
		this.alaCarteId = id;
		this.alaCarteName = n;
		this.alaCarteDesc = d;
		this.alaCartePrice = p;
		this.foodType = ft;
	}

	public int getAlaCarteId() {
		return this.alaCarteId;
	}

	public void setAlaCarteId(int id) {
		this.alaCarteId = id;
	}

	public String getAlaCarteName() {
		return this.alaCarteName;
	}


	public void setAlaCarteName(String name) {
		this.alaCarteName = name;
	}

	public String getAlaCarteDesc() {
		return this.alaCarteDesc;
	}

	public void setAlaCarteDesc(String desc) {
		this.alaCarteDesc = desc;
	}

	public Double getAlaCartePrice() {
		return this.alaCartePrice;
	}

	public void setAlaCartePrice(double price) {
		this.alaCartePrice = price;
	}

	public FoodType getFoodType() {
		return this.foodType;
	}

	public void setFoodType(FoodType foodType) {
		this.foodType = foodType;
	}

	
	// EXTRACT OUT FROM CSV FILE //Static Method //Main Read Methods
	public static ArrayList<AlaCarte> getAllAlaCarteItems() throws FileNotFoundException {
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
	private static List read(String filename) throws FileNotFoundException {
		List data = new ArrayList();
		Scanner scanner = new Scanner(new FileInputStream(filename));
		try {
			while (scanner.hasNextLine()) {
				data.add(scanner.nextLine());
			}
		} finally {
			scanner.close();
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
			for (int i = 0; i < data.size(); i++) {
				
				out.write((String) data.get(i) + "\n");
			}
		} finally {
			out.close();
		}
	}
}
