package entity;

public class Reservation {

	private String resDate;
	private String resTime;
	private int resNoPax;
	private String resName;
	private String resContact;
	Table reserveTable;
	Customer customer;
	Staff staff;
	private int resId;

	public int getResId() {
		return this.resId;
	}

	/**
	 * 
	 * @param resId
	 */
	public void setResId(int resId) {
		this.resId = resId;
	}

	/**
	 * 
	 * @param tableNo
	 */
	public void allocateTable(int tableNo) {
		// TODO - implement Reservation2.allocateTable
		throw new UnsupportedOperationException();
	}

}