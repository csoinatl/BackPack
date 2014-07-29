package Project3;
/**
 * CS6423 Algorithmic Processes
 * Summer 2014
 * Project 3: TravelMatrix adjacency matrix that hold the edge-weight data
 * Daniel Kerr and Charles So
 * Date: 08/01/2014
 * File: TravelMatrix.java
 */
public class TravelMatrix {

	public float[][] travelMatrix;
        //Easy way to store carrier info
        public String[][] carrierMatrix;

	public TravelMatrix() {
		travelMatrix = new float[13][13];
                carrierMatrix = new String[13][13];
	}
	
	public TravelMatrix(int itemCount) {
		travelMatrix = new float[itemCount][itemCount];
                carrierMatrix = new String[itemCount][itemCount];
	}
	
	public float[][] getTravelMatrix() {
		return travelMatrix;
	}

	public void setTravelMatrix(float[][] travelMatrix) {
		this.travelMatrix = travelMatrix;
	}

    /**
     * @return the carrierMatrix
     */
    public String[][] getCarrierMatrix() {
        return carrierMatrix;
    }

    /**
     * @param carrierMatrix the carrierMatrix to set
     */
    public void setCarrierMatrix(String[][] carrierMatrix) {
        this.carrierMatrix = carrierMatrix;
    }
}
