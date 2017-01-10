/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.netatmo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home {
	
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String PERSONS = "persons";
	public static final String CAMERAS = "cameras";
	public static final String EVENTS = "events";
	
	private final String id;
	private final String name;
	private final Map<String/*id*/ , Person> persons;
	private final Map<String /*id*/, WelcomeCamera> cameras;
	private final List<Event> events;
	
	public Home(final String pId, final String pName) {
		id = pId;
		name = pName;
		
		persons = new HashMap<>();
		cameras = new HashMap<>();
		events = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public Person getPerson(String personId) {
		synchronized (persons) {
			return persons.get(personId);
		}
	}
	
	public Map<String, Person> getPersons() {
		Map<String, Person> toBeReturned = new HashMap<>();
		synchronized (persons) {
			toBeReturned.putAll(persons);
		}
		return toBeReturned;
	}
	
	public boolean addOrUpdatePerson(Person person) {
		synchronized (persons) {
			Person existingPerson = persons.get(person.getId());
			if (existingPerson != null) {
				// update 
				existingPerson.setLastSeen(person.getLastSeen());
				existingPerson.setOutOfSight(person.getOutOfSight());
				return true;
			} else { 
				// add
				persons.put(person.getId(), person);
				return false;
			}
		}
	}
	
	public void removePerson(String personId) {
		synchronized (persons) {
			persons.remove(personId);
		}
	}
	
	public boolean addOrUpdateCamera(WelcomeCamera pCamera) {
		synchronized (cameras) {
			WelcomeCamera currentCamera = cameras.get(pCamera.getId());
			if (currentCamera != null) {
				// update case
				currentCamera.setAlimOk(pCamera.getAlimOk());
				currentCamera.setIsOn(pCamera.getIsOn());
				currentCamera.setSdOk(pCamera.getSdOk());
				currentCamera.setVpnUrl(pCamera.getVpnUrl());
				return true;
			} else {
				// add case
				cameras.put(pCamera.getId(), pCamera);
				return false;
			}
		}
	}
	
	public Map<String, WelcomeCamera> getCameras() {
		Map<String, WelcomeCamera> toBeReturned = new HashMap<>();
		synchronized (cameras) {
			toBeReturned.putAll(cameras);
		}
		return toBeReturned;
	}
	
	public WelcomeCamera getCamera(String cameraId) {
		synchronized (cameras) {
			return cameras.get(cameraId);
		}
	}
	
	public List<Event> getEvents() {
		List<Event> toBeReturned = new ArrayList<>();
		synchronized (events) {
			toBeReturned.addAll(events);
		}
		return toBeReturned;
	}
	
	public void setEvents(List<Event> toBeReplacedWith) {
		synchronized (events) {
			events.clear();
			events.addAll(toBeReplacedWith);
		}
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("Home(id=").append(id).append(", name=").append(name).append(", persons=[");
		Map<String, Person> ps = getPersons();
		for(Person p : ps.values()) {
			sb.append(p.toString()).append(",");
		}
		sb.append("], cameras=[");
		Map<String, WelcomeCamera> cs = getCameras();
		for(WelcomeCamera c: cs.values()) {
			sb.append(c.toString()).append(",");
		}
		sb.append("], events=[");
		List<Event> es = getEvents();
		for(Event e : es) {
			sb.append(e.toString()).append(",");
		}
		sb.append("])");
		return sb.toString();
	}

}
