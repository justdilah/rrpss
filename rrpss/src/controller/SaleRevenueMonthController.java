package controller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SaleRevenueMonthController {

	public ArrayList<ArrayList<String>> generateMonthRevenue(int monthchosen) throws IOException {
			// based on the month chosen by the user
			// get OrderID array to compare against OrderItem.csv
			// get the Subtotal, Discount & GST to calculate overall total
			BufferedReader reader = new BufferedReader(new FileReader("DataSet/Invoice.csv"));
			ArrayList<ArrayList<String>> store = new ArrayList<>();
			int increment = 0;
			
			ArrayList<Integer> invoiceList_orderID = new ArrayList<>();
			ArrayList<Double> invoiceList_Total = new ArrayList<>();
			
			String headerLine = reader.readLine();
			String currentLine;
			while ((currentLine = reader.readLine()) != null) { 
	            String[] str = currentLine.split(",");
	            String[] date = str[6].split("-");
	            int month = Integer.parseInt(date[1]);
	            if(month == monthchosen) {
	            	// do not include duplicate orderIDs
	            	if(!invoiceList_orderID.contains(Integer.parseInt(str[9]))) {
	            		invoiceList_orderID.add(Integer.parseInt(str[9]));
	            	}
	            invoiceList_Total.add(Double.parseDouble(str[4]));
	            }
	        }

			ArrayList<String> invoiceList_TotalSt = new ArrayList<>();
			for(Double d: invoiceList_Total)
				invoiceList_TotalSt.add(String.valueOf(d));

			store.add(invoiceList_TotalSt);


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
			ArrayList<String> OIList_OrderItemName_distinct = new ArrayList<>();
			ArrayList<Integer> OIList_qty_total = new ArrayList<>();
			ArrayList<Double> OIList_price_total = new ArrayList<>();
			
			int itemindex = 0;
			for(String item: OIList_OrderItemName) {
				if(!OIList_OrderItemName_distinct.contains(item)) {
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


			ArrayList<String>OIList_qty_totalSt = new ArrayList<>();
			for(Integer i: OIList_qty_total)
				OIList_qty_totalSt.add(String.valueOf(i));

			ArrayList<String>OIList_price_totalSt = new ArrayList<>();
			for(Double d: OIList_price_total)
				OIList_price_totalSt.add(String.valueOf(d));

			store.add(OIList_OrderItemName_distinct);
			store.add(OIList_qty_totalSt);
			store.add(OIList_price_totalSt);

			return store;

	}
}