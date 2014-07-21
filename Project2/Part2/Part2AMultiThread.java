package Project2.Part2;

import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * CS6423 Algorithmic Processes
 * Summer 2014
 * Project 2: Part 2A - Brute Force Algorithm for Knapsack problem
 * 			For this iteration, we added in the Multi-Threading the 
 * 			improve performance.
 * Daniel Kerr and Charles So
 * Date: 7/6/2014
 * File: Part2AMultiThread.java
 */
public class Part2AMultiThread extends RecursiveTask<KnapSack>
	implements Runnable {

	private static final long serialVersionUID = 4240129099199625641L;
	
	private Store exhaustiveStore;
	private BigInteger startIndex;
	private BigInteger endIndex;
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
	public Part2AMultiThread(Store store) {
		this.exhaustiveStore = store;
	}
	
	/**
	 * Constructor generated to be able to pass values to the children classes
	 * for executing the level down steps
	 * 
	 * @param startIndex index of the beginning of the array to run through the codes
	 * @param endIndex index of the ending of the array to run through the codes
	 * @param valueList list of values - this is the entire list of values
	 * @param weightList list of weights - this is the entire list of weights
	 * @param weightLimit limit that was put on the list
	 */
	public Part2AMultiThread(BigInteger startIndex, BigInteger endIndex, Double[] valueList, Double[] weightList, double weightLimit) {
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
		// This is where the magic happens.  If the difference between the start index and end index is
		// less than 1,000,000 we run the search through that list.
		// if not, we fork the items into smaller pieces and recursively call the child in
		// hopefully separate threads.
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
			// This is where the fork/join occurs.
			BigInteger midPoint = endIndex.subtract(startIndex).divide(new BigInteger("2"));
			
			Part2AMultiThread left = new Part2AMultiThread(startIndex, startIndex.add(midPoint), valueList, weightList, weightLimit);
			left.fork();
			Part2AMultiThread right = new Part2AMultiThread(startIndex.add(midPoint), endIndex, valueList, weightList, weightLimit);
			return max(right.compute(), left.join());
		}
		
	}

	/**
	 * This is the max function for comparing 2 knapsacks to determine which knapsack has the higher value
	 * 
	 * @param knapSack1 First knapsack
	 * @param knapSack2 Second knapsack
	 * @return the Larger of the two knapsacks
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
		
		final ForkJoinPool pool = new ForkJoinPool(4);
		final Part2AMultiThread part2a = new Part2AMultiThread(BigInteger.ZERO, bi2, valueList, weightList, weightLimit);
		
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
			String valueFileName = "./values20.txt";
			String weightFileName = "./weights20.txt";
			double weightLimit = 375.07;
	
			Part2AMultiThread packMyKnapSack = new Part2AMultiThread(new Store(valueFileName,
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
