/**
 * 
 * Holds the details for a single graded assignment
 * 
 * @author Kyle Brown
 * 
 */

public class Assignment 
{

	private int grade;
	private String comment;
	private String title;

	public Assignment() {
		this.grade = 0;
		this.comment = null;
		this.title = null;
	}
	public Assignment(String title) {
		this();
		this.title = title;
	}
	public Assignment(String title, int grade) {
		this(title);
		this.title = title;
	}
	public Assignment(String title, int grade, String comment) {
		this(title, grade);
		this.comment = comment;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the grade
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * @param grade the grade to set
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (!comment.equals(null))
			return "For this exam the student recieved " + grade + "%"
					+ ". There are no comments available for this exam.";
		else
			return "For this assignment the student recieved " + grade + "%"
					+ ". comments for this assignment are as follows" + comment + ".";

	}





}
