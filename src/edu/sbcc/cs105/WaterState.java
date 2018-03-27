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
	
	public static String getWaterState(String temperature, String pressure) {
		
			MatterState WaterState;
		
			//transform temperature to Celcius
			char temperatureUnit = temperature.charAt(temperature.length() - 1);
		
			double rawTemperatureValue = Double.parseDouble(temperature.substring(0, temperature.length() - 1));
			double temperatureValueC = 0.0;
			if(temperatureUnit == 'F') temperatureValueC = (rawTemperatureValue - 32.0) * (5.0/9.0);
			else if(temperatureUnit == 'K') temperatureValueC = (rawTemperatureValue - 273.15);
			else if(temperatureUnit == 'C') temperatureValueC = rawTemperatureValue;
			else {
				System.out.println(" ! Invalid or Unknown Temperature unit. Assuming Celcius ! ");
				temperatureValueC = Double.parseDouble(temperature);
			}
			
			//transform pressure to Torr
			char pressureUnit = pressure.charAt(pressure.length() - 1);
			
			double pressureValueTorr = 0.0;
			if(pressureUnit == 'a') pressureValueTorr = Double.parseDouble(pressure.substring(0, pressure.length() - 2)) / 133.322368421;
			else if(pressureUnit == 'm') pressureValueTorr = Double.parseDouble(pressure.substring(0, pressure.length() - 3)) * 760;
			else if(pressureUnit == 'r') pressureValueTorr = Double.parseDouble(pressure.substring(0, pressure.length() - 4));
			else {
				System.out.println(" ! Invalid or Unknown Pressure unit. Assuming Torr ! ");
				pressureValueTorr = Double.parseDouble(pressure);
			}
		
			//figure out the boiling point
			double boilingPoint = 100.0;
			
			boilingPoint = (1730.63/(8.07131-Math.log10(pressureValueTorr))) - 233.426;
			
			System.out.println(boilingPoint);
			
			//figure out the freezing point
			double freezingPoint = 0.0;
			
			
			
			//do the calculations
			if(temperatureValueC <= freezingPoint) WaterState = MatterState.SOLID;
			else if(temperatureValueC >= boilingPoint) WaterState = MatterState.GAS;
			else WaterState = MatterState.LIQUID;
			
			
			String returnString = "Water state: " + WaterState;
			return returnString;
		
		
	}
	
	public static String getWaterState(String temperature) {
		return getWaterState(temperature, "1atm");
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        System.out.print("Enter a temperature as any number followed by one letter unit in F, C, or K (ie: 123.4C): ");
        String temperature = in.next();
        System.out.print("Enter the current atmospheric pressure as any number directly followed the unit in Torr, Atm, or Pa (ie: 120000Pa): ");
        String pressure = in.next();
        in.close();
        System.out.print(getWaterState(temperature, pressure));
	}
	
}
