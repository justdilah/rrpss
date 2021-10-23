package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.AlaCarte;
import entity.FoodType;


public class MenuController {
	
	AlaCarte m = new AlaCarte();
	
	public void addFoodItem(String menuName, String menuDesc, double menuPrice, FoodType menuType) throws IOException {
		int last = m.getAllMenuItems().size();
		int id = m.getAllMenuItems().get(last-1).getFoodId()+ 1;
		
		String foodItem = id + "," + menuName + "," + menuDesc +  "," +  menuPrice+ "," +menuType;
		List l = new ArrayList();
		l.add(foodItem);
		m.saveFoodItem(l);
		
	}

	/**
	 * 
	 * @param menuId
	 */
	public void getFoodItem(int menuId) {
		// TODO - implement MenuController.getFoodItem
		throw new UnsupportedOperationException();
	}
	
	public AlaCarte getFoodByName(String name) throws FileNotFoundException {
		// TODO - implement MenuController.getFoodItem
		return m.selectFoodByName(name);
	}

	public ArrayList<AlaCarte> getAllFoodItem() throws FileNotFoundException {
		// TODO - implement MenuController.getAllFoodItem
		return m.getAllMenuItems();
	}

	/**
	 * 
	 * @param menuId
	 * @param menuName
	 */
	public void updateFoodItemName(int menuId, String menuName) {
		// TODO - implement MenuController.updateFoodItemName
		throw new UnsupportedOperationException();
	}
	
	public void updateFoodItem(AlaCarte a) throws IOException {
		m.updateFoodItem(a);
	}

	/**
	 * 
	 * @param menuId
	 * @param menuDesc
	 */
	public void updateFoodItemDesc(int menuId, String menuDesc) {
		// TODO - implement MenuController.updateFoodItemDesc
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param menuId
	 * @param menuPrice
	 */
	public void updateFoodItemPrice(int menuId, double menuPrice) {
		// TODO - implement MenuController.updateFoodItemPrice
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param menuId
	 * @throws IOException 
	 */
	public void deleteFoodItem(AlaCarte a) throws IOException {
		// TODO - implement MenuController.deleteFoodItem
		m.deleteFoodItem(a);
	}

}
