/**
 * CS 105 Theory & Practice I
 * CRN: 62499
 * Assignment: Integer Pairs
 * 
 * Statement of code ownership: I hereby state that I have written all of this
 * code and I have not copied this code from any other person or source.
 * 
 * @author Mattys C vanZeyl
 */
package edu.sbcc.cs105;

import java.util.Scanner;

public class WaterState {
	public enum MatterState { SOLID, LIQUID, GAS }
	
	public static String getWaterState(String temperature) {
		
		MatterState WaterState;
		
			double temperatureValue = Double.parseDouble(temperature.substring(0, temperature.length() - 1));
			char temperatureUnit = temperature.charAt(temperature.length() - 1);
			if(temperatureUnit == 'F') temperatureValue = (temperatureValue - 32.0) * (5.0/9.0);
			else if(temperatureUnit == 'K') temperatureValue = (temperatureValue - 273.15);
			else if(temperatureUnit == 'C');
			else {
				System.out.println(" ! Invalid or Unknown unit. Assuming Celcius ! ");
			}
		
			double freezingPoint = 0.0;
			double boilingPoint = 100.0;
			
			if(temperatureValue <= freezingPoint) WaterState = MatterState.SOLID;
			else if(temperatureValue >= boilingPoint) WaterState = MatterState.GAS;
			else WaterState = MatterState.LIQUID;
			
			String returnString = "Water state: " + WaterState;
			return returnString;
		
		
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        System.out.print("Enter a temperature: ");
        String temperature = in.next();
        in.close();
        System.out.print(getWaterState(temperature));
	}
	
}
