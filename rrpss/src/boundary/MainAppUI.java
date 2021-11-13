package boundary;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import controller.MainAppController;

/**
 * This class represents the Main Application of the restaurant.
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class MainAppUI {

	/**
	 * This method is the main method in which the program will first run
	 * @param args
	 * @throws IOException Display error message if any I/O error found while retrieving the various records.
	 */
	public static void main(String[] args) throws IOException
	{
		print();
	}

	/**
	 * This method display a list of options for the staff to select which function he/she wants to perform
	 * @throws IOException Display error message if any I/O error found while retrieving the various records.
	 */
	public static void print() throws IOException
	{
		MainAppController mc = new MainAppController();

		int choice = -1;
		Scanner sc = new Scanner(System.in);

		do {

			mc.checkReservation();

			System.out.println("=================================");
			System.out.println("\t Welcome to RRPSS");
			System.out.println("=================================");
			System.out.println("(1) Reservation");
			System.out.println("(2) Orders");
			System.out.println("(3) Sales Revenue Report");
			System.out.println("(4) Menu");
			System.out.println("(5) Promotion");
			System.out.println("(6) Payment");
			System.out.println("(7) Exit");


			do {
				try {
					System.out.print("Please enter your choice : ");
					choice = sc.nextInt();

				}catch(InputMismatchException e)
				{
					System.out.println("=================================");
					System.out.println("Invalid Entry has been entered. ");
					System.out.println("Please enter numbers only. ");
					System.out.println("=================================");
					System.out.println("\t Welcome to RRPSS");
					System.out.println("=================================");
					System.out.println("(1) Reservation");
					System.out.println("(2) Orders");
					System.out.println("(3) Sales Revenue Report");
					System.out.println("(4) Menu");
					System.out.println("(5) Promotion");
					System.out.println("(6) Exit");
				}
				sc.nextLine();
			}while (choice == -1);


			switch (choice) {

				case 1:
					mc.getReservationForm();
					break;

				case 2:
					mc.getOrderForm();
					break;

				case 3:
					mc.getSalesRevenueMonthForm();
					break;

				case 4:
					mc.getAlaCarteForm();
					break;

				case 5:
					mc.getPromotionForm();
					break;

				case 6:
					mc.getInvoiceForm();
					break;

				case 7:
					break;

				default:
					System.out.println("=========================================================");
					System.out.println("\tInvalid input. Please enter again!");
					System.out.println("=========================================================");
					System.out.println("(1) Reservation");
					System.out.println("(2) Orders ");
					System.out.println("(3) Sales Revenue Report ");
					System.out.println("(4) Menu");
					System.out.println("(5) Promotion");
					System.out.println("(6) Exit ");
					choice = sc.nextInt();
			}
		}while (choice <1||choice>6);
	}

}