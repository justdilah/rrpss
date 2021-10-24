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
		int last = ps.getAllPromotionItems().size();
		int id = ps.getAllPromotionItems().get(last-1).getPackId()+ 1;
		String packitems ="";
		for(int i=0;i<pslist.size();i++) {
			System.out.println(pslist.get(i).getFoodName());
			if(i == pslist.size()-1) {
				packitems += pslist.get(i).getFoodName();
			} else {
				packitems += pslist.get(i).getFoodName() + "@";
			}	
		}
		
		String item = id + "," + name + "," + packitems + "," + desc +  "," +  p;
		List l = new ArrayList();
		l.add(item);
		ps.saveFoodItem(l);
		
	}
	
	public PromotionSet getPromotionSetByName(String n) throws FileNotFoundException {
		return ps.selectPromotionSetByName(n);
	}
	
	
//	public void addPromoItem(String packName, String packDesc, double packPrice) {
//		int last = m.getAllMenuItems().size();
//		int id = m.getAllMenuItems().get(last-1).getFoodId()+ 1;
//		String newft = menuType.toString().replace("_"," ");
//		String foodItem = id + "," + menuName + "," + menuDesc +  "," +  menuPrice+ "," +newft;
//		List l = new ArrayList();
//		l.add(foodItem)
//		// TODO - implement PromotionController.addPromoItem
//		throw new UnsupportedOperationException();
//	}

	public ArrayList<PromotionSet> getAllPromotionSets() throws FileNotFoundException {
		return ps.getAllPromotionItems();
	}
	
//	public ArrayList<>
	/**
	 * 
	 * @param packId
	 */
	public void getPromoItem(int packId) {
		// TODO - implement PromotionController.getPromoItem
		throw new UnsupportedOperationException();
	}

	public void getAllPromoItem() {
		// TODO - implement PromotionController.getAllPromoItem
		
	}

	/**
	 * 
	 * @param packId
	 * @param packName
	 * @throws IOException 
	 */
	public void updatePromoItemName(PromotionSet p, String packName) throws IOException {
		p.setPackName(packName);
		ps.updateFoodItem(p);
	}
	
	public void updateAlaCarteItems(PromotionSet p, ArrayList<AlaCarte> items) throws IOException {
		p.setPackItem(items);
		ps.updateFoodItem(p);
	}

	/**
	 * 
	 * @param packId
	 * @param packDesc
	 * @throws IOException 
	 */
	public void updatePromoItemDesc(PromotionSet p, String packDesc) throws IOException {
		p.setPackDesc(packDesc);
		ps.updateFoodItem(p);

	}

	/**
	 * 
	 * @param packId
	 * @param packPrice
	 * @throws IOException 
	 */
	public void updatePromoPrice(PromotionSet p, double packPrice) throws IOException {
		p.setPackPrice(packPrice);;
		ps.updateFoodItem(p);
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


