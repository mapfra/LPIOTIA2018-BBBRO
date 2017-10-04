/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.om2m.sdt.events.SDTEventListener;
import org.eclipse.om2m.sdt.events.SDTNotification;
import org.eclipse.om2m.sdt.utils.Activator;
import org.eclipse.om2m.sdt.utils.Logger;

public abstract class ModuleClass extends Element {
	
	static private final Identifiers OWNER = new Identifiers() {
		@Override
		public String getShortName() {
			return "owner";
		}
		@Override
		public String getLongName() {
			return "owner";
		}
		@Override
		public String getDefinition() {
			return "owner";
		}
	};
	
	private Extended extended;
	
	private boolean optional;
	
	private Map<String, Action> actions;

	private Map<String, DataPoint> dataPoints;
	private Map<String, DataPoint> dataPointsByShortDefinitionType;

	private Map<String, Event> events;

	private Map<String, Property> properties;
	
	private Device owner;
	
	ModuleClass(final String id, final Domain domain) {
		super(id);
		if (domain.getModule(id) != null) {
			String msg = "Already a module with name " + id + " in domain " + domain;
			Logger.warning(msg);
			throw new IllegalArgumentException(msg);
		}
		optional = false;
		this.actions = new HashMap<String, Action>();
		this.dataPoints = new HashMap<String, DataPoint>();
		this.dataPointsByShortDefinitionType = new HashMap<String, DataPoint>();
		this.events = new HashMap<String, Event>();
		this.properties = new HashMap<String, Property>();
		domain.addModule(this);
	}

	public String getPid() {
		return getName().replaceAll("\\.", "_");
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(final boolean optional) {
		this.optional = optional;
	}

	public Collection<String> getActionNames() {
		return actions.keySet();
	}

	public Collection<Action> getActions() {
		return actions.values();
	}

	public Action getAction(final String name) {
		return actions.get(name);
	}

	public void addAction(final Action action) {
		if (actions.get(action.getName()) != null) {
			String msg = "Already an action with name " + action.getName() 
					+ " in module " + getName();
			Logger.warning(msg);
			throw new IllegalArgumentException(msg);
		}
		action.setParent((Module) this);
		action.setOwner(owner);
		actions.put(action.getName(), action);
	}

	public void removeAction(final String name) {
		actions.remove(name);
	}

	public Collection<String> getDataPointNames() {
		return dataPoints.keySet();
	}

	public Collection<DataPoint> getDataPoints() {
		return dataPoints.values();
	}

	public DataPoint getDataPoint(final String name) {
		return dataPoints.get(name);
	}
	
	public DataPoint getDataPointByShortName(final String shortDefinitionType) {
		return dataPointsByShortDefinitionType.get(shortDefinitionType);
	}

	public void addDataPoint(final DataPoint dp) {
		if (dataPoints.get(dp.getName()) != null) {
			String msg = "Already a datapoint with name " + dp.getName() + " in module " + getName();
			Logger.warning(msg);
			throw new IllegalArgumentException(msg);
		}
		dp.setParent((Module) this);
		if (dp.getShortDefinitionType() == null) {
			String msg = "Short definition type is null of " + dp.getName() + " in module " + getName();
			Logger.warning(msg);
			throw new IllegalArgumentException(msg);
		}
		dataPoints.put(dp.getName(), dp);
		dataPointsByShortDefinitionType.put(dp.getShortDefinitionType(), dp);
		if (owner!= null) {
			dp.setOwner(owner);
		}
	}

	public void removeDataPoint(final String name) {
		DataPoint dp = dataPoints.remove(name);
		if (dp != null) {
			dataPointsByShortDefinitionType.remove(dp.getShortDefinitionType());
		}
	}

	public Collection<String> getEventNames() {
		return events.keySet();
	}

	public Collection<Event> getEvents() {
		return events.values();
	}

	public Event getEvent(final String name) {
		return events.get(name);
	}

	public void addEvent(final Event evt) {
		try {
			evt.setTimeStamp();
			events.put(evt.getName(), evt);
			Logger.info("New event " + evt + " for module " + this);
			for (DataPoint dp : evt.getDataPoints()) {
				notifyListeners(evt.getName(), evt.getValue(), dp);
			}
		} catch (Exception e) {
			Logger.warning("Error notifying event", e);
		}
	}
	
	private void notifyListeners(String name, Object val, DataPoint dp) {
		if (dataPoints.get(dp.getName()) == null) {
			Logger.warning("Wrong datapoint " + dp + ". Not in module " + this + ". Ignored...");
			return;
		}
		Collection<SDTEventListener> listeners = Activator.getListeners(((Module)this), dp);
		if (listeners.isEmpty()) {
			Logger.info("No listener to be notified");
			return;
		}
		Logger.info("notify " + listeners.size() + " listeners");
		SDTNotification notif = new SDTNotification(name, val, owner, this, dp);
		for (SDTEventListener l : listeners) {
			try {
				if (Activator.isGrantedEventAccess(l, dp))
					l.handleNotification(notif);
				else
					Logger.info("Non authorized listener " + l.getClass());
			} catch (Exception e) {
				Logger.warning("Error handling notification " + notif 
						+ " by listener " + l.getClass(), e);
			}
		}
	}

	public void removeEvent(final String name) {
		events.remove(name);
	}

	public Collection<String> getPropertyNames() {
		return properties.keySet();
	}

	public Collection<Property> getProperties() {
		return properties.values();
	}

	public Property getProperty(final String name) {
		return properties.get(name);
	}
	
	public Property getPropertyByShortName(final String shortDefinitionType) {
		for(Property property : properties.values()) {
			if (property.getShortName().equals(shortDefinitionType)) {
				return property;
			}
		}
		return null;
	}

	public void addProperty(final Property arg) {
		properties.put(arg.getName(), arg);
	}

	public void removeProperty(final String name) {
		properties.remove(name);
	}

	public Extended getExtends() {
		return extended;
	}

	public void setExtends(final String domain, final String clazz) {
		extended = new Extended(domain, clazz);
	}

	@Override
	protected String prettyPrint(String t1) {
		String t2 = t1 + "\t";
		StringBuffer ret = new StringBuffer(t1).append("<ModuleClass name=\"")
				.append(getName()).append("\">");
		if (getDoc() != null) ret.append("\n").append(t2).append(getDoc());
		prettyPrint(ret, properties.values(), "Properties", t2);
		prettyPrint(ret, dataPoints.values(), "Data", t2);
		prettyPrint(ret, actions.values(), "Actions", t2);
		prettyPrint(ret, events.values(), "Events", t2);
		return ret.append("\n").append(t1).append("</ModuleClass>").toString();
	}
	
	void setOwner(Device owner) {
		this.owner = owner;
		addProperty(new Property(OWNER, owner.getPid()));
		for (DataPoint dp : dataPoints.values()) {
			dp.setOwner(owner);
		}
		for (Action a : actions.values()) {
			a.setOwner(owner);
		}
	}

	public Device getOwner() {
		return owner;
	}
	
    protected void finalize() throws Throwable {
    	Logger.info("finalize " + this);
    	actions.clear();
    	dataPoints.clear();
    	properties.clear();
    	events.clear();
    }

}
