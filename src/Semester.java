import java.util.ArrayList;

/**
 * @author Abe
 *
 */
public class Semester {
	private String year;
	private char season;
	private ArrayList<Course> courses;
	
	public Semester() {
		this.year = null;
		this.season = ' ';
		this.courses = new ArrayList<Course>(0);
	}
	
	public Semester(String year) {
		this();
		this.year = year;
	}
	
	/**
	 * constructor
	 * @param year the year as a String
	 * @param season the season as a String, should be some variation of S, s, F, f, Fall, Spring, etc. (NOT summer)
	 */
	public Semester(String year, String season) {
		this(year);
		if(season.charAt(0) == 'F' || season.charAt(0) == 'f') {
			this.season = 'F';
		}
		if(season.charAt(0) == 'S' || season.charAt(0) == 's') {
			this.season = 'S';
		}
	}
	
	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * @return the season
	 */
	public char getSeason() {
		return season;
	}
	/**
	 * @param season the season to set
	 */
	public void setSeason(char season) {
		this.season = season;
	}
	/**
	 * @return the courses
	 */
	public ArrayList<Course> getCourses() {
		return courses;
	}
	/**
	 * @param courses the courses to set
	 */
	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}
	
	
}
