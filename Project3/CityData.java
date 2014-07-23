package Project3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Project2.Part2.FileReader;

public class CityData {
	
	private CityInfo[] listOfCities;
	private CostEdge[] listOfCost;
	private TravelMatrix matrixTravel; 

	public CityData() {
		new CityData("routes.txt");
	}
	
	/**
	 * This should read data from the file
	 * File Format:
	 * 
	 * Origin City | Destination City, Cost, CPT to JNB to AUH to HYD | Destination City, Cost, Connections | Destination City, Cost, Connections | Destination City, Cost, Connections
	 * 
	 */
	public CityData (String fileName) {
        BufferedReader br = null;
        InputStream is = FileReader.class.getClassLoader().getResourceAsStream(fileName);
        ArrayList<CityInfo> cityInfo = new ArrayList<CityInfo>();
        ArrayList<CostEdge> edgeInfo = new ArrayList<CostEdge>();
        
        try {
            br = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = br.readLine()) != null) {
                try {
                	String[] mainString = line.split(":");
                	if (mainString.length > 0) {
                		String originCity = mainString[0].trim();
                		CityInfo curCityInfo = new CityInfo();
                		CostEdge curCostEdge = new CostEdge();
                		
                		curCityInfo.setCityName(originCity);
                		curCostEdge.setStartCity(curCityInfo);
                		
                		for (int stringCounter = 1; stringCounter < mainString.length; stringCounter++) {
                			String[] destinationArray = mainString[stringCounter].split(",");
                			if (destinationArray.length > 0) {
                				CityInfo destination = new CityInfo();
                				destination.setCityName(destinationArray[0].trim());
                				curCostEdge.setEndCity(destination);
                				curCostEdge.setCost(Float.parseFloat(destinationArray[1].trim()));
                				curCostEdge.setConnections(destinationArray[2].trim());
                				edgeInfo.add(curCostEdge);
                			}
                		}
                		
                		cityInfo.add(curCityInfo);
                	}
                } catch (Exception ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println("IO Exception Occurred - " + ex.getLocalizedMessage());
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        listOfCities = new CityInfo[cityInfo.size()];
        listOfCities = cityInfo.toArray(listOfCities);
        listOfCost = new CostEdge[edgeInfo.size()];
        listOfCost = edgeInfo.toArray(listOfCost);
        matrixTravel = new TravelMatrix(listOfCities.length);
        
        // Parsing the Travel Matrix... dum dum da... :)
        
        for (CostEdge curEdgeInfo : edgeInfo) {
        	int originValue = CityInfo.CitySet.getCityOrdinal(curEdgeInfo.getStartCity().getCityName());
        	int endValue = CityInfo.CitySet.getCityOrdinal(curEdgeInfo.getEndCity().getCityName());
        	
        	matrixTravel.travelMatrix[originValue][endValue] = curEdgeInfo.getCost();
        }
	}
	
	public static void main(String[] args) {

	}

	public CityInfo[] getListOfCities() {
		return listOfCities;
	}

	public void setListOfCities(CityInfo[] listOfCities) {
		this.listOfCities = listOfCities;
	}

	public TravelMatrix getMatrixTravel() {
		return matrixTravel;
	}

	public void setMatrixTravel(TravelMatrix matrixTravel) {
		this.matrixTravel = matrixTravel;
	}

	public CostEdge[] getListOfCost() {
		return listOfCost;
	}

	public void setListOfCost(CostEdge[] listOfCost) {
		this.listOfCost = listOfCost;
	}

}
