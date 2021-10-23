package entity;
import java.io.BufferedWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import controller.StoreController;

public class AlaCarte {
	private static final String filename = "DataSet/AlaCarte.csv";
	Collection<Order> order;
	Collection<SetPackage> setpack;
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

	/**
	 * 
	 * @param foodId
	 */
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

	/**
	 * 
	 * @param foodDesc
	 */
	public void setFoodDesc(String foodDesc) {
		this.foodDesc = foodDesc;
	}

	public Double getFoodPrice() {
		return this.foodPrice;
	}

	/**
	 * 
	 * @param foodPrice
	 */
	public void setFoodPrice(Double foodPrice) {
		this.foodPrice = foodPrice;
	}

	public FoodType getFoodType() {
		return this.foodType;
	}

	/**
	 * 
	 * @param foodType
	 */
	public void setFoodType(FoodType foodType) {
		this.foodType = foodType;
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
		ArrayList stringitems = (ArrayList) StoreController.read(filename); 	
		
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
	public void saveFoodItem(List list) throws IOException {
		StoreController.write(filename, list);
	}
	
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
		replaceAll(l);
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
		
		replaceAll(l);		
	}
	
	//REPLACE THE ENTIRE CSV FILE 
		public void replaceAll(List list) throws IOException {
			StoreController.replace(filename, list);
		}
	

}
