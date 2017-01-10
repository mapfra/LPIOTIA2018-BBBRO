/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.netatmo.model;

import java.util.ArrayList;
import java.util.List;

public class Token {
	
	private final String accessToken;
	private final String refreshToken;
	private final List<String> scopes;
	private final Long expireIn;
	private final long expire;
	private boolean isInvalid = false;
	
	public Token(final String pAccessToken, final String pRefreshToken, final Long pExpireIn) {
		accessToken = pAccessToken;
		refreshToken = pRefreshToken;
		expireIn = pExpireIn;
		scopes = new ArrayList<>();
		expire = System.currentTimeMillis() + expireIn * 1000;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public List<String> getScopes() {
		return scopes;
	}

	public void addScope(String scope) {
		scopes.add(scope);
	}
	
	public Long getExpireIn() {
		return expireIn;
	}
	
	public boolean isValid() {
		
		return (!isInvalid) && (System.currentTimeMillis() < expire);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Token(accessToken=");
		sb.append(accessToken);
		sb.append(", refreshToken=");
		sb.append(refreshToken);
		sb.append(", expireIn=");
		sb.append(expireIn);
		sb.append(")");
		return sb.toString();
	}
	
	public void invalidateToken() {
		isInvalid = true;
	}

}
