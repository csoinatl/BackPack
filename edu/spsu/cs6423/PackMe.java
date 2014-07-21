package edu.spsu.cs6423;

/*
 * Author: Charles So
 * 
 * Knapsack problem
 * 
 */
public class PackMe {

	static final int value[] = { 10, 6, 2, 7 };
	static final int cost[] = { 5, 3, 2, 4 };

	PackMe() {
		KnapSack[] newNapsack = new KnapSack[(int) Math.pow(2, (value.length))];
		int costlimit = 7;

		System.out.println("Let's pack all the knapsacks");
		for (int i = 1; i < newNapsack.length; i++) {
			KnapSack myKnapsack = new KnapSack();
			
			String toConvert = Integer.toBinaryString(i);
			
			while (toConvert.length() < value.length) {
				toConvert = "0" + toConvert;
			}

			char[] binaryRep = toConvert.toCharArray();

			for (int j = 0; j < binaryRep.length; j++) {
				if (binaryRep[j] == '1') {
					// Add the value in
					myKnapsack.setTotalCost(myKnapsack.getTotalCost() + cost[j]);
					myKnapsack.setTotalValue(myKnapsack.getTotalValue() + value[j]);
				}
			}
			
			System.out.print("KnapSack " + i + " | ");
			System.out.print("Value = " + myKnapsack.getTotalValue() + " | ");
			System.out.println("Cost = " + myKnapsack.getTotalCost() + " | ");

			newNapsack[i] = myKnapsack;
		}
		
		System.out.println("All possibilities Generated");
		
		int maxValue = 0;
		int selectSet = 0;
		for (int i = 1; i < newNapsack.length; i++) {
			if (newNapsack[i].getTotalValue() > maxValue 
					&& newNapsack[i].getTotalCost() <= costlimit) {
				maxValue = newNapsack[i].getTotalValue();
				selectSet = i;
			}
		}

		System.out.println();
		System.out.println("This is the result of the exhaustive search");
		System.out.println("The highest value with in the weight limit was " + maxValue);
		System.out.println("The set was composed of the following value/weight:");

		String toConvert = Integer.toBinaryString(selectSet);
		while (toConvert.length() < value.length) {
			toConvert = "0" + toConvert;
		}

		char[] binaryRep = toConvert.toCharArray();
		boolean first = true;

		for (int j = 0; j < binaryRep.length; j++) {
			if (binaryRep[j] == '1') {
				if (!first){
					System.out.print(", ");
				}
				System.out.print(value[j] + "/" + cost[j]);
				first = false;
			}
		}
		System.out.println();
		

	}

	public static void main(String[] args) {
		PackMe packMyKnapSack = new PackMe();
	}
}
