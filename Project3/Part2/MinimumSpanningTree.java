package Project3.Part2;

import java.util.ArrayList;

import Project3.CityData;
import Project3.CityInfo;
import Project3.CostEdge;

public class MinimumSpanningTree {

	private CityInfo[] cityInfo; 
	private float[][] costMatrix;
	
	public MinimumSpanningTree() {
		CityData newCityData = new CityData("routes.txt");
		
		cityInfo = newCityData.getListOfCities();
		costMatrix = newCityData.getMatrixTravel().getTravelMatrix();
		
		for (CityInfo curCity : cityInfo) {
			// Let's traverse the cities and add items
			String startCity = curCity.getCityName();
			int startCityCount = CityInfo.CitySet.getCityOrdinal(startCity);
			
			ArrayList<Integer> cityAddedIn = new ArrayList<Integer>();
			ArrayList<CostEdge> minimumTreeList = new ArrayList<CostEdge>();
			
			int[] cityList = new int[2];
			cityList[0] = startCityCount;
			cityList[1] = -1;

			int count = 0;
			while (count < cityInfo.length) {
				float minimum = Long.MAX_VALUE;
				int minimumCity = 0;
				for (int cityCounter = 0; cityCounter < cityList.length; cityCounter++) {
					if (cityList[cityCounter] != -1) {
						for (int counter = 0; counter < cityInfo.length; counter++) {
							if (costMatrix[startCityCount][counter] < minimum 
									&& !cityAddedIn.contains(new Integer(counter))) {
								minimum = costMatrix[startCityCount][counter];
								minimumCity = counter;
							}
						}
					}
				}
				
				cityList[1] = minimumCity;
				cityAddedIn.add(new Integer(minimumCity));
				
				CostEdge newEdge= new CostEdge();
				newEdge.setStartCity(cityInfo[startCityCount]);
				newEdge.setEndCity(cityInfo[minimumCity]);
				newEdge.setCost(costMatrix[startCityCount][minimumCity]);
				
				minimumTreeList.add(newEdge);
				
				count++;
			}
			
			System.out.println("Printing minimum cost spanning tree from " + cityInfo[startCityCount].getCityName());
			
			for (CostEdge curCostEdge:minimumTreeList) {
				System.out.println(curCostEdge.toString());
			}
			
			System.out.println("===== End of the tree =====");
		}
		
	}

	public static void main(String[] args) {
		MinimumSpanningTree testTree = new MinimumSpanningTree();
	}

}
