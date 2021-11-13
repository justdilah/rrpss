package boundary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import controller.*;
import entity.*;

public class InvoiceForm {
	
	InvoiceController  ivc= new InvoiceController();
	OrderController or = new OrderController();
	ResTableController tc = new ResTableController();
	CustomerController cc = new CustomerController();
    StaffController sct = new StaffController();
    ReservationController rct = new ReservationController();
    ResTableController rtc = new ResTableController();
    Scanner sc = new Scanner(System.in);
	
	public void displayOption() throws IOException {
		printInvoice();
	}

	public void printInvoice() throws IOException {

        int choice =-1;
        double subTotalprice , discount, gst, svc, totalPrice=0, discountedP=0;
        String member;
        String format = "%-30s%s%n", format1 = "%-30s%-10s%s%n";;
        ArrayList<Order> orders;
        ArrayList<OrderItem> oitems = new ArrayList<>();
        LocalDate orderDate;
        LocalTime time;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        Order order;
        Staff s;
        Customer cust;

        //RETRIEVE OCCUPIED TABLES
        if(tc.getAllOccupiedTables().size()>0){
            for(int i=0; i<tc.getAllOccupiedTables().size(); i++) {
                print(i+1 + ") Table No " + tc.getAllOccupiedTables().get(i).getTableNo());
            }
        }
        else{
            print("============================================================");
            print("There are no occupied tables");
            print("============================================================");
            MainAppUI.print();
        }

        print("=================================");
        print("\t Generating Invoice ");
        print("=================================");

        print("Please enter your choice: ");
        do{
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice < 0 || choice < tc.getAllOccupiedTables().size())
                    print("Choice does not exist, please enter a valid choice: ");
            }catch(NumberFormatException e){
                print("Choice is of invalid format, please enter a valid choice: ");
            }
        }while(choice<0||choice>tc.getAllOccupiedTables().size());

        //RETRIEVE SPECIFIC TABLE
        Table t = tc.getAllOccupiedTables().get(choice-1);

        orders = ivc.getUnpaidOrdersByTableNo(t);
        cust = orders.get(0).getCust();
        String staffID = orders.get(0).getStaffId();

        order = orders.get(0);
        orderDate = orders.get(0).getDate();
        s = sct.getStaffById(Integer.parseInt(orders.get(0).getStaffId()));

        if(!cc.isMember(cust.getCustId())){
            print("Would you like to be member? (Y/N) ");
            member = sc.nextLine();
            switch(member){
                case "Y" -> {
                    cust.setMembership(true);
                    cc.updateMembership(cust);
                }
                case "N" ->{

                }
            }
        }

        print("=================================");
        print("\t Invoice ");
        print("=================================");

        printf(format,"Table No : " , String.valueOf(t.getTableNo()));
        printf(format,"Customer Name : ", cust.getPersName());
        printf(format,"Staff ID : ", staffID);

        //PRINTS OUT THE ORDER ITEMS
        print("==================================");
        print("\t Order Items");
        print("==================================");
        int counter = 0;
        if(ivc.checkFileEmpty() == 1)
            counter = 8000;
        else{
            int last =  InvoiceController.getAllInvoice().size()-1;
            counter = InvoiceController.getAllInvoice().get(last).getInvoiceNo()+1;
        }

        //SETTING PAYMENT TO TRUE
        for (Order ord: orders){
            oitems = ord.getOrderItemList();
            ord.setIsPaid(true);
            or.replaceOrder(ord);
        }

        for(OrderItem oitem: oitems){
            System.out.printf(format1, oitem.getOrderItemName(), oitem.getOrderItemQty(), "$"+ oitem.getOrderItemPrice());
            print("");
        }

        subTotalprice = ivc.calculateSubTotalPriceOrder(oitems);
        gst = ivc.calculateGst(subTotalprice);
        svc = ivc.calculateService(subTotalprice);
        totalPrice = subTotalprice+gst+svc;

        if(cc.isMember(cust.getCustId()))
            discount = ivc.calculateDiscount(totalPrice);
        else
            discount = 0;

        discountedP = totalPrice - discount;
        time = LocalTime.parse(LocalTime.now().toString(),formatter);


        Invoice inv = new Invoice(counter, subTotalprice,svc,discount,discountedP,gst,orderDate,time,s,order,cust,t.getTableNo());
        ivc.saveInvoice(inv);


        Invoice setInv = ivc.getInvoiceById(counter);
        if (setInv == null)
            print("Unexpected Error has occured");
        else
        {
            print("------------------------------------------------------------");
            printf(format,"Sub Total Price :", "$"+ subTotalprice);
            printf(format,"GST :", "$"+ String.format("%.2f",gst));
            printf(format,"Discount :", "S"+ String.format("%.2f",discount));
            printf(format,"Total Price :","$" + String.format("%.2f",((subTotalprice + gst)- discount)));
            print("------------------------------------------------------------");
            Reservation r = ReservationController.getReservationByContact(setInv.getCustomer().getPersPhoneNo());
            int res_id = r.getResId();
            rct.removeReservation(r);
            rtc.deteleTable(res_id);
        }
    }

	public void print(String message)
	{
	    System.out.println(message);
	}

    public void printf(String format, String message, String message2){
        System.out.printf(format,message,message2);
    }

}