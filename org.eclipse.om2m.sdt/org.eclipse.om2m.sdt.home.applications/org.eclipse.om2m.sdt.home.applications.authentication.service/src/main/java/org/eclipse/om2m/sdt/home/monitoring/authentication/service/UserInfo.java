package org.eclipse.om2m.sdt.home.monitoring.authentication.service;

public class UserInfo {
	
	private String userId;
	private String firstName;
	private String lastName;
	
	public UserInfo(String id) {
		this.userId = id;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String clientId) {
		this.userId = clientId;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "<UserInfo clientId=" + userId 
				+ " firstName=" + firstName + " lastName=" + lastName + "/>";
	}

}
