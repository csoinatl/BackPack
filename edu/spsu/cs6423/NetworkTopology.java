package edu.spsu.cs6423;

public class NetworkTopology {

	public boolean checkForLoop(int[][] adjacencyMatrix) {
		boolean curLoop = true;
		for (int edge = 0; edge < adjacencyMatrix.length; edge++) {
			if (adjacencyMatrix[edge][edge] > 0)
				curLoop = false;
		}
		return curLoop;
	}
	
	public boolean checkForSymmetry(int[][] adjacencyMatrix) {
		boolean curSymmetry = true;
		
		for (int edge = 0; edge < adjacencyMatrix.length; edge++) {
			for (int edge1 = 0; edge1 < adjacencyMatrix.length; edge1++) {
				if (adjacencyMatrix[edge][edge1] != adjacencyMatrix[edge1][edge])
					curSymmetry = false;
			}
		}
		
		return curSymmetry;
	}
	
	public String findTopologyType(int[][] adjacencyMatrix) {
		String topology = "";
		int[] edgeSum = new int[adjacencyMatrix.length];
		
		// We populate the row counter
		for (int rowCounter = 0; rowCounter < adjacencyMatrix.length; rowCounter++) {
			for (int colCounter = 0; colCounter < adjacencyMatrix.length; colCounter++) {
				edgeSum[rowCounter] += adjacencyMatrix[rowCounter][colCounter];
			}
		}
		
		// now we check the sums of row counter
		// as we have found out.
		// if all edge sum = 2, then Ring
		// if all edge sum = n - 1, then mesh
		// if n - 1 edge sum = 1 and 1 edge sum = n - 1, then star
		
		boolean ring = false, mesh = true, star = false;
		
		if (edgeSum[0] == 1) {
			int fullEdgeCount = 0;
			int singleEdgeCount = 0;
			// Checking for or star
			for (int numEdges : edgeSum) {
				if (numEdges == edgeSum.length - 1)
					fullEdgeCount++;
				else if (numEdges == 1)
					singleEdgeCount++;
			}
			if (fullEdgeCount == 1 && singleEdgeCount == edgeSum.length - 1)
				return "Star Topology";
			else
				return "N/A";
		} else if (edgeSum[0] == 2) {
			// checking for ring
			for (int numEdges : edgeSum) {
				if (numEdges != 2) {
					return "N/A";
				}
			}
			return "Ring Topology";
		} else {
			// checking for mesh
			for (int numEdges : edgeSum) {
				if (numEdges < edgeSum.length - 1) {
					return "N/A";
				}
			}
			return "Mesh Topolgy";
		}
	}
	
	public static void main(String[] args) {
	    int[][] ring = {{0, 1, 0, 0, 0, 1}, {1, 0, 1, 0, 0, 0}, {0, 1, 0, 1, 0, 0},
	            {0, 0, 1, 0, 1, 0}, {0, 0, 0, 1, 0, 1}, {1, 0, 0, 0, 1, 0}};

	    //Star topology
	    int[][] star = {{0, 0, 0, 1, 0, 0}, {0, 0, 0, 1, 0, 0}, {0, 0, 0, 1, 0, 0},
	        {1, 1, 1, 0, 1, 1}, {0, 0, 0, 1, 0, 0}, {0, 0, 0, 1, 0, 0}};

	    //Mesh topology
	    int[][] mesh = {{0, 1, 1, 1, 1, 1}, {1, 0, 1, 1, 1, 1}, {1, 1, 0, 1, 1, 1},
	        {1, 1, 1, 0, 1, 1}, {1, 1, 1, 1, 0, 1}, {1, 1, 1, 1, 1, 0}};

	    NetworkTopology topologyTest = new NetworkTopology();
	    if (topologyTest.checkForLoop(ring) && topologyTest.checkForSymmetry(ring))
	    	System.out.println("The Topology = " + topologyTest.findTopologyType(ring));
	    else
	    	System.out.println("Not a valid topology");
	    
	    if (topologyTest.checkForLoop(star) && topologyTest.checkForSymmetry(star))
	    	System.out.println("The Topology = " + topologyTest.findTopologyType(star));
	    else
	    	System.out.println("Not a valid topology");

	    if (topologyTest.checkForLoop(mesh) && topologyTest.checkForSymmetry(mesh))
	    	System.out.println("The Topology = " + topologyTest.findTopologyType(mesh));
	    else
	    	System.out.println("Not a valid topology");

	}
}
