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
import java.util.Scanner;

public class GradebookFactory {
	private Scanner input;
	private PrintWriter output;
	private ArrayList<Student> students;
	private ArrayList<Semester> semesters;

	public void readData(String season, String year, String courseName) {
		String courseId = courseName.replaceAll("[^\\d]+", "");
		String[] header;
		String[] inputData;
		String[] outputData;
		Semester semester;
		boolean existingSemester = false;
		
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
		if (semesters != null) {
			for(Semester currS : semesters) {
				if(currS.getYear().equals(year) && currS.getSeason() == season.charAt(0)) {
					for(Course currC : currS.getCourses()) {
						if(currC.getCourseID().equals(courseName)) {
							System.out.println(courseName + " already exists for the " + season + " " + year + " semester.");
							return;
						}
					}
					existingSemester = true;
					semester = currS;
				}
			}
		}
		
		//Open File
		try {
			input = new Scanner(new File(courseId + "-" + season + "-" + year + ".csv"));
		}
		catch(FileNotFoundException e) {
			System.out.println("No data found for " + courseName + " " + season + " " + year + ".");
			return;
		}
		
		inputData = input.nextLine().split(",");
		header = readLine(inputData);
		for(int i = 0; i < header.length; i++) {
			System.out.println(header[i]);
		}
		
		readOrder = createReadOrder(header);
		
		for(int i = 0; i < readOrder.length - 1; i++) {
			System.out.print(readOrder[i] + ", ");
		}
		System.out.println(readOrder[readOrder.length - 1]);
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
	private String[] readLine(String[] inputData) {
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

}
