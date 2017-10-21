package test;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ElectrcityCalculatorManager {

	private boolean fileCreated = false;
	//private String curDir = null;
	private ElectricityCalcualtor calc = null;
	float[] units = null;
	float[] prev_units = {0,0,0};
	float[] charge = null;
	float totalAmount;
	
	public void calculateForMonth(Month month, int year, float totalAmount,float totalUnits ){
		if(null == month ){
			System.out.println("Check the month input in the format MMM  "+month);
			return;
		}
		if(year == 0 || year<2017) {
			System.out.println("Check the year input in the format YYYY  "+year);
			return;
		}
		if(totalAmount <= 0 || totalUnits <= 0) {
			System.out.println("total amount or total units consumed cannot be zero or less...");
			return;
		}
		this.totalAmount = totalAmount;
		List<String> fileRows = ElectricityFileProcessorUtil.readFile(ElectricityConstants.FILE_NAME);
		if(null != fileRows){
		String[] monthsUpdated = fileRows.get(0).split(ElectricityConstants.SPLITTER);
		CalendarManupulator calMan = new CalendarManupulator();
		String previousMonth = calMan.getPerviousMonth(month, year);
		System.out.println("The pervious month is..."+previousMonth);
			int lineNumber = -1;
			try {
				lineNumber = ElectricityFileProcessorUtil.searchForEntry(previousMonth);
			} catch (IOException e) {
				System.out.println("error in file reading");
				e.printStackTrace();
			}
			prev_units = ElectricityFileProcessorUtil.processUnitsForLineNumber(lineNumber);
			System.out.println("the previous units will bee taken from the bill  "+previousMonth);
		}
			getUnitsForDifferentPersons();
			getprevUnitsFromUser();
			caluclateCurrentUnits(prev_units);
			calculateCurrentCharge();
			try {
				ElectricityFileProcessorUtil.writeOutputToFile(month, year+"",units,charge, totalAmount, totalUnits);
			} catch (Exception e) {
				System.out.println("error in file writing");
				e.printStackTrace();
			}
			printCaluculation();
		}
	
//	public void calculateForNextMonth(){
//		List<String> fileRows = null;
//		try {
//			fileRows = ElectricityFileProcessorUtil.readFile("electrcity_bill_details.txt");
//		} catch (IOException e) {
//			e.printStackTrace();
//			System.out.println("An error has occrueed while reading the file");
//		}
//		
//	}
	
	private void caluclateCurrentUnits(float[] prev_units){
	if(prev_units.length < ElectricityConstants.NO_OF_ROOMS) {
		System.out.println("error in processing the previous month units pls check");
	}
	for(int i=0 ;i< prev_units.length ;i++){
		units[i] -= prev_units[i]; 
	}
	}
	
	private void calculateCurrentCharge(){
		calc = new ElectricityCalcualtor();
		for(int i=0; i<units.length ;i++){
			charge[i] = calc.calculationForUnits(units[i]);
		}
		charge = calc.splitRemainingEqually(charge, totalAmount);
	}
	
	private void printCaluculation(){
		System.out.println("THE CHARGE FOR ROOM\n");
		for(int i=0 ;i<charge.length ;i++){
			System.out.println(ElectricityConstants.NAMES[i]+"\t");
			System.out.println("units consumed ->"+units[i]+"\t");
			System.out.println("total charge ->"+charge[i]);
		}
	}
	
	private void getUnitsForDifferentPersons(){
		System.out.print("Please check the input & Do you want to continue: Y or N");
		Scanner scanner = new Scanner(System.in);
		String proceed = scanner.nextLine();
		if(null != proceed && proceed.equalsIgnoreCase("Y")){
			units = new float[ElectricityConstants.NO_OF_ROOMS];
			charge = new float[ElectricityConstants.NO_OF_ROOMS];
		for(int i=0; i < ElectricityConstants.NO_OF_ROOMS;i++){
			System.out.println("Enter the readings from "+ElectricityConstants.NAMES[i]+"'s meter");
			units[i] = scanner.nextFloat();
			System.out.println(ElectricityConstants.NAMES[i]+"..."+units[i]);
		}
		}  else {
			System.out.println("program terminating as you have chose not to continue");
			System.exit(0);
		}
	}
	
	private void getprevUnitsFromUser(){
		System.out.println("Do you want to manually enter the previous month meter readings: Y or N");
		Scanner scanner = new Scanner(System.in);
		String proceed = scanner.nextLine();
		if(null != proceed && proceed.equalsIgnoreCase("Y")){
			for(int i=0; i < ElectricityConstants.NO_OF_ROOMS;i++){
				System.out.println("Enter the PREVIOUS MONTH readings for "+ElectricityConstants.NAMES[i]+"'s meter");
				prev_units[i] = scanner.nextFloat();
				System.out.println(ElectricityConstants.NAMES[i]+"..."+units[i]);
			}
			scanner.close();
		}
	}
}
