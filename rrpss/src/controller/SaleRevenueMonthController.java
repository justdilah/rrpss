package controller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * This class represents the Sale Revenue by Month controller of the restaurant.
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class SaleRevenueMonthController {

	/**
	 * This method generates the salary revenue based on the month chosen by the staff
	 * @param monthchosen The month the staff specified to view the report of
	 * @return An arraylist within an arraylist to store the report data
	 * @throws IOException
	 */
	public ArrayList<ArrayList<String>> generateMonthRevenue(int monthchosen) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("DataSet/Invoice.csv"));
		ArrayList<ArrayList<String>> store = new ArrayList<>();

		ArrayList<Integer> invoiceList_orderID = new ArrayList<>();
		ArrayList<Double> invoiceList_Total = new ArrayList<>();

		String headerLine = reader.readLine();
		String currentLine;
		while ((currentLine = reader.readLine()) != null) {
			String[] str = currentLine.split(",");
			String[] date = str[6].split("-");
			int month = Integer.parseInt(date[1]);
			if(month == monthchosen) {
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
					OIList_OrderItemName.add(str2[1]);
					OIList_qty.add(Integer.parseInt(str2[2]));
					OIList_price.add(Double.parseDouble(str2[3]));
				}
			}
		}

		ArrayList<String> OIList_OrderItemName_distinct = new ArrayList<>();
		ArrayList<Integer> OIList_qty_total = new ArrayList<>();
		ArrayList<Double> OIList_price_total = new ArrayList<>();

		int itemindex = 0;
		for(String item: OIList_OrderItemName) {
			if(!OIList_OrderItemName_distinct.contains(item)) {
				OIList_OrderItemName_distinct.add(item);
				OIList_qty_total.add(OIList_qty.get(OIList_OrderItemName.indexOf(item)));
				OIList_price_total.add(OIList_price.get(OIList_OrderItemName.indexOf(item)));
			}else {
				int index = OIList_OrderItemName_distinct.indexOf(item);
				Integer totalqty = OIList_qty_total.get(index);
				Integer qtytobeadded = OIList_qty.get(itemindex);
				totalqty = totalqty + qtytobeadded;
				OIList_qty_total.set(index, totalqty);

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