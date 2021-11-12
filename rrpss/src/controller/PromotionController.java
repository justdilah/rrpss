package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.AlaCarte;
import entity.FoodType;
import entity.Promotion;

public class PromotionController {
	
	private static final String filename = "DataSet/PromotionSet.csv";
	Promotion ps = new Promotion();
	/**
	 * 
	 * @param packName
	 * @param packDesc
	 * @param packPrice
	 */
	
	public void addPromotionSet(String name, ArrayList<AlaCarte> allist, String desc, double price) throws IOException {
		ps.savePromotionSetItem(name, allist, desc, price);
	}
	
	public Promotion getPromotionSetByName(String name) throws FileNotFoundException {
		return ps.selectPromotionSetByName(name);
	}
	
	public ArrayList<Promotion> getAllPromotionSets() throws FileNotFoundException {
		return ps.getAllPromotionItems();
	}
	
	/**
	 * 
	 * @param packId
	 * @param packName
	 * @throws IOException 
	 */
	public void updatePromotionSetName(Promotion promo, String name) throws IOException {
		promo.setPackName(name);
		ps.updatePromotionSet(promo);
	}
	
	public void updateAlaCarteItems(Promotion promo, ArrayList<AlaCarte> items) throws IOException {
		promo.setPackItems(items);
		ps.updatePromotionSet(promo);
	}

	/**
	 * 
	 * @param packId
	 * @param packDesc
	 * @throws IOException 
	 */
	public void updatePromotionSetDesc(Promotion promo, String desc) throws IOException {
		promo.setPackDesc(desc);
		ps.updatePromotionSet(promo);

	}

	/**
	 * 
	 * @param packId
	 * @param packPrice
	 * @throws IOException 
	 */
	public void updatePromotionSetPrice(Promotion promo, double price) throws IOException {
		promo.setPackPrice(price);;
		ps.updatePromotionSet(promo);
	}

	/**
	 * 
	 * @param packId
	 * @throws IOException 
	 */
	public void deletePromotionSet(Promotion promo) throws IOException {
		ps.deletePromotionSet(promo);
		
	}
	
	

}


