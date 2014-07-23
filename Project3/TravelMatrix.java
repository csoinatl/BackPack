package Project3;

public class TravelMatrix {

	public float[][] travelMatrix;

	public TravelMatrix() {
		travelMatrix = new float[13][13];
	}
	
	public TravelMatrix(int itemCount) {
		travelMatrix = new float[itemCount][itemCount];
	}
	
	public float[][] getTravelMatrix() {
		return travelMatrix;
	}

	public void setTravelMatrix(float[][] travelMatrix) {
		this.travelMatrix = travelMatrix;
	}
}
