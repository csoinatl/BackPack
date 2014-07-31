package Project3.Part2;

import java.util.Comparator;

import Project3.CostEdge;
/**
 * CS6423 Algorithmic Processes
 * Summer 2014
 * Project 3: CostComparator - comparator object for comparing CostEdges by cost
 * Daniel Kerr and Charles So
 * Date: 08/01/2014
 * File: CostComparator.java
 */
public class CostComparator implements Comparator<CostEdge>{

	@Override
	public int compare(CostEdge edge1, CostEdge edge2) {
		return Float.compare(edge1.getCost(), edge2.getCost());
	}
    
}
