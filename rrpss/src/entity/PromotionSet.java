package entity;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PromotionSet {
	
	private static final String filename = "DataSet/PromotionSet.csv";

	private int packId;
	private String packName;
	private Double packPrice;
	private ArrayList<AlaCarte> packItems;
	private String packDesc;
//	Collection<OrderItem> orderitem;
	
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
	
	public PromotionSet selectItemById(int id) throws FileNotFoundException {	
		PromotionSet p = null;
		for(int i=0;i<getAllPromotionItems().size();i++) {
			if(getAllPromotionItems().get(i).getPackId() == id) {
				p = getAllPromotionItems().get(i);
			}
		}
		return p;
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
	
	//SAVES THE ITEM TO CSV
	public void savePromotionSetItem(String name, ArrayList<AlaCarte> pslist, String desc, double p) throws IOException {
		int last = getAllPromotionItems().size();
		int id = getAllPromotionItems().get(last-1).getPackId()+ 1;
		String packitems ="";
		for(int i=0;i<pslist.size();i++) {
			System.out.println(pslist.get(i).getFoodName());
			if(i == pslist.size()-1) {
				packitems += pslist.get(i).getFoodName();
			} else {
				packitems += pslist.get(i).getFoodName() + "@";
			}	
		}
		
		String item = id + "," + name + "," + packitems + "," + desc +  "," +  p;
		List l = new ArrayList();
		l.add(item);
		write(filename, l);
	}
	
	//ADD ALACARTE ITEMS 
	private ArrayList<AlaCarte> addAlaCarteItems(String[] n) throws FileNotFoundException {
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
			PromotionSet m = new PromotionSet(Integer.parseInt(id),name,packItems,newdesc,Double.valueOf(price));
			psList.add(m);
		}
		return psList;
	}
	
	
	//FOR UPDATE
	public void updatePromotionSet(PromotionSet a) throws IOException {
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
	
	//DELETE THE PROMOTION SET
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
		replace(filename, list);
	}
	
	//READ AND WRITE TO CSV
	private List read(String filename) throws FileNotFoundException {
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
	
	private void write(String filename, List data) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(filename,true));
		try {
			for (int i = 0; i < data.size(); i++) {
				
				out.write((String) data.get(i)+"\n");
			}
		} finally {
			out.close();
		}
	}
	
	private void replace(String filename, List data) throws IOException {
		
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