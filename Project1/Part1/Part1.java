package Project1.Part1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * CS6423 Algorithmic Processes Summer 2014 
 * Project 1: Part 1 - Sum number from file
 * 		And also find the subsequence, which
 * 		creates the largest sum 
 * Daniel Kerr and Charles So 
 * Date: 06/20/2014 
 * File: NumberReader.java
 */
public class Part1 {

	private int sum = 0;
	private ArrayList<Integer> numberAdded = new ArrayList<Integer>();
	private ArrayList<Integer> allNumbers = new ArrayList<Integer>();
	private static double oCount = 0;

	/**
	 * addNumbers receives a number and adds it to the sum and if the new sum is
	 * greater than the old sum, then we set the new sum. When the new sum is
	 * set, then we add the number into the list of numbers added
	 * 
	 * @param numberToAdd
	 *            number sent from processFile to added in from the processFile
	 */
	public void addNumbers(ArrayList<Integer> numberToAdd) {
		// test the number with tempSum
		int beginIndex = 0;
		int endIndex = 0;

		for (int outerCounter = 0; outerCounter < numberToAdd.size(); outerCounter++) {
			// We set the begin index as the outerCounter
			System.out.print(".");
			for (int innerCounter = outerCounter; innerCounter <= numberToAdd.size(); innerCounter++) {
				// We set the end index as the innerCounter
				int tempSum = 0;

				// We add from the begin index to the end index
				for (int summationCounter = outerCounter; summationCounter < innerCounter; summationCounter++) {
					tempSum += numberToAdd.get(summationCounter);
					incrOCount();
				}

				// We compare the sum from above and see if the value is larger
				// than the current maximum value
				if (tempSum > getSum()) {
					setSum(tempSum);
					beginIndex = outerCounter;
					endIndex = innerCounter - 1;
					System.out.println();
					System.out.println("New Maximum subsequent sum found with value of " + getSum());
				}
			}
		}
		System.out.println();

		// After we find out the beginning and ending of the subsequence
		// we save those values into an array that will hold the values.
		for (int i = beginIndex; i <= endIndex; i++) {
			getNumberAdded().add(getAllNumbers().get(i));
		}
	}

	/**
	 * Reads from the file and calls the addNumbers function for processing the
	 * number
	 * 
	 * @param fileName
	 *            Name of the file to read from
	 */
	public void processFile(String fileName) {
		InputStream is = Part1.class.getClassLoader().getResourceAsStream(fileName);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(is));

			String line;
			while ((line = br.readLine()) != null) {
				System.out.println("Reading in number = " + line);
				getAllNumbers().add(new Integer(line));
			}
		} catch (IOException ex) {
			System.out.println("IO Exception Occurred - "
					+ ex.getLocalizedMessage());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		addNumbers(getAllNumbers());

		System.out.println("All Numbers Processed");
		System.out.println();
		System.out.println("The largest subsequence sum is " + getSum());
		System.out.println("The sum was made with the following numbers: ");
		StringBuffer subSet = new StringBuffer();
		ArrayList<Integer> displayNumberAdded = getNumberAdded();

		for (Integer number : displayNumberAdded) {
			subSet.append(number).append(", ");
		}

		// Remove the comma;
		subSet.deleteCharAt(subSet.length() - 2);
		System.out.println(subSet.toString());
		System.out.println();

		System.out.println("This is the epitome of Brute Force.");
		System.out.println("The main action takes place inside a loop inside a loop inside a loop.\n"
				+ "We are looking at a Big O of n^3.\n\n"
				+ "From the file, we loaded up " + getAllNumbers().size() + " numbers\n\n"
				+ "With those numbers, we performed " + getOCount() + " summations\n"
				+ "(adding the current element to the temporary sum)");
	}

	public static void main(String[] args) {
		Part1 myNumberReader = new Part1();
		// If the file was defined, we use the file name
		if (args.length > 0)
			myNumberReader.processFile(args[0]);
		else
			myNumberReader.processFile("/numbers10000.txt");
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public ArrayList<Integer> getNumberAdded() {
		return numberAdded;
	}

	public void setNumberAdded(ArrayList<Integer> numberAdded) {
		this.numberAdded = numberAdded;
	}

	public ArrayList<Integer> getAllNumbers() {
		return allNumbers;
	}

	public void setAllNumbers(ArrayList<Integer> allNumbers) {
		this.allNumbers = allNumbers;
	}

	/**
	 * Increments oCount, used for tracking the number of main operations
	 */
	public static void incrOCount() {
		oCount++;
	}

	/**
	 * Getter method for oCount
	 * 
	 * @return int number of main operations executed by the algorithm.
	 */
	public static double getOCount() {
		return oCount;
	}
}
