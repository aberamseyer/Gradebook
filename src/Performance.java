/**
 * This class represents a student's performance in an individual course. It contains a set of all
 * his work and the final grade he received for the course.
 * 
 * @author Abe Ramseyer
 *
 */
import java.util.ArrayList;

public class Performance {
	private ArrayList<Assignment> assignments;
	private double grade;
	private char letterGrade;
	
	public Performance() {
		assignments = new ArrayList<Assignment>();	// a ArrayList has a default capacity of 16
		grade = 0;
		letterGrade = 0;
	}
	
	public Performance(double grade) {
		this();
		this.grade = grade;
	}
	
	public Performance(double grade, char letterGrade) {
		this(grade);
		this.letterGrade = letterGrade;
	}

	/**
	 * @return the grade
	 */
	public double getGrade() {
		return grade;
	}

	/**
	 * @param grade the grade to set
	 */
	public void setGrade(double grade) {
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
	 * @return the assignments
	 */
	public ArrayList<Assignment> getAssignments() {
		return assignments;
	}
	
}
