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
import entity.TableStatus;

public class ResTableController {

    private static List<Table> tables = new ArrayList<>();

    public Table getTableByResId(int resid) throws IOException
    {
        Table t = new Table();
        ArrayList<Table> tList = Table.getAllTableDetails();

        for(int i=0; i<tList.size();i++)
        {
            if(tList.get(i).getTableRes().getResId() == resid) {
                t = tList.get(i);
            }
        }
        return t;
    }

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


    public ArrayList<Table> getAllReservedTables() throws FileNotFoundException
    {
        ArrayList<Table> tableList = new ArrayList<>();
        ArrayList<Table> tList = Table.getAllTableDetails();

        int checker = 0;

        for(int i=0; i<tList.size();i++)
        {
            if(tList.get(i).getStatus().equals(TableStatus.RESERVED))
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

    public static void addTable(int id, int max)
    {
        tables.add(new Table(id, max));
    }

//    public Table reserveTable(LocalDate finaldate, LocalTime finaltime, int pax, int resid) throws IOException
//    {
//        Table temp = null;
//
//        // get first available table
//        for (Table table : tables)
//        {
//            int max = table.returnINTcapcity(table.getSeatCapacity());
//
//            if (isFree(finaldate,finaltime,table.getTableNo()) && ((max-pax)==0 || (max-pax)==1))
//            {
//                table.insertTable(table.getTableNo(), table.getSeatCapacity(), finaldate, finaltime, resid);
//
//                temp = table;
//            }
//        }
//        return temp;
//    }

    public void reserveTable(LocalDate finaldate, LocalTime finaltime, int resid, Table t) throws IOException {

        t.insertTable(t.getTableNo(),t.getSeatCapacity(),finaldate,finaltime,resid);
    }

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



    public Table checkAvailTable(LocalDate date, LocalTime time, int numOfGuests, int resid) throws IOException
    {
        Table temp = null;

        for (Table table : tables)
        {
            int max = table.returnINTcapcity(table.getSeatCapacity());

            if (isFree(date,time,table.getTableNo()) && ((max-numOfGuests)==0 || (max-numOfGuests)==1))
            {
                temp = table;
            }
        }
        return temp;
    }

    public Boolean checkTableReserved(int tableNo) throws FileNotFoundException
    {
        if(getTableByTableNo(tableNo).getStatus().equals(TableStatus.RESERVED))
        {
            return true;
        }
        return false;
    }

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

    public void updateTablePax(Table t, int resid, int tno, int pax) throws IOException
    {
        t.setTableNo(tno);
        Table.updateTable(t, resid);
    }


    public void updateTableDate(Table t, int resid, int tno, String date) throws IOException
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate convertdate = LocalDate.parse(date,formatter);
        t.setTableDate(convertdate);
        t.setTableNo(tno);

        Table.updateTable(t, resid);
    }

    public void updateTableTime(Table t, int resid, int tno, String time) throws IOException
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime converttime = LocalTime.parse(time,formatter);
        t.setTableTime(converttime);
        Table.updateTable(t,resid);
    }


    public void updateTableStatus(Table table) throws IOException
    {
        Table.updateTableStatus(table);
    }

    public void deleteTable(int resID) throws IOException
    {
        Table.deleteTable(resID);
    }

    public ArrayList<Integer> deleteLateComerTable() throws IOException
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

    public ArrayList<Integer> deletePastReservationTable() throws IOException
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
