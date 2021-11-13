package entity;

/**
 * This class represents the Person of the restaurant.
 * @version JDK 1.1
 * @since 2021-10-13
 * @author SSP3 Group 3
 */
public class Person {

	/**
	 * The name of a person
	 */
	private String persName;

	/**
	 * The contact of a person
	 */
	private String persPhoneNo;

	/**
	 * Default constructor of Person
	 */
	public Person() {

	}

	/**
	 * Person constructor with parameters
	 * @param name The name of a person
	 * @param contact The contact of a person
	 */
	public Person(String name,String contact) {
		this.persName = name;
		this.persPhoneNo = contact;
	}


	/**
	 * Get the person name
	 * @return The person name
	 */
	public String getPersName() {
		return this.persName;
	}

	/**
	 * Set the person name
	 * @param persName The person name
	 */
	public void setPersName(String persName) {
		this.persName = persName;
	}

	/**
	 * Get the person contact
	 * @return The person contact
	 */
	public String getPersPhoneNo() {
		return this.persPhoneNo;
	}


	/**
	 * Set the person contact
	 * @param persPhoneNo The person contact
	 */
	public void setPersPhoneNo(String persPhoneNo) {
		this.persPhoneNo = persPhoneNo;
	}

}