package Project3.Part2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Project3.CityData;
import Project3.CityInfo;
import Project3.CostEdge;
import java.util.PriorityQueue;

public class MinimumSpanningTree {

	private CityInfo[] cityInfo;
	private float[][] costMatrix;

	//Opens edges out of a city that has been visisted for the first time
        public void openCity(PriorityQueue<CostEdge> edgeList, CityInfo startCity, CityInfo[] cityInfo){
            for(CityInfo connectedCity : cityInfo){
                int startIndex = CityInfo.CitySet.getCityOrdinal(startCity.getCityName());
                int endIndex = CityInfo.CitySet.getCityOrdinal(connectedCity.getCityName());
                if(startIndex != endIndex && costMatrix[startIndex][endIndex] >= 0){
                    CostEdge newEdge = new CostEdge();
                    newEdge.setStartCity(startCity);
                    newEdge.setEndCity(connectedCity);
                    newEdge.setCost(costMatrix[startIndex][endIndex]);
                    edgeList.add(newEdge);
                }
            }
           
        }
        
        public CostEdge getMinimumEdge(int startCityCount, CityInfo[] cityInfo,
			float[][] costMatrix, ArrayList<Integer> cityAddedIn) {
		CostEdge edgeToReturn = null;
		float minimum = Long.MAX_VALUE;
		int minimumCity = -1;

		for (int counter = 0; counter < cityInfo.length; counter++) {
			if (costMatrix[startCityCount][counter] < minimum
					&& !cityAddedIn.contains(new Integer(counter))
					&& costMatrix[startCityCount][counter] != -1) {
				minimum = costMatrix[startCityCount][counter];
				minimumCity = counter;
			}
		}

		if (minimumCity != -1) {
			edgeToReturn = new CostEdge();
			edgeToReturn.setStartCity(cityInfo[startCityCount]);
			edgeToReturn.setEndCity(cityInfo[minimumCity]);
			edgeToReturn.setCost(costMatrix[startCityCount][minimumCity]);
		}

		return edgeToReturn;
	}

	public MinimumSpanningTree() {
		CityData newCityData = new CityData("routes.txt");

		cityInfo = newCityData.getListOfCities();
		costMatrix = newCityData.getMatrixTravel().getTravelMatrix();

		for (CityInfo curCity : cityInfo) {
			// Let's traverse the cities and add items
			String startCity = curCity.getCityName();
			int startCityCount = CityInfo.CitySet.getCityOrdinal(startCity);
                        CostComparator costComparator = new CostComparator();
                        PriorityQueue<CostEdge> possibleEdges = new PriorityQueue<CostEdge>(newCityData.getListOfCities().length, costComparator);
			ArrayList<Integer> cityAddedIn = new ArrayList<Integer>();
			ArrayList<CostEdge> minimumTreeList = new ArrayList<CostEdge>();
			
                        cityAddedIn.add(startCityCount);
                        openCity(possibleEdges, curCity, cityInfo);
                        
			

			while (cityAddedIn.size() < cityInfo.length) {
				float minimum = Long.MAX_VALUE;
				int minimumCity = -1;
				
				CostEdge minimumEdge = possibleEdges.poll();
				if (minimumEdge != null 
						&& !cityAddedIn.contains(CityInfo.CitySet.getCityOrdinal(minimumEdge.getEndCity().getCityName()))) {
					minimumTreeList.add(minimumEdge);
					int endCityCount = CityInfo.CitySet.getCityOrdinal(minimumEdge.getEndCity().getCityCode());
					cityAddedIn.add(endCityCount);
					openCity(possibleEdges, minimumEdge.getEndCity(), cityInfo);
				}

			}

			System.out.println("Printing minimum cost spanning tree from "
					+ curCity.getCityName());

                        float totalCost = 0.0f;
			for (CostEdge curCostEdge : minimumTreeList) {
				System.out.println(curCostEdge.toString());
                                totalCost += curCostEdge.getCost();
			}

                        System.out.println("Total cost: " + totalCost);
			System.out.println("===== End of the tree =====");
		}

	}

	public static void main(String[] args) {
		MinimumSpanningTree testTree = new MinimumSpanningTree();
	}

}
