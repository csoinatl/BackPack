package edu.spsu.cs6423;

/*
 * Author: Charles So
 * 
 * Knapsack problem
 * 
 */
public class KnapSack {

		private int totalValue;
		private int totalCost;
		
		KnapSack() {
			setTotalCost(0);
			setTotalValue(0);
		}
		
		KnapSack(int cost, int value) {
			setTotalCost(cost);
			setTotalValue(value);
		}
		
		public int getTotalValue() {
			return totalValue;
		}
		public void setTotalValue(int totalValue) {
			this.totalValue = totalValue;
		}
		public int getTotalCost() {
			return totalCost;
		}
		public void setTotalCost(int totalCost) {
			this.totalCost = totalCost;
		}
	
}
