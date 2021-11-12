package controller;

import java.io.IOException;

import entity.Staff;

public class StaffController {
	Staff s = new Staff();


	public Boolean isIdExists(int id) throws IOException {
		return s.isIdExists(id);
	}

	public Staff getStaffById(int id) throws IOException
	{
		return s.getStaffById(id);
	}
}
