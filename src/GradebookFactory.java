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
	
	public void readData(String season, String year, String courseName) {
		String courseId = courseName.replaceAll("[^\\d]+", "");
		String[] header;
		String[] inputData;
		String[] outputData;
		season = season.toLowerCase();
			
		try {
			input = new Scanner(new File(courseId + "-" + season + "-" + year + ".csv"));
		}
		catch(FileNotFoundException e) {
			System.out.println("No data found for " + courseName + " " + season + " " + year + ".");
			return;
		}
		
		//Logic to split csv files
		while(input.hasNextLine()) {
			inputData = input.nextLine().split(",");
			outputData = readLine(inputData);
			for(int i = 0; i < outputData.length; i++) {
				System.out.println(outputData[i]);
				System.out.println();
			}
			System.out.println("---------------------------------\n");
		}
//		header = input.nextLine().split(",");
//		readLine(header);
//		for(int i = 0; i < header.length; i++) {
//			System.out.println(header[i]);
//		}
		
		//Logic to determine file structure
	}
	
	/**
	 * Reads an array that has been created by splitting 
	 * a line of a .csv file by commas and merges and replaces any
	 * elements where commas that were marked to be kept
	 * @param inputData And array resulting from splitting a csv file
	 * @return An array that contains any commas 
	 */
	public String[] readLine(String[] inputData) {
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
