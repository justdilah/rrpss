package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.Table;


public class TableController {
	Table t = new Table();
	
	public Boolean tableExists(int tableNo) throws FileNotFoundException {
		return t.tableExists(tableNo);
	}
	
	public void updateTableStatus(Table table) throws IOException 
	{
		t.updateTableStatus(table);
	} 
	
	public Boolean checkTableReserved(int tableNo) throws FileNotFoundException {

		return t.checkTableReserved(tableNo);
	}
	public void updateTableStatusString(String status,int tableNo) throws IOException {
		t.updateTableStatusString(status, tableNo);
	}
	
//	public Table getAllVacantTableByCapacity(TableCapacity cap) throws FileNotFoundException
//	{
//		
//	}
	
	public ArrayList<Table> getAllOccupiedTables() throws FileNotFoundException
	{
		return t.getAllOccupiedTables();
	}


	public ArrayList<Table> getAllReservedTables() throws FileNotFoundException
	{
		return t.getAllRservedTables();
	}
	
	public ArrayList<Table> getAllReservedTables() throws FileNotFoundException
	{
		return t.getAllRservedTables();
	}
	
	
}
