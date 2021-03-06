package boundary;

import entity.AlaCarte;
import entity.FoodType;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import controller.AlaCarteController;


/**This class represents the boundary class for the Ala Carte class
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 *
 */
public class AlaCarteForm {


    /**
     * Control class for Ala Carte
     */
    AlaCarteController ac = new AlaCarteController();
    /**
     * Scanner class to read the user's inputs
     */
    Scanner sc = new Scanner(System.in);

    /**
     * initialise DecimalFormat df variable
     */
    DecimalFormat df= new DecimalFormat("0.00");


    /**
     * This method calls the displayAlaCarteOptions method to display the Ala Carte options
     * @throws IOException Display error message if any I/O error found while retrieving from the ala carte records.
     */
    public void displayOption() throws IOException{
        int choice;

        displayAlaCarteOptions();
        print("Please enter your choice: ");
        do{
            choice = Integer.parseInt(sc.nextLine());
            try {
                switch (choice) {
                    case 1 -> displayAlaCarte();

                    case 2 -> insertAlaCarte();

                    case 3 -> updateAlaCarte();

                    case 4 -> deleteAlaCarte();

                    case 5 -> MainAppUI.print();

                }
            }catch(NumberFormatException e){
                print("Choice is of invalid format, please enter a valid choice: ");
            }
        }while(choice>4 || choice < 1);
    }

