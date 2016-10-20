
import java.util.Arrays;
import java.util.Scanner;

/**
 * handles user input for GradeBook
 * 
 * @author Kyle Brown
 * 
 */
public class GradeBookDriver {

	public static void main(String[] args) {
		GradebookFactory gradebook = new GradebookFactory();
		boolean keepGoing = true;
		Scanner keyboard = new Scanner(System.in);
		char choice;
		
		System.out.println("Welcome to our Gradebook Simulator\n");
		while (keepGoing) {
			System.out
					.print("Please insert a character to select an option\n\n"
							+ "A: Add course\n"
							+ "S: Save data for a student\n"
							+ "G: Show distribution of letter grades for either a course\n"
							+ "   across a semester, year, or all years, or all courses\n"
							+ "   across a semseter or year\n"
							+ "E: Exit Program"
							+ "\n\nChoice: ");
			choice = keyboard.next().toLowerCase().charAt(0); 
			keyboard.nextLine();
			System.out.println();
			switch (choice) {
			case 'a':
				String season,
				year,
				courseName;
				System.out
						.println("You have chosen to add data to the repository\n\n"
								+ "Please indicate the semester (Fall or Spring) you would like read");
				System.out.print("Semester: ");
				season = keyboard.nextLine();
				System.out
						.print("\nPlease indicate the year the course was taken\nYear: ");
				year = keyboard.nextLine();
				System.out
						.print("\nPlease indicate the courses Department and course number seperated with a space"
								+ " Ex:\"IT 226\"\nCourse ID: ");
				courseName = keyboard.nextLine().toUpperCase();

				gradebook.readData(season, year, courseName);
				break;
			case 's':
				String ID, exportFile;
				System.out
						.print("You have chosen to save data a new student\n\n"
								+ "Please give the students ID\nStudent ID: ");
				ID = keyboard.nextLine();
				System.out
						.print("\nPlease indicate the name of the file you would like to \n" + 
				"have this information exported to\nFilename: ");
				exportFile = keyboard.nextLine();
				gradebook.saveData(ID, exportFile);
				break;

			case 'g':
				year = "none";
				season = "none";
				courseName = "NONE";
				int[] gradeComp = null;
				System.out.print("You have chosen to return number of students who got"
						+ " a specific grade in a specific course in a specific semester\n\n"
						+ "Please indicate the course number or type \"none\" to skip this step\nCourse number: ");
				courseName = keyboard.nextLine().toUpperCase();
				System.out.print("\nPlease indicate the semester (Fall or Spring)"
						+ " or type \"none\" to skip this step\nSemester: ");
				season = keyboard.nextLine();
				//Make Sure a year is specified for the semester
				if(!season.equals("none")) {
					System.out.print("\nPlease indicate the year\nYear: ");
					year = keyboard.nextLine();
				}			
				//Course Name Specified
				if (!courseName.equals("NONE")) {
					//Course Name and Semester
					if(!season.equals("none")) {
						gradeComp = gradebook.findGrade(courseName, season.toUpperCase().charAt(0), year);
					}
					//Only Course Name
					else {
						gradeComp = gradebook.findGrade(courseName);
					}
				}
				//Course Name Not Specified)
				else if (!season.equals("none")) {
						gradeComp = gradebook.findGrade(season.toUpperCase().charAt(0), year);
				}
				if(Arrays.equals(gradeComp, new int[] {0, 0, 0, 0, 0}) || gradeComp == null) {
					if(courseName.equals("NONE") && season.equals("none") && year.equals("none")) {
						System.out.println("\nBoth steps were skipped.");
					}
					System.out.println("Returning to menu\n");
				}
				else {
					System.out.println("\nGrade Distribution");
					int mod = 0;
					for(int i = 65; i < 71; i++) {
						if(i == 69) {
							i++;
							mod = 1;
						}
						System.out.println( (char) i + "'s: " + gradeComp[i-65-mod]);
					}
					System.out.println();
				}
				break;

			case 'e':
				System.out.println("You have chosen to exit the gradebook\n"
						+ "Thank you for using our gradebook");
				keepGoing = false;
				keyboard.close();
				break;
				
			default:
				System.out.println("I'm sorry that was an invalid input, please try again\n");
			}
		}
	}
}
