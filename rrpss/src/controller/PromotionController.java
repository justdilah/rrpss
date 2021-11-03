package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.AlaCarte;
import entity.FoodType;
import entity.PromotionSet;

public class PromotionController {
	
	private static final String filename = "DataSet/PromotionSet.csv";
	PromotionSet ps = new PromotionSet();
	/**
	 * 
	 * @param packName
	 * @param packDesc
	 * @param packPrice
	 */
	
	public void addPromotionSet(String name, ArrayList<AlaCarte> pslist, String desc, double p) throws IOException {
		ps.savePromotionSetItem(name, pslist, desc, p);
	}
	
	public PromotionSet getPromotionSetByName(String n) throws FileNotFoundException {
		return ps.selectPromotionSetByName(n);
	}
	
	public ArrayList<PromotionSet> getAllPromotionSets() throws FileNotFoundException {
		return ps.getAllPromotionItems();
	}
	
	/**
	 * 
	 * @param packId
	 * @param packName
	 * @throws IOException 
	 */
	public void updatePromoItemName(PromotionSet p, String packName) throws IOException {
		p.setPackName(packName);
		ps.updatePromotionSet(p);
	}
	
	public void updateAlaCarteItems(PromotionSet p, ArrayList<AlaCarte> items) throws IOException {
		p.setPackItem(items);
		ps.updatePromotionSet(p);
	}

	/**
	 * 
	 * @param packId
	 * @param packDesc
	 * @throws IOException 
	 */
	public void updatePromoItemDesc(PromotionSet p, String packDesc) throws IOException {
		p.setPackDesc(packDesc);
		ps.updatePromotionSet(p);

	}

	/**
	 * 
	 * @param packId
	 * @param packPrice
	 * @throws IOException 
	 */
	public void updatePromoPrice(PromotionSet p, double packPrice) throws IOException {
		p.setPackPrice(packPrice);;
		ps.updatePromotionSet(p);
	}

	/**
	 * 
	 * @param packId
	 * @throws IOException 
	 */
	public void deletePromoSet(PromotionSet s) throws IOException {
		// TODO - implement PromotionController.deletePromoItem
		ps.deletePromotionSet(s);
		
	}
	
	

}


