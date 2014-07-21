package Project2.Part2;

import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * CS6423 Algorithmic Processes
 * Summer 2014
 * Project 2: Part 2A - Brute Force Algorithm for Knapsack problem
 * Daniel Kerr and Charles So
 * Date: 7/6/2014
 * File: Part2A.java
 */
public class Part2A implements Runnable {

	// Private internal variables
	private Store exhaustiveStore;
	private Double[] valueList;
	private Double[] weightList;
	private double weightLimit;
	private KnapSack theKnapsack = new KnapSack();

	/**
	 * Constructor for this algorithm, will receive the exhaustive store, and 
	 * set it as the variable to start from
	 * 
	 * @param store Store object defined, the loads value/weights from the files
	 */
	public Part2A(Store store) {
		this.exhaustiveStore = store;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		long start = System.currentTimeMillis();

		// Get the initialization from the store
		setValueList(exhaustiveStore.getValues());
		setWeightList(exhaustiveStore.getWeights());
		setWeightLimit(exhaustiveStore.getMaxWeight());

		KnapSack selectedKnapSack = new KnapSack();

		// This is important, we need to find the total
		// number of combinations to run through
		BigInteger bi1 = new BigInteger("2");
		BigInteger bi2;

		bi2 = bi1.pow(valueList.length);

		// Initialize the counter
		BigInteger counter = new BigInteger("0");
		while (counter.compareTo(bi2) < 0) {

			// Convert the large counter into a binary string of
			// length , which should be the number of values/weights
			String binaryString = counter.toString(2);
			
			// We need to buffer the beginning with 0's 
			while (binaryString.length() < valueList.length) {
				binaryString = "0" + binaryString;
			}

			// This is to only print out, if we see at least 20, 0's
			if (binaryString.endsWith("00000000000000000000"))
				System.out.println("Counter at = " + binaryString);
			
			// We need to convert the String to the character array for easier traversal
			char[] binaryChar = binaryString.toCharArray();
			Double totalWeight = new Double(0);
			Double totalValue = new Double(0);

			// Easiest loop in the code - when I see 1, add the value corresponding to that position
			// if 0, then we move to the next one.
			for (int charCounter = 0; charCounter < binaryString.length(); charCounter++) {
				if (binaryChar[charCounter] == '1') {
					// Since it exists
					totalWeight += weightList[charCounter];
					totalValue += valueList[charCounter];
					if (totalWeight > weightLimit)
						continue;
				}
			}
			
			// If we see that the total weight has gone past, then we skip out of the loop
			if (totalWeight > weightLimit) {
				counter = counter.add(BigInteger.ONE);
				continue;
			}

			// Initial bag
			if (selectedKnapSack.getSelectedBinary().length() == 0) {
				selectedKnapSack.setTotalWeight(totalWeight);
				selectedKnapSack.setTotalValue(totalValue);
				selectedKnapSack.setSelectedBinary(binaryString);
			} else {
				// Check to see if we have a larger value for the bag and replace it as needed
				if (totalWeight < weightLimit) {
					if (totalValue > selectedKnapSack.getTotalValue()) {
						System.out.println("New high found - new value = "
								+ String.format( "%.2f", totalValue) + " @ " + binaryString);
						// System.out.print(".");
						selectedKnapSack.setTotalWeight(totalWeight);
						selectedKnapSack.setTotalValue(totalValue);
						selectedKnapSack.setSelectedBinary(binaryString);
					}
				}
			}

			counter = counter.add(BigInteger.ONE);
		}
		System.out.println();
		System.out.println("This is the result of the exhaustive search");
		System.out.print("The highest value with in the weight limit was ");
		System.out.println( String.format( "%.2f", selectedKnapSack.getTotalValue()));
		System.out.print("The total weight was ");
		System.out.println( String.format( "%.2f", selectedKnapSack.getTotalWeight()));
		System.out.println("The set was composed of the following value/weight:");
		System.out.println();

		// Print out the list of value/weight pair that made it to the largest list.
		char[] binaryRep = selectedKnapSack.getSelectedBinary().toCharArray();
		boolean first = true;

		for (int j = 0; j < binaryRep.length; j++) {
			if (binaryRep[j] == '1') {
				if (!first) {
					System.out.print(", ");
				}
				System.out.print(valueList[j] + "/" + weightList[j]);
				first = false;
			}
		}
		System.out.println();
		long stop = System.currentTimeMillis();
		// Print out the time it took to process.
        System.out.println("This took " + (stop - start) + " miliseconds to do.");
	}

	/**
	 * To be able to test the function without running the entire set of codes
	 * 
	 * @param args String arguments to test the values
	 */
	public static void main(String[] args) {
		try {
			String valueFileName = "./values30.txt";
			String weightFileName = "./weights30.txt";
			double weightLimit = 254.163;
	
			Part2A packMyKnapSack = new Part2A(new Store(valueFileName,
					weightFileName, weightLimit));
			packMyKnapSack.run();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Store getExhaustiveStore() {
		return exhaustiveStore;
	}

	public void setExhaustiveStore(Store exhaustiveStore) {
		this.exhaustiveStore = exhaustiveStore;
	}

	public Double[] getValueList() {
		return valueList;
	}

	public void setValueList(Double[] valueList) {
		this.valueList = valueList;
	}

	public Double[] getWeightList() {
		return weightList;
	}

	public void setWeightList(Double[] weightList) {
		this.weightList = weightList;
	}

	public double getWeightLimit() {
		return weightLimit;
	}

	public void setWeightLimit(double weightLimit) {
		this.weightLimit = weightLimit;
	}

}
