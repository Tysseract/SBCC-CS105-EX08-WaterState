package edu.sbcc.cs105;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
	
	public enum MatterState {
		SOLID,
		LIQUID,
		GAS
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Pattern defaultPattern = in.delimiter();
		in.useDelimiter("\\s*F|C\\s*");
		
		System.out.print("Enter a temperature: ");
		double temp = in.nextDouble();
		
		in.useDelimiter(defaultPattern);
		String unit = in.next();
		
		if(unit.equals("F")) {
			temp = 5.0 / 9.0 * (temp - 32.0); 
		}
		
		MatterState state = MatterState.SOLID;
		
		if(temp > 0.0) {
			if(temp < 100.0) {
				state = MatterState.LIQUID;
			} else {
				state = MatterState.GAS;
			}
		}
		
		System.out.println("Water state: " + state);
	}

}
