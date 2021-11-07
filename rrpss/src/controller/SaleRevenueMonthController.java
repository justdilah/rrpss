package controller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.*;

import entity.Invoice;

public class SaleRevenueMonthController {

	public void generateMonthRevenue(int monthchosen) throws IOException {

			// based on the month chosen by the user
			// get OrderID array to compare against OrderItem.csv
			// get the Subtotal, Discount & GST to calculate overall total
			BufferedReader reader = new BufferedReader(new FileReader("DataSet/Invoice.csv"));
			
			List<Integer> invoiceList_orderID = new ArrayList<>();
			List<Double> invoiceList_SubTotal = new ArrayList<>();
			List<Double> invoiceList_discount = new ArrayList<>();
			List<Double> invoiceList_GST = new ArrayList<>();
			
			String headerLine = reader.readLine();
			String currentLine;
			while ((currentLine = reader.readLine()) != null) { 
	            String[] str = currentLine.split(",");
	            String[] date = str[2].split("-");
	            int month = Integer.parseInt(date[1]);
	            if(month == monthchosen) {
	            	// do not include duplicate orderIDs
	            	if(invoiceList_orderID.contains(Integer.parseInt(str[1])) == false) {
	            		invoiceList_orderID.add(Integer.parseInt(str[1]));
	            	}
	            invoiceList_SubTotal.add(Double.parseDouble(str[3]));
	            invoiceList_discount.add(Double.parseDouble(str[4]));
	            invoiceList_GST.add(Double.parseDouble(str[5]));
	            }
	        }
			
			// get the OrderItemName, qty, price for individual dish calculation
			List<String> OIList_OrderItemName = new ArrayList<>();
			List<Integer> OIList_qty = new ArrayList<>();
			List<Double> OIList_price = new ArrayList<>();
			
			for (int value: invoiceList_orderID) {
				BufferedReader reader2 = new BufferedReader(new FileReader("DataSet/OrderItem.csv"));
				String headerLine2 = reader2.readLine();
				String currentLine2;
				while ((currentLine2 = reader2.readLine()) != null) {
					String[] str2 = currentLine2.split(",");
					if(Integer.parseInt(str2[4]) == value) {
							// repeated values will be add to array at this stage
							// due to ConcurrentModificationException:
							// can't iterate the OIList_OrderItemName if it is being modified
							OIList_OrderItemName.add(str2[1]);
							OIList_qty.add(Integer.parseInt(str2[2]));
							OIList_price.add(Double.parseDouble(str2[3]));
					}
		        }
		    }
			
//			System.out.println(OIList_OrderItemName); 
//			System.out.println(OIList_qty); 
//			System.out.println(OIList_price); 
			
			// now just distinct dish name, the total qty and price across all orders
			List<String> OIList_OrderItemName_distinct = new ArrayList<>();
			List<Integer> OIList_qty_total = new ArrayList<>();
			List<Double> OIList_price_total = new ArrayList<>();
			
			int itemindex = 0;
			for(String item: OIList_OrderItemName) {
				if(OIList_OrderItemName_distinct.contains(item) == false) {
					OIList_OrderItemName_distinct.add(item);
					OIList_qty_total.add(OIList_qty.get(OIList_OrderItemName.indexOf(item)));
//					System.out.println(OIList_OrderItemName.indexOf(item)); 
					OIList_price_total.add(OIList_price.get(OIList_OrderItemName.indexOf(item)));
				}else {
					// add duplicate qty to the first obtained qty
					int index = OIList_OrderItemName_distinct.indexOf(item);
					Integer totalqty = OIList_qty_total.get(index);
					Integer qtytobeadded = OIList_qty.get(itemindex);
					totalqty = totalqty + qtytobeadded;
					OIList_qty_total.set(index, totalqty);
					
					// add duplicate price to the first obtained price
					int index2 = OIList_OrderItemName_distinct.indexOf(item);
					Double totalprice = OIList_price_total.get(index);
					Double pricetobeadded = OIList_price.get(itemindex);
					totalprice = totalprice + pricetobeadded;
					OIList_price_total.set(index, totalprice);
				}
				itemindex++;
			}
			
//			System.out.println(OIList_OrderItemName_distinct); 
//			System.out.println(OIList_qty_total); 
//			System.out.println(OIList_price_total); 
			
			// PRINTING OF MONTHLY REVENUE
			System.out.println("-----------------------------------------------------------------");
			System.out.println("|\t\t\t REVENUE REPORT \t\t\t|");
			System.out.println("|\t\t\t                \t\t\t|");
			System.out.println("|Item \t\t\t\t    Total Qty\t    Total Price\t|");
			System.out.println("|---- \t\t\t\t    ---------\t    -----------\t|");
			int printindex = 0;
			for(String item: OIList_OrderItemName_distinct) {
				String qty = OIList_qty_total.get(printindex).toString();
				String price = OIList_price_total.get(printindex).toString();
				
				System.out.println(String.format("|%-40s %-15s $%-5s|",item, qty, price));
				
				printindex++;
			}
			// generate overall total sales for the month
			double sum_orderID = 0;
			for(double value: invoiceList_SubTotal) {
				sum_orderID += value;
			}
			
			double sum_discount = 0;
			for(double value: invoiceList_discount) {
				sum_discount += value;
			}
			
			double sum_GST = 0;
			for(double value: invoiceList_GST) {
				sum_GST += value;
			}
			
			Double FinalTotal = sum_orderID - sum_discount + sum_GST;
			
			System.out.println("-----------------------------------------------------------------");
			System.out.println("|\t\t\t                \t\t\t|");
			System.out.printf("| \t\t\t             Total Revenue: SGD %.2f  |\n", FinalTotal); 
			System.out.println("|\t\t\t                \t\t\t|");
			System.out.println("-----------------------------------------------------------------");
			
		
	}
}