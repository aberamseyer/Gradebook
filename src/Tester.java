import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Tester {

	public static void main(String[] args) {
		GradebookFactory gb = new GradebookFactory();
		
		Semester s1 = new Semester("2002", "Fall");
		Course c1 = new Course("IT 226");
		Student st1 = new Student("aramsey", "Abe", "Ramseyer");
		HashSet<Assignment> ases1 = new HashSet<Assignment>();
		ases1.add(new Assignment(75, 'C', "you did well"));
		
		
		Performance p1 = new Performance(89.4, 'B');
		HashMap<Student, Performance> m1 = new HashMap<Student, Performance>();
		m1.put(st1, p1);
		
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
	}

}
