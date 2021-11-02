package entity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import controller.StoreController;

public class PromotionSet {
	
	private static final String filename = "DataSet/PromotionSet.csv";

	private int packId;
	private String packName;
	private Double packPrice;
	private ArrayList<AlaCarte> packItems;
	private String packDesc;
	Collection<OrderItem> orderitem;
	
	public PromotionSet() {
	
	}
	
	public PromotionSet(int id, String n, ArrayList<AlaCarte> packItems, String d, double price) {
		this.packId = id;
		this.packName = n;
		this.packItems = packItems;
		this.packDesc = d;
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
	
	
	/**
	 * 
	 * @param packId
	 */
	public void setPackName(String name) {
		this.packName = name;
	}


	public Double getPackPrice() {
		return this.packPrice;
	}

	/**
	 * 
	 * @param packPrice
	 */
	public void setPackPrice(double packPrice) {
		this.packPrice = packPrice;
	}

	public ArrayList<AlaCarte> getPackItem() {
		return this.packItems;
	}

	/**
	 * 
	 * @param packItem
	 */
	public void setPackItem(ArrayList<AlaCarte> packItem) {
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
	
	public PromotionSet selectPromotionSetByName(String n) throws FileNotFoundException {	
		PromotionSet a = null;
		for(int i=0;i<getAllPromotionItems().size();i++) {
			
			if(getAllPromotionItems().get(i).getPackName().equals(n)) {
				a = getAllPromotionItems().get(i);
			}
		}
		return a;
	}
	
	public void saveFoodItem(List list) throws IOException {
		StoreController.write(filename, list);
	}
	
	public ArrayList<AlaCarte> addAlaCarteItems(String[] n) throws FileNotFoundException {
		ArrayList<AlaCarte> al = new ArrayList<>();
		AlaCarte a = new AlaCarte();
		
		for(int k=0;k< n.length; k++) {			
			al.add(a.selectFoodByName(n[k]));
			
		}
		
		return al;
	}
	
	
	// RETRIEVE THE PROMOTION SET ITEMS
	public ArrayList<PromotionSet> getAllPromotionItems() throws FileNotFoundException {
		ArrayList<PromotionSet> psList= new ArrayList<>();
		ArrayList stringitems = (ArrayList) StoreController.read(filename); 	
		
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
			PromotionSet m = new PromotionSet(Integer.parseInt(id),name,packItems,newdesc,Double.valueOf(price));
			psList.add(m);
		}
		return psList;
	}
	
	
	//FOR UPDATE 
	public void updateFoodItem(PromotionSet a) throws IOException {
		List l = new ArrayList<>();
		ArrayList<PromotionSet> miList = getAllPromotionItems();
		
		for(int i=0;i<getAllPromotionItems().size();i++) {
			
			
			if(getAllPromotionItems().get(i).getPackId() == a.getPackId()) {
				miList.set(i, a);
				System.out.println(a.getPackName());
			}
			
			
					
			PromotionSet k = miList.get(i);
			
			String packitems ="";
			for(int j=0;j<k.getPackItem().size();j++) {
				if(j == k.getPackItem().size()-1) {
					packitems += k.getPackItem().get(j).getFoodName();
				} else {
					packitems += k.getPackItem().get(j).getFoodName() + "@";
				}	
			}
			String newdesc = k.getPackDesc().replace(',', '/');
			String foodItem = k.getPackId() + "," + k.getPackName() + "," + packitems + "," + newdesc +  "," +  k.getPackPrice();
			l.add(foodItem);
		}
		
		replaceAll(l);		
	}
	
	public void deletePromotionSet(PromotionSet a) throws IOException {
		List l = new ArrayList<>();
		ArrayList<PromotionSet> miList = getAllPromotionItems();
		
		for(int i=0;i<getAllPromotionItems().size()-1;i++) {
			
			
			if(getAllPromotionItems().get(i).getPackId() == a.getPackId()) {
				miList.remove(i);
				System.out.println(a.getPackName());
			}
					
			PromotionSet k = miList.get(i);
			
			String packitems ="";
			for(int j=0;j<k.getPackItem().size();j++) {
				if(j == k.getPackItem().size()-1) {
					packitems += k.getPackItem().get(j).getFoodName();
				} else {
					packitems += k.getPackItem().get(j).getFoodName() + "@";
				}	
			}
			String newdesc = k.getPackDesc().replace(',', '/');
			String foodItem = k.getPackId() + "," + k.getPackName() + "," + packitems + "," + newdesc +  "," +  k.getPackPrice();
			l.add(foodItem);
		}
		
		replaceAll(l);		
	}
	
	//REPLACE THE ENTIRE CSV FILE 
	private void replaceAll(List list) throws IOException {
		StoreController.replace(filename, list);
	}

}