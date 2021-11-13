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


    public static Reservation getReservationByContact(String contact) throws IOException
    {
        Reservation r = new Reservation();
        ArrayList<Reservation> rList = Reservation.getAllReservationDetails();

        for(int i=0; i<rList.size();i++)
        {
            if(rList.get(i).getResContact().equals(contact)) {
                r = rList.get(i);
            }
        }
        return r;
    }

    public Reservation getResById(int id) throws IOException
    {
        Reservation r = new Reservation();
        ArrayList<Reservation> rList = r.getAllReservationDetails();

        for(int i=0; i<rList.size();i++)
        {
            if(rList.get(i).getResId()==id) {
                r = rList.get(i);
                break;
            }
        }
        return r;
    }

    public ArrayList<Reservation> getAllReservation() throws NumberFormatException, IOException
    {
        return Reservation.getAllReservationDetails();
    }


    public void addReservation(String name, String contact, int pax, int custid, int staffid) throws IOException
    {
        Reservation.saveReservation(name, contact, pax, custid, staffid);
    }

    public void updateReservationPax(Reservation r, int pax) throws IOException
    {
        r.setResNoPax(pax);
        Reservation.updateReservation(r);
    }

    public void removeReservation(Reservation r) throws IOException
    {
        Reservation.deleteReservation(r);
    }

    public int deleteLateReservation(ArrayList<Integer> deleteList) throws IOException
    {
        Reservation r = new Reservation();
        int count=0;

        for (Integer resid : deleteList)
        {
            r = getResById(resid);
            Reservation.deleteReservation(r);
            count++;
        }
        return count;
    }

    public int deletePastReservation(ArrayList<Integer> deleteList) throws IOException
    {
        Reservation r = new Reservation();
        int count=0;

        for (Integer resid : deleteList)
        {
            r = getResById(resid);
            Reservation.deleteReservation(r);
            count++;
        }
        return count;
    }


}
