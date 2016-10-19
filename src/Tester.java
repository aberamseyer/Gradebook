import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Tester {

	public static void main(String[] args) {
		GradebookFactory gb = new GradebookFactory();
		
		Semester s1 = new Semester("2002", "Fall");
		Course c1 = new Course("IT 226");
		Student st1 = new Student("aramsey", "Abe", "Ramseyer");
		ArrayList<Assignment> ases1 = new ArrayList<Assignment>();
		ases1.add(new Assignment(75, "you did well", "Assignment 1"));
		
		
		Performance p1 = new Performance(89.4, 'B');
		
		HashMap<Student, Performance> m1 = new HashMap<Student, Performance>();
		m1.put(st1, p1);
		p1.setAssignments(ases1);
		ArrayList<Course> courses = new ArrayList<Course>();
		c1.setCourseData(m1);
		courses.add(c1);
		gb.getSemesters().add(s1);
		gb.getSemesters().get(0).getCourses().add(c1);
		
		
		gb.getStudents().add(st1);
		
		if(true)
		System.out.println("fun");
		System.out.println("funfun");
		gb.saveData("aramsey", "abe.csv");
		System.out.println("----------------by course number----------");
		int[] grades = gb.findGrade("IT 226");
		for(int i = 0; i < grades.length; i++) {
			System.out.println(grades[i]);
		}
		System.out.println("----------by semester season/year----------");
		grades = gb.findGrade('F', "2002");
		for(int i = 0; i < grades.length; i++) {
			System.out.println(grades[i]);
		}
		System.out.println("-------------by all three----------------------");
		grades = gb.findGrade("IT 226", 'F', "2002");
		for(int i = 0; i < grades.length; i++) {
			System.out.println(grades[i]);
		}
	}

}
