package boundary;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

import controller.ReservationController;
import entity.Customer;
import entity.Reservation;
import entity.Staff;
import entity.Table;

public class ReservationForm {
	
	Scanner sc = new Scanner(System.in);
	ReservationController rc = new ReservationController();
	
	String name, contact, date, time, gender, email, address;
	int pax;

	public void displayOption() throws IOException 
	{
		int choice = -1;
		
		do {
			print("=================================");
			print("\t Reservations");
			print("=================================");
			print("(1) Make Reservation");
			print("(2) Display Reservation");
			print("(3) Edit Reservation");
			print("(4) Delete Reservation");
			print("(5) Back");
			
			do {
				try {
					choice = sc.nextInt();
					
				}catch(InputMismatchException e) {
					
					print("=================================");
					print("Invalid Entry has been entered. ");
					print("Please enter numbers only. ");
					print("=================================");
					print("\t Reservations");
					print("=================================");
					print("(1) Make Reservation");
					print("(2) Display Reservation");
					print("(3) Edit Reservation");
					print("(4) Delete Reservation");
					print("(5) Back");
				}
				sc.nextLine();
				
			}while (choice == -1);
			
			switch(choice) {
			
			case 1:
				insertReservation();
				break;
			case 2:
				displayReservation();
				break;
			case 3:
				updateReservation(); 
				break;
			case 4:
				deleteReservation();
				break;
			case 5:
				MainAppUI.print();
				break;
			default:
		    	print("=========================================================");
				print("\tInvalid input. Please enter again!");
		    	print("=========================================================");
			}
		}while (choice<1 || choice>4);
	}
	
	
	public void insertReservation() throws IOException 
	{
		int limit=3;
		
		print("Enter staff ID: ");
		int inputstaffid = Integer.parseInt(sc.nextLine());
		
		Staff staff = new Staff();
		staff = staff.getStaffById(inputstaffid);
		
		if (staff==null)
		{
			while(limit>0)
			{
				print("Entered wrong staffId, please try again.");
				print("Enter staff ID: ");
				inputstaffid = Integer.parseInt(sc.nextLine());
				
				staff = new Staff();
				staff = staff.getStaffById(inputstaffid);
				
				if (staff==null)
					limit--;
				else
					break;
			}
			
			if (limit==0)
				print("you have exceeded 3 tries. action aborted.");
				MainAppUI.print();
				return;

		}
		
		int staffid = staff.getStaffById(inputstaffid).getStaffId();
		
		print("==================================");
		print("\t Make Reservation");
		print("==================================");
		
		
		print("Enter Customer Contact: (e.g. 81234567) ");
		contact = sc.nextLine();
		
		Customer c1 = new Customer();
		c1 = c1.getCustByContact(contact);
		
		if (c1==null)
		{
			print("Enter Customer Name: ");
			name = sc.nextLine();	
			
			Customer c2 = new Customer();
			c2.saveCustomer(name, contact);
		}
		
		name = c1.getPersName();
		
		
		
		print("Enter Date of Reservation (yyyy-MM-dd) format: ");
		date = sc.nextLine();
		

		print("Enter Time of Reservation (HH:mm) format:");
		time = sc.nextLine();

		
		// to check if the reservation.csv have the same date and time +-2hr is occupied anot
		
		// else cannot insert 
		
		
		

		Customer c = new Customer();
		int custid = c.getCustByContact(contact).getCustId();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate convertdate = LocalDate.parse(date,formatter);
		DateTimeFormatter tformatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime converttime = LocalTime.parse(time,tformatter);
		
		
		
		
		
		
		print("Enter Number of Pax: (Max: 10 pax) ");
		pax = Integer.parseInt(sc.nextLine());
		
		Boolean temp;
		if (pax <= 0)
		{
			temp =true;
			while(temp)
			{
				print("Enter Number of Pax: ");
				pax = Integer.parseInt(sc.nextLine());
				
				if (pax >=1)
					temp =false;
			}
		}
		else if (pax > 10)
		{
			temp =true;
			while(temp)
			{
				print("Exceeded No. of Pax (10)");
				print("Enter Number of Pax: ");
				pax = Integer.parseInt(sc.nextLine());
				
				if (pax >=1)
					temp =false;
			}
		}	
			
//		// check for table 
//		Table t = new Table();
//		t.checkTableAvail(pax);
//		
//		if (t==null)
//		{
//			print("Booking is full. ");
//		}
		
		
		
		


		try {
			rc.addReservation(name, contact, pax, convertdate, converttime, custid, staffid);
			print("==================================");
			print( contact +  " added successfully");
			print("==================================");
			displayOption();
			
		} catch (Exception ex) {
			print("==================================");
			print("Unsuccessful. Please try again");
			print("==================================");
			insertReservation();
		}
		
		// table available ? 
		
		// get staff id 

	}

