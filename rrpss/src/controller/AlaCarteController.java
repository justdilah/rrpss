package controller;

import java.io.IOException;
import java.util.ArrayList;

import entity.AlaCarte;
import entity.FoodType;


/**This class represents the controller for the Ala Carte class
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class AlaCarteController {


	/**This methods calls the saveAlaCarteItem method in the entity class to add an Ala Carte item
	 * @param name string value for the name of the Ala Carte item
	 * @param desc string value for the description of the Ala Carte item
	 * @param price double value for the price of the Ala Carte item
	 * @param type FoodType value for the food type of Ala Carte item
	 * @throws IOException Display error message if any I/O error found while inserting into the ala carte records.
	 */
	public void addAlaCarte(String name, String desc, double price, FoodType type) throws IOException {
		AlaCarte.saveAlaCarteItem(name, desc, price, type);
	}


	/**This method calls the getAllAlaCarteItems method in the entity class
	 * to retrieve all the Ala Carte items
	 * @return Array list of the Ala Carte items
	 * @throws IOException Display error message if any I/O error found while retrieving from the ala carte records.
	 */
	public static ArrayList<AlaCarte> getAllAlaCarteItems() throws IOException {
		return AlaCarte.getAllAlaCarteItems();
	}

	/**This method calls the updateAlaCarteName method in the entity class
	 * to update the name of the Ala Carte item with the given name
	 * @param ac Ala Carte object
	 * @param name string value for the name of the Ala Carte item
	 * @throws IOException Display error message if any I/O error found while inserting into the ala carte records.
	 */
	public void updateAlaCarteName(AlaCarte ac, String name) throws IOException {
		ac.setAlaCarteName(name);;
		AlaCarte.updateAlaCarteItem(ac);
	}

	/**This method calls the updateAlaCarteItem method in the entity class to
	 * update the description of the Ala Carte item with the given description
	 * @param ac Ala Carte object
	 * @param desc string value for the description of the Ala Carte item
	 * @throws IOException Display error message if any I/O error found while inserting into the ala carte records.
	 */
	public void updateAlaCarteDesc(AlaCarte ac, String desc) throws IOException {
		ac.setAlaCarteDesc(desc);
		AlaCarte.updateAlaCarteItem(ac);
	}

	/**This method calls the updateAlaCartePrice method in the entity class to update
	 *  the price of the Ala Carte item with the given price
	 * @param ac Ala Carte object
	 * @param price double value for the price of the Ala Carte item
	 * @throws IOException Display error message if any I/O error found while inserting into the ala carte records.
	 */
	public void updateAlaCartePrice(AlaCarte ac, double price) throws IOException {
		ac.setAlaCartePrice(price);
		AlaCarte.updateAlaCarteItem(ac);
	}

	/**This method calls the updateAlaCarteType method in the entity class
	 * to update the food type of the Ala Carte item with the given food type
	 * @param ac Ala Carte object
	 * @param type FoodType object for the type of the Ala Carte item
	 * @throws IOException Display error message if any I/O error found while inserting into the ala carte records.
	 */
	public void updateAlaCarteType(AlaCarte ac, FoodType type) throws IOException {
		ac.setFoodType(type);
		AlaCarte.updateAlaCarteItem(ac);
	}

	/**This method calls the deleteAlaCarteItem method in the entity class
	 * to delete the Ala Carte item
	 * @param a Ala Carte object
	 * @throws IOException Display error message if any I/O error found while inserting into the ala carte records.
	 */
	public void deleteAlaCarteItem(AlaCarte a) throws IOException {
		AlaCarte.deleteAlaCarteItem(a);
	}


	/**This method calls getAlaCarteByName method in the entity class
	 * to retrieve an Ala Carte item by its name
	 * @param name string value for the name of the Ala Carte item
	 * @return Ala Carte object
	 * @throws IOException Display error message if any I/O error found while retrieving from the ala carte records.
	 */
	public static AlaCarte getAlaCarteByName(String name) throws IOException {
		ArrayList<AlaCarte> alacarte = getAllAlaCarteItems();
		AlaCarte a = null;
		for(AlaCarte ala: alacarte){
			if(ala.getAlaCarteName().equals(name)) {
				a = ala;
			}
		}
		return a;
	}

	/**This method calls the getAllAlaCarteItems method in the retrieves an Ala Carte item by its id
	 * @param id int value that uniquely identifies the Ala Carte item
	 * @return Ala Carte object
	 * @throws IOException Display error message if any I/O error found while retrieving from the ala carte records.
	 */
	public AlaCarte selectAlaCarteById(int id) throws IOException {
		ArrayList<AlaCarte> alacarte = getAllAlaCarteItems();
		AlaCarte a = null;
		for(AlaCarte ala: alacarte){
			if(ala.getAlaCarteId() == id) {
				a = ala;
			}
		}
		return a;
	}
}