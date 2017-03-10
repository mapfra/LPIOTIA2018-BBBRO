package org.eclipse.om2m.persistence.mongodb;

import org.eclipse.om2m.persistence.service.DBTransaction;

public class DBTransactionImpl implements DBTransaction {
	
	private boolean childToBeLoaded = true;

	public DBTransactionImpl() {
	}

	@Override
	public void open() {
	}

	@Override
	public void commit() {
	}

	@Override
	public void close() {
	}

	@Override
	public void clear() {
	}

	@Override
	public void lock(Object object) {
	}

	@Override
	public void unlock(Object object) {
	}

	public boolean isChildToBeLoaded() {
		return childToBeLoaded;
	}

	public void setChildToBeLoaded(boolean childToBeLoaded) {
		this.childToBeLoaded = childToBeLoaded;
	}
	
	

}
