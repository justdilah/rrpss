package controller;

import java.io.IOException;
import java.util.ArrayList;

import entity.Staff;

public class StaffController {

	public Boolean isIdExists(int id) throws IOException 
	{
		if(getStaffById(id)!=null) 
		{
			return true;
		}
		return false;
	}

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