    /**This method calls getAllAlaCarteItems method in the controller class to display the Ala Carte items
     * @throws IOException Display error message if any I/O error found while retrieving from the ala carte records.
     */
    private void displayAlaCarte() throws IOException{

        int choice, c;

        print("====================================");
        print("Ala Carte Items");
        print("====================================");
        print("Select item number for more details");
        print("====================================");
        displayAlaCarteItems();
        print("Enter 0 to return to the previous page");
        print("Please enter your choice: ");

        do{
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice<0 || choice > AlaCarteController.getAllAlaCarteItems().size())
                    print("Choice does not exist, please enter a valid choice: ");
            }catch(NumberFormatException e){
                print("Choice is of invalid format, please enter a valid choice: ");
                choice = -1;
            }
        }while(choice<0 || choice > AlaCarteController.getAllAlaCarteItems().size());

        if (choice == 0)
            displayOption();
        else{
            AlaCarte a = AlaCarteController.getAllAlaCarteItems().get(choice-1);
            print("==================================");
            print("\t Food item details ");
            print("==================================");
            print("Name: " + a.getAlaCarteName());
            print("Description: " + a.getAlaCarteDesc());
            print("Price: $" + df.format(a.getAlaCartePrice()));
            print("Food Type: " + a.getFoodType());
            print("Press 0 to return to the menu");
            do{
                try{
                    c = Integer.parseInt(sc.nextLine());
                    if(c!=0)
                        print("Press 0 to return to the menu");
                }catch(NumberFormatException e){
                    print("Press 0 to return to the menu");
                    c=-1;
                }
            }while(c!=0);
            displayAlaCarte();
        }
    }


    /**This method calls the addAlaCarte method in the controller class to add an Ala Carte item according
     * to the user's inputs
     * @throws IOException Display error message if any I/O error found while inserting into the ala carte records.
     */
    private void insertAlaCarte() throws IOException {
        String AlaCarteName, desc;
        boolean setter = true, fail = true;
        double price= 0;

        print("==================================");
        print("Add an Ala Carte item");
        print("==================================");
        print("Enter Ala Carte name: ");
        do
        {
            AlaCarteName = sc.nextLine();
            if (AlaCarteName == null || AlaCarteName.trim().isEmpty())
                print("Ala Carte name must not be empty, please enter a Ala Carte name: ");
            else
            {
                AlaCarte a = AlaCarteController.getAlaCarteByName(AlaCarteName);
                if (a == null)
                    setter = false;
                else
                    print("Ala Carte name already exist, please enter another Ala Carte name: ");
            }
        }while(setter);

        setter = true;
        print("Enter Ala Carte Description: ");
        do{
            desc = sc.nextLine();
            if (desc == null || desc.trim().isEmpty()) {
                print("Ala Carte Description must not be empty, please enter a description: ");
            }
            else
                setter = false;
        }while(setter);

        String newdesc = desc.replace(',', '/');
        print("Enter price for Ala Carte item: ");
        do{
            try{
                price = Double.parseDouble(sc.nextLine());
                fail = (BigDecimal.valueOf(price).scale()>2);
                if(fail)
                    print("Please enter the correct format for price, e.g $5.00: ");
            }catch(NumberFormatException e) {
                print("Price is of invalid format, please enter a valid price e.g $5.00: ");
            }
        }while(fail);
        FoodType ft = foodTypeOptions();
        try {
            ac.addAlaCarte(AlaCarteName, newdesc, price,ft);
            print("==================================");
            print( AlaCarteName + " added successfully");
            print("==================================");
            displayOption();
        } catch (Exception ex) {
            print("==================================");
            print("Unsuccessful. Please try again");
            print("==================================");
            insertAlaCarte();
        }
    }


    /**This method calls getAlaCarteByName method in the controller class to update the specific Ala Carte item
     * according to the user's inputs
     * @throws IOException Display error message if any I/O error found while inserting into the ala carte records.
     */
    private void updateAlaCarte() throws IOException{

        int choice, choice2;

        print("================================================");
        print("Select the Food Item you would like to update ");
        print("================================================");
        displayAlaCarteItems();

        print("Enter 0 to return to the previous page");
        print("Please enter your choice: ");
        do{
            try{
                choice = Integer.parseInt(sc.nextLine());
                if (choice<0 || choice> AlaCarteController.getAllAlaCarteItems().size())
                    print("Choice does not exist, please enter a valid choice: ");

            }catch(NumberFormatException e){
                print("Choice is of invalid format, please enter a valid choice: ");
                choice = -1;
            }

        }while(choice<0|| choice> AlaCarteController.getAllAlaCarteItems().size());

        if (choice == 0)
            displayOption();

        AlaCarte a = AlaCarteController.getAlaCarteByName(AlaCarteController.getAllAlaCarteItems().get(choice-1).getAlaCarteName());

        while(true)
        {
            print("=================================");
            print("Update Food Item Options ");
            print("=================================");
            print("1) Edit Food Item Name");
            print("2) Edit Food Description ");
            print("3) Edit Food Price ");
            print("4) Edit Food Type");
            print("5) Back");

            print("Enter your choice: ");

            do{
                try {
                    choice2 = Integer.parseInt(sc.nextLine());
                }catch(NumberFormatException e){
                    print("Choice is of invalid format, please enter a valid choice: ");
                    choice2=0;
                }
            }while(choice2 <1 || choice2>5);

            if (choice2 == 5)
                break;

            switch(choice2)
            {
                case 1->{
                    String oldname = a.getAlaCarteName();
                    setAlaCarteName(a);
                    print("Ala Carte Item Name "+oldname+" has been changed to "+a.getAlaCarteName());
                }
                case 2->{
                    String olddesc = a.getAlaCarteDesc();
                    setAlaCarteDesc(a);
                    print("Ala Carte Item Desc "+olddesc+" has been changed to "+a.getAlaCarteDesc());
                }
                case 3->{
                    double oldprice = a.getAlaCartePrice();
                    double price = 0;
                    boolean fail = true;
                    print("Enter new Ala Carte Item Price (Original Price: $"+df.format(oldprice)+"): ");
                    do{
                        try{
                            price = Double.parseDouble(sc.nextLine());
                            fail = (BigDecimal.valueOf(price).scale()>2);
                            if(fail)
                                print("Please enter the correct format for price (Original Price: $"+df.format(oldprice)+"): ");
                        }catch(NumberFormatException e) {
                            print("Price is of invalid format, please enter a valid price: (Original Price: $"+df.format(oldprice)+"): ");
                        }
                    }while(fail);
                    ac.updateAlaCartePrice(a,price);
                    print("Ala Carte Item Price $"+df.format(oldprice)+" has been changed to $" +df.format(a.getAlaCartePrice()));
                }
                case 4->{
                    print("Edit Food Type");
                    FoodType oldft =a.getFoodType();
                    FoodType ft = foodTypeOptions();
                    ac.updateAlaCarteType(a, ft);
                    print("Ala Carte item Food Type "+oldft+" has been changed to "+a.getFoodType());

                }
                default -> print("Choice does not exist, please enter a valid choice: ");
            }
        }
        updateAlaCarte();
    }

    /**This method calls the deleteAlaCarteItem method in the controller class to delete the specific
     * Ala Carte item according to the user's inputs
     * @throws IOException Display error message if any I/O error found while inserting into the ala carte records.
     */
    private void deleteAlaCarte() throws IOException{

        int choice;

        print("================================================");
        print("Select the Food Item you would like to delete ");
        print("================================================");
        displayAlaCarteItems();
        print("Enter 0 to return to the previous page");
        print("Please enter your choice: ");
        do{
            try{
                choice = Integer.parseInt(sc.nextLine());
                if (choice < 0 || choice > AlaCarteController.getAllAlaCarteItems().size())
                    print("Choice does not exist, please enter a valid choice: ");
            }catch(NumberFormatException e){
                print("Choice is of invalid format, please enter a valid choice: ");
                choice = -1;
            }
        }while(choice < 0 || choice > AlaCarteController.getAllAlaCarteItems().size());

        if (choice == 0)
            displayOption();
        else
        {
            try {
                AlaCarte c = AlaCarteController.getAlaCarteByName(AlaCarteController.getAllAlaCarteItems().get(choice-1).getAlaCarteName());
                String deletedname = c.getAlaCarteName();
                ac.deleteAlaCarteItem(c);
                System.out.println("==================================");
                System.out.println(deletedname+ " deleted successfully");
                System.out.println("==================================");
                displayOption();

            } catch (Exception ex) {
                System.out.println("==================================");
                System.out.println("Unsuccessful. Please try again");
                System.out.println("==================================");
                deleteAlaCarte();
            }
        }
    }

    /**This method calls the updateAlaCarteName method in the controller class to update the name of the
     * Ala Carte item according to the user's input
     * @param c Ala Carte Object
     * @throws IOException Display error message if any I/O error found while inserting into the ala carte records.
     */
    private void setAlaCarteName(AlaCarte c) throws IOException {
        String AlaCarteName;
        boolean setter = true;
        String oldname = c.getAlaCarteName();
        print("Enter New Ala Carte Item Name (Original Name: "+oldname+"): ");
        do
        {
            AlaCarteName = sc.nextLine();
            if (AlaCarteName == null || AlaCarteName.trim().isEmpty())
                print("Ala Carte name must not be empty, please enter a Ala Carte name (Original Name:" +oldname+"): ");
            else
            {
                AlaCarte a = AlaCarteController.getAlaCarteByName(AlaCarteName);
                if (a == null)
                    setter = false;
                else
                    print("Ala Carte name already exist, please enter another Ala Carte name (Original Name:" +oldname+"): ");
            }
        }while(setter);
        ac.updateAlaCarteName(c, AlaCarteName);
    }

    /**This method calls updateAlaCarteDesc method in the controller class to update the description of the
     * Ala Carte item according to the user's input
     * @param c Ala Carte Object
     * @throws IOException Display error message if any I/O error found while inserting into the ala carte records.
     */
    private void setAlaCarteDesc(AlaCarte c) throws IOException{

        String desc;
        boolean setter = true;
        String olddesc = c.getAlaCarteDesc();

        print("Enter Ala Carte Description: (Original Description: " +olddesc+ "): ");
        do{
            desc = sc.nextLine();
            if (desc == null || desc.trim().isEmpty()) {
                print("Ala Carte Description must not be empty, please enter a description (Original Description: " +olddesc+"): ");
            }
            else
                setter = false;
        }while(setter);

        String newdesc = desc.replace(',', '/');
        ac.updateAlaCarteDesc(c, newdesc);
    }

    /**This method displays the Food Type options and prompts the user to choose a Food Type for the Ala Carte
     * item
     * @return FoodType Object
     */
    private FoodType foodTypeOptions()
    {
        int choice =0, i;
        print("====================");
        print("Choose the food type");
        print("====================");
        for(i=0; i<FoodType.values().length; i++)
            print((i+1)+") "+FoodType.values()[i]);

        print("Please enter your choice: ");
        do {
            try {
                choice = Integer.parseInt(sc.nextLine());
                if(choice<1 || choice > FoodType.values().length){
                    print("Choice does not exist, please enter a valid choice: ");
                }
            }catch(NumberFormatException e){
                print("Choice is of invalid format, please enter a valid choice: ");
            }
        }while(choice<1 || choice>FoodType.values().length);

        FoodType ft;
        ft = FoodType.values()[choice-1];
        return ft;
    }


    /**This method calls the getAllAlaCarteItems method in the controller class to display all the Ala Carte items
     * @throws IOException Display error message if any I/O error found while inserting into the ala carte records.
     */
    private void displayAlaCarteItems() throws IOException
    {
        int i;
        String format = "%-30s%s%n";

        printf(format,"AlaCarte Name","Price");
        print("====================================");
        for(i=0; i< AlaCarteController.getAllAlaCarteItems().size() ; i++ ) {
            printf(format,i+1 + ") "+ AlaCarteController.getAllAlaCarteItems().get(i).getAlaCarteName()
                    ,"$"+df.format(AlaCarteController.getAllAlaCarteItems().get(i).getAlaCartePrice()));
        }
    }


    /**
     * This method is used to display the Ala Carte options
     */
    private void displayAlaCarteOptions()
    {
        print("=================================");
        print("\t Menu Options ");
        print("=================================");
        print("1) Display Ala Carte Items ");
        print("2) Add an Ala Carte Item ");
        print("3) Update Ala Carte Details ");
        print("4) Delete Ala Carte Item ");
        print("5) Back");
    }


    /**This method is used to replace System.out.println method to a shorter method name
     * @param message string value
     */
    public void print(String message)
    {
        System.out.println(message);
    }


    /**This method is used to replace the System.out.printf method to a shorter method name
     * @param format string value
     * @param message string value
     * @param message1 string value
     */
    public void printf(String format,String message,String message1){
        System.out.printf(format,message,message1);
    }



}