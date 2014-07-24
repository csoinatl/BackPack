package Project3;

/**
 *
 * @author KerrDa
 */
public class DjikstraRow {
    private CityInfo nodeIdentity;
    private CityInfo predicessorNode;
    private float currentCost;
    
    public DjikstraRow(){
        this.nodeIdentity = null;
        this.predicessorNode = null;
        this.currentCost = Float.MAX_VALUE;
    }
    
    public DjikstraRow(CityInfo nodeIdentity, CityInfo predecessorNode, float currentCost){
        this.nodeIdentity = nodeIdentity;
        this.predicessorNode = predicessorNode;
        this.currentCost = currentCost;
    }

    /**
     * @return the nodeIdentity
     */
    public CityInfo getNodeIdentity() {
        return nodeIdentity;
    }

    /**
     * @param nodeIdentity the nodeIdentity to set
     */
    public void setNodeIdentity(CityInfo nodeIdentity) {
        this.nodeIdentity = nodeIdentity;
    }

    /**
     * @return the predicessorNode
     */
    public CityInfo getPredicessorNode() {
        return predicessorNode;
    }

    /**
     * @param predicessorNode the predicessorNode to set
     */
    public void setPredicessorNode(CityInfo predicessorNode) {
        this.predicessorNode = predicessorNode;
    }

    /**
     * @return the currentCost
     */
    public float getCurrentCost() {
        return currentCost;
    }

    /**
     * @param currentCost the currentCost to set
     */
    public void setCurrentCost(float currentCost) {
        this.currentCost = currentCost;
    }
    
    
}
