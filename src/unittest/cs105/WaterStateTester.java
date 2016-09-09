package unittest.cs105;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.sbcc.cs105.WaterState;

public class WaterStateTester {
	private static final int maximumScore = 8;
	private static final int maximumAssignmentScore = 12;
	private static int totalScore;

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

	@Test
	public void testC() {		
		assertEquals("Water state: SOLID",	WaterState.getWaterState("-10C"));

		for (int i = 0; i < testInputsC.length; i++) {
			assertEquals("Water state: " + testResultsC[i], WaterState.getWaterState(testInputsC[i]));
		}
		totalScore += 4;
	}

	@Test
	public void testF() {
		assertEquals("Water state: SOLID", WaterState.getWaterState("-10F"));

		for (int i = 0; i < testInputsF.length; i++) {			
			assertEquals("Water state: " + testResultsF[i], WaterState.getWaterState(testInputsF[i]));
		}
		
		totalScore += 4;
	}
}
