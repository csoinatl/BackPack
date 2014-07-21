package Project2.Part1;

import java.io.Console;

/**
 * CS6423 Algorithmic Processes
 * Summer 2014
 * Project 2: Part 1 - Making Change through algorithm
 * Daniel Kerr and Charles So
 * Date: 07/06/2014
 * File: Part2C.java
 */
public class MakeChange {

	static final int[] availCoins = {100, 25, 10, 5, 1};
	
	/**
	 * Function call to get the user input for the total amount
	 * 
	 * @return the value entered in by the user
	 */
	int getTotal(){
		Console console = System.console();
		String input = console.readLine("Enter the total amount: xxx ");

		return new Integer(input);
	}
	
	/**
	 * Function call to get the user input for the amount tendered
	 * 
	 * @return the value enterd in by the user
	 */
	int getTenderAmount() {
		Console console = System.console();
		String input = console.readLine("Enter the tendered amount: xxx ");

		return new Integer(input);
	}
	
	/**
	 * Calculate and return the amount of coins involved in an array
	 * 
	 * @param total Total amount to find the change
	 * @param tender Tendered amount from the user
	 * @return list of coins in an array format
	 */
	int[] getChange(int total, int tender) {
		if (total > tender) {
			System.out.println("The total amount is more than tendered.  Please try again");
			return null;
		}
		
		int[] changeToTender = new int[availCoins.length];
		double tempTotal = tender - total;
		for (int i = 0; i < availCoins.length; i++) {
			int curValue = availCoins[i];
			while (curValue < tempTotal) {
				tempTotal = tempTotal - curValue;
				++changeToTender[i];
			}
			
			if (curValue == tempTotal) {
				tempTotal = tempTotal - curValue;
				++changeToTender[i];
			}
		}
		
		return changeToTender;
	}
	
	public static void main(String[] args) {
		MakeChange changeMaker = new MakeChange();
		int TotalAmount = changeMaker.getTotal();
		int TenderAmount = changeMaker.getTenderAmount();
		
		int[] changeToTender = changeMaker.getChange(TotalAmount, TenderAmount);

		System.out.println("This is the change required: ");
		System.out.println();
		
		for (int counter = 0; counter < changeToTender.length; counter++) {
			System.out.println(availCoins[counter] + " = " + changeToTender[counter]);
		}
	}

}
