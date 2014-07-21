package edu.spsu.cs6423;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class FindMissingNumber {

	private ArrayList<Integer> allNumbers = new ArrayList<Integer>();

	/**
	 * Reads from the file and calls the addNumbers function for processing the
	 * number
	 * 
	 * @param fileName
	 *            Name of the file to read from
	 */
	public void processFile(String fileName) {
		InputStream is = FindMissingNumber.class.getClassLoader().getResourceAsStream(fileName);
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
		
		Collections.sort(allNumbers);
		
		for (int count = 0; count < getAllNumbers().size() - 1; count++) {
			if (getAllNumbers().get(count) + 1 != getAllNumbers().get(count + 1))
				System.out.println("First missing number = " + getAllNumbers().get(count) + 1);
		}
	}

	public static void main(String[] args) {
		FindMissingNumber myNumberClass = new FindMissingNumber();
		
		if (args.length > 0)
			myNumberClass.processFile(args[0]);
		else
			myNumberClass.processFile("numbers10000.txt");

	}

	public ArrayList<Integer> getAllNumbers() {
		return allNumbers;
	}

	public void setAllNumbers(ArrayList<Integer> allNumbers) {
		this.allNumbers = allNumbers;
	}

}
