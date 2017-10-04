/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.netatmo.model;

public class Event {

	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String TIME = "time";
	public static final String CAMERA_ID = "camera_id";
	public static final String PERSON_ID = "person_id";
	
	private final String id;
	private final String type;
	private final Long time;
	private final String cameraId;
	private final String personId;
	
	public Event(final String pId, final String pType, final Long pTime, final String pCameraId, final String pPersonId) {
		 id = pId;
		 type = pType;
		 time = pTime;
		 cameraId = pCameraId;
		 personId = pPersonId;
	}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public Long getTime() {
		return time;
	}

	public String getCameraId() {
		return cameraId;
	}

	public String getPersonId() {
		return personId;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Event(id=");
		sb.append(id);
		sb.append(", type=");
		sb.append(type);
		sb.append(", time=");
		sb.append(time);
		sb.append(", cameraId=");
		sb.append(cameraId);
		sb.append(", personId=");
		sb.append(personId);
		sb.append(")");
		
		return sb.toString();
	}
	
}
