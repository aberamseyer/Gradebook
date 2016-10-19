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
import java.util.HashMap;
import java.util.Scanner;

public class GradebookFactory {
	private Scanner input;
	private PrintWriter output;
	private ArrayList<Student> students;
	private ArrayList<Semester> semesters;

	public GradebookFactory() {
		students = new ArrayList<Student>();
		semesters = new ArrayList<Semester>();
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
		//Add student data to course
		courseData.put(student, performance);

		//If student is not in repository add to list
		for(Student curr : students) {
			if(curr.getID().equals(student.getID())) {
				existingStudent = true;
			}
		}
		if(existingStudent == false) {
			students.add(student);
		}
	}
}
