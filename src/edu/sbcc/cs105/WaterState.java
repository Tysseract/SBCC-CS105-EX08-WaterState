/**
 * CS 105 Theory & Practice I
 * CRN: 62499
 * Assignment: Integer Pairs
 * 
 * The equations used for determining solid water state are derived from The International Association for the Properties of Water and Steam
 * Source: http://www.iapws.org/relguide/MeltSub2011.pdf
 * 
 * Statement of code ownership: I hereby state that I have written all of this
 * code and I have not copied this code from any other person or source.
 * 
 * @author Mattys C vanZeyl
 */
package edu.sbcc.cs105;

import java.util.Scanner;

public class WaterState {
	public enum MatterState { SOLID, LIQUID, GAS, ERROR }
	
	public static String getWaterState(String temperature, String pressure) {
		
			MatterState WaterState = MatterState.ERROR;
		
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
			
			//and then to Kelvins
			double temperatureValueK = temperatureValueC + 273.15;
			
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
			
			
			double meltingPressurePa;
			//do the calculations
			if(temperatureValueC >= boilingPointC) WaterState = MatterState.GAS; //below the vapour curve
			else if(pressureValuePa <= 208566000) { //everything below ice Ih - ice III - liquid triple point
				//3.1 Melting pressure of ice Ih (temperature range from 273.16 K to 251.165 K)
				if(temperatureValueK >= 273.16) WaterState = MatterState.LIQUID;
				else if(temperatureValueK <= 251.165) WaterState = MatterState.SOLID;
				else { //where the pressure-temperature curve exists between 273.16 K and 251.165 K
					double reducedTemperature = temperatureValueK / 273.16;
					double reducedPressure = 1 + ((1195393.37 * (1 - Math.pow(reducedTemperature, 3.0))) + (80818.3159 * (1 - Math.pow(reducedTemperature, 25.75))) + (3338.26860 * (1 - Math.pow(reducedTemperature,  103.750))));
					meltingPressurePa = reducedPressure * 611.657;
					if(pressureValuePa <= meltingPressurePa) WaterState = MatterState.SOLID;
					else WaterState = MatterState.LIQUID;
				}
			}
			else if(pressureValuePa <= 350100000) { //everything else below ice III - ice V - liquid triple point
				//3.2 Melting pressure of ice III (temperature range from 251.165 K to 256.164 K)
				if(temperatureValueK >= 256.164) WaterState = MatterState.LIQUID;
				else if(temperatureValueK <= 251.165) WaterState = MatterState.SOLID;
				else { //where the pressure-temperature curve exists between  251.165 K and 256.164 K
					double reducedTemperature = temperatureValueK / 251.165;
					double reducedPressure = 1 - 0.299948 * (1 - Math.pow(reducedTemperature, 60.0));
					meltingPressurePa = reducedPressure * 208.566;
					if(pressureValuePa <= meltingPressurePa) WaterState = MatterState.SOLID;
					else WaterState = MatterState.LIQUID;
				}
			}
			else if(pressureValuePa <= 632400000) { //everything else below ice V - ice VI - liquid triple point
				//3.3 Melting pressure of ice V (temperature range from 256.164 K to 273.31 K)
				if(temperatureValueK >= 273.31) WaterState = MatterState.LIQUID;
				else if(temperatureValueK <= 256.164) WaterState = MatterState.SOLID;
				else { //where the pressure-temperature curve exists between  256.164 K and 273.31 K
					double reducedTemperature = temperatureValueK / 256.164;
					double reducedPressure = 1 - 1.18721 * (1 - Math.pow(reducedTemperature, 8.0));
					meltingPressurePa = reducedPressure * 350.1;
					if(pressureValuePa <= meltingPressurePa) WaterState = MatterState.SOLID;
					else WaterState = MatterState.LIQUID;
				}
			}
			else WaterState = MatterState.ERROR;
			
			
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
