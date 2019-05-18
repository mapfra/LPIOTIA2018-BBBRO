/*******************************************************************************
 * Copyright (c) 2014 - 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    BAREAU Cyrille <cyrille.bareau@orange.com>, 
 *    BONNARDEL Gregory <gbonnardel.ext@orange.com>, 
 *******************************************************************************/
package org.eclipse.om2m.persistence.mongodb;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class DBTransactionImpl implements DBTransaction {
	
	private static final Log LOGGER = LogFactory.getLog(DBTransactionImpl.class);

	private static final Set<String> GLOBAL_LOCKED_OBJECTS = new HashSet<>();

	private final UUID uuid;
	private final Set<String> lockedObjects;

	private boolean childToBeLoaded = true;

	public DBTransactionImpl() {
		uuid = UUID.randomUUID();
		lockedObjects = new HashSet<String>();
	}

	@Override
	public void open() {
		// nothing to do
	}

	@Override
	public void commit() {
		LOGGER.info("commit() for transaction " + uuid);
		Set<String> currentLockedObjects = new HashSet<String>();
		synchronized (lockedObjects) {
			currentLockedObjects.addAll(lockedObjects);
		}
		
		for(String lockedResourceId : currentLockedObjects) {
			unlock(lockedResourceId);
		}
	}

	@Override
	public void close() {
	}

	@Override
	public void clear() {
	}

	@Override
	public void lock(Object object) {
		if (object == null)
			// does nothing ?
			throw new RuntimeException("Null object");

		LOGGER.debug("lock " + object);
		if (! (object instanceof ResourceEntity))
			// does nothing ?
			throw new RuntimeException("Not a ResourceEntity: " + object);
		
		// handle lock
		String resId = ((ResourceEntity) object).getResourceID();
		LOGGER.info("request lock for " + resId + " by transaction " + uuid);

		// add global lock (blocking call for 10s max
		if (addGlobalLock(resId)) {
			synchronized (lockedObjects) {
				lockedObjects.add(resId);
			}
		} else {
			throw new RuntimeException("unable to acquire lock for resource " 
					+ resId + " by transaction " + uuid);
		}
	}

	@Override
	public void unlock(Object object) {
		LOGGER.info("unlock object=" + object + " for transaction " + uuid);
		if (object instanceof ResourceEntity) {
			unlock(((ResourceEntity) object).getResourceID());
		}
	}
	
	private void unlock(String resId) {
		LOGGER.debug("try to unlock " + resId + " by transaction " + uuid);
		synchronized (lockedObjects) {
			 if (lockedObjects.remove(resId)) {
				 removeGlobalLock(resId);
			 }
		}
	}

	public boolean isChildToBeLoaded() {
		return childToBeLoaded;
	}

	public void setChildToBeLoaded(boolean childToBeLoaded) {
		this.childToBeLoaded = childToBeLoaded;
	}

	/**
	 * Check if a global lock exists for the provided resourceId
	 * 
	 * @param resId
	 * @return true if a lock exists for this resourceId
	 * /
	private static boolean isExistingLock(final String resId) {
		synchronized (GLOBAL_LOCKED_OBJECTS) {
			return GLOBAL_LOCKED_OBJECTS.contains(resId);
		}
	}*/

	private synchronized static boolean addGlobalLock(final String resId) {
		synchronized (GLOBAL_LOCKED_OBJECTS) {
			long initialTime = System.currentTimeMillis();
			while (System.currentTimeMillis() < initialTime + 10000) {
				if (GLOBAL_LOCKED_OBJECTS.contains(resId)) {
					try {
						GLOBAL_LOCKED_OBJECTS.wait(1000);
					} catch (InterruptedException e) {
						return false;
					}
				}

				if (!GLOBAL_LOCKED_OBJECTS.contains(resId)) {
					GLOBAL_LOCKED_OBJECTS.add(resId);
					return true;
				}
			}
		}
		return false;
	}

	private synchronized static void removeGlobalLock(final String resId) {
		synchronized (GLOBAL_LOCKED_OBJECTS) {
			GLOBAL_LOCKED_OBJECTS.remove(resId);
			GLOBAL_LOCKED_OBJECTS.notifyAll();
		}
	}

}
