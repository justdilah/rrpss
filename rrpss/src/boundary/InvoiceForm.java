package boundary;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import controller.CustomerController;
import controller.InvoiceController;
import controller.OrderController;
import controller.ResTableController;
import controller.ReservationController;
import controller.StaffController;
import entity.Customer;
import entity.Invoice;
import entity.Order;
import entity.OrderItem;
import entity.Reservation;
import entity.Staff;
import entity.Table;

/**
 * This class represents the Invoice form of the restaurant.
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class InvoiceForm {

    /**
     * Scanner object to scan user input
     */
    Scanner sc = new Scanner(System.in);

    /**
     * Initialise a DecimalFomat to get two decimal place
     */
    DecimalFormat df= new DecimalFormat("0.00");

    /**
     * Invoice controller object to call methods in the controller class
     */
    InvoiceController  ivc= new InvoiceController();

    /**
     * Order controller object to call methods in the controller class
     */
    OrderController or = new OrderController();

    /**
     * ResTable controller object to call methods in the controller class
     */
    ResTableController rtc = new ResTableController();

    /**
     * Customer controller object to call methods in the controller class
     */
    CustomerController cc = new CustomerController();

    /**
     * Staff controller object to call methods in the controller class
     */
    StaffController sct = new StaffController();

    /**
     * Reservation controller object to call methods in the controller class
     */
    ReservationController rct = new ReservationController();


    /**
     * This method display a list of options for the staff to select which function he/she wants to perform
     * @throws IOException Display error message if any I/O error found while retrieving the invoice records.
     */
    public void displayOption() throws IOException {
        printInvoice();
    }

    /**
     * This method prints a specific invoice
     * @throws IOException Display error message if any I/O error found while retrieving the invoice records.
     */
    public void printInvoice() throws IOException {

        int choice =-1;
        double subTotalprice , discount, gst, svc, totalPrice, discountedP;
        String member;
        String format = "%-25s%s%n", format1 = "%-30s%-10s%s%n";;
        ArrayList<Order> orders;
        ArrayList<OrderItem> oitems = new ArrayList<>();
        LocalDate orderDate;
        LocalTime time;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        Order order;
        Staff s;
        Customer cust;

        //RETRIEVE OCCUPIED TABLE

        if(rtc.getAllOccupiedTables() == null){
            print("============================================================");
            print("There are no occupied tables");
            print("============================================================");
            MainAppUI.print();
        }


        for(int i=0; i<rtc.getAllOccupiedTables().size(); i++) {
            print(i+1 + ") Table No " + rtc.getAllOccupiedTables().get(i).getTableNo());
        }


        print("=================================");
        print("\t Generating Invoice ");
        print("=================================");

        print("Please enter your choice: ");
        do{
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice < 0 || choice > rtc.getAllOccupiedTables().size())
                    print("Choice does not exist, please enter a valid choice: ");
            }catch(NumberFormatException e){
                print("Choice is of invalid format, please enter a valid choice: ");
            }
        }while(choice<0||choice>rtc.getAllOccupiedTables().size());

        Table t = rtc.getAllOccupiedTables().get(choice-1);
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
        if(ivc.checkFileEmpty() == 0)
            counter = 8000;
        else{
            int last =  InvoiceController.getAllInvoice().size()-1;
            counter = InvoiceController.getAllInvoice().get(last).getInvoiceNo()+1;
        }

        for (Order ord: orders){
            oitems = ord.getOrderItemList();
            ord.setIsPaid(true);
            or.replaceOrder(ord);
        }
        System.out.printf(format1, "Order Item name", "Quantity", "Price");
        print("=================================================");

        for(OrderItem oitem: oitems){
            System.out.printf(format1, oitem.getOrderItemName(), oitem.getOrderItemQty(), "$"+ df.format(oitem.getOrderItemPrice()));
        }

        subTotalprice = ivc.calculateSubTotalPriceOrder(oitems);
        gst = ivc.calculateGst(subTotalprice);
        svc = ivc.calculateService(subTotalprice);
        totalPrice = ivc.calculateTotal(subTotalprice,svc,gst);

        if(cc.isMember(cust.getCustId()))
            discount = ivc.calculateDiscount(totalPrice);
        else
            discount = 0;

        discountedP = totalPrice - discount;
        discountedP = Double.parseDouble(df.format(discountedP));
        time = LocalTime.parse(LocalTime.now().format(formatter));

        Invoice inv = new Invoice(counter, subTotalprice,svc,discount,discountedP,gst,orderDate,time,s,order,cust,t.getTableNo());
        ivc.saveInvoice(inv);


        Invoice setInv = ivc.getInvoiceById(counter);
        if (setInv == null)
            print("Unexpected Error has occurred");
        else
        {
            print("------------------------------------------------------------");
            printf(format,"Sub Total Price :", "$"+ df.format(subTotalprice));
            printf(format,"GST :", "$"+ df.format(gst));
            printf(format,"Service Charge: ", "$"+df.format(svc));
            printf(format,"Total Price: ", "$"+df.format(subTotalprice+svc+gst));
            if(discount == 0)
            {
                print("------------------------------------------------------------");
                Reservation r = ReservationController.getReservationByContact(setInv.getCustomer().getPersPhoneNo());
                int res_id = r.getResId();
                rtc.deleteTable(res_id);
                rct.removeReservation(r);
            }
            else {
                print("------------------------------------------------------------");
                printf(format, "Discount :", "S" + df.format(discount));
                print("------------------------------------------------------------");
                printf(format, "Discounted Total Price :", "$" + df.format(discountedP));
                print("------------------------------------------------------------");
                Reservation r = ReservationController.getReservationByContact(setInv.getCustomer().getPersPhoneNo());
                int res_id = r.getResId();
                rtc.deleteTable(res_id);
                rct.removeReservation(r);
            }
        }
        MainAppUI.print();
    }

    /**
     * This is a print method for easier printing outputs
     * @param message The various output to be printed out
     */
    public void print(String message)
    {
        System.out.println(message);
    }

    /**
     * This is a printf method for easier printing outputs
     * @param message The various output to be printed out
     */
    public void printf(String format, String message, String message2){
        System.out.printf(format,message,message2);
    }

}