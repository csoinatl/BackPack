package Project3.Part2;

import java.util.Comparator;

import Project3.CostEdge;

public class CostComparator implements Comparator<CostEdge>{

	@Override
	public int compare(CostEdge edge1, CostEdge edge2) {
		return Float.compare(edge1.getCost(), edge2.getCost());
	}
    
}
