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
	private char letterGrade;
	private String comment;
	private String title;

	public Assignment() {
		this.grade = 0;
		this.letterGrade = ' ';
		this.comment = null;
		this.title = null;
	}
	public Assignment(int grade) {
		this();
		this.grade = grade;
	}
	public Assignment(int grade, char letterGrade) {
		this(grade);
		this.letterGrade = letterGrade;
	}
	public Assignment(int grade, char letterGrade, String comment) {
		this(grade, letterGrade);
		this.comment = comment;
	}
	public Assignment(int grade, char letterGrade, String comment, String title) {
		this(grade, letterGrade, comment);
		this.title = title;
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
	 * @return the letterGrade
	 */
	public char getLetterGrade() {
		return letterGrade;
	}

	/**
	 * @param letterGrade the letterGrade to set
	 */
	public void setLetterGrade(char letterGrade) {
		this.letterGrade = letterGrade;
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
			return "For this exam the student recieved " + grade + ", for a " + letterGrade
					+ ". There are no comments available for this exam.";
		else
			return "For this assignment the student recieved " + grade + ", for a " + letterGrade
					+ ". comments for this assignment are as follows" + comment + ".";

	}





}
