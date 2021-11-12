package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import entity.Reservation;
import entity.Table;

public class ResTableController {

    Table t = new Table();

    public void createCapTable()
    {
        t.createCapTable();
    }

    public Table reserveTable(LocalDate finaldate, LocalTime finaltime, int pax, int resid) throws IOException
    {
        // call method a
        return t.reserveTable(finaldate, finaltime, pax, resid);
    }

    public Table checkAvailTable(LocalDate date, LocalTime time, int numOfGuests, int resid) throws IOException
    {
        return t.checkAvailTable(date, time, numOfGuests, resid);
    }

    public Table getTableByResId(int id) throws IOException
    {
        return t.getTableByResId(id);
    }

    public void updateTablePax(Table t, int resid, int tno, int pax) throws IOException
    {

        t.setTableNo(tno);
        t.updateTable(t, resid);
    }


    public void updateTableDate(Table t, int resid, int tno, String date) throws IOException
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate convertdate = LocalDate.parse(date,formatter);
        t.setTableDate(convertdate);

        t.setTableNo(tno);

        t.updateTable(t, resid);
    }

    public void updateTableTime(Table t, int resid, int tno, String time) throws IOException
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime converttime = LocalTime.parse(time,formatter);
        t.setTableTime(converttime);
        t.updateTable(t,resid);
    }


    public void updateTableStatus(Table table) throws IOException
    {
        t.updateTableStatus(table);
    }

    public void deteleTable(int resID) throws IOException
    {
        t.deleteTable(resID);
    }

    public ArrayList<Integer> deleteLateComerTable() throws IOException
    {
        return t.deleteLateComerTable();
    }

    public ArrayList<Integer> deletePastReservationTable() throws IOException
    {
        return t.deletePastReservationTable();
    }
}
