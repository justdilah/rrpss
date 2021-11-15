package controller;

import java.io.IOException;
import java.util.ArrayList;

import entity.AlaCarte;
import entity.Promotion;

/**This class represents the controller of the promotion class
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 2
 *
 */
public class PromotionController {

	/**This method calls savePromotionSetItem method in the entity class to add the promotion item to the promotion
	 * csv
	 * @param name string value for the name of the promotion item
	 * @param allist list of ala carte items
	 * @param desc string value for the description of the promotion item
	 * @param price double value for the price of the promotion item
	 * @throws IOException Display error message if any I/O error found while inserting into the promotion records.
	 */
	public void addPromotionSet(String name, ArrayList<AlaCarte> allist, String desc, double price) throws IOException {
		Promotion.savePromotionSetItem(name, allist, desc, price);
	}

	/**This method calls selectPromotionSetByName method in the entity class to retrieve the promotion item
	 * by the name
	 * @param name string value for the name of the promotion item
	 * @return Promotion object
	 * @throws IOException Display error message if any I/O error found while retrieving from the promotion records.
	 */
	public Promotion getPromotionSetByName(String name) throws IOException {
		return selectPromotionSetByName(name);
	}

	/**This method calls getAllPromotionItems method in the entity class to retrieve all the promotion items
	 * @return list of promotion items
	 * @throws IOException Display error message if any I/O error found while retrieving from the promotion records.
	 */
	public static ArrayList<Promotion> getAllPromotionSets() throws IOException {
		return Promotion.getAllPromotionItems();
	}

	/**This method calls updatePromotionSet method in the entity class to update the name of the promotion item
	 * @param promo Promotion object
	 * @param name string value for the name of the promotion object
	 * @throws IOException Display error message if any I/O error found while inserting into the promotion records.
	 */
	public void updatePromotionSetName(Promotion promo, String name) throws IOException {
		promo.setPackName(name);
		Promotion.updatePromotionSet(promo);
	}

	/**This method calls updatePromotionSet method in the entity class to update the list of ala carte items
	 *  of the promotion item
	 * @param promo Promotion object
	 * @param items list of ala carte items
	 * @throws IOException Display error message if any I/O error found while inserting into the promotion records.
	 */
	public void updateAlaCarteItems(Promotion promo, ArrayList<AlaCarte> items) throws IOException {
		promo.setPackItems(items);
		Promotion.updatePromotionSet(promo);
	}

	/**This method calls updatePromotionSet method in the entity class to update the description of the promotion item
	 * @param promo Promotion object
	 * @param desc string value for the description of the promotion item
	 * @throws IOException Display error message if any I/O error found while inserting into the promotion records.
	 */
	public void updatePromotionSetDesc(Promotion promo, String desc) throws IOException {
		promo.setPackDesc(desc);
		Promotion.updatePromotionSet(promo);

	}


	/**Thie methods calls updatePromotionSet method in the entity class to update the price of the promotion item
	 * @param promo Promotion object
	 * @param price double value for the price of the promotion item
	 * @throws IOException Display error message if any I/O error found while inserting into the promotion records.
	 */
	public void updatePromotionSetPrice(Promotion promo, double price) throws IOException {
		promo.setPackPrice(price);;
		Promotion.updatePromotionSet(promo);
	}


	/**This method calls deletePromotionSet method in the entity class to delete promotion item
	 * @param promo Promotion object
	 * @throws IOException Display error message if any I/O error found while inserting into the promotion records.
	 */
	public void deletePromotionSet(Promotion promo) throws IOException {
		Promotion.deletePromotionSet(promo);

	}

	/**This method calls getAllPromotionSets method in the entity class to select a specific promotion item
	 * by the given id and retrieve it
	 * @param id int value to uniquely identify the promotion item
	 * @return Promotion object
	 * @throws IOException Display error message if any I/O error found while retrieving from the promotion records.
	 */
	public Promotion selectItemById(int id) throws IOException {
		Promotion p = null;
		ArrayList<Promotion> promo = getAllPromotionSets();
		for(Promotion promos: promo){
			if(promos.getPackId() == id)
				p = promos;
		}
		return p;
	}

	/**This method calls getAllPromotionSets method in the entity class to select a specific promotion item
	 * by the given name and retrieve it
	 * @param n string value for the name of the promotion item
	 * @return Promotion object
	 * @throws IOException Display error message if any I/O error found while retrieving from the promotion records.
	 */
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
