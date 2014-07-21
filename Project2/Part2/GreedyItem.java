package Project2.Part2;
/**
 * CS6423 Algorithmic Processes
 * Summer 2014
 * Project 2: GreedyItem - represents an item for greedy knapsack algorithm
 * Daniel Kerr and Charles So
 * Date: 07/06/2014
 * File: GreedyItem.java
 */
public class GreedyItem implements Comparable<Object>{
    
    int storePosition;
    double vToWRatio;
    
    /**
     * Constructor for GreedyItem
     * @param vToWRatio The ratio of value to weight for this item
     * @param storePosition where in the store's array this item is, for printing output
     */
    public GreedyItem(double vToWRatio, int storePosition){
        
        this.storePosition = storePosition;
        this.vToWRatio = vToWRatio;
    }
    
   
    /**
     * Setter for where this item is in the Store's array
     * @param storePosition int position for this item
     */
    public void setStorePosition(int storePosition){
        this.storePosition = storePosition;
    }
    
    /**
     * getter for this item's position
     * @return int position of this item
     */
    public int getStorePosition(){
        return storePosition;
    }
    
    /**
     * Setter for this item's ratio of value to weight
     * @param vToWRatio double value to weight ratio
     */
    public void setVToWRatio(double vToWRatio){
        this.vToWRatio = vToWRatio;
    }
    
    /**
     * getter for this item's value to weight ratio
     * @return double value of this item's value to weight ratio
     */
    public double getVToWRatio(){
        return vToWRatio;
    }
    
  /**
   * Compares to items by position
   * @param o object to compare this item to
   * @return 
   */
    @Override
    public int compareTo(Object o){
        GreedyItem that = (GreedyItem)o;
        if(this.getStorePosition() < that.getStorePosition()){
            return -1;
        }
        else if(this.getStorePosition() > that.getStorePosition()){
            return 1;
        }
        else{
            return 0;
        }
    }
}
