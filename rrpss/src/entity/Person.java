package entity;

public class Person {

	private String persName;
	private String persPhoneNo;
	
	public Person() {
		
	}
	
	public Person(String name,String contact) {
		this.persName = name;
		this.persPhoneNo = contact;
	}

	public String getPersName() {
		return this.persName;
	}

	/**
	 * 
	 * @param persName
	 */
	public void setPersName(String persName) {
		this.persName = persName;
	}


	public String getPersPhoneNo() {
		return this.persPhoneNo;
	}

	/**
	 * 
	 * @param persPhoneNo
	 */
	public void setPersPhoneNo(String persPhoneNo) {
		this.persPhoneNo = persPhoneNo;
	}



}
