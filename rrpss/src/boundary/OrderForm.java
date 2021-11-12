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
import entity.PromotionSet;
import entity.Staff;
import entity.Table;

public class OrderForm {

    private final OrderController or = new OrderController();
    private final OrderItemController OI = new OrderItemController();
    private final CustomerController cc = new CustomerController();
    private final TableController tc = new TableController();
    private final StaffController scontrol = new StaffController();

    private static final Scanner sc = new Scanner(System.in);
    private static final DecimalFormat df= new DecimalFormat("0.00");

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

    //Part is not done
    private void displayOrder(){

    }

    //Creation of Order
    private void insertOrder() throws IOException{

        int tableNo =0, index = 1;
        int staffid= 0;
        String custNo ="";

        System.out.println("=================================");
        System.out.println("\t Create Order ");
        System.out.println("=================================");

        if(tc.getAllReservedAndOccupiedTables() == null)
        {
            print("No Reserved Tables at the moment");
            displayOption();
        }


        System.out.println("Please enter your choice: ");


        for(int i=0;i<tc.getAllReservedAndOccupiedTables().size();i++) {
            System.out.println(i+1 + ") Table No " + tc.getAllReservedAndOccupiedTables().get(i).getTableNo());
        }

        Table t = null;

        do {
            try {
                tableNo = Integer.parseInt(sc.nextLine());
                t = tc.getAllReservedAndOccupiedTables().get(tableNo - 1);
                System.out.println(t.getTableNo());
                if (t == null)
                    print("Table does not exist, please enter a valid table number: ");
            }catch(NumberFormatException e){
                print("Table Number is of invalid format, please enter a valid table number: ");
            }
        }while(t==null);

        index = or.getIndexFromOICsv(index);

        print("Please enter Staff ID: ");
        Staff s = new Staff();
        do {
            try{
                staffid = Integer.parseInt(sc.nextLine());
                if(!s.isIdExists(staffid)) {
                    print("Staff ID des not exist");
                    print("Please enter a valid Staff ID: ");
                }
            }catch(NumberFormatException e){
                print("Staff ID is not of valid format, please enter a valid Staff ID: ");
            }
        }while(!s.isIdExists(staffid));

        print("Please enter Customer Phone Number: ");
        Customer c = new Customer();
        do{
            custNo = sc.nextLine();
            if(custNo.trim().isEmpty())
                print("Customer Phone Number cannot be empty, please enter Customer Phone Number: ");
            else if(!c.custExists(custNo))
                print("Customer Phone Number does not exist, please enter Customer Phone Number: ");
        }while(!c.custExists(custNo)||custNo.trim().isEmpty());

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
    //insert Order Calls to insertOrderItems function
    private void insertOrderItems(int orderId, int counter) throws IOException {

        String format = "%-30s%s%n";
        String format1 = "%-27s%s%n";

        int i,c=0, indexes, qty=0, j,size;
        boolean setter;

        ArrayList<AlaCarte> alarCarteItems = or.getAllAlaCartItems();
        ArrayList<PromotionSet> promoSetItems = or.getAllPromoSets();
        printFoodMenu();
        i= alarCarteItems.size()+ promoSetItems.size();
        indexes = i-1;

        print("Please enter your choice: ");
        do{
            try {
                c = Integer.parseInt(sc.nextLine());
                if (c <= 0 || c > indexes)
                    print("Choice does not exist, please enter a valid choice: ");
            }catch(NumberFormatException e){
                print("Choice is of invalid format, please enter a valid choice: ");
            }
        }while(c<=0 || c>indexes);

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
            PromotionSet p =  promoSetItems.get(c-1-alarCarteItems.size());
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
                else if(c<0||c>indexes)
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
                                OI.updateOrderItemQty(oiList.get(j), newQty);
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
                        PromotionSet p =  promoSetItems.get(c-1-alarCarteItems.size());
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
                                OI.updateOrderItemQty(oiList.get(j), newQty);
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

    private void updateOrder() throws IOException {

        String format = "%-20s%s%n";
        int choice = 0, size = 0;

        print("================================================");
        print("Select the Order you would like to update ");
        print("================================================");
        if (or.getUnpaidOrders() == null)
        {
            print("There are no orders for updating");
            displayOption();
        }

        Customer c;
        size = or.getUnpaidOrders().size();
        ArrayList<Order> uorder = or.getUnpaidOrders();

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
                if(choice<0||choice>or.getUnpaidOrders().size())
                    print("Choice does not exist, please enter a valid choice: ");

            }catch(NumberFormatException e) {
                print("Choice is of invalid format, please enter a valid choice: ");
            }
        }while(choice<0||choice>or.getUnpaidOrders().size());

        if (choice==0)
            displayOption();

        else
            updateOptions(or.getUnpaidOrders().get(choice-1));


    }

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
                case 1 -> {
                    addOrderItem(o, o.getOrderItemList().size());
                }
                case 2 -> {
                    deleteOrderItem(o);
                }
            }
        }while(choice!=0);
        updateOrder();
    }

    //FOR UPDATE PORTION
    private void addOrderItem(Order o, int counter) throws IOException {

        int size, choice=0;
        boolean setter;

        String format = "%-30s%s%n";
        String format1 = "%-27s%s%n";

        ArrayList<AlaCarte> alarCarteItems = or.getAllAlaCartItems();
        ArrayList<PromotionSet> promoSetItems = or.getAllPromoSets();

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
                        OI.updateOrderItemQty(o.getOrderItemList().get(i), newQty);
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
                PromotionSet p =  promoSetItems.get(choice-1-alarCarteItems.size());
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
                        OI.updateOrderItemQty(o.getOrderItemList().get(i), newQty);
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

    //FOR UPDATE PORTION (Finished)
    private void deleteOrderItem(Order o) throws IOException {

        int choice = 0,  choice2 =0, qty =0;
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
                    print("Input quantity to be removed");
                    do {
                        qty = Integer.parseInt(sc.nextLine());
                        if(qty > oi.getOrderItemQty()) {
                            print("Entered Quantity cannot be over than Item Quantity");
                            print("Please enter a valid Quantity");
                        }
                        else if(qty == 0)
                            print("0 is not a valid Quantity, please enter a valid Quantity: ");
                        else{
                            int newQty = oi.getOrderItemQty() - qty;
                            OI.updateOrderItemQty(o.getOrderItemList().get(choice-1),newQty);
                            print("Quantity has been updated successfully");
                        }
                    }while(qty==0 || qty>oi.getOrderItemQty());
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


    private void deleteOrder() throws IOException{

        String format = "%-20s%s%n";
        int size, choice;

        size = or.getUnpaidOrders().size();
        if(size == 0) {
            print("================================================");
            print("There are no orders to be deleted ");
            print("================================================");
            displayOption();
        }

        print("================================================");
        print("Select the Order you would like to delete ");
        print("================================================");

        if(or.getUnpaidOrders() != null) {
            for (int i = 0; i < size; i++) {
                Customer c = cc.getCustByIds(or.getUnpaidOrders().get(i).getCust().getCustId());
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
            or.deleteOrder(or.getUnpaidOrders().get(choice - 1).getOrderId());
            System.out.println("==================================");
            System.out.println("Order deleted successfully");
            System.out.println("==================================");
        }
        displayOption();
    }

    //Print Menu/Misc
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

    public void print(String message) {
        System.out.println(message);
    }

    public void printf(String format,String message,String message1){
        System.out.printf(format,message,message1);
    }

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
        ArrayList<PromotionSet> promoSetItems = or.getAllPromoSets();

        for (PromotionSet promo: promoSetItems)
        {
            print("["+(i+1)+"]");
            printf(format,"Name: ", promo.getPackName());
            printf(format,"Description: ", promo.getPackDesc());
            printf(format,"Promotion Price: ", "$"+df.format(promo.getPackPrice()));
            for(int j=0; j<promo.getPackItem().size();j++)
                actualPrice+=promo.getPackItem().get(j).getAlaCartePrice();
            printf(format,"Actual Price: ", "$"+df.format(actualPrice));
            print("");
            i++;
        }

    }

}
