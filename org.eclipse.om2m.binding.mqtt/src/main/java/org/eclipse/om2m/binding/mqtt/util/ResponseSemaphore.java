package org.eclipse.om2m.binding.mqtt.util;

import java.util.concurrent.Semaphore;

import org.eclipse.om2m.commons.resource.ResponsePrimitive;

public class ResponseSemaphore {

	private Semaphore semaphore;
	private ResponsePrimitive responsePrimitive;

	public ResponseSemaphore(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public Semaphore getSemaphore() {
		return semaphore;
	}

	public void setSemaphore(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public ResponsePrimitive getResponsePrimitive() {
		return responsePrimitive;
	}

	public void setResponsePrimitive(ResponsePrimitive responsePrimitive) {
		this.responsePrimitive = responsePrimitive;
	}

}
