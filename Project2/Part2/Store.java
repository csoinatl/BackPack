
package Project2.Part2;

/**
 * CS6423 Algorithmic Processes
 * Summer 2014
 * Project 2: Store - A Store that holds information about the items
 * Daniel Kerr and Charles So
 * Date: 07/06/2014
 * File: Store.java
 */
public class Store {
    private Double[] weights;
    private Double[] values;
    private double maxWeight;
    
    /**
     * Constructor for an object that contains this particular problem instance's
     * values, weights and the max weight allowed
     * @param fileNameValue
     * @param fileNameWeight
     * @param maxWeight 
     */
    public Store(String fileNameValue, String fileNameWeight, double maxWeight){
        weights = FileReader.getArray(fileNameWeight);
        values = FileReader.getArray(fileNameValue);
        this.maxWeight = maxWeight;
    }

    /**
     * Getter for the array of weights
     * @return the weights
     */
    public Double[] getWeights() {
        return weights;
    }

    /**
     * Setter for the array of weights
     * @param weights the weights to set
     */
    public void setWeights(Double[] weights) {
        this.weights = weights;
    }

    /**
     * Getter for the array of values
     * @return the values
     */
    public Double[] getValues() {
        return values;
    }

    /**
     * Setter for the array of values
     * @param values the values to set
     */
    public void setValues(Double[] values) {
        this.values = values;
    }
    
    /**
     * getter for a weight at a particular index
     * @param index
     * @return 
     */
    public Double getSWeight(int index){
        return weights[index];
    }
    
    /**
     * Setter for a weight at a particular index
     * @param index
     * @param value 
     */
    public void setSWeight(int index, Double value){
        weights[index] = value;
    }
    
    /**
     * Getter for a value at a particular index
     * @param index
     * @return 
     */
    public Double getSValue(int index){
        return values[index];
    }
    
    /**
     * setter for a value at a particular index
     * @param index
     * @param value 
     */
    public void setSValue(int index, Double value){
        values[index] = value;
    }

    /**
     * Getter for the max weight allowed
     * @return the maxWeight
     */
    public double getMaxWeight() {
        return maxWeight;
    }

    /**
     * Setter for the max weight allowed
     * @param maxWeight the maxWeight to set
     */
    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }
    
    /**
     * toString override for printing out the results
     * @return 
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Weights: ");
        for(int i = 0; i < weights.length; i++){
            sb.append("[");
            sb.append(weights[i]);
            sb.append("]");
        }
        sb.append("\n");
        sb.append("Values:  ");
        for(int i = 0; i < values.length; i++){
            sb.append("[");
            sb.append(values[i]);
            sb.append("]");
        }
        return sb.toString();
    }
}
