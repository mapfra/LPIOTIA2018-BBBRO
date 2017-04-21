package org.eclipse.om2m.sdt.home.smarterkettle.communication;

public class SmarterKettleStatusDescriptor {
	
	private int code;
	private String description;
	
	public SmarterKettleStatusDescriptor(int code, String desc){
		this.code = code;
		this.description = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
