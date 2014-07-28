package Project3.Part1;

import Project3.CityData;
import Project3.CityInfo;
import Project3.CostEdge;
import Project3.DjikstraRow;
import Project3.TravelMatrix;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LeastCostPath {

	private CityInfo[] cityInfo; 
	private float[][] costMatrix;
        private String[][] carrierMatrix;
        
        public LeastCostPath(){
            CityData newCityData = new CityData("routes.txt");
            
            cityInfo = newCityData.getListOfCities();
            costMatrix = newCityData.getMatrixTravel().getTravelMatrix();
            carrierMatrix = newCityData.getMatrixTravel().getCarrierMatrix();
            
            for(CityInfo currentCity : cityInfo){
                
                String startCity = currentCity.getCityName();
		int startCityCount = CityInfo.CitySet.getCityOrdinal(startCity);
                Queue<CostEdge> edgeList = new LinkedList<CostEdge>();
                
                DjikstraRow[] leastCost = new DjikstraRow[cityInfo.length];
                for(int i = 0; i < leastCost.length; i++){
                    leastCost[i] = new DjikstraRow();
                }
                leastCost[startCityCount].setCurrentCost(0.0f);
                leastCost[startCityCount].setNodeIdentity(currentCity);
                leastCost[startCityCount].setPredicessorNode(null);
                
                //open initial cities:
                openCity(edgeList, currentCity, cityInfo);
                //until all edges are processed:
                while(!edgeList.isEmpty()){
                    CostEdge currEdge = edgeList.poll();
                    CityInfo predicessor = currEdge.getStartCity();
                    CityInfo cityToUpdate = currEdge.getEndCity();
                    int cityIndex = CityInfo.CitySet.getCityOrdinal(cityToUpdate.getCityName());
                    float costSegment = currEdge.getCost();
                    if(leastCost[cityIndex].getNodeIdentity() == null){
                        // If null, this is the first time this city has been updated, need 
                        // to add this city's outgoing edges to the edgeList
                        openCity(edgeList, cityToUpdate, cityInfo);
                        leastCost[cityIndex].setNodeIdentity(cityToUpdate);
                        leastCost[cityIndex].setPredicessorNode(predicessor);
                        leastCost[cityIndex].setCurrentCost(costSegment + getPredicessorCost(predicessor, leastCost));
                    }
                    else{
                        float totalCost = costSegment + getPredicessorCost(predicessor, leastCost);
                        if(totalCost < leastCost[cityIndex].getCurrentCost()){
                            leastCost[cityIndex].setPredicessorNode(predicessor);
                            leastCost[cityIndex].setCurrentCost(totalCost);
                        }
                    }
                }
                printLeastCost(leastCost, currentCity);
                
            }
        }
	
        public void openCity(Queue<CostEdge> edgeList, CityInfo startCity, CityInfo[] cityInfo){
            for(CityInfo connectedCity : cityInfo){
                int startIndex = CityInfo.CitySet.getCityOrdinal(startCity.getCityName());
                int endIndex = CityInfo.CitySet.getCityOrdinal(connectedCity.getCityName());
                if(startIndex != endIndex && costMatrix[startIndex][endIndex] >= 0){
                    CostEdge newEdge = new CostEdge();
                    newEdge.setStartCity(startCity);
                    newEdge.setEndCity(connectedCity);
                    newEdge.setCost(costMatrix[startIndex][endIndex]);
                    newEdge.setCarrier(carrierMatrix[startIndex][endIndex]);
                    edgeList.add(newEdge);
                }
            }
           
        }
        
        public float getPredicessorCost(CityInfo predicessor, DjikstraRow[] leastCost){
                int predIndex = CityInfo.CitySet.getCityOrdinal(predicessor.getCityName());
                if(null == leastCost[predIndex].getPredicessorNode()){
                    return leastCost[predIndex].getCurrentCost();
                }
                else{
                    return leastCost[predIndex].getCurrentCost() + getPredicessorCost(leastCost[predIndex].getPredicessorNode(), leastCost);
                }
            }
        
        public void printLeastCost(DjikstraRow[] leastCost, CityInfo startingCity){
            DecimalFormat df = new DecimalFormat("#,###,###,##0.00");
            StringBuilder output = new StringBuilder();
            output.append("The least cost path FROM ");
            output.append(startingCity.getCityName());
            output.append("\n");
            for(DjikstraRow city : leastCost){
                if(!city.getNodeIdentity().getCityCode().equals(startingCity.getCityCode())){
                    output.append("TO: ");
                    output.append(city.getNodeIdentity().getCityName());
                    output.append(" is ");
                    output.append(df.format(city.getCurrentCost()));
                    output.append(" through the cities: ");
                    getPath(output, city.getNodeIdentity(), leastCost);
                    output.append("end!\n");
                }
            }
            output.append("*-----City Done-----*\n");
            System.out.println(output.toString());
        }
        
        public void getPath(StringBuilder output, CityInfo endCity, DjikstraRow[] leastCost){
            int endIndex = CityInfo.CitySet.getCityOrdinal(endCity.getCityName());
            if(leastCost[endIndex].getPredicessorNode() == null){
                output.append(leastCost[endIndex].getNodeIdentity().getCityName());
                output.append(" ->");
            }
            else{
                getPath(output, leastCost[endIndex].getPredicessorNode(), leastCost);
                output.append(leastCost[endIndex].getNodeIdentity().getCityName());
                output.append(" ->");
            }
        }
        
	public static void main(String[] args) {
            LeastCostPath testPath = new LeastCostPath();
	}

}
