
import java.util.Scanner;

/**
 * handles user input for GradeBook
 * 
 * @author Kyle Brown
 * 
 */
public class GradeBookDriver {

	public static void main(String[] args) {
		GradebookFactory generateGradeBook = new GradebookFactory();
		boolean keepGoing = true;
		Scanner keyboard = new Scanner(System.in);

		System.out.println("Welcome to our Grade book simulator.");
		while (keepGoing) {
			System.out
					.println("Please insert a character to select an option.\n"
							+ "to Add data type ‘a’ or ‘A’.\n"
							+ "to Save data for a student type ‘s’ or ‘S’\n"
							+ "to Return number of students who got a specific grade in a specific course in a specific\n"
							+ "semester type ‘g’ or ‘G’\n"
							+ "to Exit the program type ‘e’ or ‘E\n");

			switch (keyboard.next().toLowerCase().charAt(0)) {
			case 'a':
				String season,
				year,
				courseName;
				System.out
						.println("You have chosen to add data to our repository.\n+"
								+ "Please indicate the semester you would like read.");
				season = keyboard.nextLine();
				System.out
						.println("Please indicate the year the course was taken.");
				year = keyboard.nextLine();
				System.out
						.println("Please indicate the courses Department and course number seperated with a space."
								+ "Ex:\"IT 226\"\n");
				courseName = keyboard.nextLine();

				generateGradeBook.readData(season, year, courseName);
				// **need a way to access & print the number of students whose
				// data it just read, and how many students
				// already existed in the repository.

				// 4 v. Print the number of students whose data it just read,
				// and how many students
				// already existed in the repository.
				break;
			case 's':
				String ID,
				exportFile;
				System.out
						.println("You have chosen to save data a new student.\n"
								+ "Please give the students ID.");
				ID = keyboard.nextLine();
				System.out
						.println("Please indicate which file you would like to havee this information exported from.");
				exportFile = keyboard.nextLine();
				System.out.println("data fromm student is printed here\n");
//				for(int i=0;i<assignments.length();i++)
//				{
//				path through gradeBookFactory to student map is necessary here
				
				// iii. Find all data from the repository pertaining to this
				// student, and export it in csv
				// format as follows:]

				// 1. Column headings: “Student Id”, <Course>-<Semester>-<Year>-
				// <Assignment name>,…repeated for each instance found for this
				// student.
				// For example, if data was found for this student in two
				// courses: IT 226 in Fall
				// 2002 (which has 5 assignments and a letter grade) and IT 279
				// in Spring 2003
				// (which has 3 assignments and a letter grade), then the
				// columns would be:
				// Student Id,IT226-F-2002-Assignment 1, IT226-F-2002-Assignment
				// 2, IT226-
				// F-2002-Assignment 3, IT226-F-2002-Assignment 4, IT226-F-2002-
				// Assignment 5, IT226-F-2002-Letter grade
				// IT279-S-2003-Assignment 1,
				// IT279-S-2003-Assignment 2, IT279-S-2003-Assignment 3,
				// IT279-S-2003-
				// Letter grade
//				}
				break;

			case 'g':
				System.out
						.println("You have chosen to return number of students who got"
								+ " a specific grade in a specific course in a specific semester.\n"
								+ "Please indicate the course number or type \"none\" to skip this step");
				courseName = keyboard.nextLine();
				System.out
						.println("Please indicate the semester or type \"none\" to skip this step");
				season = keyboard.nextLine();
				System.out
						.println("Please indicate the year or type \"none\" to skip this step");
				year = keyboard.nextLine();

				// iv. Return an array of integers. The array must be of length
				// 5. The first position
				// should store the number of A’s, the next one the number of
				// ‘B’s and so on.
				// 1. If the course number and semester/year were specified, it
				// should
				// contain data only for that course during that semester/year.
				if (!courseName.equals("none") && !year.equals("none")
						&& !season.equals("none"))// int[] array of grade composition is needed
					System.out.println("For " + courseName + " given in "
							+ season + " of " + year
							+ " the grades were composed as follows." + "A's"
							+ "B's" + "C's" + "D's" + "F's");

				// 2. If the course number is missing, it should contain data
				// for all the courses
				// during that semester/year.
				else if (courseName.equals("none") && !year.equals("none")
						&& !season.equals("none"))// int[] array of grade composition is needed
					System.out.println("Across all courses taken in " + season
							+ " of " + year
							+ " the grades were composed as follows." + "A's"
							+ "B's" + "C's" + "D's" + "F's");
				// 3. If the semester/year is missing, it should contain data
				// for the given
				// course across all semesters.

				else if (!courseName.equals("none") && year.equals("none")
						&& season.equals("none"))// int[] array of grade composition is needed
					System.out.println("Across all Semesters the grades for "
							+ courseName + " were composed as follows." + "A's"
							+ "B's" + "C's" + "D's" + "F's");
				// iii. If both inputs are skipped, the program should return to
				// the menu without
				// doing anything.
				else
					System.out
							.println("not enough information or invalid information was given."
									+ "Returning to menu");
				break;

			case 'e':
				System.out.println("you have chosen to exit the gradebook.\n"
						+ "Thank you for using out gradebook.");
				keepGoing = false;
				keyboard.close();
				break;
				
			default:
				System.out.println("I'm sorry that was an invalid input please try again");

			}

		}

	}

}
