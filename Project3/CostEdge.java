package Project3;

import java.util.ArrayList;

public class CostEdge {

	private CityInfo startCity;
	private CityInfo endCity;
	private float cost;
	private String connections; // This item will only hold airport code

	public CityInfo getStartCity() {
		return startCity;
	}
	public void setStartCity(CityInfo startCity) {
		this.startCity = startCity;
	}
	public CityInfo getEndCity() {
		return endCity;
	}
	public void setEndCity(CityInfo endCity) {
		this.endCity = endCity;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public String getConnections() {
		return connections;
	}
	public void setConnections(String connections) {
		this.connections = connections;
	}
	
	@Override
	public String toString() {
		return "Starting at " + getStartCity().getCityName() + " ending at " + getEndCity().getCityName() + " with cost of " + getCost() + ".";
	}
}
