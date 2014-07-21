package Project2.Part2;

import java.util.Comparator;

/**
 * CS6423 Algorithmic Processes
 * Summer 2014
 * Project 2: MaxItemComparator - used to order GreedyItems in a priority queue by vToWRatio
 * Daniel Kerr and Charles So
 * Date: 07/06/2014
 * File: MaxItemComparator.java
 */
public class MaxItemComparator implements Comparator<GreedyItem>{

    /**
     * Compares to items by value to weight ratio
     * @param g1
     * @param g2
     * @return 
     */
    @Override
    public int compare(GreedyItem g1, GreedyItem g2) {
        if(g1.getVToWRatio() > g2.getVToWRatio()){
            return -1;
        }
        else if(g1.getVToWRatio() < g2.getVToWRatio()){
            return 1;
        }
        else{
            return 0;
        }
    }
    
}
