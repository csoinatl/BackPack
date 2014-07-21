
package Project2.Part2;

import java.util.BitSet;
import java.util.Comparator;
import java.util.Random;

/**
 * CS6423 Algorithmic Processes
 * Summer 2014
 * Project 2: Gene - A Gene for the Genetic Algorithm
 * Daniel Kerr and Charles So
 * Date: 07/06/2014
 * File: Gene.java
 */
public class Gene implements Comparable<Object>{
    private BitSet code;
    private double totalWeight;
    private double totalValue;
    private double fitness;
    private int geneSize;
    
    /**
     * Constructor for the Gene
     * @param code a BitSet representing the items included in this knapsack
     * @param store a convenience class that holds the array of weights/values/max weight
     */
    public Gene(BitSet code, Store store){
        this.code = code;
        geneSize = store.getValues().length;
        setTotalWeight(generateTWeight(code, store, geneSize));
        setTotalValue(generateTValue(code, store, geneSize));
        setFitness(generateFitness(totalWeight, totalValue, store));
        
    }
    
    /**
     * A constructor for a gene that generates a random BitSet code. Used for the initial 
     * population pool
     * @param store this instances Store, which holds the weight/value info
     */
    public Gene(Store store){
        code = generateCode(store);
        geneSize = store.getValues().length;
        setTotalWeight(generateTWeight(code, store, geneSize));
        setTotalValue(generateTValue(code, store, geneSize));
        setFitness(generateFitness(totalWeight, totalValue, store));
        
        
    }
    
    /**
     * BitSet only counts bits that are set to true in its length, so we need to know
     * how long it is, including false bits. This is the setter method for that value
     * @param length the length to set the gene, including false bits
     */
    public void setGeneSize(int length){
        this.geneSize = length;
    }
    
    /**
     * A getter for the length of the gene, including false bits
     * @return int, the length of the bitset including false bits
     */
    public int getGeneSize(){
        return geneSize;
    }
    
    /**
     * A setter for the BitSet that represents this particular combination of items
     * @param code the Bitset to set for this Gene's code
     */
    public void setCode(BitSet code){
        this.code = code;
    }

    /**
     * A getter for the BitSet that represents this particular combination of items
     * @return the Bitset that represents this item combo
     */
    public BitSet getCode(){
        return code;
    }
    
    /**
     * Sets the the total weight of all of the items in this gene
     * @param totalWeight The double to set the weight to
     */
    private void setTotalWeight(double totalWeight){
        this.totalWeight = totalWeight;
    }
    
    /**
     * A getter for the total weight of the items in this combination
     * @return the total weight as a double
     */
    public double getTotalWeight(){
        return totalWeight;
    }
    
    /**
     * Sets the total value of the items in this combination
     * @param totalValue a double 
     */
    private void setTotalValue(double totalValue){
        this.totalValue = totalValue;
    }
    
    /**
     * A getter for the total value of the items in this combination
     * @return a double of the total weight
     */
    public double getTotalValue(){
        return totalValue;
    }
    
    /**
     * A setter of the fitness criteria of this gene
     * @param fitness a double fitness value
     */
    private void setFitness(double fitness){
        this.fitness = fitness;
    }
    
    /**
     * A getter for the fitness criteria of this gene
     * @return a double fitness value
     */
    public double getFitness(){
        return fitness;
    }

    /**
     * This uses the store to generate the total weight of this gene
     * @param code the BitSet of the gene to generate the weight for
     * @param store the Store with the weight values for this problem
     * @param geneSize the length of the gene including false bits
     * @return a double total weight for this particular gene
     */
    private double generateTWeight(BitSet code, Store store, int geneSize) {
        double tempWeight = 0.0;
        for(int i = 0; i < geneSize; i++){
            if(code.get(i)){
                tempWeight += store.getSWeight(i);
            }
        }
        return tempWeight;
    }

    /**
     * This uses the store to generate the total value of this gene
     * @param code the BitSet of the gene to generate the value for
     * @param store the Store with the item values for this problem
     * @param geneSize the length of the gene including false bits
     * @return a double total value for this particular gene
     */
    private double generateTValue(BitSet code, Store store, int geneSize) {
        double tempValue = 0.0; 
        for(int i = 0; i < geneSize; i++){
            if(code.get(i)){
                tempValue += store.getSValue(i);
            }
        }
        return tempValue;
    }

    /**
     * This generates the fitness criteria using the provided total weight, value and store
     * @param totalWeight a double of the total weight to calculate the fitness
     * @param totalValue a double of the total value to calculate the fitness
     * @param store the Store for this particular problem instance
     * @return 
     */
    private double generateFitness(double totalWeight, double totalValue, Store store) {
        double tempFit = 0.0;
        if(totalWeight <= store.getMaxWeight()){
            tempFit += totalValue;
            tempFit -= (store.getMaxWeight() - totalWeight);
        }
        else{
            tempFit += (store.getMaxWeight() - totalWeight);
        }
        return tempFit;
    }

    /**
     * Generates a random code, for use in the initial population
     * @param store the Store for this particular problem instance
     * @return the BitSet code for this gene
     */
    private BitSet generateCode(Store store) {
        int size = store.getValues().length;
        BitSet newCode = new BitSet(size);
        Random random = new Random();
        for(int i = 0; i < size; i++){
            newCode.set(i, random.nextBoolean());
        }
     
        return newCode;
    }

    @Override
    public int compareTo(Object o) {
        Gene that = (Gene)o;
        if(this.getFitness() < that.getFitness()){
            return -1;
        }
        else if(this.getFitness() > that.getFitness()){
            return 1;
        }
        else{
            return 0;
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("In pack: ");
        for(int i = 0; i < geneSize; i++){
            sb.append("[");
            sb.append(code.get(i));
            sb.append("]");
        }
        sb.append("\nThe total weight is: ");
        sb.append(totalWeight);
        sb.append("\nThe total value is: ");
        sb.append(totalValue);
        sb.append("\nThe fitness value is: ");
        sb.append(fitness);
        return sb.toString();
    }
    
    
}
