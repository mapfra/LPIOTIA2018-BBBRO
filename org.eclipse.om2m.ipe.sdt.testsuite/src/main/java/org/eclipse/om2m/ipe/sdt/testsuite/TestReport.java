/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt.testsuite;

public class TestReport {
	
	public enum State {
		OK,KO
	};
	
	private final String name;
	private String errorMessage;
	private State state;
	private Exception e;
	
	public Exception getException() {
		return e;
	}

	public void setException(Exception e) {
		this.e = e;
	}

	public TestReport(final String pName) {
		this.name = pName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
	
		StringBuffer sb = new StringBuffer();
		sb.append("\t");
		sb.append("name=");
		sb.append(getName());
		sb.append("\t");
		sb.append("status=");
		sb.append(getState());
		sb.append("\t");
		sb.append("errorMessage=");
		
		sb.append(getErrorMessage());
		return sb.toString();
	}

}
