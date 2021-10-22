package boundary;

import java.util.Scanner;

public class PromotionForm {
	
	public void displayForm() {
		System.out.println("=================================");
        System.out.println("\t Menu Options ");
		System.out.println("=================================");
		System.out.println("1) Display Menu ");
		System.out.println("2) Add Food Item ");
		System.out.println("3) Edit Food Description ");
		System.out.println("4) Delete Food Item ");
		System.out.println("5) Back");
		
		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();
		
		switch(choice) {
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		}
	}
}
