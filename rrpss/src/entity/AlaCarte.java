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
//	Collection<Order> order;
//	Collection<SetPackage> setpack;
	private int alaCarteId;
	private String alaCarteName;
	private String alaCarteDesc;
	private Double alaCartePrice;
	private FoodType foodType;
	
	public AlaCarte() {
		
	}
	
	public AlaCarte(int id, String n,String d,Double p, FoodType mt) {
		this.alaCarteId = id;
		this.alaCarteName = n;
		this.alaCarteDesc = d;
		this.alaCartePrice = p;
		this.foodType = mt;
	}

	public int getAlaCarteId() {
		return this.alaCarteId;
	}

	public void setFoodId(int id) {
		this.alaCarteId = id;
	}

	public String getAlaCarteName() {
		return this.alaCarteName;
	}

	/**
	 * 
	 * @param foodName
	 */
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
	
	public AlaCarte selectAlaCarteById(int id) throws FileNotFoundException {	
		AlaCarte a = null;
		for(int i=0;i<getAllAlaCarteItems().size();i++) {
			if(getAllAlaCarteItems().get(i).getAlaCarteId() == id) {
				a = getAllAlaCarteItems().get(i);
			}
		}
		return a;
	}
	
	public AlaCarte selectFoodByName(String n) throws FileNotFoundException {	
		AlaCarte a = null;
		for(int i=0;i<getAllAlaCarteItems().size();i++) {
			
			if(getAllAlaCarteItems().get(i).getAlaCarteName().equals(n)) {
				a = getAllAlaCarteItems().get(i);
			}
		}
		return a;
	}
	
	
	// EXTRACT OUT FROM CSV FILE
	public ArrayList<AlaCarte> getAllAlaCarteItems() throws FileNotFoundException {
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
	
	
	
	
	//ADDING TO CSV FILE 
	public void saveFoodItem(String menuName, String menuDesc, double menuPrice, FoodType menuType) throws IOException {
		int last = getAllAlaCarteItems().size();
		int id = getAllAlaCarteItems().get(last-1).getAlaCarteId()+ 1;
		String newft = menuType.toString().replace("_"," ");
		String foodItem = id + "," + menuName + "," + menuDesc +  "," +  menuPrice+ "," +newft;
		List l = new ArrayList();
		l.add(foodItem);
		write(filename, l);
	}
	
	//FOR DELETE
	public void deleteFoodItem(AlaCarte a) throws IOException {
		List l = new ArrayList<>();
		ArrayList<AlaCarte> miList = getAllAlaCarteItems();
		
		for(int i=0;i<getAllAlaCarteItems().size();i++) {
			if(getAllAlaCarteItems().get(i).getAlaCarteId() == a.getAlaCarteId()) {
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
	public void updateAlaCarteItem(AlaCarte a) throws IOException {
		List l = new ArrayList<>();
		ArrayList<AlaCarte> miList = getAllAlaCarteItems();
		
		for(int i=0;i<getAllAlaCarteItems().size();i++) {
			
			
			if(getAllAlaCarteItems().get(i).getAlaCarteId() == a.getAlaCarteId()) {
				miList.set(i, a);
			}
					
			AlaCarte k = miList.get(i);
			String newdesc = k.getAlaCarteDesc().replace(',', '/');
			String foodItem = k.getAlaCarteId() + "," + k.getAlaCarteName() + "," + newdesc +  "," +  k.getAlaCartePrice()+ "," +k.getFoodType();
			l.add(foodItem);
		}
		
		replace(filename, l);	
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
