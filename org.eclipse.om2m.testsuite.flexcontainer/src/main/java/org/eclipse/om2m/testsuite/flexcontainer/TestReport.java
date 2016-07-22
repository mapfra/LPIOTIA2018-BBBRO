/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.testsuite.flexcontainer;

public class TestReport {

	public enum Status {
		OK, KO, UNKNOW
	};

	private final String testMethod;

	private final String message;

	private final Status status;

	private final Exception e;

	public TestReport(String pTestMethod, Status pStatus, String pMessage, Exception pE) {
		this.testMethod = pTestMethod;
		this.message = pMessage;
		this.status = pStatus;
		this.e = pE;
	}

	public String getTestMethod() {
		return testMethod;
	}

	public String getMessage() {
		return message;
	}

	public Status getStatus() {
		return status;
	}
	
	public Exception getException() {
		return e;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\t ");
		sb.append(testMethod);
		sb.append(" - ");
		sb.append(status);
		if (status == Status.KO) {
			if (message != null) {
				sb.append("\n\t\tMessage:");
				sb.append(message);
			}
			if (e != null) {
				sb.append("\n\t\tException:");
				sb.append(e.getMessage());
				sb.append(e);
			}
		}
		return sb.toString();
	}

}
