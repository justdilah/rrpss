package controller;

import java.io.FileNotFoundException;

import boundary.MenuForm;
import boundary.PromotionForm;

public class MainController {
	
	// To navigate to different forms
	public void getMenuForm() throws FileNotFoundException {
		MenuForm mf = new MenuForm();
		mf.displayOption();
	}
	
	public void getPromotionForm() {
		PromotionForm pf = new PromotionForm();
		pf.displayOption(); 
	}
	
	public void getOrderForm() {
	}
	
	public void getReservationForm() {
		
	}
}
