public class Tester {

	public static void main(String[] args) {
		GradebookFactory factory = new GradebookFactory();
		
		factory.readData("fall", "2002", "IT 380");
		factory.readData("fall", "2003", "IT 437");
		factory.readData("fall", "2002", "IT 437");
	}
}
