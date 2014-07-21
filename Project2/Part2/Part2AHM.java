package Project2.Part2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * CS6423 Algorithmic Processes
 * Summer 2014
 * Project 2: Part 2A - Brute Force Algorithm for Knapsack problem
 * 			This iteration includes the multi-threading as well as
 * 			the heuristic limitation.
 * 				Basically, in this iteration, we check to see what
 * 				is the maximum number of items that can fit into the 
 * 				sack, starting with the smallest items, and fitting
 * 				them into the sack until we reach the limit.
 * 				With this number, we throw out any combination of
 * 				knapsack that contains more items that this limit.
 * Daniel Kerr and Charles So
 * Date: 7/6/2014
 * File: Part2AHM.java
 */
public class Part2AHM extends RecursiveTask<KnapSack>
	implements Runnable {

	private static final long serialVersionUID = -5770817790585215780L;
	
	private Store exhaustiveStore;
	private BigInteger startIndex;
	private BigInteger endIndex;
	private Double[] valueList;
	private Double[] weightList;
	private double weightLimit;
	private int limitation;
	private KnapSack theKnapsack = new KnapSack();

	/**
	 * Default constructor with store item, we have done this from the 
	 * first iteration, since we are loading the values from another class
	 * 
	 * @param store Contains the value/weight list from the files
	 */
	public Part2AHM(Store store) {
		this.exhaustiveStore = store;
	}
	
	/**
	 * Constructor utilized to pass values into the forked child to be handled 
	 * by the child.
	 * 
	 * @param startIndex Starting index of the binary combination to test
	 * @param endIndex Ending index of the binary combination to test
	 * @param valueList List of values to add
	 * @param weightList List of the values to add
	 * @param weightLimit Limitation of the weight
	 * @param limitation Max number of items that will fit into the Knapsack
	 */
	public Part2AHM(BigInteger startIndex, BigInteger endIndex, Double[] valueList, Double[] weightList, double weightLimit, int limitation) {
		setStartIndex(startIndex);
		setEndIndex(endIndex);
		setValueList(valueList);
		setWeightList(weightList);
		setWeightLimit(weightLimit);
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.RecursiveTask#compute()
	 */
	@Override
	protected KnapSack compute() {
		if (endIndex.subtract(startIndex).compareTo(new BigInteger("1000000")) <= 0) {
			BigInteger counter = startIndex;
			
			while (counter.compareTo(endIndex) < 0) {
				String binaryString = counter.toString(2);
				
				while (binaryString.length() < valueList.length) {
					binaryString = "0" + binaryString;
				}

				if (binaryString.endsWith("00000000000000000000"))
					System.out.println("Counter at = " + binaryString);
				
				char[] binaryChar = binaryString.toCharArray();
				Double totalWeight = new Double(0);
				Double totalValue = new Double(0);
				
				if (limitation < countOnes(binaryChar)) {
					counter = counter.add(BigInteger.ONE);
					continue;
				}
				
				for (int charCounter = 0; charCounter < binaryString.length(); charCounter++) {
					if (binaryChar[charCounter] == '1') {
						// Since it exists
						totalWeight += weightList[charCounter];
						totalValue += valueList[charCounter];
						if (totalWeight > weightLimit)
							continue;
					}
				}
				
				if (totalWeight > weightLimit) {
					counter = counter.add(BigInteger.ONE);
					continue;
				}
				
				if (theKnapsack.getSelectedBinary().length() == 0) {
					theKnapsack.setTotalWeight(totalWeight);
					theKnapsack.setTotalValue(totalValue);
					theKnapsack.setSelectedBinary(binaryString);
				} else {
					if (totalWeight < weightLimit) {
						if (totalValue > theKnapsack.getTotalValue()) {
							System.out.println("New high found - new value = "
									+ String.format( "%.2f", totalValue) + " @ " + binaryString);
							// System.out.print(".");
							theKnapsack.setTotalWeight(totalWeight);
							theKnapsack.setTotalValue(totalValue);
							theKnapsack.setSelectedBinary(binaryString);
						}
					}
				}
				counter = counter.add(BigInteger.ONE);
			}
			return theKnapsack;
		} else {
			BigInteger midPoint = endIndex.subtract(startIndex).divide(new BigInteger("2"));
			
			Part2AHM left = new Part2AHM(startIndex, startIndex.add(midPoint), valueList, weightList, weightLimit, limitation);
			left.fork();
			Part2AHM right = new Part2AHM(startIndex.add(midPoint), endIndex, valueList, weightList, weightLimit, limitation);
			return max(right.compute(), left.join());
		}
		
	}
	
	/**
	 * Finds the limitation of the number of items that will fit into the knapsack
	 * This will utilize the greedy algorithm starting with the smallest items
	 * to find the maximum number of items that will fit into the sack.
	 * 
	 * @param sortedWeightList Weight list as sorted items
	 * @param weightLimit Limit of the weights that can fit into the sack
	 * @return Max number of items that will fit into the sack
	 */
	private int findLimitation (ArrayList<Double> sortedWeightList, double weightLimit) {
		int limitation = 0;
		double totalWeight = 0;
		
		for (Double curWeight : sortedWeightList) {
			totalWeight += curWeight;
			limitation++;
			
			if (weightLimit < totalWeight) {
				continue;
			}
		}
		
		return limitation;
	}

	/**
	 * Counts the number of 1's in the binary character array
	 * 
	 * @param binaryCharArray Character array of binary 1's and 0's
	 * @return The total number of 1's in the array 
	 */
	private int countOnes(char[] binaryCharArray) {
		int counter = 0;
		
		for (int i = 0; i < binaryCharArray.length; i++) {
			if (binaryCharArray.equals('1')) {
				counter++;
			}
		}
		
		return counter;
	}
	
	/**
	 * Max function to let us know which of the 2 knapsacks contain a higher value
	 *  
	 * @param knapSack1 First knapsack
	 * @param knapSack2 Second knapsack
	 * @return The larger of the two knapsacks
	 */
	public KnapSack max(KnapSack knapSack1, KnapSack knapSack2) {
		if (knapSack1.getTotalValue() > knapSack2.getTotalValue())
			return knapSack1;
		return knapSack2;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		long start = System.currentTimeMillis();

		final Double[] valueList = exhaustiveStore.getValues();
		final Double[] weightList = exhaustiveStore.getWeights();
		final double weightLimit = exhaustiveStore.getMaxWeight();

		KnapSack selectedKnapSack = new KnapSack();

		final BigInteger bi1 = new BigInteger("2");
		final BigInteger bi2;

		bi2 = bi1.pow(valueList.length);
		ArrayList<Double> myArrayList = new ArrayList<Double>(Arrays.asList(weightList));
		Collections.sort(myArrayList);
		
		limitation = findLimitation(myArrayList, weightLimit);
		
		final ForkJoinPool pool = new ForkJoinPool(32);
		final Part2AHM part2a = new Part2AHM(BigInteger.ZERO, bi2, valueList, weightList, weightLimit, limitation);
		
		selectedKnapSack = pool.invoke(part2a);
		
		System.out.println();
		System.out.println("This is the result of the exhaustive search");
		System.out.println("The highest value with in the weight limit was "
				+ selectedKnapSack.getTotalValue());
		System.out.println("The total weight was "
				+ selectedKnapSack.getTotalWeight());
		System.out
				.println("The set was composed of the following value/weight:");
		System.out.println();

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
        System.out.println("This took " + (stop - start) + " miliseconds to do.");
		}
	
	public static void main(String[] args) {
		try {
			String valueFileName = "./values35.txt";
			String weightFileName = "./weights35.txt";
			double weightLimit = 290.82;
	
			Part2AHM packMyKnapSack = new Part2AHM(new Store(valueFileName,
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

	public BigInteger getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(BigInteger startIndex) {
		this.startIndex = startIndex;
	}

	public BigInteger getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(BigInteger endIndex) {
		this.endIndex = endIndex;
	}

}
