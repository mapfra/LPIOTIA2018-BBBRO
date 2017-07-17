/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt.testsuite.subscription;

import java.util.Date;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.sdt.DataPoint;

public class ReceivedNotification {
	// all cases
	private Date date;
	
	// case FlexContainer
	private AbstractFlexContainer abstractFlexContainer;
	
	// case SDT
	private DataPoint dataPoint;
	private Object value;

	public ReceivedNotification(final AbstractFlexContainer pAbstractFlexContainer, final Date pDate) {
		this.abstractFlexContainer = pAbstractFlexContainer;
		this.date = pDate;
	}
	
	public ReceivedNotification(final DataPoint pDataPoint, final Object pValue, final Date pDate) {
		this.dataPoint = pDataPoint;
		this.value = pValue;
		this.date = pDate;
	}

	public AbstractFlexContainer getFlexContainer() {
		return abstractFlexContainer;
	}

	public DataPoint getDataPoint() {
		return dataPoint;
	}

	public Object getValue() {
		return value;
	}

	public Date getDate() {
		return date;
	}

	
	
	
}
