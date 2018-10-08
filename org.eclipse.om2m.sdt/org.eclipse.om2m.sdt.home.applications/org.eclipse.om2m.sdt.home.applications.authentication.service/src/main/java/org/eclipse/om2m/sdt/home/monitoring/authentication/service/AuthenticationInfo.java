package org.eclipse.om2m.sdt.home.monitoring.authentication.service;

public class AuthenticationInfo {
	
	private String clientId;
	private String accessToken;
	
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("AuthenticationInfo(");
		sb.append("clientId:").append(clientId).append(",");
		sb.append("accessToken:").append(accessToken);
		sb.append(")");
		return super.toString();
	}

}
