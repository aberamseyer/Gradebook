/**
 * Contains the basic information for an instance of a Student
 * 
 * @author Abe Ramseyer
 * 
 */

public class Student {
	private String ID;
	private String firstName;
	private String lastName;
	
	public Student() {
		ID = null;
		firstName = null;
		lastName = null;
	}
	
	public Student(String ID) {
		this();
		this.ID = ID;
	}
	
	public Student(String ID, String firstName) {
		this(ID);
		this.firstName = firstName;
	}
	
	public Student(String ID, String firstName, String lastName) {
		this(ID, firstName);
		this.lastName = lastName;
	}

	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
}