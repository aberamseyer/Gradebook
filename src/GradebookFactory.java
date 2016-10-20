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
	 * calculates the number of students who received each letter grade in one specific course across all semesters
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
	 * calculates the number of students who received each letter grade in a specific semester and year
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
					if(course.getCourseData().containsKey(currentStudent)) {		// check each and every course for the student object we're looking for
						for(Assignment assignment : course.getCourseData().get(currentStudent).getAssignments()) {
							output.print(course.getCourseID() + "-" + semester.getSeason() + "-" + semester.getYear() + "-" + assignment.getTitle() + ",");
						}
						output.print(course.getCourseID() + "-" + semester.getSeason() + "-" + semester.getYear() + "-" + course.getCourseData().get(currentStudent).getLetterGrade() + ",");
					}
				}
			}
			System.out.println("Successfully wrote to file " + fileName);
			output.close();
		} else {
			System.err.println("Student ID was not found in database.");
			output.close();
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

	/**
	 * Reads a csv file from the project directory and stores its data
	 * @param season The semester of the class. Either Spring or Fall
	 * @param year The year the class is/was in
	 * @param courseName The name of the course
	 */
	public void readData(String season, String year, String courseName) {
		String courseId = courseName.replaceAll("[^\\d]+", "");
		String[] header;
		String inputData;
		String[] formattedData;
		Semester semester = null;
		Course course = null;
		int numStudents = 0;
		int totalNumStudents = students.size();
		boolean newSemester = false;
		boolean firstEntry = false;

		/*
		 * An array to hold codes for the type of data being read
		 * Size and contents will be determined by reading the header file
		 * 0 = ID, 1 = First Name, 
		 * 2 = Last Name, 3 = Full Name,
		 * 4 = Assignment, 5 = Comments, 
		 * 6 = Total Grade, 7 = Letter Grade
		 */
		int[] readOrder;	

		season = season.toUpperCase();

		//Check if the semester already exists and if the course is a duplicate
		for(Semester currS : semesters) {
			if(currS.getYear().equals(year) && currS.getSeason() == season.charAt(0)) {
				semester = currS;
				for(Course currC : currS.getCourses()) {
					if(currC.getCourseID().equals(courseName)) {
						System.out.println(courseName + " already exists for the " + season.charAt(0) +
								season.substring(1).toLowerCase() + " " + year + " semester.");
						return;
					}
				}
				break;
			}
		}
		if(semester == null ){
			semester = new Semester(year, season);
			newSemester = true;
		}

		//Check if this is the first addition to the repository
		if(students.isEmpty()) {
			firstEntry = true;
		}
		
		//Create Course Object
		course = new Course(courseName);

		//Open File
		try {
			input = new Scanner(new File(courseId + "-" + season.toLowerCase() + "-" + year + ".csv"));
		}
		catch(FileNotFoundException e) {
			System.out.println("No data found for " + courseName + " " + season + " " + year + ".");
			return;
		}

		//Get Header
		inputData = input.nextLine();
		header = formatData(inputData);

		//Determine file structure from header
		readOrder = createReadOrder(header);

		//Interpret Data
		while(input.hasNextLine()) {
			inputData = input.nextLine();
			formattedData = formatData(inputData);
			interpretData(formattedData, header, course.getCourseData(), readOrder);
			numStudents++;
		}

		//Close file
		input.close();

		//Add Course to semester and add semester to list of courses
		semester.getCourses().add(course);
		if(newSemester) {
			semesters.add(semester);
		}
		//If first entry, avoid showing erroneous number added
		if(firstEntry) {
			totalNumStudents = students.size();
		}
		else {
			totalNumStudents += numStudents;
		}
		System.out.println("Added " + courseName + " of the " + season + " " + year + " semester to the repository.\n" +
				numStudents + " students were enrolled, of which, " + (totalNumStudents - students.size()) +
				" were already in the repository.\n");
	}

	/*
	 * Creates an array of ints that represents the structure of the
	 * file being read
	 */
	private int[] createReadOrder(String[] header) {
		//A string to hold the column header in lowercase
		String curr;
		int[] readOrder = new int[header.length];
		for(int i = 0; i < header.length; i++) {
			curr = header[i].toLowerCase();
			if(curr.contains("name")) {
				if(curr.contains("first")) {
					readOrder[i] = 1;
					readOrder[i+1] = 2;
					i++;
				}
				else {
					readOrder[i] = 3;
				}
			}
			else if(curr.contains("student id") || curr.contains("user id")) {
				readOrder[i] = 0;
			}
			else if(curr.contains("comment")) {
				readOrder[i] = 5;
			}
			else if(curr.contains("total")) {
				readOrder[i] = 6;
				readOrder[i+1] = 7;
				i++;
			}
			else {
				readOrder[i] = 4;
			}
		}
		return readOrder;
	}

	/*
	 * Reads a line of a .csv file and puts each element into an array
	 */
	private String[] formatData(String inputData) {
		ArrayList<String> data = new ArrayList<String>();
		int index = 0;

		while(!inputData.equals("")) {
			if(inputData.charAt(0) == '\"') {
				index = inputData.indexOf("\",", index);
				if(inputData.substring(index - 1,index + 2).equals("\"\",") && !inputData.substring(index - 2,index + 2).equals("\"\"\",")) {
					index++;
				}
				else {
					data.add(inputData.substring(1, index).replaceAll("[\"+\"]+", "\""));
					if(inputData.contains(",")) {
						inputData = inputData.substring(index + 2);
					}
				}
			}
			else {
				if(!inputData.contains(",")) {
					data.add(inputData.replaceAll("[\"+\"]+", "\""));
					inputData = "";
				}
				else {
					index = inputData.indexOf(",");
					data.add(inputData.substring(0, index).replaceAll("[\"+\"]+", "\""));
					inputData = inputData.substring(index + 1);
				}
			}
		}
		return data.toArray(new String[data.size()]);
	}

	/*
	 * Interprets a live of a csv file using the readOrder array,
	 * creates objects, and passes them to their appropriate locations
	 */
	private void interpretData(String[] data, String[] header, HashMap<Student, Performance> courseData, int[] readOrder) {
		String[] fullName;
		Student student = new Student();
		Performance performance = new Performance();
		Assignment assignment;
		int grade;
		String comment;
		String title;
		boolean existingStudent = false;

		for(int i = 0; i < data.length; i++) {	
			switch (readOrder[i]) {
			//ID
			case 0:	student.setID(data[i]);
			break;
			//First Name
			case 1:	student.setFirstName(data[i]);
			break;
			//Last Name
			case 2: student.setLastName(data[i]);
			break;
			//Full Name
			case 3: fullName = data[i].split(", ");
			student.setLastName(fullName[0]);
			student.setFirstName(fullName[1]);
			break;
			//Assignment
			case 4:	title = header[i];
			grade = Integer.parseInt(data[i]);
			if(readOrder[i+1] == 5) {
				comment = data[i+1];
				assignment = new Assignment(title, grade, comment);
				i++;
			}
			else {
				assignment = new Assignment(title, grade);
			}
			performance.getAssignments().add(assignment);
			break;
			//Case 4 renders a Case 5 unreachable
			//Total Grade
			case 6:	performance.setGrade(Double.parseDouble(data[i]));
			break;
			//Letter Grade
			case 7:	performance.setLetterGrade(data[i].charAt(0));
			break;
			//This should be unreachable as well
			default:
				System.out.println("Invalid Data Type");
				break;
			}
		}
		//Check if student already exists, and get the existing one if so
		for(Student curr : students) {
			if(curr.getID().equals(student.getID())) {
				existingStudent = true;
				student = curr;
			}
		}
		//If student is not in repository add to list
		if(existingStudent == false) {
			students.add(student);
		}
		//Add student to course
		courseData.put(student, performance);

		

	}
}
