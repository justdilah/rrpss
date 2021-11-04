package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.AlaCarte;
import entity.FoodType;


public class AlaCarteController {
	
	AlaCarte m = new AlaCarte();
	
	public void addFoodItem(String menuName, String menuDesc, double menuPrice, FoodType menuType) throws IOException {
		m.saveFoodItem(menuName, menuDesc, menuPrice, menuType);
	}
	
	public AlaCarte getFoodByName(String name) throws FileNotFoundException {
		return m.selectFoodByName(name);
	}

	public ArrayList<AlaCarte> getAllFoodItem() throws FileNotFoundException {
		return m.getAllMenuItems();
	}

	public void updateFoodItemName(AlaCarte a, String n) throws IOException {
		a.setFoodName(n);;
		m.updateFoodItem(a);
	}

	public void updateFoodItemDesc(AlaCarte a, String d) throws IOException {
		a.setFoodDesc(d);
		m.updateFoodItem(a);
	}

	public void updateFoodItemPrice(AlaCarte a, double p) throws IOException {
		a.setFoodPrice(p);
		m.updateFoodItem(a);
	}
	
	public void updateFoodItemType(AlaCarte a, FoodType p) throws IOException {
		a.setFoodType(p);
		m.updateFoodItem(a);
	}
	
	public void deleteFoodItem(AlaCarte a) throws IOException {
		m.deleteFoodItem(a);
	}

}