/**
 * CS 105 Theory & Practice I
 * CRN: 62499
 * Assignment: Water State
 * 
 * @description This program takes the temperature and pressure values for a hypothetical sample of H2O (water) and outputs the theoretical physical state it should be in with a 3% margin of error
 * 
 * The equations used for determining solid water states are derived from The International Association for the Properties of Water and Steam
 * Source: http://www.iapws.org/relguide/MeltSub2011.pdf
 * The equations used for determining Gas water states are derived from the Antoine equation which is, in turn, derived from the Clausius�Clapeyron relation
 * Source: https://en.wikipedia.org/wiki/Vapor_pressure#Boiling_point_of_water
 * 
 * Statement of code ownership: I hereby state that I have written all of this
 * code and I have not copied this code from any other person or source.
 * 
 * @version a.1.1.1
 * 
 * @author Mattys C vanZeyl
 * @website https://www.tysseract.net
 */
package edu.sbcc.cs105;

import java.util.Scanner;

public class WaterState {
	public enum MatterState { SOLID, LIQUID, GAS, ERROR }
	
	public static String getWaterState(String temperature, String pressure) {
			
			if(pressure.equals("")) {
				pressure = "1atm";
				System.out.println("!Info: assuming 1 atm");
			}
			
			MatterState WaterState = MatterState.ERROR;
		
			//transform temperature to Celcius
			char temperatureUnit = temperature.charAt(temperature.length() - 1);
		
			double rawTemperatureValue;
			if(temperature.length() <= 1) rawTemperatureValue = Double.parseDouble(temperature);
			else rawTemperatureValue = Double.parseDouble(temperature.substring(0, temperature.length() - 1));
			
			double temperatureValueC = 0.0;
			if(temperatureUnit == 'F' || temperatureUnit == 'f') temperatureValueC = (rawTemperatureValue - 32.0) * (5.0/9.0);
			else if(temperatureUnit == 'K' || temperatureUnit == 'k') temperatureValueC = (rawTemperatureValue - 273.15);
			else if(temperatureUnit == 'C' || temperatureUnit == 'c') temperatureValueC = rawTemperatureValue;
			else {
				System.out.println("!Warning: Invalid or Unknown Temperature unit. Assuming Celcius... ");
				temperatureValueC = Double.parseDouble(temperature);
			}
			
			//and then to Kelvins
			double temperatureValueK = temperatureValueC + 273.15;
			
			//transform pressure to Torr
			char pressureUnit = pressure.charAt(pressure.length() - 1);
			
			double pressureValueTorr = 0.0;
			if(pressureUnit == 'a') {//Pa
				if((pressure.charAt(pressure.length() - 3) == 'T') || (pressure.charAt(pressure.length() - 3) == 't')) pressureValueTorr = Double.parseDouble(pressure.substring(0, pressure.length() - 3)) * (1000000000000.0 / 133.322368421);
				else if((pressure.charAt(pressure.length() - 3) == 'G') || (pressure.charAt(pressure.length() - 3) == 'g')) pressureValueTorr = Double.parseDouble(pressure.substring(0, pressure.length() - 3)) * (1000000000.0 / 133.322368421);
				else if((pressure.charAt(pressure.length() - 3) == 'M') || (pressure.charAt(pressure.length() - 3) == 'm')) pressureValueTorr = Double.parseDouble(pressure.substring(0, pressure.length() - 3)) * (1000000.0 / 133.322368421);
				else if((pressure.charAt(pressure.length() - 3) == 'K') || (pressure.charAt(pressure.length() - 3) == 'k')) pressureValueTorr = Double.parseDouble(pressure.substring(0, pressure.length() - 3)) * (1000.0 / 133.322368421);
				else pressureValueTorr = Double.parseDouble(pressure.substring(0, pressure.length() - 2)) / 133.322368421;
			}
			else if(pressureUnit == 'm') pressureValueTorr = Double.parseDouble(pressure.substring(0, pressure.length() - 3)) * 760;//atm
			else if(pressureUnit == 'r') pressureValueTorr = Double.parseDouble(pressure.substring(0, pressure.length() - 4));//Torr
			else {
				System.out.println("!Warning: Invalid or Unknown Pressure unit. Assuming Torr... ");
				pressureValueTorr = Double.parseDouble(pressure);
			}
			//and now to Pa
			double pressureValuePa = pressureValueTorr * 133.3223684;
		
			//figure out the boiling point
			//double vaporPressureTorr = Math.pow(10, (8.07131-(1730.63/(233.426 + temperatureValueC))));
			double boilingPointC = 100.0;
			
			boilingPointC = (1730.63/(8.07131-Math.log10(pressureValueTorr))) - 233.426;
			//System.out.println(vaporPressureTorr);
			
			
			//figure out the freezing point
			//double freezingPressureTorr = (1 - (0.626000*Math.pow(10, 6))*(1 - Math.pow(((temperatureValueC-273.16)/273.16),(-3))) + 0.197135*Math.pow(10,6)*(1 - Math.pow(((temperatureValueC - 273.16)/273.16),21.2))) * 0.000611657;
			//double freezingPoint = 0.0;
			
			
			double meltingPressurePa = 0.0;
			//do the calculations
			if(temperatureValueC >= boilingPointC) WaterState = MatterState.GAS; //below the vapour curve
			else if(pressureValuePa <= 208566000.0) { //everything below ice Ih - ice III - liquid triple point
				System.out.println("!Info: using equation 3.1");//3.1 Melting pressure of ice Ih (temperature range from 273.16 K to 251.165 K)
				if(temperatureValueK >= 273.16) WaterState = MatterState.LIQUID;
				else if(temperatureValueK <= 251.165) WaterState = MatterState.SOLID;
				else { //where the pressure-temperature curve exists between 273.16 K and 251.165 K
					double reducedTemperature = temperatureValueK / 273.16;
					double reducedPressure = 1 + ((1195393.37 * (1 - Math.pow(reducedTemperature, 3.0))) + (80818.3159 * (1 - Math.pow(reducedTemperature, 25.75))) + (3338.26860 * (1 - Math.pow(reducedTemperature,  103.750))));
					meltingPressurePa = reducedPressure * 611.657;
					if(pressureValuePa <= meltingPressurePa) WaterState = MatterState.SOLID;
					else WaterState = MatterState.LIQUID;
				}
				if(Math.abs(meltingPressurePa - pressureValuePa)/Math.min(meltingPressurePa, pressureValuePa) <= 0.02) System.out.println("!Caution: difference is within the 2% margin of error; results may not be experimentally accurate... ");
			}
			else if(pressureValuePa <= 350100000.0) { //everything else below ice III - ice V - liquid triple point
				System.out.println("!Info: using equation 3.2");//3.2 Melting pressure of ice III (temperature range from 251.165 K to 256.164 K)
				if(temperatureValueK >= 256.164) WaterState = MatterState.LIQUID;
				else if(temperatureValueK <= 251.165) WaterState = MatterState.SOLID;
				else { //where the pressure-temperature curve exists between  251.165 K and 256.164 K
					double reducedTemperature = temperatureValueK / 251.165;
					double reducedPressure = 1 - 0.299948 * (1 - Math.pow(reducedTemperature, 60.0));
					meltingPressurePa = reducedPressure * 208566000.0;
					if(pressureValuePa >= meltingPressurePa) WaterState = MatterState.SOLID;
					else WaterState = MatterState.LIQUID;
				}
				if(Math.abs(meltingPressurePa - pressureValuePa)/Math.min(meltingPressurePa, pressureValuePa) <= 0.03) System.out.println("!Caution: difference is within the 3% margin of error; results may not be experimentally accurate... ");
			}
			else if(pressureValuePa <= 632400000.0) { //everything else below ice V - ice VI - liquid triple point
				System.out.println("!Info: using equation 3.3");//3.3 Melting pressure of ice V (temperature range from 256.164 K to 273.31 K)
				if(temperatureValueK >= 273.31) WaterState = MatterState.LIQUID;
				else if(temperatureValueK <= 256.164) WaterState = MatterState.SOLID;
				else { //where the pressure-temperature curve exists between  256.164 K and 273.31 K
					double reducedTemperature = temperatureValueK / 256.164;
					double reducedPressure = 1 - 1.18721 * (1 - Math.pow(reducedTemperature, 8.0));
					meltingPressurePa = reducedPressure * 350100000.0;
					if(pressureValuePa >= meltingPressurePa) WaterState = MatterState.SOLID;
					else WaterState = MatterState.LIQUID;
				}
				if(Math.abs(meltingPressurePa - pressureValuePa)/Math.min(meltingPressurePa, pressureValuePa) <= 0.03) System.out.println("!Caution: difference is within the 3% margin of error; results may not be experimentally accurate... ");
			}
			else if(pressureValuePa <= 2216000000.0) { //everything else below ice VI - ice VII - liquid triple point
				System.out.println("!Info: using equation 3.4");//3.4 Melting pressure of ice VI (temperature range from 273.31 K to 355 K)
				if(temperatureValueK >= 355) WaterState = MatterState.LIQUID;
				else if(temperatureValueK <= 273.31) WaterState = MatterState.SOLID;
				else { //where the pressure-temperature curve exists between  273.31 K and 355 K
					double reducedTemperature = temperatureValueK / 273.31;
					double reducedPressure = 1 - 1.07476 * (1 - Math.pow(reducedTemperature, 4.6));
					meltingPressurePa = reducedPressure * 632400000.0;
					if(pressureValuePa >= meltingPressurePa) WaterState = MatterState.SOLID;
					else WaterState = MatterState.LIQUID;
				}
				if(Math.abs(meltingPressurePa - pressureValuePa)/Math.min(meltingPressurePa, pressureValuePa) <= 0.03) System.out.println("!Caution: difference is within the 3% margin of error; results may not be experimentally accurate... ");
			}
			else if(temperatureValueK <= 10000.0 && pressureValuePa <= 100000000000.0) { //everything else (accurate below 715K)
				if(temperatureValueK > 715.0) System.out.println("!Warning: above 715 Kelvins, accuracy is not tested to within 3%... ");
				System.out.println("!Info: using equation 3.5");//3.5 Melting pressure of ice VII (temperature range from 355 K to 715 K)
				if(temperatureValueK <= 355) WaterState = MatterState.LIQUID;
				//not necessary: last equation, most accurate for above 715K
				//else if(temperatureValueK >= 715.0) WaterState = MatterState.SOLID;
				else { //where the pressure-temperature curve exists between 355 K and 715 K
					double reducedTemperature = temperatureValueK / 273.31;
					double reducedPressure = 1 - 1.07476 * (1 - Math.pow(reducedTemperature, 4.6));
					//System.out.println("reduced pressure: " + reducedPressure);
					meltingPressurePa = reducedPressure * 2216000000.0;
					if(pressureValuePa >= meltingPressurePa) WaterState = MatterState.SOLID;
					else WaterState = MatterState.LIQUID;
				}
				if(Math.abs(meltingPressurePa - pressureValuePa)/Math.min(meltingPressurePa, pressureValuePa) <= 0.07) System.out.println("!Caution: difference is within the 7% margin of error; results may not be experimentally accurate... ");
			}
			else {
				System.out.println("!Error: temperature must be below 10000K and the pressure must be below 100GPa... ");
			}
			
			//System.out.println("temperatureValueK: " + temperatureValueK);
			//System.out.println("meltingPressurePa: " + meltingPressurePa);
			//System.out.println("pressureValuePa: " + pressureValuePa);
			
			String returnString = "Water state: " + WaterState;
			return returnString;
		
		
	}
	
