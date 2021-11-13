package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import entity.AlaCarte;
import entity.FoodType;


public class AlaCarteController {


	public void addAlaCarte(String name, String desc, double price, FoodType type) throws IOException {
		AlaCarte.saveAlaCarteItem(name, desc, price, type);
	}


	public static ArrayList<AlaCarte> getAllAlaCarteItems() throws IOException {
		return AlaCarte.getAllAlaCarteItems();
	}

	public void updateAlaCarteName(AlaCarte ac, String name) throws IOException {
		ac.setAlaCarteName(name);;
		AlaCarte.updateAlaCarteItem(ac);
	}

	public void updateAlaCarteDesc(AlaCarte ac, String desc) throws IOException {
		ac.setAlaCarteDesc(desc);
		AlaCarte.updateAlaCarteItem(ac);
	}

	public void updateAlaCartePrice(AlaCarte ac, double price) throws IOException {
		ac.setAlaCartePrice(price);
		AlaCarte.updateAlaCarteItem(ac);
	}
	
	public void updateAlaCarteType(AlaCarte ac, FoodType type) throws IOException {
		ac.setFoodType(type);
		AlaCarte.updateAlaCarteItem(ac);
	}
	
	public void deleteAlaCarteItem(AlaCarte a) throws IOException {
		AlaCarte.deleteAlaCarteItem(a);
	}


	//Shifted Methods from Entity
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
