/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.netatmo.model;

import java.util.Date;

public class Person {
	
	public static final String ID = "id";
	public static final String LAST_SEEN = "last_seen";
	public static final String PSEUDO ="pseudo";
	public static final String OUT_OF_SIGHT = "out_of_sight";
	
	private final String id;
	private final String pseudo;
	private Long lastSeen;
	private Boolean outOfSight;
	
	public Person(final String pId, final String pPseudo) {
		id = pId;
		pseudo = pPseudo;
	}

	public Long getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(Long lastSeen) {
		this.lastSeen = lastSeen;
	}

	public Boolean getOutOfSight() {
		return outOfSight;
	}

	public void setOutOfSight(Boolean outOfSight) {
		this.outOfSight = outOfSight;
	}

	public String getId() {
		return id;
	}

	public String getPseudo() {
		return pseudo;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Person(id=");
		sb.append(id);
		sb.append(", pseudo=");
		sb.append(pseudo);
		sb.append(", lastSeen=");
		sb.append(new Date(lastSeen*1000l));
		sb.append(", outOfSight=");
		sb.append(outOfSight);
		sb.append(")");
		return sb.toString();
	}
	

}
