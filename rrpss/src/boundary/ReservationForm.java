package boundary;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

import controller.CustomerController;
import controller.ResTableController;
import controller.ReservationController;
import controller.StaffController;
import entity.Customer;
import entity.Reservation;
import entity.Staff;
import entity.Table;

public class ReservationForm {

	Scanner sc = new Scanner(System.in);
	ReservationController rc = new ReservationController();
	ResTableController rtc = new ResTableController();
	StaffController stc = new StaffController();

	String name, contact, date, time;
	int pax;


	public void checkReservation() throws IOException
	{
		//detect and delete past reservation
		rc.deletePastReservation(rtc.deletePastReservationTable());
		//detect and delete late customer reservation
		rc.deleteLateReservation(rtc.deleteLateComerTable());

	}

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

			print("Please enter your choice: ");

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
		int inputstaffid=0;

		ResTableController rt = new ResTableController();
		rt.createCapTable();

		int limit=3;

		print("Enter Staff ID: ");
		do{
			try{
				inputstaffid = Integer.parseInt(sc.nextLine());
				Staff staff;
				staff = stc.getStaffById(inputstaffid);
				if(staff == null){
					if(limit!=1) {
						print("Entered wrong staffId, please try again.");
						print("Enter staff ID: ");
					}
					limit--;
				}
				else{
					break;
				}
			}catch(NumberFormatException e){
				if (limit!=1)
					print("Staff ID is of invalid format, please enter a valid Staff ID: ");
				else
					print("You have exceeded 3 tries. Action Aborted. ");
				limit--;
			}

		}while(limit!=0);

		if (limit == 0){
			print("You have exceeded 3 tries. Action Aborted. ");
			MainAppUI.print();
		}

		int staffid = stc.getStaffById(inputstaffid).getStaffId();



		print("==================================");
		print("\t Make Reservation");
		print("==================================");


		print("Enter Customer Contact: (e.g. 81234567) ");
		contact = sc.nextLine();

		CustomerController c1 = new CustomerController();
		Customer cc1 = new Customer();
		cc1 = c1.getCustByContact(contact);

		if (cc1==null)
		{
			print("Enter Customer Name: ");
			name = sc.nextLine();

			c1.saveCustomer(name, contact);
		}
		else
		{
			name = cc1.getPersName();
		}


		print("Enter Date of Reservation (yyyy-MM-dd) format: ");
		date = sc.nextLine();

		//	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		//	LocalDate finaldate = LocalDate.parse(date,formatter);
		LocalDate finaldate = checkDate(date);


		print("Enter Time of Reservation (HH:mm) format:");
		time = sc.nextLine();

		//	DateTimeFormatter tformatter = DateTimeFormatter.ofPattern("HH:mm");
		//	LocalTime finaltime = LocalTime.parse(time, tformatter);

		LocalTime finaltime = checkTime(time);



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

		int custid = c1.getCustByContact(contact).getCustId();


