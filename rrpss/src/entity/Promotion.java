package entity;

import controller.AlaCarteController;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Promotion {
	
	private static final String filename = "DataSet/PromotionSet.csv";

	private int packId;
	private String packName;
	private double packPrice;
	private static ArrayList<AlaCarte> packItems;
	private String packDesc;
	
	public Promotion() {
	
	}
	
	public Promotion(int id, String name, ArrayList<AlaCarte> packItems, String desc, double price) {
		this.packId = id;
		this.packName = name;
		this.packItems = packItems;
		this.packDesc = desc;
		this.packPrice = price;
	}
	
	public int getPackId() {
		return this.packId;
	}
	
	
	/**
	 * 
	 * @param packId
	 */
	public void setPackId(int packId) {
		this.packId = packId;
	}
	
	public String getPackName() {
		return this.packName;
	}
	

	public void setPackName(String name) {
		this.packName = name;
	}


	public double getPackPrice() {
		return this.packPrice;
	}

	/**
	 * 
	 * @param packPrice
	 */
	public void setPackPrice(double packPrice) {
		this.packPrice = packPrice;
	}

	public ArrayList<AlaCarte> getPackItems() {
		return this.packItems;
	}

	/**
	 * 
	 * @param packItem
	 */
	public void setPackItems(ArrayList<AlaCarte> packItem) {
		this.packItems = packItem;
	}

	public String getPackDesc() {
		return this.packDesc;
	}

	/**
	 * 
	 * @param packDesc
	 */

	public void setPackDesc(String packDesc) {
		this.packDesc = packDesc;
	}


	//SAVES THE ITEM TO CSV
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
	
	//ADD ALACARTE ITEMS 
	private static ArrayList<AlaCarte> addAlaCarteItems(String[] n) throws FileNotFoundException {
		ArrayList<AlaCarte> al = new ArrayList<>();

		for(int k=0;k< n.length; k++) {			
			al.add(AlaCarteController.getAlaCarteByName(n[k]));
		}
		return al;
	}
	
	
	// RETRIEVE THE PROMOTION SET ITEMS
	public static ArrayList<Promotion> getAllPromotionItems() throws FileNotFoundException {
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
	
	
	//FOR UPDATE
	public static void updatePromotionSet(Promotion a) throws IOException {
		List l = new ArrayList<>();
		ArrayList<Promotion> miList = getAllPromotionItems();
		
		for(int i=0;i<getAllPromotionItems().size();i++) {
			
			
			if(getAllPromotionItems().get(i).getPackId() == a.getPackId()) {
				miList.set(i, a);
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
	
	//DELETE THE PROMOTION SET
	public static void deletePromotionSet(Promotion a) throws IOException {
		List l = new ArrayList<>();
		ArrayList<Promotion> miList = getAllPromotionItems();
		
		for(int i=0;i<getAllPromotionItems().size()-1;i++) {
			
			
			if(getAllPromotionItems().get(i).getPackId() == a.getPackId()) {
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
	
	//REPLACE THE ENTIRE CSV FILE 
	private static void replaceAll(List list) throws IOException {
		replace(filename, list);
	}
	
	//READ AND WRITE TO CSV
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