package controller;

import boundary.MenuForm;
import boundary.PromotionForm;

public class MainController {
	
	// To navigate to different forms
	public void getMenuForm() {
		MenuForm mf = new MenuForm();
		mf.displayForm();
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
