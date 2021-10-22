package controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import entity.MenuItem;
import entity.MenuItemType;

public class MenuController {

	/**
	 * 
	 * @param menuName
	 * @param menuDesc
	 * @param menuPrice
	 * @param menuType
	 * 
	 * 
	 */
	
	MenuItem m = new MenuItem();
	public void addFoodItem(String menuName, String menuDesc, double menuPrice, MenuItemType menuType) {
		// TODO - implement MenuController.addFoodItem
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param menuId
	 */
	public void getFoodItem(int menuId) {
		// TODO - implement MenuController.getFoodItem
		throw new UnsupportedOperationException();
	}

	public ArrayList<MenuItem> getAllFoodItem() throws FileNotFoundException {
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
	 */
	public void deleteFoodItem(int menuId) {
		// TODO - implement MenuController.deleteFoodItem
		throw new UnsupportedOperationException();
	}

}
