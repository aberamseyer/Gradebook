
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
		GradebookFactory generateGradeBook = new GradebookFactory();
		boolean keepGoing = true;
		Scanner keyboard = new Scanner(System.in);

		System.out.println("Welcome to our Grade book simulator.\n");
		while (keepGoing) {
			System.out
					.print("Please insert a character to select an option.\n\n"
							+ "to Add data type ‘a’ or ‘A’.\n"
							+ "to Save data for a student type ‘s’ or ‘S’\n"
							+ "to Return number of students who got a specific grade in a specific course in a specific\n"
							+ "semester type ‘g’ or ‘G’\n"
							+ "to Exit the program type ‘e’ or ‘E: ");
			char choice = keyboard.next().toLowerCase().charAt(0); 
			keyboard.nextLine();
			switch (choice) {
			case 'a':
				String season,
				year,
				courseName;
				System.out
						.println("You have chosen to add data to our repository.\n"
								+ "Please indicate the semester (Fall or Spring) you would like read.");
				season = keyboard.nextLine();
				System.out
						.println("Please indicate the year the course was taken.");
				year = keyboard.nextLine();
				System.out
						.println("Please indicate the courses Department and course number seperated with a space."
								+ "Ex:\"IT 226\"");
				courseName = keyboard.nextLine();

				generateGradeBook.readData(season, year, courseName);
				break;
			case 's':
				String ID, exportFile;
				System.out
						.println("You have chosen to save data a new student.\n"
								+ "Please give the students ID.");
				ID = keyboard.nextLine();
				System.out
						.println("Please indicate which file you would like to have this information exported from.");
				exportFile = keyboard.nextLine();
				System.out.println("data from student is printed here\n");
				generateGradeBook.saveData(ID, exportFile);
				break;

			case 'g':
				year = "none";
				season = "none";
				courseName = "none";
				int[] gradeComp = null;
				System.out
						.println("You have chosen to return number of students who got"
								+ " a specific grade in a specific course in a specific semester.\n"
								+ "Please indicate the course number or type \"none\" to skip this step");
				courseName = keyboard.nextLine();
				System.out
						.println("Please indicate the semester (Fall or Spring) or type \"none\" to skip this step");
				season = keyboard.nextLine();
				
				if (!season.equals("none")) {
					System.out.println("Please indicate the year");
					year = keyboard.nextLine();
				}
				if (!courseName.equals("none") && !year.equals("none")
						&& !season.equals("none")) {
					gradeComp = generateGradeBook.findGrade(courseName, season.toUpperCase().charAt(0), year);
				}
				else if (courseName.equals("none") && !year.equals("none")
						&& !season.equals("none")) {
					gradeComp = generateGradeBook.findGrade(season.toUpperCase().charAt(0), year);
				}
				else if (!courseName.equals("none") && year.equals("none")
						&& season.equals("none")) {
					gradeComp = generateGradeBook.findGrade(courseName);
				}
				if(Arrays.equals(gradeComp, new int[] {0, 0, 0, 0, 0}))
					System.out
							.println("not enough information or invalid information was given. "
									+ "Returning to menu");
				else {
					int mod = 0;
					for(int i = 65; i < 71; i++) {
						if(i == 69) {
							i++;
							mod = 1;
						}
						System.out.println( (char) i + "'s :" + gradeComp[i-65-mod] + " ");
					}
				}
				break;

			case 'e':
				System.out.println("you have chosen to exit the gradebook.\n"
						+ "Thank you for using our gradebook.");
				keepGoing = false;
				keyboard.close();
				break;
				
			default:
				System.out.println("I'm sorry that was an invalid input, please try again");

			}

		}

	}

}
