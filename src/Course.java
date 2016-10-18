/**
 * 
 * @author Abe
 *
 */
import java.util.HashMap;

public class Course {
	private String courseID;
	private HashMap<Student, Performance> courseData;
	
	public Course() {
		this.courseID = null;
		this.courseData = new HashMap<Student, Performance>(12);
	}
	
	public Course(String courseID) {
		this();
		this.courseID = courseID;
	}
	
	/**
	 * @return the courseID
	 */
	public String getCourseID() {
		return courseID;
	}
	/**
	 * @param courseID the courseID to set
	 */
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	/**
	 * @return the courseData
	 */
	public HashMap<Student, Performance> getCourseData() {
		return courseData;
	}
	/**
	 * @param courseData the courseData to set
	 */
	public void setCourseData(HashMap<Student, Performance> courseData) {
		this.courseData = courseData;
	}
	
	
}
