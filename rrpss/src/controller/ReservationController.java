package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import entity.Reservation;

public class ReservationController {
    
    Reservation robj = new Reservation();
    
    public void addReservation(String name, String contact, int pax, LocalDate date, LocalTime time, int custid, int staffid) throws IOException 
    {   
        robj.saveReservation(name, contact, pax, date, time, custid, staffid);
    }

    public Reservation getReservationByContact(String contact) throws NumberFormatException, IOException
    {
        return robj.getResByContact(contact);
        
    }

    public ArrayList<Reservation> getAllReservation() throws NumberFormatException, IOException
    {
        return robj.getAllReservationDetails();
    }


    public void removeReservation(Reservation r) throws IOException
    {
        robj.deleteReservation(r);
    }


//    public void checkAvailability(int pax) throws IOException 
//    {
//    	robj.allocateTable(pax);
//    }
    
    
    public void updateReservationPax(Reservation r, int pax) throws IOException 
    {
        r.setResNoPax(pax);
        robj.updateReservation(r);
    }
    
    public void updateReservationDate(Reservation r, String date) throws IOException 
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate convertdate = LocalDate.parse(date,formatter);
        r.setResDate(convertdate);
        robj.updateReservation(r);
    }
    
    
    public void updateReservationTime(Reservation r, String time) throws IOException 
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime converttime = LocalTime.parse(time,formatter);
        r.setResTime(converttime);
        robj.updateReservation(r);
    }
    
    

}
