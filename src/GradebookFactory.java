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
	
	public void readData(String season, String year, String courseName) {
		String courseId = courseName.replaceAll("[^\\d]+", "");
		String[] header;
		String[] inputData;
		String[] formattedData;
		Semester semester = null;
		Course course = null;
		int totalNumStudents = students.size();
		int numStudents = 0;
		
		/*
		 * An array to hold codes for the type of data being read
		 * Size and contents will be determined by reading the header file
		 * 0 = ID, 1 = First Name, 
		 * 2 = Last Name, 3 = Full Name,
		 * 4 = Assignment, 5 = Comments, 
		 * 6 = Total Grade, 7 = Letter Grade
		 */
		int[] readOrder;	
		
		season = season.toLowerCase();
		
		//Check if course has already been added
		for(Semester currS : semesters) {
			if(currS.getYear().equals(year) && currS.getSeason() == season.charAt(0)) {
				semester = currS;
				for(Course currC : currS.getCourses()) {
					if(currC.getCourseID().equals(courseName)) {
						System.out.println(courseName + " already exists for the " + season + " " + year + " semester.");
						return;
					}
				}
				break;
			}
		}
		if(semester == null ){
			semester = new Semester(year, season);
		}


		
		//Create Course Object
		course = new Course(courseName);
		
		//Open File
		try {
			input = new Scanner(new File(courseId + "-" + season + "-" + year + ".csv"));
		}
		catch(FileNotFoundException e) {
			System.out.println("No data found for " + courseName + " " + season + " " + year + ".");
			return;
		}
		
		//Get Header
		inputData = input.nextLine().split(",");
		header = formatData(inputData);

		//Determine file structure from header
		readOrder = createReadOrder(header);
		
		//Interpret Data
		while(input.hasNextLine()) {
			inputData = input.nextLine().split(",");
			formattedData = formatData(inputData);
			interpretData(formattedData, header, course.getCourseData(), readOrder);
			numStudents++;
		}
		
		//Add Course to semester
		semester.getCourses().add(course);
		System.out.println("Added " + courseName + " of the " + season + " " + year + "semester to the repository.\n" +
		numStudents + " students were enrolled, of which, " + (students.size() - totalNumStudents) +
		" were already in the repository.");
	}

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

	/**
	 * Reads an array that has been created by splitting 
	 * a line of a .csv file by commas and merges and replaces any
	 * elements where commas that were marked to be kept
	 * @param inputData And array resulting from splitting a csv file
	 * @return An array that contains any commas 
	 */
	private String[] formatData(String[] inputData) {
		ArrayList<String> outputData = new ArrayList<String>();

		for(int i = 0; i < inputData.length; i++) {
			//Check to see if this element is part of one that contained commas
			if(inputData[i].charAt(0) == '\"') {
				//Remove quotation mark from beginning
				inputData[i] = inputData[i].substring(1);
				for(int j = i+1; j < inputData.length; j++) {
					//Add comma back and combine with next element
					inputData[i] += "," + inputData[j];
					//Check if this element is now complete
					if(inputData[j].charAt(inputData[j].length() - 1) == '\"') {
						//Add completed element to output array
						outputData.add(inputData[i].substring(0, inputData[i].length() - 2));
						//Move i to end of complete element and end for loop
						i = j;
						j = inputData.length;
					}
				}
			}
			//Add element to output array
			else {
				outputData.add(inputData[i]);
			}
		}
		return outputData.toArray(new String[outputData.size()]);
	}
	
	private void interpretData(String[] data, String[] header, HashMap<Student, Performance> courseData, int[] readOrder) {
		String[] fullName;
		Student student = new Student();
		Performance performance = new Performance();
		Assignment assignment;
		int grade;
		String comment;
		String title;
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
			case 3: fullName = data[i].split(",");
					student.setLastName(fullName[0]);
					student.setLastName(fullName[1]);
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
		if(!students.contains(student)) {
			students.add(student);
		}
	}

}
