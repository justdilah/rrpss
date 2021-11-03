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
	private int foodId;
	private String foodName;
	private String foodDesc;
	private Double foodPrice;
	private FoodType foodType;
	
	public AlaCarte() {
		
	}
	
	public AlaCarte(int id, String n,String d,Double p, FoodType mt) {
		this.foodId = id;
		this.foodName = n;
		this.foodDesc = d;
		this.foodPrice = p;
		this.foodType = mt;
	}

	public int getFoodId() {
		return this.foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	public String getFoodName() {
		return this.foodName;
	}

	/**
	 * 
	 * @param foodName
	 */
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public String getFoodDesc() {
		return this.foodDesc;
	}

	public void setFoodDesc(String foodDesc) {
		this.foodDesc = foodDesc;
	}

	public Double getFoodPrice() {
		return this.foodPrice;
	}

	public void setFoodPrice(Double foodPrice) {
		this.foodPrice = foodPrice;
	}

	public FoodType getFoodType() {
		return this.foodType;
	}

	public void setFoodType(FoodType foodType) {
		this.foodType = foodType;
	}
	
	public AlaCarte selectItemById(int id) throws FileNotFoundException {	
		AlaCarte a = null;
		for(int i=0;i<getAllMenuItems().size();i++) {
			if(getAllMenuItems().get(i).getFoodId() == id) {
				a = getAllMenuItems().get(i);
			}
		}
		return a;
	}
	
	public AlaCarte selectFoodByName(String n) throws FileNotFoundException {	
		AlaCarte a = null;
		for(int i=0;i<getAllMenuItems().size();i++) {
			
			if(getAllMenuItems().get(i).getFoodName().equals(n)) {
				a = getAllMenuItems().get(i);
			}
		}
		return a;
	}
	
	
	// EXTRACT OUT FROM CSV FILE
	public ArrayList<AlaCarte> getAllMenuItems() throws FileNotFoundException {
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
		int last = getAllMenuItems().size();
		int id = getAllMenuItems().get(last-1).getFoodId()+ 1;
		String newft = menuType.toString().replace("_"," ");
		String foodItem = id + "," + menuName + "," + menuDesc +  "," +  menuPrice+ "," +newft;
		List l = new ArrayList();
		l.add(foodItem);
		write(filename, l);
	}
	
	//FOR DELETE
	public void deleteFoodItem(AlaCarte a) throws IOException {
		List l = new ArrayList<>();
		ArrayList<AlaCarte> miList = getAllMenuItems();
		
		for(int i=0;i<getAllMenuItems().size();i++) {
			if(getAllMenuItems().get(i).getFoodId() == a.getFoodId()) {
				miList.remove(i);
			} else {
				AlaCarte k = miList.get(i);
				String newdesc = k.getFoodDesc().replace(',', '/');
				String foodItem = k.getFoodId() + "," + k.getFoodName() + "," + newdesc +  "," +  k.getFoodPrice()+ "," +k.getFoodType();
				l.add(foodItem);
			}
		}
		replace(filename, l);
	}
	
	//FOR UPDATE 
	public void updateFoodItem(AlaCarte a) throws IOException {
		List l = new ArrayList<>();
		ArrayList<AlaCarte> miList = getAllMenuItems();
		
		for(int i=0;i<getAllMenuItems().size();i++) {
			
			
			if(getAllMenuItems().get(i).getFoodId() == a.getFoodId()) {
				miList.set(i, a);
			}
					
			AlaCarte k = miList.get(i);
			String newdesc = k.getFoodDesc().replace(',', '/');
			String foodItem = k.getFoodId() + "," + k.getFoodName() + "," + newdesc +  "," +  k.getFoodPrice()+ "," +k.getFoodType();
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
