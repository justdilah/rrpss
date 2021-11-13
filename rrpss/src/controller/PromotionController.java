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

	
	public void addPromotionSet(String name, ArrayList<AlaCarte> allist, String desc, double price) throws IOException {
		Promotion.savePromotionSetItem(name, allist, desc, price);
	}
	
	public Promotion getPromotionSetByName(String name) throws IOException {
		return selectPromotionSetByName(name);
	}
	
	public static ArrayList<Promotion> getAllPromotionSets() throws IOException {
		return Promotion.getAllPromotionItems();
	}
	

	public void updatePromotionSetName(Promotion promo, String name) throws IOException {
		promo.setPackName(name);
		Promotion.updatePromotionSet(promo);
	}
	
	public void updateAlaCarteItems(Promotion promo, ArrayList<AlaCarte> items) throws IOException {
		promo.setPackItems(items);
		Promotion.updatePromotionSet(promo);
	}


	public void updatePromotionSetDesc(Promotion promo, String desc) throws IOException {
		promo.setPackDesc(desc);
		Promotion.updatePromotionSet(promo);

	}


	public void updatePromotionSetPrice(Promotion promo, double price) throws IOException {
		promo.setPackPrice(price);;
		Promotion.updatePromotionSet(promo);
	}


	public void deletePromotionSet(Promotion promo) throws IOException {
		Promotion.deletePromotionSet(promo);
		
	}

	//Newly Shifted From Entity
	public Promotion selectItemById(int id) throws IOException {
		Promotion p = null;
		ArrayList<Promotion> promo = getAllPromotionSets();
		for(Promotion promos: promo){
			if(promos.getPackId() == id)
				p = promos;
		}
		return p;
	}

	public static Promotion selectPromotionSetByName(String n) throws IOException {
		Promotion p = null;
		ArrayList<Promotion> promo = getAllPromotionSets();
		for(Promotion promos: promo){
			if(promos.getPackName().equals(n))
				p = promos;
		}
		return p;
	}



}


