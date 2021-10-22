package entity;
import java.io.FileNotFoundException;
import java.util.*;

import controller.StoreController;

public class MenuItem {
	
	private static final String filename = "DataSet/MenuItem.csv"; 
	Collection<Order> order;
	Collection<SetPackage> setpack;
	private String menuId;
	private String menuName;
	private String menuDesc;
	private Double menuPrice;
	private MenuItemType menuType;
	
	
	public MenuItem() {
		
	}
	
	public MenuItem(String id, String n,String d,Double p,String mt) {
		this.menuId = id;
		this.menuName = n;
		this.menuDesc = d;
		this.menuPrice = p;
		this.menuType = MenuItemType.valueOf(mt);
		this.menuDesc = d;
	}

	public String getMenuName() {
		return this.menuName;
	}

	/**
	 * 
	 * @param menuName
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuDesc() {
		return this.menuDesc;
	}

	/**
	 * 
	 * @param menuDesc
	 */
	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public Double getMenuPrice() {
		return this.menuPrice;
	}

	/**
	 * 
	 * @param menuPrice
	 */
	public void setMenuPrice(Double menuPrice) {
		this.menuPrice = menuPrice;
	}

	public MenuItemType getMenuType() {
		return this.menuType;
	}

	/**
	 * 
	 * @param menuType
	 */
	public void setMenuType(MenuItemType menuType) {
		this.menuType = menuType;
	}
	
	public ArrayList<MenuItem> getAllMenuItems() throws FileNotFoundException {
		ArrayList<MenuItem> miList= new ArrayList<>();
		ArrayList stringitems = (ArrayList) StoreController.read(filename); 	
		
		for (int i = 0; i < stringitems.size(); i++) {
			String st = (String) stringitems.get(i);
			StringTokenizer star = new StringTokenizer(st, ",");
			String menuId = star.nextToken().trim();
			String name = star.nextToken().trim();
			String desc = star.nextToken().trim();
			String price = star.nextToken().trim();
			String menuType =  star.nextToken().trim();
			MenuItem m = new MenuItem(menuId,name,desc,Double.valueOf(price),menuType);
			miList.add(m);
		}
		return miList;
	}

}