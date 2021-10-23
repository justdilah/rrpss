package controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import boundary.AlaCarteForm;
import boundary.PromotionForm;

public class MainAppController {
	
	// To navigate to different forms
	public void getMenuForm() throws IOException {
		AlaCarteForm mf = new AlaCarteForm();
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
