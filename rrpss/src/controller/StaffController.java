package controller;

import java.io.IOException;
import java.util.ArrayList;

import entity.Staff;

/**
 * This class represents the Staff controller of the restaurant.
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class StaffController {

	/**
	 * The method checks if the staff exists
	 * @param id The staff id
	 * @return Boolean Expression (true: is a valid staff and false: is not a valid staff)
	 * @throws IOException Display error message if any I/O error found while retrieving the staff records.
	 */
	public Boolean isIdExists(int id) throws IOException
	{
		if(getStaffById(id)!=null)
		{
			return true;
		}
		return false;
	}

	/**
	 * This method gets a specific staff object by the staff object
	 * @param id The staff id
	 * @return A staff object
	 * @throws IOException Display error message if any I/O error found while retrieving the staff records.
	 */
	public Staff getStaffById(int id) throws IOException
	{
		Staff c = new Staff();
		ArrayList<Staff> sList = Staff.getAllStaffDetails();

		for(int i=0; i<sList.size();i++)
		{
			if(sList.get(i).getStaffId() == id) {
				c = sList.get(i);
			}
		}
		return c;
	}
}
