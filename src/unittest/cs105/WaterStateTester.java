package unittest.cs105;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.sbcc.cs105.Main;

public class WaterStateTester {
	private static final int maximumScore = 8;
	private static final int maximumAssignmentScore = 12;
	private static int totalScore;

	private PrintStream oldOut;
	private InputStream oldIn;
	private ByteArrayOutputStream baos;
	private ByteArrayInputStream bais;
	
	private static String[] testInputsC = { "-10C", "50C", "150C", "0C", "100C" };
	private static String[] testResultsC = { "SOLID", "LIQUID", "GAS", "SOLID", "GAS" };

	private static String[] testInputsF = { "-10F", "50F", "250F", "0F", "300F" };
	private static String[] testResultsF = { "SOLID", "LIQUID", "GAS", "SOLID", "GAS" };

	@BeforeClass
	public static void beforeTesting() {
		totalScore = 0;
	}

	@AfterClass
	public static void afterTesting() {
		System.out.printf("Your program's functionality scores %d out of %d.\n\n", totalScore, maximumScore);

		int difference = maximumAssignmentScore - maximumScore;
		String correctedPoint = (difference == 1) ? "point" : "points";

		System.out.printf("The assignment is worth a total of %d where the remainder of %d %s\n",
				maximumAssignmentScore, difference, correctedPoint);
		System.out.println("comes from grading related to documentation, algorithms, and other");
		System.out.println("criteria.");
	}

	private void runTest(String temp) {
		this.bais = new ByteArrayInputStream((temp + "\n").getBytes());
		System.setIn(this.bais);		
	}
	
	private void test(String expected) {
		Pattern tester = Pattern.compile(expected);
		Matcher testMatcher = tester.matcher(this.baos.toString());
		assertTrue("Check", testMatcher.matches());	
	}
	
	@Before
	public void setUp() {
		this.baos = new ByteArrayOutputStream();
		this.oldOut = System.out;
		this.oldIn  = System.in;
		System.setOut(new PrintStream(baos));
	}

	@After
	public void tearDown() {
		System.setOut(this.oldOut);
		System.setIn(this.oldIn);
		
		this.baos.reset();
	}

	@Test
	public void testC() {
		runTest("-10C");
		Main.main(null);
		
		test("Enter a temperature:\\s+Water state:\\s+SOLID\\s*");

		for (int i = 0; i < testInputsC.length; i++) {
			tearDown();
			setUp();
			
			runTest(testInputsC[i]);
			Main.main(null);
			
			test("Enter a temperature:\\s+Water state:\\s+" + testResultsC[i] + "\\s*");
		}
		totalScore += 4;
	}

	@Test
	public void testF() {
		runTest("-10F");
		Main.main(null);
		
		test("Enter a temperature:\\s+Water state:\\s+SOLID\\s*");

		for (int i = 0; i < testInputsF.length; i++) {
			tearDown();
			setUp();
			
			runTest(testInputsF[i]);
			Main.main(null);
			
			test("Enter a temperature:\\s+Water state:\\s+" + testResultsF[i] + "\\s*");
		}
		
		totalScore += 4;
	}
}
