package controller;

import java.io.FileNotFoundException;

import entity.Staff;

public class StaffController {
	Staff s = new Staff();
	public Boolean isIdExists(int id) throws FileNotFoundException {
		return s.isIdExists(id);
	}
}
