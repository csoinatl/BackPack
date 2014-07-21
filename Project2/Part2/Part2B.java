package Project2.Part2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * CS6423 Algorithmic Processes
 * Summer 2014
 * Project 2: Part2B- A greedy algorithm for the knapsack problem
 * Daniel Kerr and Charles So
 * Date: 07/06/2014
 * File: Part2B.java
 */
public class Part2B {
    private Store store;
    private PriorityQueue<GreedyItem> itemInRatioOrder;
    private ArrayList<GreedyItem> includedItems;
    private double totalWeight;
    private double totalValue;
    
    /**
     * A constructor for this algorithm. Stores all of the items in a priority queue
     * that orders them so that the one with the highest value to weight ratio is at the 
     * head
     * @param store 
     */
    public Part2B(Store store){
        this.store = store;
        itemInRatioOrder = new PriorityQueue<GreedyItem>(store.getValues().length, new MaxItemComparator());
        for(int i = 0; i < store.getValues().length; i++){
            itemInRatioOrder.add(new GreedyItem((store.getSValue(i)/store.getSWeight(i)), i));
        }
        includedItems = new ArrayList<GreedyItem>();
        totalWeight = 0;
        totalValue = 0;
    }
    
    /**
     * This simply goes through the priority queue, adding the item to the result
     * if there is room
     */
    public void getGreedyKnapsack(){
        long start = System.currentTimeMillis();
        while(!itemInRatioOrder.isEmpty()){
            GreedyItem temp = itemInRatioOrder.poll();
            if(store.getSWeight(temp.getStorePosition()) <= (store.getMaxWeight() - totalWeight)){
                includedItems.add(temp);
                totalWeight += store.getSWeight(temp.getStorePosition());
                totalValue += store.getSValue(temp.getStorePosition());
            }
        }
        
        Collections.sort(includedItems);
        long stop = System.currentTimeMillis();
        printStatistics(start, stop, includedItems);
    }

    /**
     * prints out the results of the algorithm 
     * @param start start time in miliseconds
     * @param stop stop time in milisecond
     * @param includedItems The items included in this result
     */
    private void printStatistics(long start, long stop, ArrayList<GreedyItem> includedItems) {
        System.out.println("The Greedy algorithm took: " + ((stop - start)) + " miliseconds.");
        System.out.println("The total weight filled is: " + totalWeight);
        System.out.println("The total value is: " + totalValue);
        System.out.println("The best combination is now:");
        System.out.println(store.toString());
        System.out.println(printIncludedItems(includedItems));
        
    }

    /**
     * Helper method for printing the items included in this result
     * @param includedItems
     * @return 
     */
    private String printIncludedItems(ArrayList<GreedyItem> includedItems) {
        StringBuilder sb = new StringBuilder();
        sb.append("In pack: ");
        for(int i = 0; i < store.getWeights().length; i++){
            sb.append("[");
            
            if(!includedItems.isEmpty() && includedItems.get(0).storePosition == i){
                sb.append("true");
                includedItems.remove(0);
            }
            else{
                sb.append("false");
            }
            sb.append("]");
        
        }
        return sb.toString();
    }
}