	public static String getWaterState(String temperature) {
		return getWaterState(temperature, "");
	}
	
	public static void main(String[] args) {
		
		//introduce the program
		System.out.println("WaterState.java: takes the temperature and pressure values for a hypothetical sample of H2O (water) and outputs the theoretical physical state it should be in");
		System.out.println("Solid water states are derived from IAPWS equations and gas water states are derived from the Antoine equation");
		System.out.println("Program written by Mattys C vanZeyl, https://www.tysseract.net");
		
		//initialize scanner
		Scanner in = new Scanner(System.in);
		//don't repeat unless told otherwise
		boolean yn = false;
		do {//at least once
			
			System.out.println("Enter a temperature as any number followed by one letter unit in F, C, or K (ie: 123.4C): ");
			String temperature = in.nextLine();
			System.out.println("Enter the atmospheric pressure as any number directly followed the unit in Torr, Atm, Pa, or Mpa (ie: 120000Pa); leave blank to assume 1atm: ");
			String pressure = in.nextLine();
			System.out.println(getWaterState(temperature, pressure));
			System.out.println("Continue? (y/n): ");
			String continueValue = in.nextLine();
			if(continueValue.equals(""));
			else if(continueValue.contains("n")) yn = false;
			else if(continueValue.contains("y")) yn = true;
		}while(yn);
		
		in.close();
	}
	
}
