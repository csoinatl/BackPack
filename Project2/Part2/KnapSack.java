package Project2.Part2;

/**
 * CS6423 Algorithmic Processes
 * Summer 2014
 * Project 2: KnapSack - Knapsack for Brute Force Algorithm
 * Daniel Kerr and Charles So
 * Date: 07/06/2014
 * File: KnapSack.java
 */
public class KnapSack {

		private Double totalValue;
		private Double totalWeight;
		private String selectedBinary;
		
                /**
                 * Default constructor for Knapsack
                 */
		KnapSack() {
			setTotalWeight(new Double(0));
			setTotalValue(new Double(0));
			setSelectedBinary("");
		}
		
                /**
                 * Constructor for Knapsack
                 * @param cost The total weight in this knapsack
                 * @param value the total value for this knapsack
                 */
		KnapSack(Double cost, Double value) {
			setTotalWeight(cost);
			setTotalValue(value);
			setSelectedBinary("");
		}
		
                /**
                 * Getter for this Knapsacks's total value
                 * @return Double of this Knapsack's total value
                 */
		public Double getTotalValue() {
			return totalValue;
		}
                
                /**
                 * Setter for this knapsack's total value
                 * @param totalValue Double total value to set
                 */
		public void setTotalValue(Double totalValue) {
			this.totalValue = totalValue;
		}
                
                /**
                 * Getter for the total weight of this particular knapsack
                 * @return Double of the weight of this knapsack
                 */
		public Double getTotalWeight() {
			return totalWeight;
		}
                
                /**
                 * Setter for this Knapsack's total weight
                 * @param totalCost Double weight to set this knapsack to
                 */
		public void setTotalWeight(Double totalCost) {
			this.totalWeight = totalCost;
		}

                /**
                 * Getter for this knapsacks Binary String representing the items in it
                 * @return the String binary code for this knapsack
                 */
		public String getSelectedBinary() {
			return selectedBinary;
		}

                /**
                 * Setter for this knapsack's Binary String representing the items in it
                 * @param selectedBinary the String binary code to set for this knapsack
                 */
		public void setSelectedBinary(String selectedBinary) {
			this.selectedBinary = selectedBinary;
		}
	
}
