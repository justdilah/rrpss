package boundary;

import java.util.Scanner;

import controller.MainController;
import controller.PromotionController;
import entity.SetPackage;

public class PromotionForm {
	
	private MainController mainControl = new MainController();
	private PromotionController control = new PromotionController();
	private static Scanner sc = new Scanner(System.in);
	private static int itemid;
	private static 	String name, desc;
	private static double price;

	public void displayOption() {
		System.out.println("=================================");
		System.out.println("\t Promotion Menu");
		System.out.println("=================================");
		System.out.println("(1) Insert Promotion Item");
		System.out.println("(2) Display Promotion Item ");
		System.out.println("(3) Update Promotion Item ");
		System.out.println("(4) Delete Promotion Item");
		System.out.println("(5) Return to Main Menu");
		
		
		int choice = sc.nextInt();
		
		switch(choice) {
		case 1:
			insertPromotionItem();
			break;
		case 2:
			displayPromotionItem();
			break;
		case 3:
			displayPromotionItem(); 
			updatePromotionItem();
			break;
		case 4:
			displayPromotionItem();
			deletePromotionItem();
			break;
		case 5:
			break;
		}
		throw new UnsupportedOperationException();
	}

	public void insertPromotionItem() {
		System.out.println("enter item name: ");
		name = sc.next();
		System.out.println("enter item desc: ");
		desc = sc.next();
		System.out.println("enter item price: ");
		price = sc.nextInt();
		control.addPromoItem(name, desc, price);
		throw new UnsupportedOperationException();
	}

	public void displayPromotionItem() {
		control.getAllPromoItem();
		throw new UnsupportedOperationException();
	}

	public void updatePromotionItem() {
		System.out.println("enter item id: ");
		itemid = sc.nextInt();
		System.out.println("enter item name: ");
		name = sc.next();
		control.updatePromoItemName(itemid, name);
		throw new UnsupportedOperationException();
	}

	public void deletePromotionItem() {
		System.out.println("enter item id: ");
		itemid = sc.nextInt();
		control.deletePromoItem(itemid);
		throw new UnsupportedOperationException();
	}

}