	public void displayReservation() throws IOException
	{
		print("==================================");
		print("\t Reservation ");
		print("==================================");
		print("Enter customer contact to retrieve details: ");
		print("==================================");
		String choice = sc.next();
		
		Reservation r1 = new Reservation();
		r1 = rc.getReservationByContact(choice);	
		
		if (r1.getResId() == 0)
			print("Reservation does not exist");
		else 
		{
			print("==================================================");
			print("");
			System.out.println("ResId: " +  r1.getResId());

			// get custid and staffid
			Customer c = new Customer();
			int cid = c.getCustByContact(r1.getResContact()).getCustId();
			
			Staff s = new Staff();
			
			System.out.println("Customer [" + cid +  "] Name: " + r1.getResName());
			System.out.println("No. of Pax: " + r1.getResNoPax());
			System.out.println("Contact No: " + r1.getResContact());
			System.out.println("Reservation Date: " + r1.getResDate().toString().substring(8, 10) + "-" + r1.getResDate().toString().substring(5,7) + "-" + r1.getResDate().toString().substring(0, 4));
			System.out.println("Reservation Time: " + r1.getResTime() + " HR");
			System.out.println(": []"  + "");
//			print("Order Taken by: [" + sid + "], ");
			print("==================================================");
		}
	}
	
	
	public void updateReservation() throws IOException
	{
		Reservation r = new Reservation();
		
		print("==================================");
		print("\t Reservation ");
		print("==================================");
		print("Enter customer contact to update reservation: ");
		print("==================================");
		String stringchoice = sc.next();
	
		r = rc.getReservationByContact(stringchoice);	
		
		if (r.getResId() == 0)
			print("Reservation does not exist");
		else
		{
			print("=========================================================");
			print("\t Select which field to update");
	    	print("=========================================================");
			print("(1) Pax");
			print("(2) Date");
			print("(3) Time");
			int choice = sc.nextInt();

			switch(choice) {
			case 1:
				updateReservationPax(r);
				break;
			case 2:
				updateReservationDate(r);
				break;
			case 3:
				updateReservationTime(r);
				break;
			default:
		    	break;
			}	
		}
	}

	public void updateReservationPax(Reservation r) throws IOException
	{
		print("Enter updated pax number: ");
        pax = sc.nextInt();
        
		try {
			rc.updateReservationPax(r, pax);
			print("==================================");
			print(" data uppdated successfully");
			print("==================================");
			displayOption();
			
		} catch (Exception ex) {
			print("==================================");
			print("Unsuccessful. Please try again");
			print("==================================");
			rc.updateReservationPax(r, pax);
		}
	}
	
	public void updateReservationDate(Reservation r) throws IOException
	{
		print("Enter new date (yyyy-MM-dd): ");
        String date = sc.next();
        
		try {
			rc.updateReservationDate(r, date);
			print("==================================");
			print(" data uppdated successfully");
			print("==================================");
			displayOption();
			
		} catch (Exception ex) {
			print("==================================");
			print("Unsuccessful. Please try again");
			print("==================================");
			rc.updateReservationDate(r, date);
		}
	}
	
	public void updateReservationTime(Reservation r) throws IOException
	{
		print("Enter new time (HH:mm): ");
        String time = sc.next();
        
		try {
			rc.updateReservationTime(r, time);	
			print("==================================");
			print(" data uppdated successfully");
			print("==================================");
			displayOption();
			
		} catch (Exception ex) {
			print("==================================");
			print("Unsuccessful. Please try again");
			print("==================================");
			rc.updateReservationTime(r, time);
		}
	}

	public void deleteReservation() throws IOException
	{
		print("================================================");
        print("Enter customer contact to remove his/her appointment: ");
		print("================================================");
		
		String choice = sc.nextLine();
		try {
			Reservation r = rc.getReservationByContact(choice);
			rc.removeReservation(r);
			print("==================================");
			print("Remove successfully");
			print("==================================");
			displayOption();
		} catch (Exception ex) {
			print("==================================");
			print("Unsuccessful. Please try again");
			print("==================================");
			deleteReservation();
		}	
	}

	public void print(String message)
	{
		System.out.println(message);
	}

}