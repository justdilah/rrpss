package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import entity.Table;
import entity.TableCapacity;
import entity.TableStatus;

/**
 * This class represents a controller class for the table.
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class ResTableController {

    /**
     * Initialized a list of tables to be stored under a restaurant.
     */
    private static List<Table> tables = new ArrayList<>();

    /**
     * This method will return a Table object that is allocated for a given reservation (resid) after searching through all the table records.
     * @param resid int value that uniquely identify a reservation.
     * @return Table object allocated to the reservation or null object if nothing is found.
     * @throws IOException Display error message if any I/O error found while retrieving the table records.
     */
    public Table getTableByResId(int resid) throws IOException
    {
        Table t = null;
        ArrayList<Table> tList = Table.getAllTableDetails();

        for(int i=0; i<tList.size();i++)
        {
            if(tList.get(i).getTableRes().getResId() == resid) {
                t = tList.get(i);
            }
        }
        return t;
    }

    /**
     * This method will return a Table object after searching through all the table records given its tableNo.
     * @param tableNo int value that uniquely identify a table.
     * @return Table object that belongs to the tableNo or null object if nothing is found.
     * @throws FileNotFoundException Display error message if any I/O error found while retrieving the table records.
     */
    public Table getTableByTableNo(int tableNo) throws FileNotFoundException
    {
        Table t = null;
        ArrayList<Table> tList = Table.getAllTableDetails();

        for(int i=0; i<tList.size();i++) {

            if(tList.get(i).getTableNo() == tableNo) {
                t = tList.get(i);
            }
        }
        return t;
    }

    /**
     * This method will return a ArrayList of Table object that are currently in "OCCUPIED" status after searching through all the table records.
     * @return ArrayList of occupied table or null if nothing is found.
     * @throws FileNotFoundException Display error message if any I/O error found while retrieving the table records.
     */
    public ArrayList<Table> getAllOccupiedTables() throws FileNotFoundException
    {
        ArrayList<Table> tableList = new ArrayList<>();
        ArrayList<Table> tList = Table.getAllTableDetails();

        int checker = 0;

        for(int i=0; i<tList.size();i++)
        {
            if(tList.get(i).getStatus().equals(TableStatus.OCCUPIED))
            {
                tableList.add(tList.get(i));
                checker++;
            }
        }

        if(checker == 0) {
            tableList = null;
        }
        return tableList;
    }


    /**
     * This method will return a ArrayList of Table object that are currently in "RESERVED" status after searching through all the table records.
     * @param date LocalDate variable that is passed into retrieve reserved tables of current date.
     * @return ArrayList of occupied table or null if nothing is found.
     * @throws FileNotFoundException Display error message if any I/O error found while retrieving the table records.
     */
    public ArrayList<Table> getAllReservedTables(LocalDate date) throws FileNotFoundException
    {
        ArrayList<Table> tableList = new ArrayList<>();
        ArrayList<Table> tList = Table.getAllTableDetails();

        int checker = 0;

        for(int i=0; i<tList.size();i++)
        {
            if(tList.get(i).getStatus().equals(TableStatus.RESERVED) && tList.get(i).getTableDate().isEqual(date))
            {
                tableList.add(tList.get(i));
                checker++;
            }
        }

        if(checker == 0) {
            tableList = null;
        }
        return tableList;
    }

    /**
     * This method will return a ArrayList of Table object that are currently in both "RESERVED" and "OCCUPIED" status after searching through all the table records.
     * @return a ArrayList of occupied table or null if nothing is found.
     * @throws FileNotFoundException Display error message if any I/O error found while retrieving the table records.
     */
    public ArrayList<Table> getAllReservedAndOccupiedTables() throws FileNotFoundException
    {
        ArrayList<Table> tableList = new ArrayList<>();
        ArrayList<Table> tList = Table.getAllTableDetails();

        int checker = 0;

        for(int i=0; i<tList.size();i++)
        {
            if(tList.get(i).getStatus().equals(TableStatus.OCCUPIED) || tList.get(i).getStatus().equals(TableStatus.RESERVED))
            {
                tableList.add(tList.get(i));
                checker++;
            }
        }

        if(checker == 0) {
            tableList = null;
        }
        return tableList;
    }

    /**
     * This method initializes all the tables that the restaurant has.
     */
    public void createCapTable()
    {
        addTable(1, 2);
        addTable(2, 2);
        addTable(3, 4);
        addTable(4, 4);
        addTable(5, 6);
        addTable(6, 6);
        addTable(7, 8);
        addTable(8, 8);
        addTable(9, 10);
        addTable(10, 10);
    }

    /**
     * This method creates a new Table object with a given id and its maximum capacity.
     * @param id int value that uniquely identify a table.
     * @param max int value that specify the capacity of a table.
     */
    public static void addTable(int id, int max)
    {
        tables.add(new Table(id, max));
    }

    /**
     * This method inserts a new record/row into Table records with the given parameters.
     * @param finaldate LocalDate value that indicate the date of the reservation
     * @param finaltime LocalTime value that indicate the time of the reservation
     * @param resid int value that represents its corresponding reservation of a table.
     * @param t Table object that is being assigned.
     * @throws IOException Display error message if any I/O error found while inserting into the table records.
     */
    public void reserveTable(LocalDate finaldate, LocalTime finaltime, int resid, Table t) throws IOException {

        t.insertTable(t.getTableNo(),t.getSeatCapacity(),finaldate,finaltime,resid);
    }

    /**
     * This method will first checks for any table that is not reserved with the given date and time, and secondly checks for whether its capacity is enough for the given pax while ensuring a bigger table will not be assigned. Lastly, it returns the first table found (if any).
     * @param finaldate LocalDate value that indicate the date of the reservation.
     * @param finaltime LocalTime value that indicate the time of the reservation.
     * @param pax int value that indicates the number of customers booking for the reservation.
     * @return Table object that is available to be reserved by the customer, or null if no such table is found.
     * @throws IOException Display error message if any I/O error found while inserting into the table records.
     */
    public Table checkForTable(LocalDate finaldate, LocalTime finaltime, int pax) throws IOException{
        Table temp = null;
        // get first available table
        for (Table table : tables)
        {
            int max = table.returnINTcapcity(table.getSeatCapacity());

            if (isFree(finaldate,finaltime,table.getTableNo()) && ((max-pax)==0 || (max-pax)==1))
            {
                temp = table;
            }
        }
        return temp;
    }

    /**
     * This method checks through the table records and return a boolean value to indicate if the given table is reserved or not.
     * @param tableNo int value that uniquely identify a table.
     * @return True if the given table is reserved, or false if the table in other status.
     * @throws FileNotFoundException Display error message if any error found while retrieving the table records.
     */
    public Boolean checkTableReserved(int tableNo) throws FileNotFoundException
    {
        if(getTableByTableNo(tableNo).getStatus().equals(TableStatus.RESERVED))
        {
            return true;
        }
        return false;
    }

    /**
     * This method checks whether the given reservation time is late by 10 minutes by comparing to the current time and return an int value.
     * @param currentT LocalTime value that represents current time.
     * @param resT LocalTime value that represents the time of the given reservation
     * @return The amount of late minutes if it is late by 10 minutes, or -1 (default value) if it is not late.
     */
    public int checkLateOrNot(LocalTime currentT, LocalTime resT)
    {
        int result = -1; // default value to indicate the table reservation is before current time, so is don't need to delete it
        if(currentT.isAfter(resT)) { //for those table reservation time that is before current time, we ignore them. Thus we only check those after (potential late res)
            int late_mins = (int) resT.until(currentT, ChronoUnit.MINUTES); //this will return how many minutes they are late
            if (late_mins>10) { //if the customer is 10 minutes late
                return late_mins; // return a value that is not -1, meaning that reservation should be deleted.
            }
        }
        return result; //return default value -1
    }

    /**
     * This methods check whether the given table is available for reservation under the given date and time by comparing it with all the table records and return a boolean result.
     * @param arrDate LocalDate value that indicate the date of the reservation.
     * @param arrTime LocalTime value that indicate the time of the reservation.
     * @param tableNo int value that uniquely identify a table to be checked.
     * @return True if the table is available for reservation, or false if someone has booked the table with the given date and time.
     * @throws IOException Display error message if any I/O error found while retrieving into the table records.
     */
    public boolean isFree(LocalDate arrDate, LocalTime arrTime, int tableNo) throws IOException
    {
        boolean free = true;
        ArrayList<Table> tList = Table.getAllTableDetails();

        for(int i=0; i<tList.size();i++)
        { //if cannot find any matching table = the table is vacant
            if(tList.get(i).getTableNo()==tableNo) //if tableno is equal
            {
                if(tList.get(i).getTableDate().equals(arrDate)) { //if date is equal then compare time
                    if(tList.get(i).getTableTime().equals(arrTime))  //means this specific table is taken
                        //if(!getAllTableDetails().get(i).getStatus().equals(TableStatus.VACANT))
                        return false;
                    else if((arrTime.isAfter(tList.get(i).getTableTime())) &&
                            (arrTime.isBefore(tList.get(i).getTableTime().plusHours(2)))) { //means the user selected time within 2 hours of some reservation
                        return false;
                    }
                }
            }
            else {
                //do nothing
            }

        }
        return free;
    }


    /**
     * This method updates the status of a given table.
     * @param status String value that represents the new status of the table to be updated.
     * @param tableNo int value that uniquely identify a table to update its status.
     * @throws IOException Display error message if any I/O error found while updating into the table records.
     */
    public void updateTableStatusString(String status,int tableNo) throws IOException
    {
        TableStatus t = null;

        switch(status) {
            case "OCCUPIED":
                t = TableStatus.OCCUPIED;
                break;
            case "VACANT":
                t = TableStatus.VACANT;
                break;
            case "RESERVED":
                t = TableStatus.RESERVED;
                break;
        }
        Table table = getTableByTableNo(tableNo);
        table.setStatus(t);
        updateTableStatus(table);
    }

    /**
     * This method updates the pax of a given table and reassign a new Table.
     * @param t Table object that is to be updated.
     * @param resid int value that uniquely identify a reservation that belongs to the table.
     * @param tno int value indicates the new table number to be assigned.
     * @throws IOException Display error message if any I/O error found while updating into the table records.
     */
    public void updateTablePax(Table t, int resid, int tno) throws IOException
    {
        Table te;
        te = tables.get(tno-1);
        TableCapacity capacity = te.getSeatCapacity();
        t.setTableNo(tno);
        t.setSeatCapacity(capacity);
        Table.updateTable(t, resid);
    }


    /**
     *  This method updates the date of a given table and reassign a new Table.
     * @param t Table object that is to be updated.
     * @param resid int value that uniquely identify a reservation to that belongs to the table.
     * @param tno int value indicates the new table number to be assigned.
     * @param date String value indicates the new date to be updated.
     * @throws IOException Display error message if any I/O error found while updating into the table records.
     */
    public void updateTableDate(Table t, int resid, int tno, String date) throws IOException
    {
        Table te;
        te= tables.get(tno-1);
        TableCapacity capacity = te.getSeatCapacity();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate convertdate = LocalDate.parse(date,formatter);
        t.setTableDate(convertdate);
        t.setTableNo(tno);
        t.setSeatCapacity(capacity);

        Table.updateTable(t, resid);
    }

    /**
     * This method updates the time of a given table and reassign a new Table.
     * @param t Table object that is to be updated.
     * @param resid int value that uniquely identify a reservation to that belongs to the table.
     * @param tno int value indicates the new table number to be assigned.
     * @param time String value indicates the new time to be updated.
     * @throws IOException Display error message if any I/O error found while updating into the table records.
     */
    public void updateTableTime(Table t, int resid, int tno, String time) throws IOException
    {
        Table te;
        te = tables.get(tno-1);
        TableCapacity capacity = te.getSeatCapacity();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime converttime = LocalTime.parse(time,formatter);
        t.setTableTime(converttime);
        t.setTableNo(tno);
        t.setSeatCapacity(capacity);
        Table.updateTable(t,resid);
    }


    /**
     * This method calls the Table entity to update the status of a given table.
     * @param table Table object that is to be updated.
     * @throws IOException Display error message if any I/O error found while updating into the table records.
     */
    public void updateTableStatus(Table table) throws IOException
    {
        Table.updateTableStatus(table);
    }

    /**
     * This method calls the Table entity to delete a table given its reservation ID that belongs to it.
     * @param resID int value that is unique across all tables records to identify which table to be removed.
     * @throws IOException Display error message if any I/O error found while deleting the table records.
     */
    public void deleteTable(int resID) throws IOException
    {
        Table.deleteTable(resID);
    }

    /**
     * This method will checks for any table is before today and remove them while storing their corresponding reservation ID in an ArrayList and return them.
     * @return ArrayList<Integer> that consists a list of reservation ID that belongs to the deleted tables.
     * @throws IOException Display error message if any I/O error found while deleting the table records.
     */
    public ArrayList<Integer> deletePastReservationTable() throws IOException
    {
        ArrayList<Integer> listOfDelete = new ArrayList<Integer>();

        try {
            ArrayList<Table> tlist= new ArrayList<>(Table.getAllTableDetails());

            for (int i=0;i<tlist.size();i++)
            {
                LocalDate tableDate = tlist.get(i).getTableDate(); //get the current table date
                LocalTime table = tlist.get(i).getTableTime(); //get the current table time
                LocalDate todayDate = LocalDate.now();
                LocalTime currTime = LocalTime.now();

                int temp_resID;
                if(tableDate.isBefore(todayDate)) { // those table reservation that is before today's date
                    temp_resID = tlist.get(i).getTableRes().getResId();
                    Table.deleteTable(temp_resID);
                    listOfDelete.add(temp_resID);
                }
                else {
                    //do nothing
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return listOfDelete;
    }

    /**
     * This method will checks for any table that is late by 10 minutes and remove them while storing their corresponding reservation ID in an ArrayList and return them.
     * @return ArrayList<Integer> that consists a list of reservation ID that belongs to the deleted tables.
     * @throws IOException Display error message if any I/O error found while deleting the table records.
     */
    public ArrayList<Integer> deleteLateComerTable() throws IOException
    {
        ArrayList<Integer> listOfDelete = new ArrayList<Integer>();

        try {
            ArrayList<Table> tlist= new ArrayList<>(Table.getAllTableDetails());

            for (int i=0;i<tlist.size();i++)
            {
                LocalDate tableDate = tlist.get(i).getTableDate(); //get the current table date
                LocalTime tableTime = tlist.get(i).getTableTime(); //get the current table time
                LocalDate todayDate = LocalDate.now();
                LocalTime currTime = LocalTime.now();
                TableStatus tempStatus = tlist.get(i).getStatus();

                int temp_resID;
                if((tableDate.equals(todayDate)) && (tempStatus.equals(TableStatus.RESERVED))) { //those reservation that is booked today AND those reserved one
                    int late = checkLateOrNot(currTime,tableTime); //if -1 means OK, if not: late
                    if (late!=-1) { // this table needs to be deleted
                        temp_resID = tlist.get(i).getTableRes().getResId();
                        Table.deleteTable(temp_resID);
                        listOfDelete.add(temp_resID);
                    }
                }
                else {
                    //do nothing
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return listOfDelete;
    }

}
