package entity;
import java.util.*;

public class MenuItem {

	Collection<Order> order;
	Collection<SetPackage> setpack;
	private String menuName;
	private String menuDesc;
	private Double menuPrice;
	private MenuItemType menuType;

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

}