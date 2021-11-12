package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.AlaCarte;
import entity.FoodType;


public class AlaCarteController {
	
	AlaCarte ala = new AlaCarte();
	
	public void addAlaCarte(String name, String desc, double price, FoodType type) throws IOException {
		ala.saveFoodItem(name, desc, price, type);
	}
	
	public AlaCarte getAlaCarteByName(String name) throws FileNotFoundException {
		return ala.selectFoodByName(name);
	}

	public ArrayList<AlaCarte> getAllAlaCarteItems() throws FileNotFoundException {
		return ala.getAllAlaCarteItems();
	}

	public void updateAlaCarteName(AlaCarte ac, String name) throws IOException {
		ac.setAlaCarteName(name);;
		ala.updateAlaCarteItem(ac);
	}

	public void updateAlaCarteDesc(AlaCarte ac, String desc) throws IOException {
		ac.setAlaCarteDesc(desc);
		ala.updateAlaCarteItem(ac);
	}

	public void updateAlaCartePrice(AlaCarte ac, double price) throws IOException {
		ac.setAlaCartePrice(price);
		ala.updateAlaCarteItem(ac);
	}
	
	public void updateAlaCarteType(AlaCarte ac, FoodType type) throws IOException {
		ac.setFoodType(type);
		ala.updateAlaCarteItem(ac);
	}
	
	public void deleteAlaCarteItem(AlaCarte a) throws IOException {
		ala.deleteFoodItem(a);
	}

}
