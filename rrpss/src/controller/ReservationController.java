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
    
    public void addReservation(String name, String contact, int pax, int custid, int staffid) throws IOException
    {
        robj.saveReservation(name, contact, pax, custid, staffid);
    }

    public Reservation getReservationByContact(String contact) throws NumberFormatException, IOException
    {
        return robj.getResByContact(contact);
        
    }

    public Reservation getResById(Integer id) throws IOException
    {
        return robj.getResById(id);
    }

    public ArrayList<Reservation> getAllReservation() throws NumberFormatException, IOException
    {
        return robj.getAllReservationDetails();
    }


    public void removeReservation(Reservation r) throws IOException
    {
        robj.deleteReservation(r);
    }


    public void updateReservationPax(Reservation r, int pax) throws IOException 
    {
        r.setResNoPax(pax);
        robj.updateReservation(r);
    }

    public void deleteLateReservation(ArrayList<Integer> deleteList) throws IOException
    {
        robj.deleteLateReservation(deleteList);
    }

    public void deletePastReservation(ArrayList<Integer> deleteList) throws IOException
    {
        robj.deletePastReservation(deleteList);
    }

}
