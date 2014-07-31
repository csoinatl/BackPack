package Project3;

import java.text.DecimalFormat;
import java.util.ArrayList;
/**
 * CS6423 Algorithmic Processes
 * Summer 2014
 * Project 3: CostEdge representation of an edge between cities
 * Daniel Kerr and Charles So
 * Date: 08/01/2014
 * File: CityEdge.java
 */
public class CostEdge {

	private CityInfo startCity;
	private CityInfo endCity;
	private float cost;
	private String carrier; 

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
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String connections) {
		this.carrier = connections;
	}
	
	@Override
	public String toString() {
                DecimalFormat df = new DecimalFormat("#,###,###,##0.00");
		return getStartCity().getCityName() + " <-----> " + getEndCity().getCityName() + " with cost of " + df.format(getCost()) + ".";
	}
}