		try {
			rc.addReservation(name, contact, pax, custid, staffid);

			int resid = rc.getReservationByContact(contact).getResId();
			Table t = rt.reserveTable(finaldate, finaltime, pax, resid);


			if(t != null)
			{
				print("==================================");
				print( contact +  " added successfully");
				print("Table " + t.getTableNo() +  " has been assigned successfully");
				print("==================================");
				displayOption();
			}
			else
			{
				print("==================================");
				print("No table found");
				print("==================================");
				displayOption();
			}

		} catch (Exception ex) {
			print("==================================");
			print("Unsuccessful. Please try again");
			//print(ex.toString());
			print("==================================");
			insertReservation();
		}

	}


	public LocalDate checkDate(String date)
	{
		LocalDate local = null;
		String tempdate;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		if (isDateValid(date))
		{
			LocalDate inputdate = LocalDate.parse(date,formatter);

			String stringdate = LocalDate.now().toString();
			LocalDate convertdate = LocalDate.parse(stringdate,formatter);


			LocalDate convertdateafter = convertdate.plusDays(14);

			local = inputdate;

			if (inputdate.isBefore(convertdate))
			{
				print("Reservation can only be done in advance.");
				print("Re-enter Date of Reservation (yyyy-MM-dd) format: ");
				tempdate = sc.nextLine();

				checkDate(tempdate);
			}
			else if (inputdate.isAfter(convertdateafter))
			{
				print("Reservation only can be done 2 weeks in advance");

				print("Re-enter Date of Reservation (yyyy-MM-dd) format: ");
				tempdate = sc.nextLine();

				checkDate(tempdate);
			}
		}
		else
		{
			print("Invalid Date. Re-enter Date of Reservation (yyyy-MM-dd) format:");
			tempdate = sc.nextLine();

			local = checkDate(tempdate);
		}



		return local;

	}

	public static boolean isDateValid(String value) {
		try {
			String[] date = value.split("-");
			return  Integer.parseInt(date[0]) >= 2021 && Integer.parseInt(date[1]) <= 12 && Integer.parseInt(date[2]) <= 30;
		} catch (Exception e) {
			return false;
		}
	}

	public LocalTime checkTime(String time)
	{
		LocalTime local = null;
		String temptime;

		DateTimeFormatter tformatter = DateTimeFormatter.ofPattern("HH:mm");

		if (isTimeValid(time))
		{
			LocalTime inputtime = LocalTime.parse(time,tformatter);
			return inputtime;
		}
		else
		{
			print("Invalid Time. Re-enter Time of Reservation (HH:mm) format:");
			temptime = sc.nextLine();

			local = checkTime(temptime);
		}
		return local;
	}


	public static boolean isTimeValid(String value) {
		try {
			String[] time = value.split(":");
			return  Integer.parseInt(time[0]) < 24 && Integer.parseInt(time[1]) < 60;
		} catch (Exception e) {
			return false;
		}
	}


	public void displayReservation() throws IOException
	{
		print("==================================");
		print("\t Reservation ");
		print("==================================");
		print("Enter customer contact to retrieve details: ");
		print("==================================");
		String choice = sc.next();

		Reservation r1 = rc.getReservationByContact(choice);

		if (r1.getResId() == 0)
			print("Reservation does not exist");
		else
		{
			print("==================================================");
			print("");
			System.out.println("ResId: " +  r1.getResId());

			// get custid and staffid
			CustomerController c = new CustomerController();
			int cid = c.getCustByContact(r1.getResContact()).getCustId();

			Staff s = new Staff();
			s = s.getStaffById(r1.getStaff().getStaffId());
			int sid = s.getStaffId();
			String name = s.getPersName();

			ResTableController rt = new ResTableController();
			Table t = rt.getTableByResId(r1.getResId());
			int tid = t.getTableNo();

			print("Customer [" + cid +  "] Name: " + r1.getResName());
			print("No. of Pax: " + r1.getResNoPax());
			print("Contact No: " + r1.getResContact());
			print("Reservation Date: " + t.getTableDate().toString().substring(8, 10) + "-" + t.getTableDate().toString().substring(5,7) + "-" + t.getTableDate().toString().substring(0, 4));
			print("Reservation Time: " + t.getTableTime() + " HR");
			print("Order Taken by Staff: [" + sid + ": " + name + "] ");
			print("Allocated: [Table " + tid + "]");
			print("Reservation will be auto-cancelled at " + t.getTableTime().plusMinutes(10) + " HR");
			print("==================================================");
		}
	}


	public void updateReservation() throws IOException
	{
		Reservation r = new Reservation();
		Table t = new Table();

		print("==================================");
		print("\t Reservation ");
		print("==================================");
		print("Enter customer contact to update reservation: ");
		print("==================================");
		String stringchoice = sc.next();

		r = rc.getReservationByContact(stringchoice);

		t = t.getTableByResId(r.getResId());

		int p1 = r.getResNoPax();

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
					updateReservationPax(r, t);
					break;
				case 2:
					updateReservationDate(r, t, p1);
					break;
				case 3:
					updateReservationTime(r, t, p1);
					break;
				default:
					break;
			}
		}
	}

	public void updateReservationPax(Reservation r, Table t) throws IOException
	{
		ResTableController rt = new ResTableController();
		ReservationController rc = new ReservationController();

		rt.createCapTable();

		print("Enter updated pax number: ");
		int pax1 = sc.nextInt();


		int resid = r.getResId();

		try
		{

			// try catch for insert into reservation
			try
			{
				rc.updateReservationPax(r, pax1);
				print("==================================");
				print("loading to search for table");
				print("==================================");
			}
			catch (Exception ex)
			{
				print("==================================");
				print("Unsuccessful. Please try again");
				print("==================================");
				updateReservationPax(r, t);
			}


			LocalTime converttime = rt.getTableByResId(resid).getTableTime();
			LocalDate convertdate = rt.getTableByResId(resid).getTableDate();
			int finalpax = rc.getResById(resid).getResNoPax();


			// pass in date time and pax to check
			int new_tno = -1;
			new_tno = rt.checkAvailTable(convertdate, converttime, finalpax, resid).getTableNo();

			if (new_tno != -1)
			{
				rt.updateTablePax(t, resid, new_tno, finalpax);
				print("==================================");
				print(" data updated successfully");
				print("==================================");
			}
			else
			{
				print("=====================================");
				print("No table found, Update not completed.");
				print("=====================================");
			}
			displayOption();

		} catch (Exception ex) {
			print("==================================");
			print("Unsuccessful. Please try again");
			print("==================================");
			updateReservationPax(r, t);
		}
	}

	public void updateReservationDate(Reservation r, Table t, int p1) throws IOException
	{
		ResTableController rt = new ResTableController();
		rt.createCapTable();

		print("Enter new date (yyyy-MM-dd): ");
		String date = sc.next();
		LocalDate finaldate = checkDate(date);

		int resid = r.getResId();


		try {
//
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//			LocalDate convertdate = LocalDate.parse(date,formatter);

			LocalTime converttime = rt.getTableByResId(resid).getTableTime();


			// pass in time and pax to check
			int new_tno = -1;
			new_tno = rt.checkAvailTable(finaldate, converttime, p1, resid).getTableNo();

			if (new_tno != -1)
			{
				rt.updateTableDate(t, resid, new_tno, date);
				print("==================================");
				print(" data updated successfully");
				print("==================================");
			}
			else
			{
				print("=====================================");
				print("No table found, Update not completed.");
				print("=====================================");
			}
			displayOption();

		} catch (Exception ex) {
			print("==================================");
			print("Unsuccessful. Please try again");
			print("==================================");
			updateReservationDate(r, t, p1);
		}
	}

	public void updateReservationTime(Reservation r, Table t, int p1) throws IOException
	{
		ResTableController rt = new ResTableController();
		rt.createCapTable();

		print("Enter new time (HH:mm): ");
		String time = sc.next();
		LocalTime finaltime = checkTime(time);

		int resid = r.getResId();

		try {
//			DateTimeFormatter tformatter = DateTimeFormatter.ofPattern("HH:mm");
//			LocalTime converttime = LocalTime.parse(time,tformatter);

			LocalDate convertdate = rt.getTableByResId(resid).getTableDate();

			// pass in time and pax to check
			int new_tno = -1;
			new_tno = rt.checkAvailTable(convertdate, finaltime, p1, resid).getTableNo();

			if (new_tno != -1)
			{
				rt.updateTableTime(t, resid, new_tno, time);
				print("==================================");
				print(" data updated successfully");
				print("==================================");
			}
			else
			{
				print("=====================================");
				print("No table found, Update not completed.");
				print("=====================================");
			}
			displayOption();

		} catch (Exception ex) {
			print("==================================");
			print("Unsuccessful. Please try again");
			print("==================================");
			updateReservationTime(r, t, p1);
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