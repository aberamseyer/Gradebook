/**
 * 
 * Provides all the logic for reading from files, storing them into an accessible data structure, and 
 * outputing information based on specifications
 * 
 * @author Michael McHugh
 * @author Abe Ramseyer
 * 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class GradebookFactory {
	private Scanner input;
	private PrintWriter output;
	private ArrayList<Semester> semesters; 
	private ArrayList<Student> students;

	public GradebookFactory()
	{
		semesters = new ArrayList<Semester>();
		students = new ArrayList<Student>();
	}

	/**
	 * @return the semesters
	 */
	public ArrayList<Semester> getSemesters() {
		return semesters;
	}

	/**
	 * @return the students
	 */
	public ArrayList<Student> getStudents() {
		return students;
	}

	/**
	 * calculates the number of students who received each letter grade in one specifc course across all semesters
	 * 
	 * @param courseNumber the course identifier
	 * @return an int[5] containing the number of A's, B's, C's, D's, and F's
	 */
	public int[] findGrade(String courseNumber) {
		int[] grades = {0, 0, 0, 0, 0};
		for(Semester semester : semesters) {
			for(Course course : semester.getCourses()) {
				if(course.getCourseID().equals(courseNumber)) {
					grades = iterateThroughCourseMap(course, grades);
				}
			}
		}
		if(Arrays.equals(grades, new int[]{0, 0, 0, 0, 0})) {
			System.err.println("Course ID was not found in the database.");
		}
		return grades;
	}

	/**
	 * calculates the number of students who received each letter grade in a specifc semester and year
	 * 
	 * @param season the season (Fall or Spring)
	 * @param year the year (XXXX format)
	 * @return an int[5] containing the number of A's, B's, C's, D's, and F's
	 */
	public int[] findGrade(char season, String year) {
		int[] grades = {0, 0, 0, 0, 0};
		for(Semester semester : semesters) {
			if(semester.getYear().equals(year) && semester.getSeason() == season) {
				for(Course course : semester.getCourses()) {
						grades = iterateThroughCourseMap(course, grades);
				}
			}
		}
		if(Arrays.equals(grades, new int[]{0, 0, 0, 0, 0})) {
			System.err.println("Semester was not found in the database.");
		}
		return grades;
	}

	/**
	 * 
	 * @param courseNumber the course identifier
	 * @param season the season (Fall or Spring)
	 * @param year the year (XXXX format)
	 * @return an int[5] containing the number of A's, B's, C's, D's, and F's
	 */
	public int[] findGrade(String courseNumber, char season, String year) {
		int[] grades = {0, 0, 0, 0, 0};
		for(Semester semester : semesters) {
			if(semester.getYear().equals(year) && semester.getSeason() == season) {
				for(Course course : semester.getCourses()) {
					if(course.getCourseID().equals(courseNumber)) {
						grades = iterateThroughCourseMap(course, grades);
					}
				}
			}
		}
		if(Arrays.equals(grades, new int[]{0, 0, 0, 0, 0})) {
			System.err.println("Semester/Course combination was not found in the database.");
		}
		return grades;
	}


	/**
	 * Exports data for a specific student to a .csv file that shows all the data relevant to
	 * that student
	 * 
	 * @param ID the student ID
	 * @param fileName the name of the file to export the data to
	 */
	public void saveData(String ID, String fileName) {
		try {
			output = new PrintWriter(new File(fileName));
		} catch (FileNotFoundException e) {
			System.err.println("Unable to create file with specified name.");
		}
		Student currentStudent = findStudentByID(ID);
		if(output != null && currentStudent != null) {
			output.print(ID + ",");
			for(Semester semester : semesters) {
				for(Course course : semester.getCourses()) {
					if(course.getCourseData().get(currentStudent) != null) {		// check each and every course for the student object we're looking for
						for(Assignment assignment : course.getCourseData().get(currentStudent).getAssignments()) {
							output.print(course.getCourseID() + "-" + semester.getSeason() + "-" + semester.getYear() + "-" + assignment.getTitle() + ",");
						}
						output.print(course.getCourseID() + "-" + semester.getSeason() + "-" + semester.getYear() + "-" + course.getCourseData().get(currentStudent).getLetterGrade());
					}
				}
			}
			System.out.println("Successfully wrote to file " + fileName);
			output.close();
		} else {
			System.err.println("Student ID was not found in database.");
		}
	}
	
	/*
	 * makes a specified course's hashmaps iterable and finds the amount of A-Fs in them
	 */
	private int[] iterateThroughCourseMap(Course course, int[] grades) {
		for (HashMap.Entry<Student, Performance> performance : course.getCourseData().entrySet()) {	// wizard code that makes a hashmap iterable
			switch (performance.getValue().getLetterGrade()) {
			case 'A':
				grades[0]++;
				break;
			case 'B':
				grades[1]++;
				break;
			case 'C':
				grades[2]++;
				break;
			case 'D':
				grades[3]++;
				break;
			case 'F':
				grades[4]++;
				break;
			default:
				break;
			}
		}
		return grades;
	}
	
	/*
	 * returns the student object that an ID belongs to
	 */
	private Student findStudentByID(String ID) {
		for(Student student : students) {
			if(student.getID().equals(ID)) {
				return student;
			}
		}
		return null;
	}
}
