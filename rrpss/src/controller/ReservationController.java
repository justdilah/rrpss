package controller;

import java.io.IOException;
import java.util.ArrayList;
import entity.Reservation;

/**
 * This class represents the Reservation controller of the restaurant.
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class ReservationController {

    /**
     * This method gets a specific reservation object by the customer contact
     * @param contact The customer contact who the reservation belongs to
     * @return A reservation object
     * @throws IOException Display error message if any I/O error found while retrieving the reservation records.
     */
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

    /**
     * This method gets a specific reservation object by its own reservation id
     * @param id The unique identifier of reservation
     * @return A reservation object
     * @throws IOException Display error message if any I/O error found while retrieving the reservation records.
     */

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

    /**
     * This methods calls the getAllReservationDetails() in the entity class to retrieve the entire list of reservation
     * @return The whole list of reservation details
     * @throws IOException Display error message if any I/O error found while retrieving the reservation records.
     */
    public ArrayList<Reservation> getAllReservation() throws IOException
    {
        return Reservation.getAllReservationDetails();
    }


    /**
     * This methods calls the saveReservation() in the entity class to save a reservation
     * @param name The name of the customer who the reservation belongs to
     * @param contact The contact of the customer who the reservation belong to
     * @param pax The number of pax
     * @param custid The customer id
     * @param staffid The staff id
     * @throws IOException Display error message if any I/O error found while inserting into the reservation records.
     */
    public void addReservation(String name, String contact, int pax, int custid, int staffid) throws IOException
    {
        Reservation.saveReservation(name, contact, pax, custid, staffid);
    }

    /**
     * This method calls the updateReservation() in the entity class to update a reservation
     * @param r A reservation object
     * @param pax The number of pax
     * @throws IOException Display error message if any I/O error found while updating into the reservation records.
     */
    public void updateReservationPax(Reservation r, int pax) throws IOException
    {
        r.setResNoPax(pax);
        Reservation.updateReservation(r);
    }

    /**
     * This method calls the deleteReservation() in the entity class to delete a reservation
     * @param r A reservation object
     * @throws IOException Display error message if any I/O error found while removing from the reservation records.
     */
    public void removeReservation(Reservation r) throws IOException
    {
        Reservation.deleteReservation(r);
    }

    /**
     * This method delete a list of reservation where the customer of the reservation is late 10 minutes for the reservation date and time
     * @param deleteList The list of reservation to be deleted
     * @return The number of deleted reservation
     * @throws IOException Display error message if any I/O error found while removing from the reservation records.
     */
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

    /**
     * The method delete a list a reservation where the customer of the reservation did not turn up for the reservation and the reservation have passed
     * @param deleteList The list of the reservation to be deleted
     * @return The number of deleted reservation
     * @throws IOException Display error message if any I/O error found while inserting into the reservation records.
     */
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