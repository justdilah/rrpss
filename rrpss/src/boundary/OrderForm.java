package boundary;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import controller.*;
import entity.AlaCarte;
import entity.Customer;
import entity.Order;
import entity.OrderItem;
import entity.Promotion;
import entity.Table;

/**
 * This class represents a OrderForm in boundary.
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class OrderForm {


    /**
     * Order controller object to call methods in the controller class
     */
    private final OrderController or = new OrderController();
    /**
     * Customer controller object to call methods in the controller class
     */
    private final CustomerController cc = new CustomerController();

    /**
     * ResTable controller object to call methods in the controller class
     */
    private final ResTableController tc = new ResTableController();

    /**
     * Staff controller object to call methods in the controller class
     */
    private final StaffController stc = new StaffController();

    /**
     * Scanner object to scan user input
     */
    private static final Scanner sc = new Scanner(System.in);

    /**
     * DecimalFormat formatter to convert to 2 decimal place
     */
    private static final DecimalFormat df= new DecimalFormat("0.00");

    /**
     * This method will displays a list of options for the staff to select what function he/she wants to perform and executes the selected function.
     * @throws IOException Display error message if any I/O error found while accessing the order records.
     */
    public void displayOption() throws IOException{

        int choice = 0;
        printOrderFormMenu();

        print("Please enter your choice: ");
        do{
            try {
                choice = Integer.parseInt(sc.nextLine());
                if(choice==0||choice>5)
                    print("Choice does not exist, please enter a valid choice");
            }catch(NumberFormatException e){
                print("Choice is of invalid format, please enter a valid choice: ");
            }
        }while(choice==0||choice>5);

        switch (choice) {
            case 1 -> displayOrder();
            case 2 -> insertOrder();
            case 3 -> updateOrder();
            case 4 -> deleteOrder();
            case 5 -> MainAppUI.print();
        }

    }

    /**
     * This method will prompts the staff to either displays current order, or past orders and displays them accordingly.
     * @throws IOException Display error message if any I/O error found while retrieving the order records.
     */
    private void displayOrder() throws IOException {
        int choice=-1, c;
        ArrayList<Order> CurrentOrder;
        ArrayList<OrderItem>CustomerOrder;
        String format = "%-30s%s%n", format1 = "%-30s%-10s%s%n";
        double TotalPrice = 0;
        LocalDate date, date2 = null;
        boolean setter= true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        print("=================================");
        print("\t Display Order ");
        print("=================================");
        print("1) Display Current Orders");
        print("2) Display Past Orders");
        print("Enter 0 to return to the previous page");
        do {
            try{
                choice = Integer.parseInt(sc.nextLine());
                if(choice<0 || choice>2)
                    print("Choice does not exist, please enter a valid choice: ");
            }catch(NumberFormatException e){
                print("Choice is of invalid format, please enter a valid choice: ");
            }
        }while(choice<0 || choice>2);

        if(choice == 0)
            displayOption();

        print("=================================");
        if(choice == 1)
        {
            date = LocalDate.now();
            CurrentOrder = or.getCurrentDateOrder(date);
            if(CurrentOrder==null){
                print("There are currently no orders at the moment");
            }
            else{
                for (Order order: CurrentOrder){
                    if (!order.getIsPaid()) {
                        TotalPrice = 0;
                        printf(format, "Order ID :", String.valueOf(order.getOrderId()));
                        printf(format, "Time Ordered : ", String.valueOf(order.getTimeStamp()));
                        printf(format, "Date Ordered : ", order.getDate().toString().substring(8, 10) +
                                "-" + order.getDate().toString().substring(5, 7) + "-" + order.getDate().toString().substring(0, 4));
                        printf(format, "Payment Completed : ", String.valueOf(order.getIsPaid()));
                        printf(format, "Staff ID : ", String.valueOf(order.getStaffId()));
                        printf(format, "Table No : ", String.valueOf(order.getTable().getTableNo()));
                        CustomerOrder = or.getOrderItemByOrderId(order.getOrderId());
                        print("=============================================");
                        System.out.printf(format1, "Order Item name", "Quantity", "Price");
                        print("=============================================");
                        for (OrderItem oitem : CustomerOrder) {
                            System.out.printf(format1, oitem.getOrderItemName(), oitem.getOrderItemQty(), "$" + df.format(oitem.getOrderItemPrice()));
                            TotalPrice += oitem.getOrderItemPrice();
                        }
                        print("");
                        printf(format, "Total Price: ", "$" + df.format(TotalPrice));
                        print("");
                        print("=============================================");
                    }
                }
            }
            print("Enter 0 to return to the previous page");
            do {
                try{
                    c = Integer.parseInt(sc.nextLine());
                    if(c!=0)
                        print("Enter 0 to return to the previous page");

                }catch(NumberFormatException e){
                    print("Enter 0 to return to the previous page");
                    c=-1;
                }
            }while(c!=0);

            displayOption();

        }
        else if (choice == 2){
            print("Please enter the date for the orders you wish to view (format: yyyy-MM-dd): ");
            do{
                try{
                    date2 = LocalDate.parse(sc.nextLine(),formatter);
                    setter = false;

                }catch(Exception e){
                    print("Date is of invalid format, please enter a valid date");
                }
            }while(setter);

            if(date2.isAfter(LocalDate.now())) {
                print("Date has not arrived yet.");
                displayOption();
            }

            CurrentOrder = or.getCurrentDateOrder(date2);
            if(CurrentOrder.size()==0){
                print("There are currently no orders as of this date");
            }
            else{
                print("=============================================");
                for (Order order: CurrentOrder){
                    TotalPrice = 0;
                    printf(format,"Order ID :", String.valueOf(order.getOrderId()));
                    printf(format,"Time Ordered : " , String.valueOf(order.getTimeStamp()));
                    printf(format,"Date Ordered : " , order.getDate().toString().substring(8,10)+
                            "-"+order.getDate().toString().substring(5,7)+"-"+order.getDate().toString().substring(0,4));
                    printf(format,"Payment Completed : " , String.valueOf(order.getIsPaid()));
                    printf(format,"Staff ID : " , String.valueOf(order.getStaffId()));
                    printf(format,"Table No : " , String.valueOf(order.getTable().getTableNo()));
                    CustomerOrder = or.getOrderItemByOrderId(order.getOrderId());
                    print("=============================================");
                    System.out.printf(format1,"Order Item name","Quantity","Price");
                    print("=============================================");
                    for (OrderItem oitem: CustomerOrder){
                        System.out.printf(format1,oitem.getOrderItemName(),oitem.getOrderItemQty(),"$"+df.format(oitem.getOrderItemPrice()));
                        TotalPrice+= oitem.getOrderItemPrice();
                    }
                    print("");
                    printf(format,"Total Price: ", "$"+df.format(TotalPrice));
                    print("");
                }
            }
            print("Enter 0 to return to the previous page");
            do {
                try{
                    c = Integer.parseInt(sc.nextLine());
                    if(c!=0)
                        print("Enter 0 to return to the previous page");

                }catch(NumberFormatException e){
                    print("Enter 0 to return to the previous page");
                    c=-1;
                }
            }while(c!=0);
            displayOption();
        }
    }


    /**
     * This method will prompt the staff for different inputs, and calls the addOrder(..) in the controller class to add an order.
     * @throws IOException Display error message if any I/O error found while accessing records.
     */
    private void insertOrder() throws IOException{

        int tableNo, index = 1;
        int staffid= 0;
        String custNo;

        DateTimeFormatter formatterD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date2 = LocalDate.parse(LocalDate.now().toString(),formatterD);
        ArrayList<Table> table = tc.getAllReservedTables(date2);



        System.out.println("=================================");
        System.out.println("\t Create Order ");
        System.out.println("=================================");

        if(table == null)
        {
            print("No Reserved Tables at the moment");
            displayOption();
        }

        System.out.println("Please enter your choice: ");


        for(int i=0;i<table.size();i++) {
            System.out.println(i+1 + ") Table No " + table.get(i).getTableNo());
        }

        Table t = null;

        do {
            try {
                tableNo = Integer.parseInt(sc.nextLine());
                t = table.get(tableNo - 1);
                if (t == null)
                    print("Table does not exist, please enter a valid table number: ");
            }catch(NumberFormatException e){
                print("Table Number is of invalid format, please enter a valid table number: ");
            }
        }while(t==null);

        index = or.getIndexFromOICsv(index);

        print("Please enter Staff ID: ");
        do {
            try{
                staffid = Integer.parseInt(sc.nextLine());
                if(!stc.isIdExists(staffid)) {
                    print("Staff ID does not exist");
                    print("Please enter a valid Staff ID: ");
                }
            }catch(NumberFormatException e){
                print("Staff ID is not of valid format, please enter a valid Staff ID: ");
            }
        }while(!stc.isIdExists(staffid));

        print("Please enter Customer Phone Number: ");
        do{
            custNo = sc.nextLine();
            if(custNo.trim().isEmpty())
                print("Customer Phone Number cannot be empty, please enter Customer Phone Number: ");
            else if(!cc.custExists(custNo))
                print("Customer Phone Number does not exist, please enter Customer Phone Number: ");
        }while(!cc.custExists(custNo)||custNo.trim().isEmpty());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(LocalTime.now().format(formatter));
        LocalDate date = LocalDate.now();

        tc.updateTableStatusString("OCCUPIED", t.getTableNo());
        or.addOrder(index,time,date,staffid,false,custNo,t.getTableNo());
        insertOrderItems(index,0);
        print("==================================");
        print("Order added successfully");
        print("==================================");

    }
    /**
     * This method will display the food menu and prompts the staff to select which food item to be added and its quantity,
     * then it calls the addAlaCarteOrderItem(..) or addPromoOrderItem(..) in the controller class to add an orderItem.
     * @param orderId The order that the orderItem is going to be inserted into.
     * @param counter The value to increment the orderID
     * @throws IOException Display error message if any I/O error found while accessing records.
     */
    private void insertOrderItems(int orderId, int counter) throws IOException {

        String format = "%-30s%s%n";
        String format1 = "%-27s%s%n";

        int i,c=0, indexes, qty=0, j,size;
        boolean setter;

        ArrayList<AlaCarte> alarCarteItems = or.getAllAlaCartItems();
        ArrayList<Promotion> promoSetItems = or.getAllPromoSets();
        printFoodMenu();
        i= alarCarteItems.size()+ promoSetItems.size();
        indexes = i-1;

        print("Please enter your choice: ");
        do{
            try {
                c = Integer.parseInt(sc.nextLine());
                if (c <= 0 || c > indexes+1)
                    print("Choice does not exist, please enter a valid choice: ");
            }catch(NumberFormatException e){
                print("Choice is of invalid format, please enter a valid choice: ");
            }
        }while(c<=0 || c>indexes+1);

        print("Please enter Qty: ");
        do{
            try{
                qty = Integer.parseInt(sc.nextLine());
                if (qty<1)
                    print("Quantity cannot be smaller than 0, please enter a valid quantity: ");
            }catch(NumberFormatException e) {
                print("Quantity is of invalid format, please enter a valid quantity: ");
            }
        }while(qty<1);

        if (c<= alarCarteItems.size()){
            AlaCarte a = alarCarteItems.get(c-1);
            or.addAlaCarteOrderItem(++counter,a,qty,orderId);
        }
        else{
            Promotion p =  promoSetItems.get(c-1-alarCarteItems.size());
            or.addPromoOrderItem(++counter, p, qty,orderId);
        }

        do{
            print("=================================");
            print("Current items that are added");
            print("=================================");
            printf(format1,"Item","Quantity");
            print("===================================");

            size = or.getOrderById(orderId).getOrderItemList().size();
            ArrayList<OrderItem> oiList = or.getOrderById(orderId).getOrderItemList();

            for(j=0;j<size ; j++ ) {
                System.out.printf(format,j+1 + ") " +oiList.get(j).getOrderItemName(),
                        oiList.get(j).getOrderItemQty());
            }
            print("====================================");
            print("Please enter 0 to stop adding");
            print("====================================");

            print("Please enter your choice: ");
            c=0;
            setter = false;
            try{
                c = Integer.parseInt(sc.nextLine());
                if(c==0)
                    break;
                else if(c<0||c>indexes+1)
                    print("Choice does not exist, please enter a valid choice: ");
                else{
                    if (c<= alarCarteItems.size()){
                        AlaCarte a = alarCarteItems.get(c-1);
                        for(j =0; j<size; j++)
                        {
                            if(oiList.get(j).getOrderItemName().equals(a.getAlaCarteName()))
                            {
                                print("Enter Quantity");
                                int Qty = 0;
                                do {
                                    try {
                                        Qty = Integer.parseInt(sc.nextLine());
                                        if (Qty == 0)
                                            print("Quantity cannot be 0, please enter a valid quantity: ");
                                    } catch (NumberFormatException e) {
                                        print("Quantity is of invalid format, please enter a valid quantity: ");
                                    }
                                } while (Qty == 0);
                                int newQty = oiList.get(j).getOrderItemQty() + Qty;
                                or.updateOrderItemQty(oiList.get(j), newQty);
                                print("Item has been successfully added into the Order");
                                setter = true;
                                break;
                            }
                        }

                        if(!setter){
                            int Qty = 0;
                            print("Enter Quantity: ");
                            do{
                                try
                                {
                                    Qty = Integer.parseInt(sc.nextLine());
                                    if(Qty == 0)
                                        print("Quantity cannot be 0, please enter a valid quantity: ");
                                }catch(NumberFormatException e){
                                    print("Quantity is of invalid format, please enter a valid quantity: ");
                                }
                            }while(Qty==0);
                            or.addAlaCarteOrderItem(++counter,a,Qty,orderId);
                            print("Item has been successfully added into the Order");
                        }
                    }
                    else{
                        Promotion p =  promoSetItems.get(c-1-alarCarteItems.size());
                        for (j=0; j<size; j++) {
                            if (oiList.get(j).getOrderItemName().equals(p.getPackName())) {
                                print("Enter Quantity: ");
                                int Qty = 0;
                                do {
                                    try {
                                        Qty = Integer.parseInt(sc.nextLine());
                                        if (Qty == 0)
                                            print("Quantity cannot be 0, please enter a valid quantity");
                                    } catch (NumberFormatException e) {
                                        print("Quantity is of invalid format, please enter a valid quantity");
                                    }
                                } while (Qty == 0);
                                int newQty = oiList.get(j).getOrderItemQty() + Qty;
                                or.updateOrderItemQty(oiList.get(j), newQty);
                                print("Item has been successfully added into the Order");
                                setter = true;
                                break;
                            }
                        }

                        if(!setter)
                        {
                            int Qty = 0;
                            print("Enter Quantity: ");
                            do{
                                try
                                {
                                    Qty = Integer.parseInt(sc.nextLine());
                                    if(Qty == 0)
                                        print("Quantity cannot be 0, please enter a valid quantity");
                                }catch(NumberFormatException e){
                                    print("Quantity is of invalid format, please enter a valid quantity");
                                }
                            }while(Qty==0);
                            or.addPromoOrderItem(++counter, p, Qty,orderId);
                            print("Item has been successfully added into the Order");
                        }
                    }
                }
            }catch(NumberFormatException e){
                print("Choice is not of valid format, please enter a valid choice: ");
            }
        }while(c!=0);
        displayOption();
    }

    /**
     *  This method displays the whole list of orders for the staff to select which order he/she wants to perform the update on,
     *  then it will proceeds to updateOptions(..) for further inputs.
     * @throws IOException Display error message if any I/O error found while updating into the order records.
     */
    private void updateOrder() throws IOException {

        String format = "%-20s%s%n";
        int choice = 0, size;

        print("================================================");
        print("Select the Order you would like to update ");
        print("================================================");
        if (OrderController.getUnpaidOrders() == null)
        {
            print("There are no orders for updating");
            displayOption();
        }

        Customer c;
        size = OrderController.getUnpaidOrders().size();
        ArrayList<Order> uorder = OrderController.getUnpaidOrders();

        for (int i = 0; i < size; i++) {
            c = uorder.get(i).getCust();
            String orderId = String.valueOf(uorder.get(i).getOrderId());
            String tableNo = String.valueOf(uorder.get(i).getTable().getTableNo());
            print("("+(i+1)+")");
            printf(format,"Order Id :" ,orderId);
            printf(format,"Customer Name :" , c.getPersName());
            printf(format,"Table No :" ,tableNo);
            print("");
        }

        print("Enter 0 to return to the previous page");
        print("Please enter your choice: ");
        do {
            try{
                choice = Integer.parseInt(sc.nextLine());
                if(choice<0||choice> OrderController.getUnpaidOrders().size())
                    print("Choice does not exist, please enter a valid choice: ");

            }catch(NumberFormatException e) {
                print("Choice is of invalid format, please enter a valid choice: ");
            }
        }while(choice<0||choice> OrderController.getUnpaidOrders().size());

        if (choice==0)
            displayOption();

        else
            updateOptions(OrderController.getUnpaidOrders().get(choice-1));


    }

    /**
     * This method display a list of options for the staff to select which update functions he/she wants to perform on the given order.
     * @param o The order that is going to be updated on
     * @throws IOException Display error message if any I/O error found while updating the records.
     */
    private void updateOptions(Order o) throws IOException{

        int choice = 0;

        String format = "%-30s%s%n";
        String format1 = "%-27s%s%n";

        do {

            print("===================================");
            print("Current Order Items");
            print("===================================");
            printf(format1,"Item","Quantity");
            print("===================================");

            ArrayList<OrderItem> items = o.getOrderItemList();
            for (OrderItem item : items) printf(format, item.getOrderItemName(), String.valueOf(item.getOrderItemQty()));


            print("===================================");
            print("Update Options");
            print("===================================");
            print("1) Add Order Item to Order");
            print("2) Remove Order Item from Order");
            print("Enter 0 to return to the last page");
            print("===================================");
            System.out.print("Please enter your choice: ");
            do {
                try {
                    choice = Integer.parseInt(sc.nextLine());
                    if (choice < 0 || choice > 2)
                        print("Choice does not exist, please enter a valid choice: ");
                } catch (NumberFormatException e) {
                    print("Choice is of invalid format, please enter a valid choice: ");
                }
            } while (choice < 0 || choice > 2);

            switch (choice) {
                case 1 -> addOrderItem(o, o.getOrderItemList().size());
                case 2 -> deleteOrderItem(o);
            }
        }while(choice!=0);
        updateOrder();
    }

    /**
     * This method will display the food menu and prompts the staff to select an orderItem to be added given an Order
     * and calls the addAlaCarteOrderItem(..) or addPromoOrderItem(..) in the controller class to add the selected orderItem.
     * @param o The order that is going to be updated on
     * @param counter The value to increment the orderID
     * @throws IOException Display error message if any I/O error found while inserting the records.
     */
    private void addOrderItem(Order o, int counter) throws IOException {

        int size, choice=0;
        boolean setter;

        String format = "%-30s%s%n";
        String format1 = "%-27s%s%n";

        ArrayList<AlaCarte> alarCarteItems = or.getAllAlaCartItems();
        ArrayList<Promotion> promoSetItems = or.getAllPromoSets();

        size= alarCarteItems.size()+ promoSetItems.size();

        print("===================================");
        print("Select the order item to be added");
        print("===================================");
        printFoodMenu();

        print("===================================");
        print("Current Order Items List");
        print("===================================");
        printf(format1,"Item","Quantity");
        print("===================================");


        for(int i=0;i<o.getOrderItemList().size() ; i++ ) {
            System.out.printf(format,i+1 + ") " +o.getOrderItemList().get(i).getOrderItemName(),
                    o.getOrderItemList().get(i).getOrderItemQty());
        }

        print("Enter 0 to return to the previous page");
        print("Enter an option to add: ");

        do{
            try{
                choice = Integer.parseInt(sc.nextLine());
                if (choice<0 || choice>size)
                    print("Choice does not exist, please enter a valid choice: ");
                else if (choice == 0)
                    break;
            }catch(NumberFormatException e){
                print("Choice is of invalid format, please enter a valid choice: ");
            }
        }while(choice<0 || choice>size);

        setter = false;
        if(choice!=0)
        {
            if (choice<= alarCarteItems.size()){
                AlaCarte a = alarCarteItems.get(choice-1);
                for(int i =0; i<o.getOrderItemList().size(); i++) {
                    if (o.getOrderItemList().get(i).getOrderItemName().equals(a.getAlaCarteName())) {
                        print("Enter Quantity");
                        int Qty = 0;
                        do {
                            try {
                                Qty = Integer.parseInt(sc.nextLine());
                                if (Qty == 0)
                                    print("Quantity cannot be 0, please enter a valid quantity");
                            } catch (NumberFormatException e) {
                                print("Quantity is of invalid format, please enter a valid quantity");
                            }
                        } while (Qty == 0);
                        int newQty = o.getOrderItemList().get(i).getOrderItemQty() + Qty;
                        or.updateOrderItemQty(o.getOrderItemList().get(i), newQty);
                        print("Item has been successfully added into the Order");
                        setter = true;
                        break;
                    }
                }

                if(!setter)
                {
                    int Qty = 0;
                    print("Enter Quantity: ");
                    do{
                        try
                        {
                            Qty = Integer.parseInt(sc.nextLine());
                            if(Qty == 0)
                                print("Quantity cannot be 0, please enter a valid quantity");
                        }catch(NumberFormatException e){
                            print("Quantity is of invalid format, please enter a valid quantity");
                        }
                    }while(Qty==0);
                    or.addAlaCarteOrderItem(++counter,a,Qty,o.getOrderId());
                    print("Item has been successfully added into the Order");
                }


            }
            else{
                Promotion p =  promoSetItems.get(choice-1-alarCarteItems.size());
                for (int i=0; i<o.getOrderItemList().size(); i++){
                    if (o.getOrderItemList().get(i).getOrderItemName().equals(p.getPackName())){
                        print("Enter Quantity");
                        int Qty = 0;
                        do {
                            try {
                                Qty = Integer.parseInt(sc.nextLine());
                                if (Qty == 0)
                                    print("Quantity cannot be 0, please enter a valid quantity");
                            } catch (NumberFormatException e) {
                                print("Quantity is of invalid format, please enter a valid quantity");
                            }
                        } while (Qty == 0);
                        int newQty = o.getOrderItemList().get(i).getOrderItemQty() + Qty;
                        or.updateOrderItemQty(o.getOrderItemList().get(i), newQty);
                        print("Item has been successfully added into the Order");
                        setter = true;
                        break;
                    }

                }
                if(!setter)
                {
                    int Qty = 0;
                    print("Enter Quantity: ");
                    do{
                        try
                        {
                            Qty = Integer.parseInt(sc.nextLine());
                            if(Qty == 0)
                                print("Quantity cannot be 0, please enter a valid quantity");
                        }catch(NumberFormatException e){
                            print("Quantity is of invalid format, please enter a valid quantity");
                        }
                    }while(Qty==0);
                    or.addPromoOrderItem(++counter, p, Qty,o.getOrderId());
                    print("Item has been successfully added into the Order");
                }
            }
        }
        updateOptions(or.getOrderById(o.getOrderId()));
    }

    /**
     * This method will display all the orderItems and prompts the staff to select an orderItem to be removed from the given order
     * then the staff can chooses to either remove an quantity or remove it completely.
     * Thus, it will calls either the updateOrderItemQty(..) or addPromoOrderItem(..) in the controller class to delete the selected orderItem.
     * @param o The order that is going to be deleted from
     * @throws IOException Display error message if any I/O error found while accessing the records.
     */
    private void deleteOrderItem(Order o) throws IOException {

        int choice = 0,  choice2 =0, qty;
        String format = "%-30s%s%n";
        String format1 = "%-27s%s%n";

        print("===================================");
        print("Select the Order item to be removed");
        print("===================================");
        print("===================================");
        printf(format1,"Item","Quantity");
        print("===================================");


        for(int i=0;i<o.getOrderItemList().size() ; i++ ) {
            System.out.printf(format,i+1 + ") " +o.getOrderItemList().get(i).getOrderItemName(),
                    o.getOrderItemList().get(i).getOrderItemQty());
        }
        System.out.print("Please enter your choice: ");
        do{
            try{
                choice = Integer.parseInt(sc.nextLine());
                if (choice == 0)
                    break;
                else if(choice<0 || choice > o.getOrderItemList().size())
                    print("Choice does not exist, please enter a valid choice: ");

            }catch(NumberFormatException e){
                print("Choice is of invalid format, please enter a valid choice: ");
            }

        }while(choice<0 || choice > o.getOrderItemList().size());

        if (choice != 0) {
            OrderItem oi = o.getOrderItemList().get(choice-1);
            if(oi.getOrderItemQty()>1)
            {
                print("1) Remove food item completely");
                print("2) Remove a qty");
                print("Please enter your choice" );
                do{
                    try{
                        choice2 = Integer.parseInt(sc.nextLine());
                        if (choice2<=0 || choice2>2)
                            print("Choice does not exist, please enter a valid choice: ");

                    }catch(NumberFormatException e){
                        print("Choice is of invalid format, Please enter a valid choice");
                    }
                }while(choice2<=0 || choice2>2);

                if (choice2 == 1)
                {
                    or.removeOrderItem(o.getOrderId(), o.getOrderItemList().get(choice - 1).getOrderItemId());
                    print("Order Item has been deleted successfully");
                    updateOptions(or.getOrderById(o.getOrderId()));
                }
                else {
                    int ogQty = oi.getOrderItemQty();
                    print("Input quantity to be removed");
                    do {
                        qty = Integer.parseInt(sc.nextLine());
                        if(qty > ogQty) {
                            print("Entered Quantity cannot be over than Item Quantity");
                            print("Please enter a valid Quantity");
                        }
                        else if(qty == 0)
                            print("0 is not a valid Quantity, please enter a valid Quantity: ");
                        else{
                            int newQty = ogQty - qty;
                            if (newQty == 0) {
                                or.removeOrderItem(o.getOrderId(), o.getOrderItemList().get(choice - 1).getOrderItemId());
                                print("Order Item has been deleted successfully");
                            }
                            else {
                                or.updateOrderItemQty(o.getOrderItemList().get(choice - 1), newQty);
                                print("Quantity has been updated successfully");
                            }
                        }
                    }while(qty==0 || qty>ogQty);
                }
            }
            else{
                or.removeOrderItem(o.getOrderId(), o.getOrderItemList().get(choice - 1).getOrderItemId());
                print("Order Item has been deleted successfully");
                updateOptions(or.getOrderById(o.getOrderId()));
            }
        }
        updateOptions(or.getOrderById(o.getOrderId()));
    }

    /**This method will display all the orders and prompts the staff to select an order to be deleted, so it will calls
     * the deleteOrder(..) in the controller class to remove the chosen order.
     * @throws IOException Display error message if any I/O error found while deleting the records.
     */
    private void deleteOrder() throws IOException{

        String format = "%-20s%s%n";
        int size, choice;

        if (OrderController.getUnpaidOrders() == null)
        {
            print("There are no orders for to be deleted");
            displayOption();
        }
        size = or.getUnpaidOrders().size();

        print("================================================");
        print("Select the Order you would like to delete ");
        print("================================================");

        if(or.getUnpaidOrders() != null) {
            for (int i = 0; i < size; i++) {
                Customer c = cc.getCustById(or.getUnpaidOrders().get(i).getCust().getCustId());
                print("("+(i+1)+")");
                printf(format,"Order Id :" ,String.valueOf(or.getUnpaidOrders().get(i).getOrderId()));
                printf(format,"Customer Name :" , c.getPersName());
                printf(format,"Table No :" ,String.valueOf(or.getUnpaidOrders().get(i).getTable().getTableNo()));
                print("");
            }
        }

        print("Enter 0 to return to the previous page");
        print("Please enter your choice: ");
        choice = 0;
        do{
            try {
                choice = Integer.parseInt(sc.nextLine());
                if(choice < 0 || choice>size) {
                    print("Choice does not exist, please enter a valid choice: ");
                }
                else if (choice == 0)
                    break;

            }catch(NumberFormatException e){
                print("Choice is of invalid format, please enter a valid choice: ");
            }
        }while(choice<0 || choice>size);

        if (choice != 0) {
            or.deleteOrder(OrderController.getUnpaidOrders().get(choice - 1).getOrderId());
            System.out.println("==================================");
            System.out.println("Order deleted successfully");
            System.out.println("==================================");
        }
        displayOption();
    }

    /**
     * This method will only display different order functions.
     */
    public void printOrderFormMenu()
    {
        print("=================================");
        print("\t Order Options ");
        print("=================================");
        print("1) Display Orders ");
        print("2) Create an Order ");
        print("3) Update an Order ");
        print("4) Delete an Order ");
        print("5) Back");
    }

    /**
     * This method displays the given message.
     * @param message The message to be displayed.
     */
    public void print(String message) {
        System.out.println(message);
    }

    /** This method displays the given message with formatting.
     * @param template The string template
     * @param arg1 The first argument string data to be formatted into the template
     * @param arg2 The second argument string data to be formatted into the template
     */
    public void printf(String format,String message,String message1){
        System.out.printf(format,message,message1);
    }

    /**
     * This methods displays the food menu with details in different categories (AlaCarte and Promotion Sets).
     * @throws IOException Display error message if any I/O error found while accessing the records.
     */
    public void printFoodMenu() throws IOException {

        String format = "%-20s%s%n";
        int i = 0, actualPrice=0;

        print("===========================================");
        print("RRPSS Menu");
        print("===========================================");
        print("");
        print("===========================================");
        print("Ala Carte Items");
        print("===========================================");
        ArrayList<AlaCarte> alarCarteItems = or.getAllAlaCartItems();
        for(AlaCarte item: alarCarteItems)
        {
            print("["+(i+1)+"]");
            printf(format,"Name: ",item.getAlaCarteName());
            printf(format, "Description: ", item.getAlaCarteDesc());
            System.out.printf(format, "Course Type: ", item.getFoodType());
            printf(format,"Price: ", "$"+df.format(item.getAlaCartePrice()));
            print("");
            i++;
        }
        print("=================================");
        print("Promotion Sets ");
        print("=================================");
        ArrayList<Promotion> promoSetItems = or.getAllPromoSets();

        for (Promotion promo: promoSetItems)
        {
            print("["+(i+1)+"]");
            printf(format,"Name: ", promo.getPackName());
            printf(format,"Description: ", promo.getPackDesc());
            printf(format,"Promotion Price: ", "$"+df.format(promo.getPackPrice()));
            for(int j=0; j<promo.getPackItems().size();j++)
                actualPrice+=promo.getPackItems().get(j).getAlaCartePrice();
            printf(format,"Actual Price: ", "$"+df.format(actualPrice));
            print("");
            i++;
        }

    }

}