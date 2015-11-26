package org.eclipse.om2m.ipe.sample.model;

public class Lamp {

    /** Default Lamps location */
    public final static String LOCATION = "Home";
    /** Toggle */
    public final static String TOGGLE = "toggle";
    /** Default Lamps type */
    public final static String TYPE = "LAMP";
    /** Lamp state */
    private boolean state = false;
    /** Lamp ID */
    private String lampId;
    
    public Lamp(String lampId, boolean initState){
    	this.lampId = lampId;
    	this.state = initState;
    }
    
	/**
	 * @return the state
	 */
	public boolean getState() {
		return state;
	}
	
	/**
	 * @param state the state to set
	 */
	public void setState(boolean state) {
		this.state = state;
	}

	/**
	 * @return the lampId
	 */
	public String getLampId() {
		return lampId;
	}

	/**
	 * @param lampId the lampId to set
	 */
	public void setLampId(String lampId) {
		this.lampId = lampId;
	}
	
}
