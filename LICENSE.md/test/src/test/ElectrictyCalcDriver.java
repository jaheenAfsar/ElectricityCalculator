package test;

import java.util.Scanner;

public class ElectrictyCalcDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.print("ENTER THE REQUIRED INPUT FOR CALCULATION\n\n");
		System.out.print("ENTER THE MONTH(MMM) OF CALCULATION  ->");
		String month = scanner.nextLine();
		System.out.print("ENTER THE YEAR(YYYY) OF CALCULATION  ->");
		int year = scanner.nextInt();
		System.out.print("ENTER THE TOTAL AMOUNT IN INR  ->");
		float totalAmount = scanner.nextFloat();
		System.out.print("ENTER THE TOTAL UNITS AS SHOWN IN THE BILL kWh  ->");
		float totalUnits = scanner.nextFloat();
		
		ElectrcityCalculatorManager ele = new ElectrcityCalculatorManager();
		ele.calculateForMonth(Month.valueOf(month),year,totalAmount,totalUnits);

	}

}
