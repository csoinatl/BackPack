package Project3.Part2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Project3.CityData;
import Project3.CityInfo;
import Project3.CostEdge;

public class MinimumSpanningTree {

	private CityInfo[] cityInfo;
	private float[][] costMatrix;

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

			ArrayList<Integer> cityAddedIn = new ArrayList<Integer>();
			ArrayList<CostEdge> minimumTreeList = new ArrayList<CostEdge>();
			cityAddedIn.add(startCityCount);

			int[] cityList = new int[2];
			cityList[0] = startCityCount;
			cityList[1] = -1;

			Queue<Integer> cityQueue = new LinkedList<Integer>();

			int count = 0;
			while (count < cityInfo.length) {
				float minimum = Long.MAX_VALUE;
				int minimumCity = -1;
				
				CostEdge minimumEdge = getMinimumEdge(startCityCount, cityInfo, costMatrix, cityAddedIn);
				if (minimumEdge != null 
						&& minimumEdge.getStartCity().getCityCode() != minimumEdge.getEndCity().getCityCode()) {
					minimumTreeList.add(minimumEdge);
					int endCityCount = CityInfo.CitySet.getCityOrdinal(minimumEdge.getEndCity().getCityCode());
					cityAddedIn.add(endCityCount);
					startCityCount = endCityCount;
				}

				count++;
			}

			System.out.println("Printing minimum cost spanning tree from "
					+ curCity.getCityName());

			for (CostEdge curCostEdge : minimumTreeList) {
				System.out.println(curCostEdge.toString());
			}

			System.out.println("===== End of the tree =====");
		}

	}

	public static void main(String[] args) {
		MinimumSpanningTree testTree = new MinimumSpanningTree();
	}

}
