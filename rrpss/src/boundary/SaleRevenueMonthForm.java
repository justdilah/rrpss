package boundary;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import controller.SaleRevenueMonthController;
import entity.OrderItem;
import entity.Invoice;

public class SaleRevenueMonthForm {
	
	SaleRevenueMonthController sr = new SaleRevenueMonthController();
	
	Scanner sc = new Scanner(System.in);

	DecimalFormat df = new DecimalFormat("0.00");

	//SHOW ORDER
	public void displayOption() throws IOException {

		int monthchosen=-1,choice=-1;
		ArrayList<ArrayList<String>>store = new ArrayList<>();

		print("===========================================================");
        print("\t\t Monthly Revenue Report ");
		print("===========================================================");
		print("Guide: January = 1 ... December = 12");
		print("Which month's revenue report would you like to print?");
		print("Enter 0 to return to the Main Menu");
		print("Enter Month: ");
		
		do{
			try{
				monthchosen = Integer.parseInt(sc.nextLine());
				if(monthchosen<0 || monthchosen > 12)
					print("Month does not exist, please enter a valid month: ");
				
			}catch(NumberFormatException e){
				print("Month chosen is of invalid format, please enter a valid month: ");
			}
		}while(monthchosen<0 || monthchosen >12);
		
		if(monthchosen ==0)
			MainAppUI.print();
		
		store = sr.generateMonthRevenue(monthchosen);
		printMonthlyRevenueReport(store,monthchosen);


		print("Enter 0 to return to the Main Menu");
		do{
			try {
				choice = Integer.parseInt(sc.nextLine());
				if (choice != 0)
					print("Enter 0 to return to the Main Menu");
			}catch(NumberFormatException e){
				print("Enter 0 to return to the Main Menu");
			}
		}while(choice!=0);
		MainAppUI.print();
	}

	private void printMonthlyRevenueReport(ArrayList<ArrayList<String>>store, int monthchosen)
	{
		String format = "%-25s%s%n";
		String format1 = "%-26s %-25s%s%n";
		String format2 = "%-26s%-25s%-9s%s%n";
		String format3 = "%-18s%-46s%s%n";

		String month;
		double sum_order = 0;

		month = new DateFormatSymbols().getMonths()[monthchosen-1];

		ArrayList<String> invoiceList_totalSt = store.get(0);

		ArrayList<Double> invoiceList_total = new ArrayList<>();
		for(String s: invoiceList_totalSt)
			invoiceList_total.add(Double.parseDouble(s));

		ArrayList<String> OIList_OrderItemName = store.get(1);

		ArrayList<String> OIList_qtySt = store.get(2);

		ArrayList<Integer> OIList_qty = new ArrayList<>();

		for(String s: OIList_qtySt)
			OIList_qty.add(Integer.parseInt(s));

		ArrayList<String> OIList_price_totalSt = store.get(3);

		ArrayList<Double> OIList_price = new ArrayList<>();
		for(String s: OIList_price_totalSt)
			OIList_price.add(Double.parseDouble(s));

		
		print("-----------------------------------------------------------------");
		System.out.printf(format3,"|","REVENUE REPORT FOR "+month.toUpperCase(),"|");
		print("|\t\t\t\t\t\t                \t\t\t\t\t\t|");
		System.out.printf(format1,"| Item","Total Qty","Total Price |");
		print("| ----\t\t\t\t\t   ---------\t\t\t    -----------\t|");

		int printindex = 0;
		for(String item: OIList_OrderItemName) {
			String qty = OIList_qty.get(printindex).toString();
			String price = df.format(OIList_price.get(printindex));
			System.out.printf(format2,"| "+item, "\t\t"+qty, "$"+price,"|");
			printindex++;
		}
		
		for(double value: invoiceList_total)
			sum_order += value;


		print("-----------------------------------------------------------------");
		print("|\t\t\t\t\t\t                \t\t\t\t\t\t|");
		System.out.printf(format,"| \t\t\t\t\t\t             Total Revenue: SGD ", sum_order+"\t|");
		print("|\t\t\t\t\t\t                \t\t\t\t\t\t|");
		print("-----------------------------------------------------------------");
		

	}
	
	private void print(String message){
		System.out.println(message);
	}
}